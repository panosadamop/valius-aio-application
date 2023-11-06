package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.Industry;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.IndustryRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link IndustryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndustryResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/industries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndustryMockMvc;

    private Industry industry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industry createEntity(EntityManager em) {
        Industry industry = new Industry().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return industry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industry createUpdatedEntity(EntityManager em) {
        Industry industry = new Industry().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return industry;
    }

    @BeforeEach
    public void initTest() {
        industry = createEntity(em);
    }

    @Test
    @Transactional
    void createIndustry() throws Exception {
        int databaseSizeBeforeCreate = industryRepository.findAll().size();
        // Create the Industry
        restIndustryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isCreated());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate + 1);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIndustry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIndustry.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createIndustryWithExistingId() throws Exception {
        // Create the Industry with an existing ID
        industry.setId(1L);

        int databaseSizeBeforeCreate = industryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryRepository.findAll().size();
        // set the field null
        industry.setValue(null);

        // Create the Industry, which fails.

        restIndustryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryRepository.findAll().size();
        // set the field null
        industry.setLanguage(null);

        // Create the Industry, which fails.

        restIndustryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndustries() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industry.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get the industry
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL_ID, industry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(industry.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIndustry() throws Exception {
        // Get the industry
        restIndustryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry
        Industry updatedIndustry = industryRepository.findById(industry.getId()).get();
        // Disconnect from session so that the updates on updatedIndustry are not directly saved in db
        em.detach(updatedIndustry);
        updatedIndustry.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndustry.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIndustry.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, industry.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndustryWithPatch() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry using partial update
        Industry partialUpdatedIndustry = new Industry();
        partialUpdatedIndustry.setId(industry.getId());

        partialUpdatedIndustry.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);

        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndustry.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIndustry.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateIndustryWithPatch() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry using partial update
        Industry partialUpdatedIndustry = new Industry();
        partialUpdatedIndustry.setId(industry.getId());

        partialUpdatedIndustry.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndustry.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIndustry.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, industry.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeDelete = industryRepository.findAll().size();

        // Delete the industry
        restIndustryMockMvc
            .perform(delete(ENTITY_API_URL_ID, industry.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
