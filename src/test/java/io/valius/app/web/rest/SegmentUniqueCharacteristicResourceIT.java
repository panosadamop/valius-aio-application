package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.SegmentUniqueCharacteristic;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.SegmentUniqueCharacteristicRepository;
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
 * Integration tests for the {@link SegmentUniqueCharacteristicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SegmentUniqueCharacteristicResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/segment-unique-characteristics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SegmentUniqueCharacteristicRepository segmentUniqueCharacteristicRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentUniqueCharacteristicMockMvc;

    private SegmentUniqueCharacteristic segmentUniqueCharacteristic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentUniqueCharacteristic createEntity(EntityManager em) {
        SegmentUniqueCharacteristic segmentUniqueCharacteristic = new SegmentUniqueCharacteristic()
            .value(DEFAULT_VALUE)
            .category(DEFAULT_CATEGORY)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return segmentUniqueCharacteristic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentUniqueCharacteristic createUpdatedEntity(EntityManager em) {
        SegmentUniqueCharacteristic segmentUniqueCharacteristic = new SegmentUniqueCharacteristic()
            .value(UPDATED_VALUE)
            .category(UPDATED_CATEGORY)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return segmentUniqueCharacteristic;
    }

    @BeforeEach
    public void initTest() {
        segmentUniqueCharacteristic = createEntity(em);
    }

    @Test
    @Transactional
    void createSegmentUniqueCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = segmentUniqueCharacteristicRepository.findAll().size();
        // Create the SegmentUniqueCharacteristic
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isCreated());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeCreate + 1);
        SegmentUniqueCharacteristic testSegmentUniqueCharacteristic = segmentUniqueCharacteristicList.get(
            segmentUniqueCharacteristicList.size() - 1
        );
        assertThat(testSegmentUniqueCharacteristic.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSegmentUniqueCharacteristic.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSegmentUniqueCharacteristic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSegmentUniqueCharacteristic.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createSegmentUniqueCharacteristicWithExistingId() throws Exception {
        // Create the SegmentUniqueCharacteristic with an existing ID
        segmentUniqueCharacteristic.setId(1L);

        int databaseSizeBeforeCreate = segmentUniqueCharacteristicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = segmentUniqueCharacteristicRepository.findAll().size();
        // set the field null
        segmentUniqueCharacteristic.setValue(null);

        // Create the SegmentUniqueCharacteristic, which fails.

        restSegmentUniqueCharacteristicMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = segmentUniqueCharacteristicRepository.findAll().size();
        // set the field null
        segmentUniqueCharacteristic.setCategory(null);

        // Create the SegmentUniqueCharacteristic, which fails.

        restSegmentUniqueCharacteristicMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = segmentUniqueCharacteristicRepository.findAll().size();
        // set the field null
        segmentUniqueCharacteristic.setLanguage(null);

        // Create the SegmentUniqueCharacteristic, which fails.

        restSegmentUniqueCharacteristicMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSegmentUniqueCharacteristics() throws Exception {
        // Initialize the database
        segmentUniqueCharacteristicRepository.saveAndFlush(segmentUniqueCharacteristic);

        // Get all the segmentUniqueCharacteristicList
        restSegmentUniqueCharacteristicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segmentUniqueCharacteristic.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getSegmentUniqueCharacteristic() throws Exception {
        // Initialize the database
        segmentUniqueCharacteristicRepository.saveAndFlush(segmentUniqueCharacteristic);

        // Get the segmentUniqueCharacteristic
        restSegmentUniqueCharacteristicMockMvc
            .perform(get(ENTITY_API_URL_ID, segmentUniqueCharacteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segmentUniqueCharacteristic.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSegmentUniqueCharacteristic() throws Exception {
        // Get the segmentUniqueCharacteristic
        restSegmentUniqueCharacteristicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSegmentUniqueCharacteristic() throws Exception {
        // Initialize the database
        segmentUniqueCharacteristicRepository.saveAndFlush(segmentUniqueCharacteristic);

        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();

        // Update the segmentUniqueCharacteristic
        SegmentUniqueCharacteristic updatedSegmentUniqueCharacteristic = segmentUniqueCharacteristicRepository
            .findById(segmentUniqueCharacteristic.getId())
            .get();
        // Disconnect from session so that the updates on updatedSegmentUniqueCharacteristic are not directly saved in db
        em.detach(updatedSegmentUniqueCharacteristic);
        updatedSegmentUniqueCharacteristic
            .value(UPDATED_VALUE)
            .category(UPDATED_CATEGORY)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);

        restSegmentUniqueCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSegmentUniqueCharacteristic.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSegmentUniqueCharacteristic))
            )
            .andExpect(status().isOk());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        SegmentUniqueCharacteristic testSegmentUniqueCharacteristic = segmentUniqueCharacteristicList.get(
            segmentUniqueCharacteristicList.size() - 1
        );
        assertThat(testSegmentUniqueCharacteristic.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSegmentUniqueCharacteristic.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSegmentUniqueCharacteristic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSegmentUniqueCharacteristic.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingSegmentUniqueCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();
        segmentUniqueCharacteristic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentUniqueCharacteristic.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSegmentUniqueCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();
        segmentUniqueCharacteristic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSegmentUniqueCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();
        segmentUniqueCharacteristic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSegmentUniqueCharacteristicWithPatch() throws Exception {
        // Initialize the database
        segmentUniqueCharacteristicRepository.saveAndFlush(segmentUniqueCharacteristic);

        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();

        // Update the segmentUniqueCharacteristic using partial update
        SegmentUniqueCharacteristic partialUpdatedSegmentUniqueCharacteristic = new SegmentUniqueCharacteristic();
        partialUpdatedSegmentUniqueCharacteristic.setId(segmentUniqueCharacteristic.getId());

        partialUpdatedSegmentUniqueCharacteristic.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);

        restSegmentUniqueCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentUniqueCharacteristic.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmentUniqueCharacteristic))
            )
            .andExpect(status().isOk());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        SegmentUniqueCharacteristic testSegmentUniqueCharacteristic = segmentUniqueCharacteristicList.get(
            segmentUniqueCharacteristicList.size() - 1
        );
        assertThat(testSegmentUniqueCharacteristic.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSegmentUniqueCharacteristic.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSegmentUniqueCharacteristic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSegmentUniqueCharacteristic.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateSegmentUniqueCharacteristicWithPatch() throws Exception {
        // Initialize the database
        segmentUniqueCharacteristicRepository.saveAndFlush(segmentUniqueCharacteristic);

        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();

        // Update the segmentUniqueCharacteristic using partial update
        SegmentUniqueCharacteristic partialUpdatedSegmentUniqueCharacteristic = new SegmentUniqueCharacteristic();
        partialUpdatedSegmentUniqueCharacteristic.setId(segmentUniqueCharacteristic.getId());

        partialUpdatedSegmentUniqueCharacteristic
            .value(UPDATED_VALUE)
            .category(UPDATED_CATEGORY)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);

        restSegmentUniqueCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentUniqueCharacteristic.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmentUniqueCharacteristic))
            )
            .andExpect(status().isOk());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        SegmentUniqueCharacteristic testSegmentUniqueCharacteristic = segmentUniqueCharacteristicList.get(
            segmentUniqueCharacteristicList.size() - 1
        );
        assertThat(testSegmentUniqueCharacteristic.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSegmentUniqueCharacteristic.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSegmentUniqueCharacteristic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSegmentUniqueCharacteristic.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingSegmentUniqueCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();
        segmentUniqueCharacteristic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, segmentUniqueCharacteristic.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSegmentUniqueCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();
        segmentUniqueCharacteristic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSegmentUniqueCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = segmentUniqueCharacteristicRepository.findAll().size();
        segmentUniqueCharacteristic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentUniqueCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentUniqueCharacteristic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentUniqueCharacteristic in the database
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSegmentUniqueCharacteristic() throws Exception {
        // Initialize the database
        segmentUniqueCharacteristicRepository.saveAndFlush(segmentUniqueCharacteristic);

        int databaseSizeBeforeDelete = segmentUniqueCharacteristicRepository.findAll().size();

        // Delete the segmentUniqueCharacteristic
        restSegmentUniqueCharacteristicMockMvc
            .perform(delete(ENTITY_API_URL_ID, segmentUniqueCharacteristic.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SegmentUniqueCharacteristic> segmentUniqueCharacteristicList = segmentUniqueCharacteristicRepository.findAll();
        assertThat(segmentUniqueCharacteristicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
