package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.BuyingCriteriaWeighting;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.BuyingCriteriaWeightingRepository;
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
 * Integration tests for the {@link BuyingCriteriaWeightingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyingCriteriaWeightingResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/buying-criteria-weightings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuyingCriteriaWeightingRepository buyingCriteriaWeightingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyingCriteriaWeightingMockMvc;

    private BuyingCriteriaWeighting buyingCriteriaWeighting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyingCriteriaWeighting createEntity(EntityManager em) {
        BuyingCriteriaWeighting buyingCriteriaWeighting = new BuyingCriteriaWeighting()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return buyingCriteriaWeighting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyingCriteriaWeighting createUpdatedEntity(EntityManager em) {
        BuyingCriteriaWeighting buyingCriteriaWeighting = new BuyingCriteriaWeighting()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return buyingCriteriaWeighting;
    }

    @BeforeEach
    public void initTest() {
        buyingCriteriaWeighting = createEntity(em);
    }

    @Test
    @Transactional
    void createBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeCreate = buyingCriteriaWeightingRepository.findAll().size();
        // Create the BuyingCriteriaWeighting
        restBuyingCriteriaWeightingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isCreated());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeCreate + 1);
        BuyingCriteriaWeighting testBuyingCriteriaWeighting = buyingCriteriaWeightingList.get(buyingCriteriaWeightingList.size() - 1);
        assertThat(testBuyingCriteriaWeighting.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testBuyingCriteriaWeighting.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBuyingCriteriaWeighting.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createBuyingCriteriaWeightingWithExistingId() throws Exception {
        // Create the BuyingCriteriaWeighting with an existing ID
        buyingCriteriaWeighting.setId(1L);

        int databaseSizeBeforeCreate = buyingCriteriaWeightingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyingCriteriaWeightingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingCriteriaWeightingRepository.findAll().size();
        // set the field null
        buyingCriteriaWeighting.setValue(null);

        // Create the BuyingCriteriaWeighting, which fails.

        restBuyingCriteriaWeightingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingCriteriaWeightingRepository.findAll().size();
        // set the field null
        buyingCriteriaWeighting.setLanguage(null);

        // Create the BuyingCriteriaWeighting, which fails.

        restBuyingCriteriaWeightingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuyingCriteriaWeightings() throws Exception {
        // Initialize the database
        buyingCriteriaWeightingRepository.saveAndFlush(buyingCriteriaWeighting);

        // Get all the buyingCriteriaWeightingList
        restBuyingCriteriaWeightingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyingCriteriaWeighting.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getBuyingCriteriaWeighting() throws Exception {
        // Initialize the database
        buyingCriteriaWeightingRepository.saveAndFlush(buyingCriteriaWeighting);

        // Get the buyingCriteriaWeighting
        restBuyingCriteriaWeightingMockMvc
            .perform(get(ENTITY_API_URL_ID, buyingCriteriaWeighting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyingCriteriaWeighting.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBuyingCriteriaWeighting() throws Exception {
        // Get the buyingCriteriaWeighting
        restBuyingCriteriaWeightingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyingCriteriaWeighting() throws Exception {
        // Initialize the database
        buyingCriteriaWeightingRepository.saveAndFlush(buyingCriteriaWeighting);

        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();

        // Update the buyingCriteriaWeighting
        BuyingCriteriaWeighting updatedBuyingCriteriaWeighting = buyingCriteriaWeightingRepository
            .findById(buyingCriteriaWeighting.getId())
            .get();
        // Disconnect from session so that the updates on updatedBuyingCriteriaWeighting are not directly saved in db
        em.detach(updatedBuyingCriteriaWeighting);
        updatedBuyingCriteriaWeighting.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBuyingCriteriaWeighting))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteriaWeighting testBuyingCriteriaWeighting = buyingCriteriaWeightingList.get(buyingCriteriaWeightingList.size() - 1);
        assertThat(testBuyingCriteriaWeighting.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBuyingCriteriaWeighting.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteriaWeighting.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();
        buyingCriteriaWeighting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();
        buyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();
        buyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyingCriteriaWeightingWithPatch() throws Exception {
        // Initialize the database
        buyingCriteriaWeightingRepository.saveAndFlush(buyingCriteriaWeighting);

        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();

        // Update the buyingCriteriaWeighting using partial update
        BuyingCriteriaWeighting partialUpdatedBuyingCriteriaWeighting = new BuyingCriteriaWeighting();
        partialUpdatedBuyingCriteriaWeighting.setId(buyingCriteriaWeighting.getId());

        partialUpdatedBuyingCriteriaWeighting.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);

        restBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyingCriteriaWeighting))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteriaWeighting testBuyingCriteriaWeighting = buyingCriteriaWeightingList.get(buyingCriteriaWeightingList.size() - 1);
        assertThat(testBuyingCriteriaWeighting.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBuyingCriteriaWeighting.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteriaWeighting.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateBuyingCriteriaWeightingWithPatch() throws Exception {
        // Initialize the database
        buyingCriteriaWeightingRepository.saveAndFlush(buyingCriteriaWeighting);

        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();

        // Update the buyingCriteriaWeighting using partial update
        BuyingCriteriaWeighting partialUpdatedBuyingCriteriaWeighting = new BuyingCriteriaWeighting();
        partialUpdatedBuyingCriteriaWeighting.setId(buyingCriteriaWeighting.getId());

        partialUpdatedBuyingCriteriaWeighting.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyingCriteriaWeighting))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteriaWeighting testBuyingCriteriaWeighting = buyingCriteriaWeightingList.get(buyingCriteriaWeightingList.size() - 1);
        assertThat(testBuyingCriteriaWeighting.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBuyingCriteriaWeighting.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteriaWeighting.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();
        buyingCriteriaWeighting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();
        buyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaWeightingRepository.findAll().size();
        buyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteriaWeighting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyingCriteriaWeighting in the database
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyingCriteriaWeighting() throws Exception {
        // Initialize the database
        buyingCriteriaWeightingRepository.saveAndFlush(buyingCriteriaWeighting);

        int databaseSizeBeforeDelete = buyingCriteriaWeightingRepository.findAll().size();

        // Delete the buyingCriteriaWeighting
        restBuyingCriteriaWeightingMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyingCriteriaWeighting.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuyingCriteriaWeighting> buyingCriteriaWeightingList = buyingCriteriaWeightingRepository.findAll();
        assertThat(buyingCriteriaWeightingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
