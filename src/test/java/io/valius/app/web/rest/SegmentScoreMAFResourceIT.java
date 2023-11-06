package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.SegmentScoreMAF;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.SegmentScoreMAFRepository;
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
 * Integration tests for the {@link SegmentScoreMAFResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SegmentScoreMAFResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/segment-score-mafs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SegmentScoreMAFRepository segmentScoreMAFRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentScoreMAFMockMvc;

    private SegmentScoreMAF segmentScoreMAF;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentScoreMAF createEntity(EntityManager em) {
        SegmentScoreMAF segmentScoreMAF = new SegmentScoreMAF()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return segmentScoreMAF;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentScoreMAF createUpdatedEntity(EntityManager em) {
        SegmentScoreMAF segmentScoreMAF = new SegmentScoreMAF()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return segmentScoreMAF;
    }

    @BeforeEach
    public void initTest() {
        segmentScoreMAF = createEntity(em);
    }

    @Test
    @Transactional
    void createSegmentScoreMAF() throws Exception {
        int databaseSizeBeforeCreate = segmentScoreMAFRepository.findAll().size();
        // Create the SegmentScoreMAF
        restSegmentScoreMAFMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isCreated());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeCreate + 1);
        SegmentScoreMAF testSegmentScoreMAF = segmentScoreMAFList.get(segmentScoreMAFList.size() - 1);
        assertThat(testSegmentScoreMAF.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSegmentScoreMAF.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSegmentScoreMAF.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createSegmentScoreMAFWithExistingId() throws Exception {
        // Create the SegmentScoreMAF with an existing ID
        segmentScoreMAF.setId(1L);

        int databaseSizeBeforeCreate = segmentScoreMAFRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentScoreMAFMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = segmentScoreMAFRepository.findAll().size();
        // set the field null
        segmentScoreMAF.setValue(null);

        // Create the SegmentScoreMAF, which fails.

        restSegmentScoreMAFMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isBadRequest());

        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = segmentScoreMAFRepository.findAll().size();
        // set the field null
        segmentScoreMAF.setLanguage(null);

        // Create the SegmentScoreMAF, which fails.

        restSegmentScoreMAFMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isBadRequest());

        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSegmentScoreMAFS() throws Exception {
        // Initialize the database
        segmentScoreMAFRepository.saveAndFlush(segmentScoreMAF);

        // Get all the segmentScoreMAFList
        restSegmentScoreMAFMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segmentScoreMAF.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getSegmentScoreMAF() throws Exception {
        // Initialize the database
        segmentScoreMAFRepository.saveAndFlush(segmentScoreMAF);

        // Get the segmentScoreMAF
        restSegmentScoreMAFMockMvc
            .perform(get(ENTITY_API_URL_ID, segmentScoreMAF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segmentScoreMAF.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSegmentScoreMAF() throws Exception {
        // Get the segmentScoreMAF
        restSegmentScoreMAFMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSegmentScoreMAF() throws Exception {
        // Initialize the database
        segmentScoreMAFRepository.saveAndFlush(segmentScoreMAF);

        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();

        // Update the segmentScoreMAF
        SegmentScoreMAF updatedSegmentScoreMAF = segmentScoreMAFRepository.findById(segmentScoreMAF.getId()).get();
        // Disconnect from session so that the updates on updatedSegmentScoreMAF are not directly saved in db
        em.detach(updatedSegmentScoreMAF);
        updatedSegmentScoreMAF.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restSegmentScoreMAFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSegmentScoreMAF.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSegmentScoreMAF))
            )
            .andExpect(status().isOk());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
        SegmentScoreMAF testSegmentScoreMAF = segmentScoreMAFList.get(segmentScoreMAFList.size() - 1);
        assertThat(testSegmentScoreMAF.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSegmentScoreMAF.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSegmentScoreMAF.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingSegmentScoreMAF() throws Exception {
        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();
        segmentScoreMAF.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentScoreMAFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentScoreMAF.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSegmentScoreMAF() throws Exception {
        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();
        segmentScoreMAF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentScoreMAFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSegmentScoreMAF() throws Exception {
        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();
        segmentScoreMAF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentScoreMAFMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSegmentScoreMAFWithPatch() throws Exception {
        // Initialize the database
        segmentScoreMAFRepository.saveAndFlush(segmentScoreMAF);

        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();

        // Update the segmentScoreMAF using partial update
        SegmentScoreMAF partialUpdatedSegmentScoreMAF = new SegmentScoreMAF();
        partialUpdatedSegmentScoreMAF.setId(segmentScoreMAF.getId());

        partialUpdatedSegmentScoreMAF.description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restSegmentScoreMAFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentScoreMAF.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmentScoreMAF))
            )
            .andExpect(status().isOk());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
        SegmentScoreMAF testSegmentScoreMAF = segmentScoreMAFList.get(segmentScoreMAFList.size() - 1);
        assertThat(testSegmentScoreMAF.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSegmentScoreMAF.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSegmentScoreMAF.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateSegmentScoreMAFWithPatch() throws Exception {
        // Initialize the database
        segmentScoreMAFRepository.saveAndFlush(segmentScoreMAF);

        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();

        // Update the segmentScoreMAF using partial update
        SegmentScoreMAF partialUpdatedSegmentScoreMAF = new SegmentScoreMAF();
        partialUpdatedSegmentScoreMAF.setId(segmentScoreMAF.getId());

        partialUpdatedSegmentScoreMAF.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restSegmentScoreMAFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentScoreMAF.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmentScoreMAF))
            )
            .andExpect(status().isOk());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
        SegmentScoreMAF testSegmentScoreMAF = segmentScoreMAFList.get(segmentScoreMAFList.size() - 1);
        assertThat(testSegmentScoreMAF.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSegmentScoreMAF.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSegmentScoreMAF.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingSegmentScoreMAF() throws Exception {
        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();
        segmentScoreMAF.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentScoreMAFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, segmentScoreMAF.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSegmentScoreMAF() throws Exception {
        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();
        segmentScoreMAF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentScoreMAFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSegmentScoreMAF() throws Exception {
        int databaseSizeBeforeUpdate = segmentScoreMAFRepository.findAll().size();
        segmentScoreMAF.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentScoreMAFMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentScoreMAF))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentScoreMAF in the database
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSegmentScoreMAF() throws Exception {
        // Initialize the database
        segmentScoreMAFRepository.saveAndFlush(segmentScoreMAF);

        int databaseSizeBeforeDelete = segmentScoreMAFRepository.findAll().size();

        // Delete the segmentScoreMAF
        restSegmentScoreMAFMockMvc
            .perform(delete(ENTITY_API_URL_ID, segmentScoreMAF.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SegmentScoreMAF> segmentScoreMAFList = segmentScoreMAFRepository.findAll();
        assertThat(segmentScoreMAFList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
