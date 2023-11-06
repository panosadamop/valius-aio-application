package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.InfoPageCategory;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.InfoPageCategoryRepository;
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
 * Integration tests for the {@link InfoPageCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InfoPageCategoryResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/info-page-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfoPageCategoryRepository infoPageCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfoPageCategoryMockMvc;

    private InfoPageCategory infoPageCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoPageCategory createEntity(EntityManager em) {
        InfoPageCategory infoPageCategory = new InfoPageCategory().value(DEFAULT_VALUE).language(DEFAULT_LANGUAGE);
        return infoPageCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoPageCategory createUpdatedEntity(EntityManager em) {
        InfoPageCategory infoPageCategory = new InfoPageCategory().value(UPDATED_VALUE).language(UPDATED_LANGUAGE);
        return infoPageCategory;
    }

    @BeforeEach
    public void initTest() {
        infoPageCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createInfoPageCategory() throws Exception {
        int databaseSizeBeforeCreate = infoPageCategoryRepository.findAll().size();
        // Create the InfoPageCategory
        restInfoPageCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isCreated());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        InfoPageCategory testInfoPageCategory = infoPageCategoryList.get(infoPageCategoryList.size() - 1);
        assertThat(testInfoPageCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testInfoPageCategory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createInfoPageCategoryWithExistingId() throws Exception {
        // Create the InfoPageCategory with an existing ID
        infoPageCategory.setId(1L);

        int databaseSizeBeforeCreate = infoPageCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoPageCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoPageCategoryRepository.findAll().size();
        // set the field null
        infoPageCategory.setValue(null);

        // Create the InfoPageCategory, which fails.

        restInfoPageCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isBadRequest());

        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = infoPageCategoryRepository.findAll().size();
        // set the field null
        infoPageCategory.setLanguage(null);

        // Create the InfoPageCategory, which fails.

        restInfoPageCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isBadRequest());

        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInfoPageCategories() throws Exception {
        // Initialize the database
        infoPageCategoryRepository.saveAndFlush(infoPageCategory);

        // Get all the infoPageCategoryList
        restInfoPageCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoPageCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getInfoPageCategory() throws Exception {
        // Initialize the database
        infoPageCategoryRepository.saveAndFlush(infoPageCategory);

        // Get the infoPageCategory
        restInfoPageCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, infoPageCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infoPageCategory.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInfoPageCategory() throws Exception {
        // Get the infoPageCategory
        restInfoPageCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInfoPageCategory() throws Exception {
        // Initialize the database
        infoPageCategoryRepository.saveAndFlush(infoPageCategory);

        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();

        // Update the infoPageCategory
        InfoPageCategory updatedInfoPageCategory = infoPageCategoryRepository.findById(infoPageCategory.getId()).get();
        // Disconnect from session so that the updates on updatedInfoPageCategory are not directly saved in db
        em.detach(updatedInfoPageCategory);
        updatedInfoPageCategory.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restInfoPageCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInfoPageCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInfoPageCategory))
            )
            .andExpect(status().isOk());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
        InfoPageCategory testInfoPageCategory = infoPageCategoryList.get(infoPageCategoryList.size() - 1);
        assertThat(testInfoPageCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testInfoPageCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingInfoPageCategory() throws Exception {
        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();
        infoPageCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoPageCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infoPageCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInfoPageCategory() throws Exception {
        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();
        infoPageCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoPageCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInfoPageCategory() throws Exception {
        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();
        infoPageCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoPageCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInfoPageCategoryWithPatch() throws Exception {
        // Initialize the database
        infoPageCategoryRepository.saveAndFlush(infoPageCategory);

        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();

        // Update the infoPageCategory using partial update
        InfoPageCategory partialUpdatedInfoPageCategory = new InfoPageCategory();
        partialUpdatedInfoPageCategory.setId(infoPageCategory.getId());

        restInfoPageCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoPageCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoPageCategory))
            )
            .andExpect(status().isOk());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
        InfoPageCategory testInfoPageCategory = infoPageCategoryList.get(infoPageCategoryList.size() - 1);
        assertThat(testInfoPageCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testInfoPageCategory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateInfoPageCategoryWithPatch() throws Exception {
        // Initialize the database
        infoPageCategoryRepository.saveAndFlush(infoPageCategory);

        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();

        // Update the infoPageCategory using partial update
        InfoPageCategory partialUpdatedInfoPageCategory = new InfoPageCategory();
        partialUpdatedInfoPageCategory.setId(infoPageCategory.getId());

        partialUpdatedInfoPageCategory.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restInfoPageCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoPageCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoPageCategory))
            )
            .andExpect(status().isOk());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
        InfoPageCategory testInfoPageCategory = infoPageCategoryList.get(infoPageCategoryList.size() - 1);
        assertThat(testInfoPageCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testInfoPageCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingInfoPageCategory() throws Exception {
        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();
        infoPageCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoPageCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, infoPageCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInfoPageCategory() throws Exception {
        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();
        infoPageCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoPageCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInfoPageCategory() throws Exception {
        int databaseSizeBeforeUpdate = infoPageCategoryRepository.findAll().size();
        infoPageCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoPageCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoPageCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoPageCategory in the database
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInfoPageCategory() throws Exception {
        // Initialize the database
        infoPageCategoryRepository.saveAndFlush(infoPageCategory);

        int databaseSizeBeforeDelete = infoPageCategoryRepository.findAll().size();

        // Delete the infoPageCategory
        restInfoPageCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, infoPageCategory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoPageCategory> infoPageCategoryList = infoPageCategoryRepository.findAll();
        assertThat(infoPageCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
