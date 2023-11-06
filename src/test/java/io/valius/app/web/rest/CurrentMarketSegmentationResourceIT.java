package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.CurrentMarketSegmentation;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.CurrentMarketSegmentationRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
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
 * Integration tests for the {@link CurrentMarketSegmentationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrentMarketSegmentationResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/current-market-segmentations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrentMarketSegmentationRepository currentMarketSegmentationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrentMarketSegmentationMockMvc;

    private CurrentMarketSegmentation currentMarketSegmentation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentMarketSegmentation createEntity(EntityManager em) {
        CurrentMarketSegmentation currentMarketSegmentation = new CurrentMarketSegmentation()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return currentMarketSegmentation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentMarketSegmentation createUpdatedEntity(EntityManager em) {
        CurrentMarketSegmentation currentMarketSegmentation = new CurrentMarketSegmentation()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return currentMarketSegmentation;
    }

    @BeforeEach
    public void initTest() {
        currentMarketSegmentation = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrentMarketSegmentation() throws Exception {
        int databaseSizeBeforeCreate = currentMarketSegmentationRepository.findAll().size();
        // Create the CurrentMarketSegmentation
        restCurrentMarketSegmentationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isCreated());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeCreate + 1);
        CurrentMarketSegmentation testCurrentMarketSegmentation = currentMarketSegmentationList.get(
            currentMarketSegmentationList.size() - 1
        );
        assertThat(testCurrentMarketSegmentation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCurrentMarketSegmentation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCurrentMarketSegmentation.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCurrentMarketSegmentationWithExistingId() throws Exception {
        // Create the CurrentMarketSegmentation with an existing ID
        currentMarketSegmentation.setId(1L);

        int databaseSizeBeforeCreate = currentMarketSegmentationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentMarketSegmentationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = currentMarketSegmentationRepository.findAll().size();
        // set the field null
        currentMarketSegmentation.setValue(null);

        // Create the CurrentMarketSegmentation, which fails.

        restCurrentMarketSegmentationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isBadRequest());

        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = currentMarketSegmentationRepository.findAll().size();
        // set the field null
        currentMarketSegmentation.setLanguage(null);

        // Create the CurrentMarketSegmentation, which fails.

        restCurrentMarketSegmentationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isBadRequest());

        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCurrentMarketSegmentations() throws Exception {
        // Initialize the database
        currentMarketSegmentationRepository.saveAndFlush(currentMarketSegmentation);

        // Get all the currentMarketSegmentationList
        restCurrentMarketSegmentationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currentMarketSegmentation.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getCurrentMarketSegmentation() throws Exception {
        // Initialize the database
        currentMarketSegmentationRepository.saveAndFlush(currentMarketSegmentation);

        // Get the currentMarketSegmentation
        restCurrentMarketSegmentationMockMvc
            .perform(get(ENTITY_API_URL_ID, currentMarketSegmentation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currentMarketSegmentation.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCurrentMarketSegmentation() throws Exception {
        // Get the currentMarketSegmentation
        restCurrentMarketSegmentationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCurrentMarketSegmentation() throws Exception {
        // Initialize the database
        currentMarketSegmentationRepository.saveAndFlush(currentMarketSegmentation);

        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();

        // Update the currentMarketSegmentation
        CurrentMarketSegmentation updatedCurrentMarketSegmentation = currentMarketSegmentationRepository
            .findById(currentMarketSegmentation.getId())
            .get();
        // Disconnect from session so that the updates on updatedCurrentMarketSegmentation are not directly saved in db
        em.detach(updatedCurrentMarketSegmentation);
        updatedCurrentMarketSegmentation.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCurrentMarketSegmentationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCurrentMarketSegmentation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCurrentMarketSegmentation))
            )
            .andExpect(status().isOk());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
        CurrentMarketSegmentation testCurrentMarketSegmentation = currentMarketSegmentationList.get(
            currentMarketSegmentationList.size() - 1
        );
        assertThat(testCurrentMarketSegmentation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCurrentMarketSegmentation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCurrentMarketSegmentation.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCurrentMarketSegmentation() throws Exception {
        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();
        currentMarketSegmentation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentMarketSegmentationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currentMarketSegmentation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrentMarketSegmentation() throws Exception {
        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();
        currentMarketSegmentation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMarketSegmentationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrentMarketSegmentation() throws Exception {
        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();
        currentMarketSegmentation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMarketSegmentationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCurrentMarketSegmentationWithPatch() throws Exception {
        // Initialize the database
        currentMarketSegmentationRepository.saveAndFlush(currentMarketSegmentation);

        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();

        // Update the currentMarketSegmentation using partial update
        CurrentMarketSegmentation partialUpdatedCurrentMarketSegmentation = new CurrentMarketSegmentation();
        partialUpdatedCurrentMarketSegmentation.setId(currentMarketSegmentation.getId());

        partialUpdatedCurrentMarketSegmentation.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restCurrentMarketSegmentationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrentMarketSegmentation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrentMarketSegmentation))
            )
            .andExpect(status().isOk());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
        CurrentMarketSegmentation testCurrentMarketSegmentation = currentMarketSegmentationList.get(
            currentMarketSegmentationList.size() - 1
        );
        assertThat(testCurrentMarketSegmentation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCurrentMarketSegmentation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCurrentMarketSegmentation.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCurrentMarketSegmentationWithPatch() throws Exception {
        // Initialize the database
        currentMarketSegmentationRepository.saveAndFlush(currentMarketSegmentation);

        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();

        // Update the currentMarketSegmentation using partial update
        CurrentMarketSegmentation partialUpdatedCurrentMarketSegmentation = new CurrentMarketSegmentation();
        partialUpdatedCurrentMarketSegmentation.setId(currentMarketSegmentation.getId());

        partialUpdatedCurrentMarketSegmentation.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCurrentMarketSegmentationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrentMarketSegmentation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrentMarketSegmentation))
            )
            .andExpect(status().isOk());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
        CurrentMarketSegmentation testCurrentMarketSegmentation = currentMarketSegmentationList.get(
            currentMarketSegmentationList.size() - 1
        );
        assertThat(testCurrentMarketSegmentation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCurrentMarketSegmentation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCurrentMarketSegmentation.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCurrentMarketSegmentation() throws Exception {
        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();
        currentMarketSegmentation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentMarketSegmentationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currentMarketSegmentation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrentMarketSegmentation() throws Exception {
        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();
        currentMarketSegmentation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMarketSegmentationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrentMarketSegmentation() throws Exception {
        int databaseSizeBeforeUpdate = currentMarketSegmentationRepository.findAll().size();
        currentMarketSegmentation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentMarketSegmentationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentMarketSegmentation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrentMarketSegmentation in the database
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurrentMarketSegmentation() throws Exception {
        // Initialize the database
        currentMarketSegmentationRepository.saveAndFlush(currentMarketSegmentation);

        int databaseSizeBeforeDelete = currentMarketSegmentationRepository.findAll().size();

        // Delete the currentMarketSegmentation
        restCurrentMarketSegmentationMockMvc
            .perform(delete(ENTITY_API_URL_ID, currentMarketSegmentation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrentMarketSegmentation> currentMarketSegmentationList = currentMarketSegmentationRepository.findAll();
        assertThat(currentMarketSegmentationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
