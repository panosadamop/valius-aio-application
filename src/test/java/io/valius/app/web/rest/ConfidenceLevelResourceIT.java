package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.ConfidenceLevel;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.ConfidenceLevelRepository;
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
 * Integration tests for the {@link ConfidenceLevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfidenceLevelResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/confidence-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfidenceLevelRepository confidenceLevelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfidenceLevelMockMvc;

    private ConfidenceLevel confidenceLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfidenceLevel createEntity(EntityManager em) {
        ConfidenceLevel confidenceLevel = new ConfidenceLevel()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return confidenceLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfidenceLevel createUpdatedEntity(EntityManager em) {
        ConfidenceLevel confidenceLevel = new ConfidenceLevel()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return confidenceLevel;
    }

    @BeforeEach
    public void initTest() {
        confidenceLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createConfidenceLevel() throws Exception {
        int databaseSizeBeforeCreate = confidenceLevelRepository.findAll().size();
        // Create the ConfidenceLevel
        restConfidenceLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isCreated());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeCreate + 1);
        ConfidenceLevel testConfidenceLevel = confidenceLevelList.get(confidenceLevelList.size() - 1);
        assertThat(testConfidenceLevel.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testConfidenceLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConfidenceLevel.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createConfidenceLevelWithExistingId() throws Exception {
        // Create the ConfidenceLevel with an existing ID
        confidenceLevel.setId(1L);

        int databaseSizeBeforeCreate = confidenceLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfidenceLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = confidenceLevelRepository.findAll().size();
        // set the field null
        confidenceLevel.setValue(null);

        // Create the ConfidenceLevel, which fails.

        restConfidenceLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isBadRequest());

        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = confidenceLevelRepository.findAll().size();
        // set the field null
        confidenceLevel.setLanguage(null);

        // Create the ConfidenceLevel, which fails.

        restConfidenceLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isBadRequest());

        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConfidenceLevels() throws Exception {
        // Initialize the database
        confidenceLevelRepository.saveAndFlush(confidenceLevel);

        // Get all the confidenceLevelList
        restConfidenceLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confidenceLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getConfidenceLevel() throws Exception {
        // Initialize the database
        confidenceLevelRepository.saveAndFlush(confidenceLevel);

        // Get the confidenceLevel
        restConfidenceLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, confidenceLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(confidenceLevel.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConfidenceLevel() throws Exception {
        // Get the confidenceLevel
        restConfidenceLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfidenceLevel() throws Exception {
        // Initialize the database
        confidenceLevelRepository.saveAndFlush(confidenceLevel);

        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();

        // Update the confidenceLevel
        ConfidenceLevel updatedConfidenceLevel = confidenceLevelRepository.findById(confidenceLevel.getId()).get();
        // Disconnect from session so that the updates on updatedConfidenceLevel are not directly saved in db
        em.detach(updatedConfidenceLevel);
        updatedConfidenceLevel.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restConfidenceLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConfidenceLevel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConfidenceLevel))
            )
            .andExpect(status().isOk());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
        ConfidenceLevel testConfidenceLevel = confidenceLevelList.get(confidenceLevelList.size() - 1);
        assertThat(testConfidenceLevel.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConfidenceLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConfidenceLevel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingConfidenceLevel() throws Exception {
        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();
        confidenceLevel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfidenceLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, confidenceLevel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfidenceLevel() throws Exception {
        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();
        confidenceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfidenceLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfidenceLevel() throws Exception {
        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();
        confidenceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfidenceLevelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfidenceLevelWithPatch() throws Exception {
        // Initialize the database
        confidenceLevelRepository.saveAndFlush(confidenceLevel);

        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();

        // Update the confidenceLevel using partial update
        ConfidenceLevel partialUpdatedConfidenceLevel = new ConfidenceLevel();
        partialUpdatedConfidenceLevel.setId(confidenceLevel.getId());

        partialUpdatedConfidenceLevel.language(UPDATED_LANGUAGE);

        restConfidenceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfidenceLevel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfidenceLevel))
            )
            .andExpect(status().isOk());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
        ConfidenceLevel testConfidenceLevel = confidenceLevelList.get(confidenceLevelList.size() - 1);
        assertThat(testConfidenceLevel.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testConfidenceLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConfidenceLevel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateConfidenceLevelWithPatch() throws Exception {
        // Initialize the database
        confidenceLevelRepository.saveAndFlush(confidenceLevel);

        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();

        // Update the confidenceLevel using partial update
        ConfidenceLevel partialUpdatedConfidenceLevel = new ConfidenceLevel();
        partialUpdatedConfidenceLevel.setId(confidenceLevel.getId());

        partialUpdatedConfidenceLevel.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restConfidenceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfidenceLevel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfidenceLevel))
            )
            .andExpect(status().isOk());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
        ConfidenceLevel testConfidenceLevel = confidenceLevelList.get(confidenceLevelList.size() - 1);
        assertThat(testConfidenceLevel.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConfidenceLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConfidenceLevel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingConfidenceLevel() throws Exception {
        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();
        confidenceLevel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfidenceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, confidenceLevel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfidenceLevel() throws Exception {
        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();
        confidenceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfidenceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfidenceLevel() throws Exception {
        int databaseSizeBeforeUpdate = confidenceLevelRepository.findAll().size();
        confidenceLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfidenceLevelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(confidenceLevel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfidenceLevel in the database
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfidenceLevel() throws Exception {
        // Initialize the database
        confidenceLevelRepository.saveAndFlush(confidenceLevel);

        int databaseSizeBeforeDelete = confidenceLevelRepository.findAll().size();

        // Delete the confidenceLevel
        restConfidenceLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, confidenceLevel.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConfidenceLevel> confidenceLevelList = confidenceLevelRepository.findAll();
        assertThat(confidenceLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
