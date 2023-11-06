package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketSegmentationTypeB2bCategories;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketSegmentationTypeB2bCategoriesRepository;
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
 * Integration tests for the {@link MarketSegmentationTypeB2bCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketSegmentationTypeB2bCategoriesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/market-segmentation-type-b-2-b-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketSegmentationTypeB2bCategoriesRepository marketSegmentationTypeB2bCategoriesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketSegmentationTypeB2bCategoriesMockMvc;

    private MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2bCategories createEntity(EntityManager em) {
        MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories = new MarketSegmentationTypeB2bCategories()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .placeholder(DEFAULT_PLACEHOLDER)
            .uniqueCharacteristic(DEFAULT_UNIQUE_CHARACTERISTIC)
            .language(DEFAULT_LANGUAGE);
        return marketSegmentationTypeB2bCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2bCategories createUpdatedEntity(EntityManager em) {
        MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories = new MarketSegmentationTypeB2bCategories()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .placeholder(UPDATED_PLACEHOLDER)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .language(UPDATED_LANGUAGE);
        return marketSegmentationTypeB2bCategories;
    }

    @BeforeEach
    public void initTest() {
        marketSegmentationTypeB2bCategories = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2bCategories() throws Exception {
        int databaseSizeBeforeCreate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        // Create the MarketSegmentationTypeB2bCategories
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isCreated());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSegmentationTypeB2bCategories testMarketSegmentationTypeB2bCategories = marketSegmentationTypeB2bCategoriesList.get(
            marketSegmentationTypeB2bCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bCategories.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2bCategories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bCategories.getPlaceholder()).isEqualTo(DEFAULT_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2bCategories.getUniqueCharacteristic()).isEqualTo(DEFAULT_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2bCategories.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2bCategoriesWithExistingId() throws Exception {
        // Create the MarketSegmentationTypeB2bCategories with an existing ID
        marketSegmentationTypeB2bCategories.setId(1L);

        int databaseSizeBeforeCreate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2bCategories.setValue(null);

        // Create the MarketSegmentationTypeB2bCategories, which fails.

        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUniqueCharacteristicIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2bCategories.setUniqueCharacteristic(null);

        // Create the MarketSegmentationTypeB2bCategories, which fails.

        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2bCategories.setLanguage(null);

        // Create the MarketSegmentationTypeB2bCategories, which fails.

        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketSegmentationTypeB2bCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bCategoriesRepository.saveAndFlush(marketSegmentationTypeB2bCategories);

        // Get all the marketSegmentationTypeB2bCategoriesList
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSegmentationTypeB2bCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].placeholder").value(hasItem(DEFAULT_PLACEHOLDER)))
            .andExpect(jsonPath("$.[*].uniqueCharacteristic").value(hasItem(DEFAULT_UNIQUE_CHARACTERISTIC)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketSegmentationTypeB2bCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bCategoriesRepository.saveAndFlush(marketSegmentationTypeB2bCategories);

        // Get the marketSegmentationTypeB2bCategories
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, marketSegmentationTypeB2bCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketSegmentationTypeB2bCategories.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.placeholder").value(DEFAULT_PLACEHOLDER))
            .andExpect(jsonPath("$.uniqueCharacteristic").value(DEFAULT_UNIQUE_CHARACTERISTIC))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketSegmentationTypeB2bCategories() throws Exception {
        // Get the marketSegmentationTypeB2bCategories
        restMarketSegmentationTypeB2bCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketSegmentationTypeB2bCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bCategoriesRepository.saveAndFlush(marketSegmentationTypeB2bCategories);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();

        // Update the marketSegmentationTypeB2bCategories
        MarketSegmentationTypeB2bCategories updatedMarketSegmentationTypeB2bCategories = marketSegmentationTypeB2bCategoriesRepository
            .findById(marketSegmentationTypeB2bCategories.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketSegmentationTypeB2bCategories are not directly saved in db
        em.detach(updatedMarketSegmentationTypeB2bCategories);
        updatedMarketSegmentationTypeB2bCategories
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .placeholder(UPDATED_PLACEHOLDER)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketSegmentationTypeB2bCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2bCategories testMarketSegmentationTypeB2bCategories = marketSegmentationTypeB2bCategoriesList.get(
            marketSegmentationTypeB2bCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bCategories.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2bCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bCategories.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2bCategories.getUniqueCharacteristic()).isEqualTo(UPDATED_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2bCategories.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketSegmentationTypeB2bCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        marketSegmentationTypeB2bCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketSegmentationTypeB2bCategories.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketSegmentationTypeB2bCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        marketSegmentationTypeB2bCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketSegmentationTypeB2bCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        marketSegmentationTypeB2bCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketSegmentationTypeB2bCategoriesWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bCategoriesRepository.saveAndFlush(marketSegmentationTypeB2bCategories);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();

        // Update the marketSegmentationTypeB2bCategories using partial update
        MarketSegmentationTypeB2bCategories partialUpdatedMarketSegmentationTypeB2bCategories = new MarketSegmentationTypeB2bCategories();
        partialUpdatedMarketSegmentationTypeB2bCategories.setId(marketSegmentationTypeB2bCategories.getId());

        partialUpdatedMarketSegmentationTypeB2bCategories.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2bCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2bCategories testMarketSegmentationTypeB2bCategories = marketSegmentationTypeB2bCategoriesList.get(
            marketSegmentationTypeB2bCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bCategories.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2bCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bCategories.getPlaceholder()).isEqualTo(DEFAULT_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2bCategories.getUniqueCharacteristic()).isEqualTo(DEFAULT_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2bCategories.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketSegmentationTypeB2bCategoriesWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bCategoriesRepository.saveAndFlush(marketSegmentationTypeB2bCategories);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();

        // Update the marketSegmentationTypeB2bCategories using partial update
        MarketSegmentationTypeB2bCategories partialUpdatedMarketSegmentationTypeB2bCategories = new MarketSegmentationTypeB2bCategories();
        partialUpdatedMarketSegmentationTypeB2bCategories.setId(marketSegmentationTypeB2bCategories.getId());

        partialUpdatedMarketSegmentationTypeB2bCategories
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .placeholder(UPDATED_PLACEHOLDER)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2bCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2bCategories testMarketSegmentationTypeB2bCategories = marketSegmentationTypeB2bCategoriesList.get(
            marketSegmentationTypeB2bCategoriesList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bCategories.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2bCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bCategories.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testMarketSegmentationTypeB2bCategories.getUniqueCharacteristic()).isEqualTo(UPDATED_UNIQUE_CHARACTERISTIC);
        assertThat(testMarketSegmentationTypeB2bCategories.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketSegmentationTypeB2bCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        marketSegmentationTypeB2bCategories.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketSegmentationTypeB2bCategories.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketSegmentationTypeB2bCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        marketSegmentationTypeB2bCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketSegmentationTypeB2bCategories() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bCategoriesRepository.findAll().size();
        marketSegmentationTypeB2bCategories.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bCategories))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2bCategories in the database
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketSegmentationTypeB2bCategories() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bCategoriesRepository.saveAndFlush(marketSegmentationTypeB2bCategories);

        int databaseSizeBeforeDelete = marketSegmentationTypeB2bCategoriesRepository.findAll().size();

        // Delete the marketSegmentationTypeB2bCategories
        restMarketSegmentationTypeB2bCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketSegmentationTypeB2bCategories.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategoriesList =
            marketSegmentationTypeB2bCategoriesRepository.findAll();
        assertThat(marketSegmentationTypeB2bCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
