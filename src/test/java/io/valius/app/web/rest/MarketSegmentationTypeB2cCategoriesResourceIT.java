package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketSegmentationTypeB2cCategories;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketSegmentationTypeB2cCategoriesRepository;
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
 * Integration tests for the {@link MarketSegmentationTypeB2cCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketSegmentationTypeB2cCategoriesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PLACEHOLDER = "AAAAAAAAAA";
    private static final String UPDATED_PLACEHOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_UNIQUE_CHARACTERISTIC = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_CHARACTERISTIC = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/market-segmentation-type-b-2-c-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketSegmentationTypeB2cCategoriesRepository marketSegmentationTypeB2cCategoriesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketSegmentationTypeB2cCategoriesMockMvc;

    private MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2cCategories createEntity(EntityManager em) {
        MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories = new MarketSegmentationTypeB2cCategories()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .placeholder(DEFAULT_PLACEHOLDER)
            .uniqueCharacteristic(DEFAULT_UNIQUE_CHARACTERISTIC)
            .language(DEFAULT_LANGUAGE);
        return marketSegmentationTypeB2cCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2cCategories createUpdatedEntity(EntityManager em) {
        MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories = new MarketSegmentationTypeB2cCategories()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .placeholder(UPDATED_PLACEHOLDER)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .language(UPDATED_LANGUAGE);
        return marketSegmentationTypeB2cCategories;
    }

    @BeforeEach
    public void initTest() {
        marketSegmentationTypeB2cCategories = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2cCategories() throws Exception {
        int databaseSizeBeforeCreate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        // Create the MarketSegmentationTypeB2cCategories
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isCreated());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSegmentationTypeB2cCategories testMarketSegmentationTypeB2cCategories = marketSegmentationTypeB2cCategoriesList.get(
            marketSegmentationTypeB2cCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cCategories.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2cCategories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cCategories.getPlaceholder()).isEqualTo(DEFAULT_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2cCategories.getUniqueCharacteristic()).isEqualTo(DEFAULT_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2cCategories.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2cCategoriesWithExistingId() throws Exception {
        // Create the MarketSegmentationTypeB2cCategories with an existing ID
        marketSegmentationTypeB2cCategories.setId(1L);

        int databaseSizeBeforeCreate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2cCategories.setValue(null);

        // Create the MarketSegmentationTypeB2cCategories, which fails.

        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUniqueCharacteristicIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2cCategories.setUniqueCharacteristic(null);

        // Create the MarketSegmentationTypeB2cCategories, which fails.

        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2cCategories.setLanguage(null);

        // Create the MarketSegmentationTypeB2cCategories, which fails.

        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketSegmentationTypeB2cCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cCategoriesRepository.saveAndFlush(marketSegmentationTypeB2cCategories);

        // Get all the marketSegmentationTypeB2cCategoriesList
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSegmentationTypeB2cCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].placeholder").value(hasItem(DEFAULT_PLACEHOLDER)))
            .andExpect(jsonPath("$.[*].uniqueCharacteristic").value(hasItem(DEFAULT_UNIQUE_CHARACTERISTIC)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketSegmentationTypeB2cCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cCategoriesRepository.saveAndFlush(marketSegmentationTypeB2cCategories);

        // Get the marketSegmentationTypeB2cCategories
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, marketSegmentationTypeB2cCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketSegmentationTypeB2cCategories.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.placeholder").value(DEFAULT_PLACEHOLDER))
            .andExpect(jsonPath("$.uniqueCharacteristic").value(DEFAULT_UNIQUE_CHARACTERISTIC))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketSegmentationTypeB2cCategories() throws Exception {
        // Get the marketSegmentationTypeB2cCategories
        restMarketSegmentationTypeB2cCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketSegmentationTypeB2cCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cCategoriesRepository.saveAndFlush(marketSegmentationTypeB2cCategories);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();

        // Update the marketSegmentationTypeB2cCategories
        MarketSegmentationTypeB2cCategories updatedMarketSegmentationTypeB2cCategories = marketSegmentationTypeB2cCategoriesRepository
            .findById(marketSegmentationTypeB2cCategories.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketSegmentationTypeB2cCategories are not directly saved in db
        em.detach(updatedMarketSegmentationTypeB2cCategories);
        updatedMarketSegmentationTypeB2cCategories
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .placeholder(UPDATED_PLACEHOLDER)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketSegmentationTypeB2cCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2cCategories testMarketSegmentationTypeB2cCategories = marketSegmentationTypeB2cCategoriesList.get(
            marketSegmentationTypeB2cCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cCategories.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2cCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cCategories.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2cCategories.getUniqueCharacteristic()).isEqualTo(UPDATED_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2cCategories.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketSegmentationTypeB2cCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        marketSegmentationTypeB2cCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketSegmentationTypeB2cCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketSegmentationTypeB2cCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        marketSegmentationTypeB2cCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketSegmentationTypeB2cCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        marketSegmentationTypeB2cCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketSegmentationTypeB2cCategoriesWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cCategoriesRepository.saveAndFlush(marketSegmentationTypeB2cCategories);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();

        // Update the marketSegmentationTypeB2cCategories using partial update
        MarketSegmentationTypeB2cCategories partialUpdatedMarketSegmentationTypeB2cCategories = new MarketSegmentationTypeB2cCategories();
        partialUpdatedMarketSegmentationTypeB2cCategories.setId(marketSegmentationTypeB2cCategories.getId());

        partialUpdatedMarketSegmentationTypeB2cCategories
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .placeholder(UPDATED_PLACEHOLDER)
            .language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2cCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2cCategories testMarketSegmentationTypeB2cCategories = marketSegmentationTypeB2cCategoriesList.get(
            marketSegmentationTypeB2cCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cCategories.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2cCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cCategories.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2cCategories.getUniqueCharacteristic()).isEqualTo(DEFAULT_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2cCategories.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketSegmentationTypeB2cCategoriesWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cCategoriesRepository.saveAndFlush(marketSegmentationTypeB2cCategories);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();

        // Update the marketSegmentationTypeB2cCategories using partial update
        MarketSegmentationTypeB2cCategories partialUpdatedMarketSegmentationTypeB2cCategories = new MarketSegmentationTypeB2cCategories();
        partialUpdatedMarketSegmentationTypeB2cCategories.setId(marketSegmentationTypeB2cCategories.getId());

        partialUpdatedMarketSegmentationTypeB2cCategories
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .placeholder(UPDATED_PLACEHOLDER)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2cCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2cCategories testMarketSegmentationTypeB2cCategories = marketSegmentationTypeB2cCategoriesList.get(
            marketSegmentationTypeB2cCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cCategories.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2cCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cCategories.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2cCategories.getUniqueCharacteristic()).isEqualTo(UPDATED_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2cCategories.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketSegmentationTypeB2cCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        marketSegmentationTypeB2cCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketSegmentationTypeB2cCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketSegmentationTypeB2cCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        marketSegmentationTypeB2cCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketSegmentationTypeB2cCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cCategoriesRepository.findAll().size();
        marketSegmentationTypeB2cCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2cCategories in the database
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketSegmentationTypeB2cCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cCategoriesRepository.saveAndFlush(marketSegmentationTypeB2cCategories);

        int databaseSizeBeforeDelete = marketSegmentationTypeB2cCategoriesRepository.findAll().size();

        // Delete the marketSegmentationTypeB2cCategories
        restMarketSegmentationTypeB2cCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketSegmentationTypeB2cCategories.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategoriesList =
            marketSegmentationTypeB2cCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2cCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
