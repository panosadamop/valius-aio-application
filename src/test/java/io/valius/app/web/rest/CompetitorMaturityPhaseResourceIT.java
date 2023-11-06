package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.CompetitorMaturityPhase;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.CompetitorMaturityPhaseRepository;
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
 * Integration tests for the {@link CompetitorMaturityPhaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetitorMaturityPhaseResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/competitor-maturity-phases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetitorMaturityPhaseRepository competitorMaturityPhaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitorMaturityPhaseMockMvc;

    private CompetitorMaturityPhase competitorMaturityPhase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorMaturityPhase createEntity(EntityManager em) {
        CompetitorMaturityPhase competitorMaturityPhase = new CompetitorMaturityPhase()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return competitorMaturityPhase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorMaturityPhase createUpdatedEntity(EntityManager em) {
        CompetitorMaturityPhase competitorMaturityPhase = new CompetitorMaturityPhase()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return competitorMaturityPhase;
    }

    @BeforeEach
    public void initTest() {
        competitorMaturityPhase = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetitorMaturityPhase() throws Exception {
        int databaseSizeBeforeCreate = competitorMaturityPhaseRepository.findAll().size();
        // Create the CompetitorMaturityPhase
        restCompetitorMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isCreated());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitorMaturityPhase testCompetitorMaturityPhase = competitorMaturityPhaseList.get(competitorMaturityPhaseList.size() - 1);
        assertThat(testCompetitorMaturityPhase.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCompetitorMaturityPhase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitorMaturityPhase.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCompetitorMaturityPhaseWithExistingId() throws Exception {
        // Create the CompetitorMaturityPhase with an existing ID
        competitorMaturityPhase.setId(1L);

        int databaseSizeBeforeCreate = competitorMaturityPhaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitorMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorMaturityPhaseRepository.findAll().size();
        // set the field null
        competitorMaturityPhase.setValue(null);

        // Create the CompetitorMaturityPhase, which fails.

        restCompetitorMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isBadRequest());

        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorMaturityPhaseRepository.findAll().size();
        // set the field null
        competitorMaturityPhase.setLanguage(null);

        // Create the CompetitorMaturityPhase, which fails.

        restCompetitorMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isBadRequest());

        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompetitorMaturityPhases() throws Exception {
        // Initialize the database
        competitorMaturityPhaseRepository.saveAndFlush(competitorMaturityPhase);

        // Get all the competitorMaturityPhaseList
        restCompetitorMaturityPhaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitorMaturityPhase.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getCompetitorMaturityPhase() throws Exception {
        // Initialize the database
        competitorMaturityPhaseRepository.saveAndFlush(competitorMaturityPhase);

        // Get the competitorMaturityPhase
        restCompetitorMaturityPhaseMockMvc
            .perform(get(ENTITY_API_URL_ID, competitorMaturityPhase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competitorMaturityPhase.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompetitorMaturityPhase() throws Exception {
        // Get the competitorMaturityPhase
        restCompetitorMaturityPhaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetitorMaturityPhase() throws Exception {
        // Initialize the database
        competitorMaturityPhaseRepository.saveAndFlush(competitorMaturityPhase);

        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();

        // Update the competitorMaturityPhase
        CompetitorMaturityPhase updatedCompetitorMaturityPhase = competitorMaturityPhaseRepository
            .findById(competitorMaturityPhase.getId())
            .get();
        // Disconnect from session so that the updates on updatedCompetitorMaturityPhase are not directly saved in db
        em.detach(updatedCompetitorMaturityPhase);
        updatedCompetitorMaturityPhase.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitorMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetitorMaturityPhase.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetitorMaturityPhase))
            )
            .andExpect(status().isOk());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
        CompetitorMaturityPhase testCompetitorMaturityPhase = competitorMaturityPhaseList.get(competitorMaturityPhaseList.size() - 1);
        assertThat(testCompetitorMaturityPhase.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitorMaturityPhase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitorMaturityPhase.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCompetitorMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();
        competitorMaturityPhase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competitorMaturityPhase.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetitorMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();
        competitorMaturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetitorMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();
        competitorMaturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetitorMaturityPhaseWithPatch() throws Exception {
        // Initialize the database
        competitorMaturityPhaseRepository.saveAndFlush(competitorMaturityPhase);

        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();

        // Update the competitorMaturityPhase using partial update
        CompetitorMaturityPhase partialUpdatedCompetitorMaturityPhase = new CompetitorMaturityPhase();
        partialUpdatedCompetitorMaturityPhase.setId(competitorMaturityPhase.getId());

        restCompetitorMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitorMaturityPhase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitorMaturityPhase))
            )
            .andExpect(status().isOk());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
        CompetitorMaturityPhase testCompetitorMaturityPhase = competitorMaturityPhaseList.get(competitorMaturityPhaseList.size() - 1);
        assertThat(testCompetitorMaturityPhase.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCompetitorMaturityPhase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitorMaturityPhase.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCompetitorMaturityPhaseWithPatch() throws Exception {
        // Initialize the database
        competitorMaturityPhaseRepository.saveAndFlush(competitorMaturityPhase);

        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();

        // Update the competitorMaturityPhase using partial update
        CompetitorMaturityPhase partialUpdatedCompetitorMaturityPhase = new CompetitorMaturityPhase();
        partialUpdatedCompetitorMaturityPhase.setId(competitorMaturityPhase.getId());

        partialUpdatedCompetitorMaturityPhase.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitorMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitorMaturityPhase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitorMaturityPhase))
            )
            .andExpect(status().isOk());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
        CompetitorMaturityPhase testCompetitorMaturityPhase = competitorMaturityPhaseList.get(competitorMaturityPhaseList.size() - 1);
        assertThat(testCompetitorMaturityPhase.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitorMaturityPhase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitorMaturityPhase.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCompetitorMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();
        competitorMaturityPhase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competitorMaturityPhase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetitorMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();
        competitorMaturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetitorMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = competitorMaturityPhaseRepository.findAll().size();
        competitorMaturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitorMaturityPhase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitorMaturityPhase in the database
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetitorMaturityPhase() throws Exception {
        // Initialize the database
        competitorMaturityPhaseRepository.saveAndFlush(competitorMaturityPhase);

        int databaseSizeBeforeDelete = competitorMaturityPhaseRepository.findAll().size();

        // Delete the competitorMaturityPhase
        restCompetitorMaturityPhaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, competitorMaturityPhase.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitorMaturityPhase> competitorMaturityPhaseList = competitorMaturityPhaseRepository.findAll();
        assertThat(competitorMaturityPhaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
