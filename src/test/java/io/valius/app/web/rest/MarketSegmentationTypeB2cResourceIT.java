package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketSegmentationTypeB2c;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketSegmentationTypeB2cRepository;
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
 * Integration tests for the {@link MarketSegmentationTypeB2cResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketSegmentationTypeB2cResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/market-segmentation-type-b-2-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketSegmentationTypeB2cRepository marketSegmentationTypeB2cRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketSegmentationTypeB2cMockMvc;

    private MarketSegmentationTypeB2c marketSegmentationTypeB2c;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2c createEntity(EntityManager em) {
        MarketSegmentationTypeB2c marketSegmentationTypeB2c = new MarketSegmentationTypeB2c()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return marketSegmentationTypeB2c;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2c createUpdatedEntity(EntityManager em) {
        MarketSegmentationTypeB2c marketSegmentationTypeB2c = new MarketSegmentationTypeB2c()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return marketSegmentationTypeB2c;
    }

    @BeforeEach
    public void initTest() {
        marketSegmentationTypeB2c = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2c() throws Exception {
        int databaseSizeBeforeCreate = marketSegmentationTypeB2cRepository.findAll().size();
        // Create the MarketSegmentationTypeB2c
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isCreated());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSegmentationTypeB2c testMarketSegmentationTypeB2c = marketSegmentationTypeB2cList.get(
            marketSegmentationTypeB2cList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2c.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2c.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2c.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2cWithExistingId() throws Exception {
        // Create the MarketSegmentationTypeB2c with an existing ID
        marketSegmentationTypeB2c.setId(1L);

        int databaseSizeBeforeCreate = marketSegmentationTypeB2cRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2cRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2c.setValue(null);

        // Create the MarketSegmentationTypeB2c, which fails.

        restMarketSegmentationTypeB2cMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2cRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2c.setLanguage(null);

        // Create the MarketSegmentationTypeB2c, which fails.

        restMarketSegmentationTypeB2cMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketSegmentationTypeB2cs() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cRepository.saveAndFlush(marketSegmentationTypeB2c);

        // Get all the marketSegmentationTypeB2cList
        restMarketSegmentationTypeB2cMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSegmentationTypeB2c.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketSegmentationTypeB2c() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cRepository.saveAndFlush(marketSegmentationTypeB2c);

        // Get the marketSegmentationTypeB2c
        restMarketSegmentationTypeB2cMockMvc
            .perform(get(ENTITY_API_URL_ID, marketSegmentationTypeB2c.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketSegmentationTypeB2c.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketSegmentationTypeB2c() throws Exception {
        // Get the marketSegmentationTypeB2c
        restMarketSegmentationTypeB2cMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketSegmentationTypeB2c() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cRepository.saveAndFlush(marketSegmentationTypeB2c);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();

        // Update the marketSegmentationTypeB2c
        MarketSegmentationTypeB2c updatedMarketSegmentationTypeB2c = marketSegmentationTypeB2cRepository
            .findById(marketSegmentationTypeB2c.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketSegmentationTypeB2c are not directly saved in db
        em.detach(updatedMarketSegmentationTypeB2c);
        updatedMarketSegmentationTypeB2c.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketSegmentationTypeB2c.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketSegmentationTypeB2c))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2c testMarketSegmentationTypeB2c = marketSegmentationTypeB2cList.get(
            marketSegmentationTypeB2cList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2c.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2c.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2c.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketSegmentationTypeB2c() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();
        marketSegmentationTypeB2c.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketSegmentationTypeB2c.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketSegmentationTypeB2c() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();
        marketSegmentationTypeB2c.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketSegmentationTypeB2c() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();
        marketSegmentationTypeB2c.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketSegmentationTypeB2cWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cRepository.saveAndFlush(marketSegmentationTypeB2c);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();

        // Update the marketSegmentationTypeB2c using partial update
        MarketSegmentationTypeB2c partialUpdatedMarketSegmentationTypeB2c = new MarketSegmentationTypeB2c();
        partialUpdatedMarketSegmentationTypeB2c.setId(marketSegmentationTypeB2c.getId());

        partialUpdatedMarketSegmentationTypeB2c.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2c.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2c))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2c testMarketSegmentationTypeB2c = marketSegmentationTypeB2cList.get(
            marketSegmentationTypeB2cList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2c.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2c.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2c.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketSegmentationTypeB2cWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cRepository.saveAndFlush(marketSegmentationTypeB2c);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();

        // Update the marketSegmentationTypeB2c using partial update
        MarketSegmentationTypeB2c partialUpdatedMarketSegmentationTypeB2c = new MarketSegmentationTypeB2c();
        partialUpdatedMarketSegmentationTypeB2c.setId(marketSegmentationTypeB2c.getId());

        partialUpdatedMarketSegmentationTypeB2c.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2c.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2c))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2c testMarketSegmentationTypeB2c = marketSegmentationTypeB2cList.get(
            marketSegmentationTypeB2cList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2c.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2c.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2c.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketSegmentationTypeB2c() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();
        marketSegmentationTypeB2c.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketSegmentationTypeB2c.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketSegmentationTypeB2c() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();
        marketSegmentationTypeB2c.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketSegmentationTypeB2c() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cRepository.findAll().size();
        marketSegmentationTypeB2c.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2c))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2c in the database
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketSegmentationTypeB2c() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cRepository.saveAndFlush(marketSegmentationTypeB2c);

        int databaseSizeBeforeDelete = marketSegmentationTypeB2cRepository.findAll().size();

        // Delete the marketSegmentationTypeB2c
        restMarketSegmentationTypeB2cMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketSegmentationTypeB2c.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketSegmentationTypeB2c> marketSegmentationTypeB2cList = marketSegmentationTypeB2cRepository.findAll();
        assertThat(marketSegmentationTypeB2cList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
