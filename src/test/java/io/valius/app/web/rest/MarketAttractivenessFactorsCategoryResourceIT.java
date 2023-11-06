package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketAttractivenessFactorsCategory;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketAttractivenessFactorsCategoryRepository;
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
 * Integration tests for the {@link MarketAttractivenessFactorsCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketAttractivenessFactorsCategoryResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAB = 1;
    private static final Integer UPDATED_TAB = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/market-attractiveness-factors-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketAttractivenessFactorsCategoryRepository marketAttractivenessFactorsCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketAttractivenessFactorsCategoryMockMvc;

    private MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketAttractivenessFactorsCategory createEntity(EntityManager em) {
        MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory = new MarketAttractivenessFactorsCategory()
            .value(DEFAULT_VALUE)
            .tab(DEFAULT_TAB)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return marketAttractivenessFactorsCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketAttractivenessFactorsCategory createUpdatedEntity(EntityManager em) {
        MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory = new MarketAttractivenessFactorsCategory()
            .value(UPDATED_VALUE)
            .tab(UPDATED_TAB)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return marketAttractivenessFactorsCategory;
    }

    @BeforeEach
    public void initTest() {
        marketAttractivenessFactorsCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketAttractivenessFactorsCategory() throws Exception {
        int databaseSizeBeforeCreate = marketAttractivenessFactorsCategoryRepository.findAll().size();
        // Create the MarketAttractivenessFactorsCategory
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isCreated());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MarketAttractivenessFactorsCategory testMarketAttractivenessFactorsCategory = marketAttractivenessFactorsCategoryList.get(
            marketAttractivenessFactorsCategoryList.size() - 1
        );
        assertThat(testMarketAttractivenessFactorsCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketAttractivenessFactorsCategory.getTab()).isEqualTo(DEFAULT_TAB);
        assertThat(testMarketAttractivenessFactorsCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketAttractivenessFactorsCategory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketAttractivenessFactorsCategoryWithExistingId() throws Exception {
        // Create the MarketAttractivenessFactorsCategory with an existing ID
        marketAttractivenessFactorsCategory.setId(1L);

        int databaseSizeBeforeCreate = marketAttractivenessFactorsCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketAttractivenessFactorsCategoryRepository.findAll().size();
        // set the field null
        marketAttractivenessFactorsCategory.setValue(null);

        // Create the MarketAttractivenessFactorsCategory, which fails.

        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTabIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketAttractivenessFactorsCategoryRepository.findAll().size();
        // set the field null
        marketAttractivenessFactorsCategory.setTab(null);

        // Create the MarketAttractivenessFactorsCategory, which fails.

        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketAttractivenessFactorsCategoryRepository.findAll().size();
        // set the field null
        marketAttractivenessFactorsCategory.setLanguage(null);

        // Create the MarketAttractivenessFactorsCategory, which fails.

        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketAttractivenessFactorsCategories() throws Exception {
        // Initialize the database
        marketAttractivenessFactorsCategoryRepository.saveAndFlush(marketAttractivenessFactorsCategory);

        // Get all the marketAttractivenessFactorsCategoryList
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketAttractivenessFactorsCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].tab").value(hasItem(DEFAULT_TAB)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketAttractivenessFactorsCategory() throws Exception {
        // Initialize the database
        marketAttractivenessFactorsCategoryRepository.saveAndFlush(marketAttractivenessFactorsCategory);

        // Get the marketAttractivenessFactorsCategory
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, marketAttractivenessFactorsCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketAttractivenessFactorsCategory.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.tab").value(DEFAULT_TAB))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketAttractivenessFactorsCategory() throws Exception {
        // Get the marketAttractivenessFactorsCategory
        restMarketAttractivenessFactorsCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketAttractivenessFactorsCategory() throws Exception {
        // Initialize the database
        marketAttractivenessFactorsCategoryRepository.saveAndFlush(marketAttractivenessFactorsCategory);

        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();

        // Update the marketAttractivenessFactorsCategory
        MarketAttractivenessFactorsCategory updatedMarketAttractivenessFactorsCategory = marketAttractivenessFactorsCategoryRepository
            .findById(marketAttractivenessFactorsCategory.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketAttractivenessFactorsCategory are not directly saved in db
        em.detach(updatedMarketAttractivenessFactorsCategory);
        updatedMarketAttractivenessFactorsCategory
            .value(UPDATED_VALUE)
            .tab(UPDATED_TAB)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);

        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketAttractivenessFactorsCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketAttractivenessFactorsCategory))
            )
            .andExpect(status().isOk());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
        MarketAttractivenessFactorsCategory testMarketAttractivenessFactorsCategory = marketAttractivenessFactorsCategoryList.get(
            marketAttractivenessFactorsCategoryList.size() - 1
        );
        assertThat(testMarketAttractivenessFactorsCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketAttractivenessFactorsCategory.getTab()).isEqualTo(UPDATED_TAB);
        assertThat(testMarketAttractivenessFactorsCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketAttractivenessFactorsCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketAttractivenessFactorsCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();
        marketAttractivenessFactorsCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketAttractivenessFactorsCategory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketAttractivenessFactorsCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();
        marketAttractivenessFactorsCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketAttractivenessFactorsCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();
        marketAttractivenessFactorsCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketAttractivenessFactorsCategoryWithPatch() throws Exception {
        // Initialize the database
        marketAttractivenessFactorsCategoryRepository.saveAndFlush(marketAttractivenessFactorsCategory);

        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();

        // Update the marketAttractivenessFactorsCategory using partial update
        MarketAttractivenessFactorsCategory partialUpdatedMarketAttractivenessFactorsCategory = new MarketAttractivenessFactorsCategory();
        partialUpdatedMarketAttractivenessFactorsCategory.setId(marketAttractivenessFactorsCategory.getId());

        partialUpdatedMarketAttractivenessFactorsCategory.tab(UPDATED_TAB);

        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketAttractivenessFactorsCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketAttractivenessFactorsCategory))
            )
            .andExpect(status().isOk());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
        MarketAttractivenessFactorsCategory testMarketAttractivenessFactorsCategory = marketAttractivenessFactorsCategoryList.get(
            marketAttractivenessFactorsCategoryList.size() - 1
        );
        assertThat(testMarketAttractivenessFactorsCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketAttractivenessFactorsCategory.getTab()).isEqualTo(UPDATED_TAB);
        assertThat(testMarketAttractivenessFactorsCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketAttractivenessFactorsCategory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketAttractivenessFactorsCategoryWithPatch() throws Exception {
        // Initialize the database
        marketAttractivenessFactorsCategoryRepository.saveAndFlush(marketAttractivenessFactorsCategory);

        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();

        // Update the marketAttractivenessFactorsCategory using partial update
        MarketAttractivenessFactorsCategory partialUpdatedMarketAttractivenessFactorsCategory = new MarketAttractivenessFactorsCategory();
        partialUpdatedMarketAttractivenessFactorsCategory.setId(marketAttractivenessFactorsCategory.getId());

        partialUpdatedMarketAttractivenessFactorsCategory
            .value(UPDATED_VALUE)
            .tab(UPDATED_TAB)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);

        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketAttractivenessFactorsCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketAttractivenessFactorsCategory))
            )
            .andExpect(status().isOk());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
        MarketAttractivenessFactorsCategory testMarketAttractivenessFactorsCategory = marketAttractivenessFactorsCategoryList.get(
            marketAttractivenessFactorsCategoryList.size() - 1
        );
        assertThat(testMarketAttractivenessFactorsCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketAttractivenessFactorsCategory.getTab()).isEqualTo(UPDATED_TAB);
        assertThat(testMarketAttractivenessFactorsCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketAttractivenessFactorsCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketAttractivenessFactorsCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();
        marketAttractivenessFactorsCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketAttractivenessFactorsCategory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketAttractivenessFactorsCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();
        marketAttractivenessFactorsCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketAttractivenessFactorsCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketAttractivenessFactorsCategoryRepository.findAll().size();
        marketAttractivenessFactorsCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketAttractivenessFactorsCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketAttractivenessFactorsCategory in the database
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketAttractivenessFactorsCategory() throws Exception {
        // Initialize the database
        marketAttractivenessFactorsCategoryRepository.saveAndFlush(marketAttractivenessFactorsCategory);

        int databaseSizeBeforeDelete = marketAttractivenessFactorsCategoryRepository.findAll().size();

        // Delete the marketAttractivenessFactorsCategory
        restMarketAttractivenessFactorsCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketAttractivenessFactorsCategory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategoryList =
            marketAttractivenessFactorsCategoryRepository.findAll();
        assertThat(marketAttractivenessFactorsCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
