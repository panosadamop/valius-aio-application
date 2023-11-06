package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.AttractivenessFactors;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.AttractivenessFactorsRepository;
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

/**
 * Integration tests for the {@link AttractivenessFactorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttractivenessFactorsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/attractiveness-factors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttractivenessFactorsRepository attractivenessFactorsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttractivenessFactorsMockMvc;

    private AttractivenessFactors attractivenessFactors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttractivenessFactors createEntity(EntityManager em) {
        AttractivenessFactors attractivenessFactors = new AttractivenessFactors()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return attractivenessFactors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttractivenessFactors createUpdatedEntity(EntityManager em) {
        AttractivenessFactors attractivenessFactors = new AttractivenessFactors()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return attractivenessFactors;
    }

    @BeforeEach
    public void initTest() {
        attractivenessFactors = createEntity(em);
    }

    @Test
    @Transactional
    void createAttractivenessFactors() throws Exception {
        int databaseSizeBeforeCreate = attractivenessFactorsRepository.findAll().size();
        // Create the AttractivenessFactors
        restAttractivenessFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isCreated());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeCreate + 1);
        AttractivenessFactors testAttractivenessFactors = attractivenessFactorsList.get(attractivenessFactorsList.size() - 1);
        assertThat(testAttractivenessFactors.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAttractivenessFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAttractivenessFactors.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createAttractivenessFactorsWithExistingId() throws Exception {
        // Create the AttractivenessFactors with an existing ID
        attractivenessFactors.setId(1L);

        int databaseSizeBeforeCreate = attractivenessFactorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttractivenessFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = attractivenessFactorsRepository.findAll().size();
        // set the field null
        attractivenessFactors.setValue(null);

        // Create the AttractivenessFactors, which fails.

        restAttractivenessFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = attractivenessFactorsRepository.findAll().size();
        // set the field null
        attractivenessFactors.setLanguage(null);

        // Create the AttractivenessFactors, which fails.

        restAttractivenessFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttractivenessFactors() throws Exception {
        // Initialize the database
        attractivenessFactorsRepository.saveAndFlush(attractivenessFactors);

        // Get all the attractivenessFactorsList
        restAttractivenessFactorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attractivenessFactors.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getAttractivenessFactors() throws Exception {
        // Initialize the database
        attractivenessFactorsRepository.saveAndFlush(attractivenessFactors);

        // Get the attractivenessFactors
        restAttractivenessFactorsMockMvc
            .perform(get(ENTITY_API_URL_ID, attractivenessFactors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attractivenessFactors.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAttractivenessFactors() throws Exception {
        // Get the attractivenessFactors
        restAttractivenessFactorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttractivenessFactors() throws Exception {
        // Initialize the database
        attractivenessFactorsRepository.saveAndFlush(attractivenessFactors);

        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();

        // Update the attractivenessFactors
        AttractivenessFactors updatedAttractivenessFactors = attractivenessFactorsRepository.findById(attractivenessFactors.getId()).get();
        // Disconnect from session so that the updates on updatedAttractivenessFactors are not directly saved in db
        em.detach(updatedAttractivenessFactors);
        updatedAttractivenessFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAttractivenessFactors))
            )
            .andExpect(status().isOk());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
        AttractivenessFactors testAttractivenessFactors = attractivenessFactorsList.get(attractivenessFactorsList.size() - 1);
        assertThat(testAttractivenessFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAttractivenessFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAttractivenessFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();
        attractivenessFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attractivenessFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();
        attractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();
        attractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttractivenessFactorsWithPatch() throws Exception {
        // Initialize the database
        attractivenessFactorsRepository.saveAndFlush(attractivenessFactors);

        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();

        // Update the attractivenessFactors using partial update
        AttractivenessFactors partialUpdatedAttractivenessFactors = new AttractivenessFactors();
        partialUpdatedAttractivenessFactors.setId(attractivenessFactors.getId());

        partialUpdatedAttractivenessFactors.language(UPDATED_LANGUAGE);

        restAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttractivenessFactors))
            )
            .andExpect(status().isOk());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
        AttractivenessFactors testAttractivenessFactors = attractivenessFactorsList.get(attractivenessFactorsList.size() - 1);
        assertThat(testAttractivenessFactors.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAttractivenessFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAttractivenessFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateAttractivenessFactorsWithPatch() throws Exception {
        // Initialize the database
        attractivenessFactorsRepository.saveAndFlush(attractivenessFactors);

        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();

        // Update the attractivenessFactors using partial update
        AttractivenessFactors partialUpdatedAttractivenessFactors = new AttractivenessFactors();
        partialUpdatedAttractivenessFactors.setId(attractivenessFactors.getId());

        partialUpdatedAttractivenessFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttractivenessFactors))
            )
            .andExpect(status().isOk());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
        AttractivenessFactors testAttractivenessFactors = attractivenessFactorsList.get(attractivenessFactorsList.size() - 1);
        assertThat(testAttractivenessFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAttractivenessFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAttractivenessFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();
        attractivenessFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attractivenessFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();
        attractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = attractivenessFactorsRepository.findAll().size();
        attractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attractivenessFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttractivenessFactors in the database
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttractivenessFactors() throws Exception {
        // Initialize the database
        attractivenessFactorsRepository.saveAndFlush(attractivenessFactors);

        int databaseSizeBeforeDelete = attractivenessFactorsRepository.findAll().size();

        // Delete the attractivenessFactors
        restAttractivenessFactorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, attractivenessFactors.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttractivenessFactors> attractivenessFactorsList = attractivenessFactorsRepository.findAll();
        assertThat(attractivenessFactorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
