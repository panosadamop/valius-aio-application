package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.CompetitiveFactors;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.CompetitiveFactorsRepository;
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
 * Integration tests for the {@link CompetitiveFactorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetitiveFactorsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/competitive-factors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetitiveFactorsRepository competitiveFactorsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitiveFactorsMockMvc;

    private CompetitiveFactors competitiveFactors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitiveFactors createEntity(EntityManager em) {
        CompetitiveFactors competitiveFactors = new CompetitiveFactors()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return competitiveFactors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitiveFactors createUpdatedEntity(EntityManager em) {
        CompetitiveFactors competitiveFactors = new CompetitiveFactors()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return competitiveFactors;
    }

    @BeforeEach
    public void initTest() {
        competitiveFactors = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetitiveFactors() throws Exception {
        int databaseSizeBeforeCreate = competitiveFactorsRepository.findAll().size();
        // Create the CompetitiveFactors
        restCompetitiveFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isCreated());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitiveFactors testCompetitiveFactors = competitiveFactorsList.get(competitiveFactorsList.size() - 1);
        assertThat(testCompetitiveFactors.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCompetitiveFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitiveFactors.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCompetitiveFactorsWithExistingId() throws Exception {
        // Create the CompetitiveFactors with an existing ID
        competitiveFactors.setId(1L);

        int databaseSizeBeforeCreate = competitiveFactorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitiveFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitiveFactorsRepository.findAll().size();
        // set the field null
        competitiveFactors.setValue(null);

        // Create the CompetitiveFactors, which fails.

        restCompetitiveFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isBadRequest());

        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitiveFactorsRepository.findAll().size();
        // set the field null
        competitiveFactors.setLanguage(null);

        // Create the CompetitiveFactors, which fails.

        restCompetitiveFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isBadRequest());

        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompetitiveFactors() throws Exception {
        // Initialize the database
        competitiveFactorsRepository.saveAndFlush(competitiveFactors);

        // Get all the competitiveFactorsList
        restCompetitiveFactorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitiveFactors.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getCompetitiveFactors() throws Exception {
        // Initialize the database
        competitiveFactorsRepository.saveAndFlush(competitiveFactors);

        // Get the competitiveFactors
        restCompetitiveFactorsMockMvc
            .perform(get(ENTITY_API_URL_ID, competitiveFactors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competitiveFactors.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompetitiveFactors() throws Exception {
        // Get the competitiveFactors
        restCompetitiveFactorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetitiveFactors() throws Exception {
        // Initialize the database
        competitiveFactorsRepository.saveAndFlush(competitiveFactors);

        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();

        // Update the competitiveFactors
        CompetitiveFactors updatedCompetitiveFactors = competitiveFactorsRepository.findById(competitiveFactors.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitiveFactors are not directly saved in db
        em.detach(updatedCompetitiveFactors);
        updatedCompetitiveFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitiveFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetitiveFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetitiveFactors))
            )
            .andExpect(status().isOk());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
        CompetitiveFactors testCompetitiveFactors = competitiveFactorsList.get(competitiveFactorsList.size() - 1);
        assertThat(testCompetitiveFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitiveFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitiveFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCompetitiveFactors() throws Exception {
        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();
        competitiveFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitiveFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competitiveFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetitiveFactors() throws Exception {
        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();
        competitiveFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitiveFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetitiveFactors() throws Exception {
        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();
        competitiveFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitiveFactorsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetitiveFactorsWithPatch() throws Exception {
        // Initialize the database
        competitiveFactorsRepository.saveAndFlush(competitiveFactors);

        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();

        // Update the competitiveFactors using partial update
        CompetitiveFactors partialUpdatedCompetitiveFactors = new CompetitiveFactors();
        partialUpdatedCompetitiveFactors.setId(competitiveFactors.getId());

        partialUpdatedCompetitiveFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitiveFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitiveFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitiveFactors))
            )
            .andExpect(status().isOk());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
        CompetitiveFactors testCompetitiveFactors = competitiveFactorsList.get(competitiveFactorsList.size() - 1);
        assertThat(testCompetitiveFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitiveFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitiveFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCompetitiveFactorsWithPatch() throws Exception {
        // Initialize the database
        competitiveFactorsRepository.saveAndFlush(competitiveFactors);

        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();

        // Update the competitiveFactors using partial update
        CompetitiveFactors partialUpdatedCompetitiveFactors = new CompetitiveFactors();
        partialUpdatedCompetitiveFactors.setId(competitiveFactors.getId());

        partialUpdatedCompetitiveFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitiveFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitiveFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitiveFactors))
            )
            .andExpect(status().isOk());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
        CompetitiveFactors testCompetitiveFactors = competitiveFactorsList.get(competitiveFactorsList.size() - 1);
        assertThat(testCompetitiveFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitiveFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitiveFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCompetitiveFactors() throws Exception {
        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();
        competitiveFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitiveFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competitiveFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetitiveFactors() throws Exception {
        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();
        competitiveFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitiveFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetitiveFactors() throws Exception {
        int databaseSizeBeforeUpdate = competitiveFactorsRepository.findAll().size();
        competitiveFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitiveFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitiveFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitiveFactors in the database
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetitiveFactors() throws Exception {
        // Initialize the database
        competitiveFactorsRepository.saveAndFlush(competitiveFactors);

        int databaseSizeBeforeDelete = competitiveFactorsRepository.findAll().size();

        // Delete the competitiveFactors
        restCompetitiveFactorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, competitiveFactors.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitiveFactors> competitiveFactorsList = competitiveFactorsRepository.findAll();
        assertThat(competitiveFactorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
