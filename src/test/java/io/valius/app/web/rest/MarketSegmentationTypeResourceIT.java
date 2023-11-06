package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketSegmentationType;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketSegmentationTypeRepository;
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
 * Integration tests for the {@link MarketSegmentationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketSegmentationTypeResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/market-segmentation-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketSegmentationTypeRepository marketSegmentationTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketSegmentationTypeMockMvc;

    private MarketSegmentationType marketSegmentationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationType createEntity(EntityManager em) {
        MarketSegmentationType marketSegmentationType = new MarketSegmentationType()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return marketSegmentationType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationType createUpdatedEntity(EntityManager em) {
        MarketSegmentationType marketSegmentationType = new MarketSegmentationType()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return marketSegmentationType;
    }

    @BeforeEach
    public void initTest() {
        marketSegmentationType = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketSegmentationType() throws Exception {
        int databaseSizeBeforeCreate = marketSegmentationTypeRepository.findAll().size();
        // Create the MarketSegmentationType
        restMarketSegmentationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isCreated());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSegmentationType testMarketSegmentationType = marketSegmentationTypeList.get(marketSegmentationTypeList.size() - 1);
        assertThat(testMarketSegmentationType.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationType.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeWithExistingId() throws Exception {
        // Create the MarketSegmentationType with an existing ID
        marketSegmentationType.setId(1L);

        int databaseSizeBeforeCreate = marketSegmentationTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSegmentationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeRepository.findAll().size();
        // set the field null
        marketSegmentationType.setValue(null);

        // Create the MarketSegmentationType, which fails.

        restMarketSegmentationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeRepository.findAll().size();
        // set the field null
        marketSegmentationType.setLanguage(null);

        // Create the MarketSegmentationType, which fails.

        restMarketSegmentationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketSegmentationTypes() throws Exception {
        // Initialize the database
        marketSegmentationTypeRepository.saveAndFlush(marketSegmentationType);

        // Get all the marketSegmentationTypeList
        restMarketSegmentationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSegmentationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketSegmentationType() throws Exception {
        // Initialize the database
        marketSegmentationTypeRepository.saveAndFlush(marketSegmentationType);

        // Get the marketSegmentationType
        restMarketSegmentationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, marketSegmentationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketSegmentationType.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketSegmentationType() throws Exception {
        // Get the marketSegmentationType
        restMarketSegmentationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketSegmentationType() throws Exception {
        // Initialize the database
        marketSegmentationTypeRepository.saveAndFlush(marketSegmentationType);

        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();

        // Update the marketSegmentationType
        MarketSegmentationType updatedMarketSegmentationType = marketSegmentationTypeRepository
            .findById(marketSegmentationType.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketSegmentationType are not directly saved in db
        em.detach(updatedMarketSegmentationType);
        updatedMarketSegmentationType.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketSegmentationType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketSegmentationType))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationType testMarketSegmentationType = marketSegmentationTypeList.get(marketSegmentationTypeList.size() - 1);
        assertThat(testMarketSegmentationType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationType.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketSegmentationType() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();
        marketSegmentationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketSegmentationType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketSegmentationType() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();
        marketSegmentationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketSegmentationType() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();
        marketSegmentationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketSegmentationTypeWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeRepository.saveAndFlush(marketSegmentationType);

        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();

        // Update the marketSegmentationType using partial update
        MarketSegmentationType partialUpdatedMarketSegmentationType = new MarketSegmentationType();
        partialUpdatedMarketSegmentationType.setId(marketSegmentationType.getId());

        partialUpdatedMarketSegmentationType.description(UPDATED_DESCRIPTION);

        restMarketSegmentationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationType))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationType testMarketSegmentationType = marketSegmentationTypeList.get(marketSegmentationTypeList.size() - 1);
        assertThat(testMarketSegmentationType.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationType.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketSegmentationTypeWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeRepository.saveAndFlush(marketSegmentationType);

        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();

        // Update the marketSegmentationType using partial update
        MarketSegmentationType partialUpdatedMarketSegmentationType = new MarketSegmentationType();
        partialUpdatedMarketSegmentationType.setId(marketSegmentationType.getId());

        partialUpdatedMarketSegmentationType.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationType))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationType testMarketSegmentationType = marketSegmentationTypeList.get(marketSegmentationTypeList.size() - 1);
        assertThat(testMarketSegmentationType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationType.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketSegmentationType() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();
        marketSegmentationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketSegmentationType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketSegmentationType() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();
        marketSegmentationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketSegmentationType() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeRepository.findAll().size();
        marketSegmentationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationType in the database
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketSegmentationType() throws Exception {
        // Initialize the database
        marketSegmentationTypeRepository.saveAndFlush(marketSegmentationType);

        int databaseSizeBeforeDelete = marketSegmentationTypeRepository.findAll().size();

        // Delete the marketSegmentationType
        restMarketSegmentationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketSegmentationType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketSegmentationType> marketSegmentationTypeList = marketSegmentationTypeRepository.findAll();
        assertThat(marketSegmentationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
