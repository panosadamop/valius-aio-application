package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.CompetitorCompetitivePosition;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.CompetitorCompetitivePositionRepository;
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
 * Integration tests for the {@link CompetitorCompetitivePositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetitorCompetitivePositionResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/competitor-competitive-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetitorCompetitivePositionRepository competitorCompetitivePositionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitorCompetitivePositionMockMvc;

    private CompetitorCompetitivePosition competitorCompetitivePosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorCompetitivePosition createEntity(EntityManager em) {
        CompetitorCompetitivePosition competitorCompetitivePosition = new CompetitorCompetitivePosition()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return competitorCompetitivePosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorCompetitivePosition createUpdatedEntity(EntityManager em) {
        CompetitorCompetitivePosition competitorCompetitivePosition = new CompetitorCompetitivePosition()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return competitorCompetitivePosition;
    }

    @BeforeEach
    public void initTest() {
        competitorCompetitivePosition = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetitorCompetitivePosition() throws Exception {
        int databaseSizeBeforeCreate = competitorCompetitivePositionRepository.findAll().size();
        // Create the CompetitorCompetitivePosition
        restCompetitorCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isCreated());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitorCompetitivePosition testCompetitorCompetitivePosition = competitorCompetitivePositionList.get(
            competitorCompetitivePositionList.size() - 1
        );
        assertThat(testCompetitorCompetitivePosition.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCompetitorCompetitivePosition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitorCompetitivePosition.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCompetitorCompetitivePositionWithExistingId() throws Exception {
        // Create the CompetitorCompetitivePosition with an existing ID
        competitorCompetitivePosition.setId(1L);

        int databaseSizeBeforeCreate = competitorCompetitivePositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitorCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorCompetitivePositionRepository.findAll().size();
        // set the field null
        competitorCompetitivePosition.setValue(null);

        // Create the CompetitorCompetitivePosition, which fails.

        restCompetitorCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isBadRequest());

        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorCompetitivePositionRepository.findAll().size();
        // set the field null
        competitorCompetitivePosition.setLanguage(null);

        // Create the CompetitorCompetitivePosition, which fails.

        restCompetitorCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isBadRequest());

        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompetitorCompetitivePositions() throws Exception {
        // Initialize the database
        competitorCompetitivePositionRepository.saveAndFlush(competitorCompetitivePosition);

        // Get all the competitorCompetitivePositionList
        restCompetitorCompetitivePositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitorCompetitivePosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getCompetitorCompetitivePosition() throws Exception {
        // Initialize the database
        competitorCompetitivePositionRepository.saveAndFlush(competitorCompetitivePosition);

        // Get the competitorCompetitivePosition
        restCompetitorCompetitivePositionMockMvc
            .perform(get(ENTITY_API_URL_ID, competitorCompetitivePosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competitorCompetitivePosition.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompetitorCompetitivePosition() throws Exception {
        // Get the competitorCompetitivePosition
        restCompetitorCompetitivePositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetitorCompetitivePosition() throws Exception {
        // Initialize the database
        competitorCompetitivePositionRepository.saveAndFlush(competitorCompetitivePosition);

        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();

        // Update the competitorCompetitivePosition
        CompetitorCompetitivePosition updatedCompetitorCompetitivePosition = competitorCompetitivePositionRepository
            .findById(competitorCompetitivePosition.getId())
            .get();
        // Disconnect from session so that the updates on updatedCompetitorCompetitivePosition are not directly saved in db
        em.detach(updatedCompetitorCompetitivePosition);
        updatedCompetitorCompetitivePosition.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitorCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetitorCompetitivePosition.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetitorCompetitivePosition))
            )
            .andExpect(status().isOk());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
        CompetitorCompetitivePosition testCompetitorCompetitivePosition = competitorCompetitivePositionList.get(
            competitorCompetitivePositionList.size() - 1
        );
        assertThat(testCompetitorCompetitivePosition.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitorCompetitivePosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitorCompetitivePosition.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCompetitorCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();
        competitorCompetitivePosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competitorCompetitivePosition.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetitorCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();
        competitorCompetitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetitorCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();
        competitorCompetitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetitorCompetitivePositionWithPatch() throws Exception {
        // Initialize the database
        competitorCompetitivePositionRepository.saveAndFlush(competitorCompetitivePosition);

        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();

        // Update the competitorCompetitivePosition using partial update
        CompetitorCompetitivePosition partialUpdatedCompetitorCompetitivePosition = new CompetitorCompetitivePosition();
        partialUpdatedCompetitorCompetitivePosition.setId(competitorCompetitivePosition.getId());

        partialUpdatedCompetitorCompetitivePosition.description(UPDATED_DESCRIPTION);

        restCompetitorCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitorCompetitivePosition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitorCompetitivePosition))
            )
            .andExpect(status().isOk());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
        CompetitorCompetitivePosition testCompetitorCompetitivePosition = competitorCompetitivePositionList.get(
            competitorCompetitivePositionList.size() - 1
        );
        assertThat(testCompetitorCompetitivePosition.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCompetitorCompetitivePosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitorCompetitivePosition.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCompetitorCompetitivePositionWithPatch() throws Exception {
        // Initialize the database
        competitorCompetitivePositionRepository.saveAndFlush(competitorCompetitivePosition);

        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();

        // Update the competitorCompetitivePosition using partial update
        CompetitorCompetitivePosition partialUpdatedCompetitorCompetitivePosition = new CompetitorCompetitivePosition();
        partialUpdatedCompetitorCompetitivePosition.setId(competitorCompetitivePosition.getId());

        partialUpdatedCompetitorCompetitivePosition.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitorCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitorCompetitivePosition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitorCompetitivePosition))
            )
            .andExpect(status().isOk());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
        CompetitorCompetitivePosition testCompetitorCompetitivePosition = competitorCompetitivePositionList.get(
            competitorCompetitivePositionList.size() - 1
        );
        assertThat(testCompetitorCompetitivePosition.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitorCompetitivePosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitorCompetitivePosition.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCompetitorCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();
        competitorCompetitivePosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competitorCompetitivePosition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetitorCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();
        competitorCompetitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetitorCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitorCompetitivePositionRepository.findAll().size();
        competitorCompetitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitorCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitorCompetitivePosition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitorCompetitivePosition in the database
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetitorCompetitivePosition() throws Exception {
        // Initialize the database
        competitorCompetitivePositionRepository.saveAndFlush(competitorCompetitivePosition);

        int databaseSizeBeforeDelete = competitorCompetitivePositionRepository.findAll().size();

        // Delete the competitorCompetitivePosition
        restCompetitorCompetitivePositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, competitorCompetitivePosition.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitorCompetitivePosition> competitorCompetitivePositionList = competitorCompetitivePositionRepository.findAll();
        assertThat(competitorCompetitivePositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
