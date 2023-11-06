package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.RequiredSampleSize;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.RequiredSampleSizeRepository;
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
 * Integration tests for the {@link RequiredSampleSizeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequiredSampleSizeResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/required-sample-sizes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequiredSampleSizeRepository requiredSampleSizeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequiredSampleSizeMockMvc;

    private RequiredSampleSize requiredSampleSize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequiredSampleSize createEntity(EntityManager em) {
        RequiredSampleSize requiredSampleSize = new RequiredSampleSize().value(DEFAULT_VALUE).language(DEFAULT_LANGUAGE);
        return requiredSampleSize;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequiredSampleSize createUpdatedEntity(EntityManager em) {
        RequiredSampleSize requiredSampleSize = new RequiredSampleSize().value(UPDATED_VALUE).language(UPDATED_LANGUAGE);
        return requiredSampleSize;
    }

    @BeforeEach
    public void initTest() {
        requiredSampleSize = createEntity(em);
    }

    @Test
    @Transactional
    void createRequiredSampleSize() throws Exception {
        int databaseSizeBeforeCreate = requiredSampleSizeRepository.findAll().size();
        // Create the RequiredSampleSize
        restRequiredSampleSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isCreated());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeCreate + 1);
        RequiredSampleSize testRequiredSampleSize = requiredSampleSizeList.get(requiredSampleSizeList.size() - 1);
        assertThat(testRequiredSampleSize.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testRequiredSampleSize.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createRequiredSampleSizeWithExistingId() throws Exception {
        // Create the RequiredSampleSize with an existing ID
        requiredSampleSize.setId(1L);

        int databaseSizeBeforeCreate = requiredSampleSizeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequiredSampleSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = requiredSampleSizeRepository.findAll().size();
        // set the field null
        requiredSampleSize.setValue(null);

        // Create the RequiredSampleSize, which fails.

        restRequiredSampleSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isBadRequest());

        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = requiredSampleSizeRepository.findAll().size();
        // set the field null
        requiredSampleSize.setLanguage(null);

        // Create the RequiredSampleSize, which fails.

        restRequiredSampleSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isBadRequest());

        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequiredSampleSizes() throws Exception {
        // Initialize the database
        requiredSampleSizeRepository.saveAndFlush(requiredSampleSize);

        // Get all the requiredSampleSizeList
        restRequiredSampleSizeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requiredSampleSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getRequiredSampleSize() throws Exception {
        // Initialize the database
        requiredSampleSizeRepository.saveAndFlush(requiredSampleSize);

        // Get the requiredSampleSize
        restRequiredSampleSizeMockMvc
            .perform(get(ENTITY_API_URL_ID, requiredSampleSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requiredSampleSize.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRequiredSampleSize() throws Exception {
        // Get the requiredSampleSize
        restRequiredSampleSizeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequiredSampleSize() throws Exception {
        // Initialize the database
        requiredSampleSizeRepository.saveAndFlush(requiredSampleSize);

        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();

        // Update the requiredSampleSize
        RequiredSampleSize updatedRequiredSampleSize = requiredSampleSizeRepository.findById(requiredSampleSize.getId()).get();
        // Disconnect from session so that the updates on updatedRequiredSampleSize are not directly saved in db
        em.detach(updatedRequiredSampleSize);
        updatedRequiredSampleSize.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restRequiredSampleSizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequiredSampleSize.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRequiredSampleSize))
            )
            .andExpect(status().isOk());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
        RequiredSampleSize testRequiredSampleSize = requiredSampleSizeList.get(requiredSampleSizeList.size() - 1);
        assertThat(testRequiredSampleSize.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRequiredSampleSize.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingRequiredSampleSize() throws Exception {
        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();
        requiredSampleSize.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequiredSampleSizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requiredSampleSize.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequiredSampleSize() throws Exception {
        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();
        requiredSampleSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredSampleSizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequiredSampleSize() throws Exception {
        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();
        requiredSampleSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredSampleSizeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequiredSampleSizeWithPatch() throws Exception {
        // Initialize the database
        requiredSampleSizeRepository.saveAndFlush(requiredSampleSize);

        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();

        // Update the requiredSampleSize using partial update
        RequiredSampleSize partialUpdatedRequiredSampleSize = new RequiredSampleSize();
        partialUpdatedRequiredSampleSize.setId(requiredSampleSize.getId());

        partialUpdatedRequiredSampleSize.value(UPDATED_VALUE);

        restRequiredSampleSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequiredSampleSize.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequiredSampleSize))
            )
            .andExpect(status().isOk());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
        RequiredSampleSize testRequiredSampleSize = requiredSampleSizeList.get(requiredSampleSizeList.size() - 1);
        assertThat(testRequiredSampleSize.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRequiredSampleSize.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateRequiredSampleSizeWithPatch() throws Exception {
        // Initialize the database
        requiredSampleSizeRepository.saveAndFlush(requiredSampleSize);

        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();

        // Update the requiredSampleSize using partial update
        RequiredSampleSize partialUpdatedRequiredSampleSize = new RequiredSampleSize();
        partialUpdatedRequiredSampleSize.setId(requiredSampleSize.getId());

        partialUpdatedRequiredSampleSize.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restRequiredSampleSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequiredSampleSize.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequiredSampleSize))
            )
            .andExpect(status().isOk());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
        RequiredSampleSize testRequiredSampleSize = requiredSampleSizeList.get(requiredSampleSizeList.size() - 1);
        assertThat(testRequiredSampleSize.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRequiredSampleSize.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingRequiredSampleSize() throws Exception {
        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();
        requiredSampleSize.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequiredSampleSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requiredSampleSize.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequiredSampleSize() throws Exception {
        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();
        requiredSampleSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredSampleSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequiredSampleSize() throws Exception {
        int databaseSizeBeforeUpdate = requiredSampleSizeRepository.findAll().size();
        requiredSampleSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredSampleSizeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredSampleSize))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequiredSampleSize in the database
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequiredSampleSize() throws Exception {
        // Initialize the database
        requiredSampleSizeRepository.saveAndFlush(requiredSampleSize);

        int databaseSizeBeforeDelete = requiredSampleSizeRepository.findAll().size();

        // Delete the requiredSampleSize
        restRequiredSampleSizeMockMvc
            .perform(delete(ENTITY_API_URL_ID, requiredSampleSize.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequiredSampleSize> requiredSampleSizeList = requiredSampleSizeRepository.findAll();
        assertThat(requiredSampleSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
