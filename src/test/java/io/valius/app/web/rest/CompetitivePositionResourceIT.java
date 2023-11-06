package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.CompetitivePosition;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.CompetitivePositionRepository;
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
 * Integration tests for the {@link CompetitivePositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetitivePositionResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/competitive-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetitivePositionRepository competitivePositionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitivePositionMockMvc;

    private CompetitivePosition competitivePosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitivePosition createEntity(EntityManager em) {
        CompetitivePosition competitivePosition = new CompetitivePosition()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return competitivePosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitivePosition createUpdatedEntity(EntityManager em) {
        CompetitivePosition competitivePosition = new CompetitivePosition()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return competitivePosition;
    }

    @BeforeEach
    public void initTest() {
        competitivePosition = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetitivePosition() throws Exception {
        int databaseSizeBeforeCreate = competitivePositionRepository.findAll().size();
        // Create the CompetitivePosition
        restCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isCreated());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitivePosition testCompetitivePosition = competitivePositionList.get(competitivePositionList.size() - 1);
        assertThat(testCompetitivePosition.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCompetitivePosition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitivePosition.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCompetitivePositionWithExistingId() throws Exception {
        // Create the CompetitivePosition with an existing ID
        competitivePosition.setId(1L);

        int databaseSizeBeforeCreate = competitivePositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitivePositionRepository.findAll().size();
        // set the field null
        competitivePosition.setValue(null);

        // Create the CompetitivePosition, which fails.

        restCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isBadRequest());

        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitivePositionRepository.findAll().size();
        // set the field null
        competitivePosition.setLanguage(null);

        // Create the CompetitivePosition, which fails.

        restCompetitivePositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isBadRequest());

        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompetitivePositions() throws Exception {
        // Initialize the database
        competitivePositionRepository.saveAndFlush(competitivePosition);

        // Get all the competitivePositionList
        restCompetitivePositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitivePosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getCompetitivePosition() throws Exception {
        // Initialize the database
        competitivePositionRepository.saveAndFlush(competitivePosition);

        // Get the competitivePosition
        restCompetitivePositionMockMvc
            .perform(get(ENTITY_API_URL_ID, competitivePosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competitivePosition.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompetitivePosition() throws Exception {
        // Get the competitivePosition
        restCompetitivePositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetitivePosition() throws Exception {
        // Initialize the database
        competitivePositionRepository.saveAndFlush(competitivePosition);

        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();

        // Update the competitivePosition
        CompetitivePosition updatedCompetitivePosition = competitivePositionRepository.findById(competitivePosition.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitivePosition are not directly saved in db
        em.detach(updatedCompetitivePosition);
        updatedCompetitivePosition.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetitivePosition.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetitivePosition))
            )
            .andExpect(status().isOk());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
        CompetitivePosition testCompetitivePosition = competitivePositionList.get(competitivePositionList.size() - 1);
        assertThat(testCompetitivePosition.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitivePosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitivePosition.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();
        competitivePosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competitivePosition.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();
        competitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();
        competitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitivePositionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetitivePositionWithPatch() throws Exception {
        // Initialize the database
        competitivePositionRepository.saveAndFlush(competitivePosition);

        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();

        // Update the competitivePosition using partial update
        CompetitivePosition partialUpdatedCompetitivePosition = new CompetitivePosition();
        partialUpdatedCompetitivePosition.setId(competitivePosition.getId());

        partialUpdatedCompetitivePosition.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);

        restCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitivePosition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitivePosition))
            )
            .andExpect(status().isOk());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
        CompetitivePosition testCompetitivePosition = competitivePositionList.get(competitivePositionList.size() - 1);
        assertThat(testCompetitivePosition.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitivePosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitivePosition.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCompetitivePositionWithPatch() throws Exception {
        // Initialize the database
        competitivePositionRepository.saveAndFlush(competitivePosition);

        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();

        // Update the competitivePosition using partial update
        CompetitivePosition partialUpdatedCompetitivePosition = new CompetitivePosition();
        partialUpdatedCompetitivePosition.setId(competitivePosition.getId());

        partialUpdatedCompetitivePosition.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetitivePosition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetitivePosition))
            )
            .andExpect(status().isOk());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
        CompetitivePosition testCompetitivePosition = competitivePositionList.get(competitivePositionList.size() - 1);
        assertThat(testCompetitivePosition.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompetitivePosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitivePosition.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();
        competitivePosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competitivePosition.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();
        competitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetitivePosition() throws Exception {
        int databaseSizeBeforeUpdate = competitivePositionRepository.findAll().size();
        competitivePosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitivePositionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competitivePosition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompetitivePosition in the database
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetitivePosition() throws Exception {
        // Initialize the database
        competitivePositionRepository.saveAndFlush(competitivePosition);

        int databaseSizeBeforeDelete = competitivePositionRepository.findAll().size();

        // Delete the competitivePosition
        restCompetitivePositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, competitivePosition.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitivePosition> competitivePositionList = competitivePositionRepository.findAll();
        assertThat(competitivePositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
