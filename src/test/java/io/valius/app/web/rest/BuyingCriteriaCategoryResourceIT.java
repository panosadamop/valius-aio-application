package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.BuyingCriteriaCategory;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.BuyingCriteriaCategoryRepository;
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
 * Integration tests for the {@link BuyingCriteriaCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyingCriteriaCategoryResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/buying-criteria-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuyingCriteriaCategoryRepository buyingCriteriaCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyingCriteriaCategoryMockMvc;

    private BuyingCriteriaCategory buyingCriteriaCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyingCriteriaCategory createEntity(EntityManager em) {
        BuyingCriteriaCategory buyingCriteriaCategory = new BuyingCriteriaCategory()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return buyingCriteriaCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyingCriteriaCategory createUpdatedEntity(EntityManager em) {
        BuyingCriteriaCategory buyingCriteriaCategory = new BuyingCriteriaCategory()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return buyingCriteriaCategory;
    }

    @BeforeEach
    public void initTest() {
        buyingCriteriaCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createBuyingCriteriaCategory() throws Exception {
        int databaseSizeBeforeCreate = buyingCriteriaCategoryRepository.findAll().size();
        // Create the BuyingCriteriaCategory
        restBuyingCriteriaCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isCreated());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        BuyingCriteriaCategory testBuyingCriteriaCategory = buyingCriteriaCategoryList.get(buyingCriteriaCategoryList.size() - 1);
        assertThat(testBuyingCriteriaCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testBuyingCriteriaCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBuyingCriteriaCategory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createBuyingCriteriaCategoryWithExistingId() throws Exception {
        // Create the BuyingCriteriaCategory with an existing ID
        buyingCriteriaCategory.setId(1L);

        int databaseSizeBeforeCreate = buyingCriteriaCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyingCriteriaCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingCriteriaCategoryRepository.findAll().size();
        // set the field null
        buyingCriteriaCategory.setValue(null);

        // Create the BuyingCriteriaCategory, which fails.

        restBuyingCriteriaCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isBadRequest());

        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingCriteriaCategoryRepository.findAll().size();
        // set the field null
        buyingCriteriaCategory.setLanguage(null);

        // Create the BuyingCriteriaCategory, which fails.

        restBuyingCriteriaCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isBadRequest());

        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuyingCriteriaCategories() throws Exception {
        // Initialize the database
        buyingCriteriaCategoryRepository.saveAndFlush(buyingCriteriaCategory);

        // Get all the buyingCriteriaCategoryList
        restBuyingCriteriaCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyingCriteriaCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getBuyingCriteriaCategory() throws Exception {
        // Initialize the database
        buyingCriteriaCategoryRepository.saveAndFlush(buyingCriteriaCategory);

        // Get the buyingCriteriaCategory
        restBuyingCriteriaCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, buyingCriteriaCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyingCriteriaCategory.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBuyingCriteriaCategory() throws Exception {
        // Get the buyingCriteriaCategory
        restBuyingCriteriaCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyingCriteriaCategory() throws Exception {
        // Initialize the database
        buyingCriteriaCategoryRepository.saveAndFlush(buyingCriteriaCategory);

        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();

        // Update the buyingCriteriaCategory
        BuyingCriteriaCategory updatedBuyingCriteriaCategory = buyingCriteriaCategoryRepository
            .findById(buyingCriteriaCategory.getId())
            .get();
        // Disconnect from session so that the updates on updatedBuyingCriteriaCategory are not directly saved in db
        em.detach(updatedBuyingCriteriaCategory);
        updatedBuyingCriteriaCategory.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restBuyingCriteriaCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBuyingCriteriaCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBuyingCriteriaCategory))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteriaCategory testBuyingCriteriaCategory = buyingCriteriaCategoryList.get(buyingCriteriaCategoryList.size() - 1);
        assertThat(testBuyingCriteriaCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBuyingCriteriaCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteriaCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingBuyingCriteriaCategory() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();
        buyingCriteriaCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyingCriteriaCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyingCriteriaCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyingCriteriaCategory() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();
        buyingCriteriaCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyingCriteriaCategory() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();
        buyingCriteriaCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyingCriteriaCategoryWithPatch() throws Exception {
        // Initialize the database
        buyingCriteriaCategoryRepository.saveAndFlush(buyingCriteriaCategory);

        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();

        // Update the buyingCriteriaCategory using partial update
        BuyingCriteriaCategory partialUpdatedBuyingCriteriaCategory = new BuyingCriteriaCategory();
        partialUpdatedBuyingCriteriaCategory.setId(buyingCriteriaCategory.getId());

        partialUpdatedBuyingCriteriaCategory.description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restBuyingCriteriaCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyingCriteriaCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyingCriteriaCategory))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteriaCategory testBuyingCriteriaCategory = buyingCriteriaCategoryList.get(buyingCriteriaCategoryList.size() - 1);
        assertThat(testBuyingCriteriaCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testBuyingCriteriaCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteriaCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateBuyingCriteriaCategoryWithPatch() throws Exception {
        // Initialize the database
        buyingCriteriaCategoryRepository.saveAndFlush(buyingCriteriaCategory);

        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();

        // Update the buyingCriteriaCategory using partial update
        BuyingCriteriaCategory partialUpdatedBuyingCriteriaCategory = new BuyingCriteriaCategory();
        partialUpdatedBuyingCriteriaCategory.setId(buyingCriteriaCategory.getId());

        partialUpdatedBuyingCriteriaCategory.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restBuyingCriteriaCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyingCriteriaCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyingCriteriaCategory))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteriaCategory testBuyingCriteriaCategory = buyingCriteriaCategoryList.get(buyingCriteriaCategoryList.size() - 1);
        assertThat(testBuyingCriteriaCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBuyingCriteriaCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteriaCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingBuyingCriteriaCategory() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();
        buyingCriteriaCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyingCriteriaCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyingCriteriaCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyingCriteriaCategory() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();
        buyingCriteriaCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyingCriteriaCategory() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaCategoryRepository.findAll().size();
        buyingCriteriaCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyingCriteriaCategory in the database
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyingCriteriaCategory() throws Exception {
        // Initialize the database
        buyingCriteriaCategoryRepository.saveAndFlush(buyingCriteriaCategory);

        int databaseSizeBeforeDelete = buyingCriteriaCategoryRepository.findAll().size();

        // Delete the buyingCriteriaCategory
        restBuyingCriteriaCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyingCriteriaCategory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuyingCriteriaCategory> buyingCriteriaCategoryList = buyingCriteriaCategoryRepository.findAll();
        assertThat(buyingCriteriaCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
