package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.PopulationSize;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.PopulationSizeRepository;
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
 * Integration tests for the {@link PopulationSizeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PopulationSizeResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/population-sizes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PopulationSizeRepository populationSizeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPopulationSizeMockMvc;

    private PopulationSize populationSize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopulationSize createEntity(EntityManager em) {
        PopulationSize populationSize = new PopulationSize()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return populationSize;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopulationSize createUpdatedEntity(EntityManager em) {
        PopulationSize populationSize = new PopulationSize()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return populationSize;
    }

    @BeforeEach
    public void initTest() {
        populationSize = createEntity(em);
    }

    @Test
    @Transactional
    void createPopulationSize() throws Exception {
        int databaseSizeBeforeCreate = populationSizeRepository.findAll().size();
        // Create the PopulationSize
        restPopulationSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isCreated());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeCreate + 1);
        PopulationSize testPopulationSize = populationSizeList.get(populationSizeList.size() - 1);
        assertThat(testPopulationSize.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPopulationSize.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPopulationSize.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createPopulationSizeWithExistingId() throws Exception {
        // Create the PopulationSize with an existing ID
        populationSize.setId(1L);

        int databaseSizeBeforeCreate = populationSizeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopulationSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = populationSizeRepository.findAll().size();
        // set the field null
        populationSize.setValue(null);

        // Create the PopulationSize, which fails.

        restPopulationSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isBadRequest());

        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = populationSizeRepository.findAll().size();
        // set the field null
        populationSize.setLanguage(null);

        // Create the PopulationSize, which fails.

        restPopulationSizeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isBadRequest());

        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPopulationSizes() throws Exception {
        // Initialize the database
        populationSizeRepository.saveAndFlush(populationSize);

        // Get all the populationSizeList
        restPopulationSizeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(populationSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getPopulationSize() throws Exception {
        // Initialize the database
        populationSizeRepository.saveAndFlush(populationSize);

        // Get the populationSize
        restPopulationSizeMockMvc
            .perform(get(ENTITY_API_URL_ID, populationSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(populationSize.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPopulationSize() throws Exception {
        // Get the populationSize
        restPopulationSizeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPopulationSize() throws Exception {
        // Initialize the database
        populationSizeRepository.saveAndFlush(populationSize);

        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();

        // Update the populationSize
        PopulationSize updatedPopulationSize = populationSizeRepository.findById(populationSize.getId()).get();
        // Disconnect from session so that the updates on updatedPopulationSize are not directly saved in db
        em.detach(updatedPopulationSize);
        updatedPopulationSize.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restPopulationSizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPopulationSize.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPopulationSize))
            )
            .andExpect(status().isOk());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
        PopulationSize testPopulationSize = populationSizeList.get(populationSizeList.size() - 1);
        assertThat(testPopulationSize.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPopulationSize.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPopulationSize.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingPopulationSize() throws Exception {
        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();
        populationSize.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPopulationSizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, populationSize.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPopulationSize() throws Exception {
        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();
        populationSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopulationSizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPopulationSize() throws Exception {
        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();
        populationSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopulationSizeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePopulationSizeWithPatch() throws Exception {
        // Initialize the database
        populationSizeRepository.saveAndFlush(populationSize);

        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();

        // Update the populationSize using partial update
        PopulationSize partialUpdatedPopulationSize = new PopulationSize();
        partialUpdatedPopulationSize.setId(populationSize.getId());

        partialUpdatedPopulationSize.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restPopulationSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPopulationSize.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPopulationSize))
            )
            .andExpect(status().isOk());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
        PopulationSize testPopulationSize = populationSizeList.get(populationSizeList.size() - 1);
        assertThat(testPopulationSize.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPopulationSize.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPopulationSize.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdatePopulationSizeWithPatch() throws Exception {
        // Initialize the database
        populationSizeRepository.saveAndFlush(populationSize);

        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();

        // Update the populationSize using partial update
        PopulationSize partialUpdatedPopulationSize = new PopulationSize();
        partialUpdatedPopulationSize.setId(populationSize.getId());

        partialUpdatedPopulationSize.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restPopulationSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPopulationSize.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPopulationSize))
            )
            .andExpect(status().isOk());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
        PopulationSize testPopulationSize = populationSizeList.get(populationSizeList.size() - 1);
        assertThat(testPopulationSize.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPopulationSize.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPopulationSize.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingPopulationSize() throws Exception {
        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();
        populationSize.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPopulationSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, populationSize.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPopulationSize() throws Exception {
        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();
        populationSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopulationSizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isBadRequest());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPopulationSize() throws Exception {
        int databaseSizeBeforeUpdate = populationSizeRepository.findAll().size();
        populationSize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopulationSizeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(populationSize))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PopulationSize in the database
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePopulationSize() throws Exception {
        // Initialize the database
        populationSizeRepository.saveAndFlush(populationSize);

        int databaseSizeBeforeDelete = populationSizeRepository.findAll().size();

        // Delete the populationSize
        restPopulationSizeMockMvc
            .perform(delete(ENTITY_API_URL_ID, populationSize.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PopulationSize> populationSizeList = populationSizeRepository.findAll();
        assertThat(populationSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
