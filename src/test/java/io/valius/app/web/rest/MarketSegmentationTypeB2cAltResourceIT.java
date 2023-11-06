package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketSegmentationTypeB2cAlt;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketSegmentationTypeB2cAltRepository;
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
 * Integration tests for the {@link MarketSegmentationTypeB2cAltResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketSegmentationTypeB2cAltResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/market-segmentation-type-b-2-c-alts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketSegmentationTypeB2cAltRepository marketSegmentationTypeB2cAltRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketSegmentationTypeB2cAltMockMvc;

    private MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2cAlt createEntity(EntityManager em) {
        MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt = new MarketSegmentationTypeB2cAlt()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return marketSegmentationTypeB2cAlt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2cAlt createUpdatedEntity(EntityManager em) {
        MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt = new MarketSegmentationTypeB2cAlt()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return marketSegmentationTypeB2cAlt;
    }

    @BeforeEach
    public void initTest() {
        marketSegmentationTypeB2cAlt = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2cAlt() throws Exception {
        int databaseSizeBeforeCreate = marketSegmentationTypeB2cAltRepository.findAll().size();
        // Create the MarketSegmentationTypeB2cAlt
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isCreated());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSegmentationTypeB2cAlt testMarketSegmentationTypeB2cAlt = marketSegmentationTypeB2cAltList.get(
            marketSegmentationTypeB2cAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cAlt.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2cAlt.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cAlt.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2cAltWithExistingId() throws Exception {
        // Create the MarketSegmentationTypeB2cAlt with an existing ID
        marketSegmentationTypeB2cAlt.setId(1L);

        int databaseSizeBeforeCreate = marketSegmentationTypeB2cAltRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2cAltRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2cAlt.setValue(null);

        // Create the MarketSegmentationTypeB2cAlt, which fails.

        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2cAltRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2cAlt.setLanguage(null);

        // Create the MarketSegmentationTypeB2cAlt, which fails.

        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketSegmentationTypeB2cAlts() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cAltRepository.saveAndFlush(marketSegmentationTypeB2cAlt);

        // Get all the marketSegmentationTypeB2cAltList
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSegmentationTypeB2cAlt.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketSegmentationTypeB2cAlt() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cAltRepository.saveAndFlush(marketSegmentationTypeB2cAlt);

        // Get the marketSegmentationTypeB2cAlt
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(get(ENTITY_API_URL_ID, marketSegmentationTypeB2cAlt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketSegmentationTypeB2cAlt.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketSegmentationTypeB2cAlt() throws Exception {
        // Get the marketSegmentationTypeB2cAlt
        restMarketSegmentationTypeB2cAltMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketSegmentationTypeB2cAlt() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cAltRepository.saveAndFlush(marketSegmentationTypeB2cAlt);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();

        // Update the marketSegmentationTypeB2cAlt
        MarketSegmentationTypeB2cAlt updatedMarketSegmentationTypeB2cAlt = marketSegmentationTypeB2cAltRepository
            .findById(marketSegmentationTypeB2cAlt.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketSegmentationTypeB2cAlt are not directly saved in db
        em.detach(updatedMarketSegmentationTypeB2cAlt);
        updatedMarketSegmentationTypeB2cAlt.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketSegmentationTypeB2cAlt.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2cAlt testMarketSegmentationTypeB2cAlt = marketSegmentationTypeB2cAltList.get(
            marketSegmentationTypeB2cAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cAlt.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2cAlt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cAlt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketSegmentationTypeB2cAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();
        marketSegmentationTypeB2cAlt.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketSegmentationTypeB2cAlt.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketSegmentationTypeB2cAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();
        marketSegmentationTypeB2cAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketSegmentationTypeB2cAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();
        marketSegmentationTypeB2cAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketSegmentationTypeB2cAltWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cAltRepository.saveAndFlush(marketSegmentationTypeB2cAlt);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();

        // Update the marketSegmentationTypeB2cAlt using partial update
        MarketSegmentationTypeB2cAlt partialUpdatedMarketSegmentationTypeB2cAlt = new MarketSegmentationTypeB2cAlt();
        partialUpdatedMarketSegmentationTypeB2cAlt.setId(marketSegmentationTypeB2cAlt.getId());

        partialUpdatedMarketSegmentationTypeB2cAlt.description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2cAlt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2cAlt testMarketSegmentationTypeB2cAlt = marketSegmentationTypeB2cAltList.get(
            marketSegmentationTypeB2cAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cAlt.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2cAlt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cAlt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketSegmentationTypeB2cAltWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cAltRepository.saveAndFlush(marketSegmentationTypeB2cAlt);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();

        // Update the marketSegmentationTypeB2cAlt using partial update
        MarketSegmentationTypeB2cAlt partialUpdatedMarketSegmentationTypeB2cAlt = new MarketSegmentationTypeB2cAlt();
        partialUpdatedMarketSegmentationTypeB2cAlt.setId(marketSegmentationTypeB2cAlt.getId());

        partialUpdatedMarketSegmentationTypeB2cAlt.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2cAlt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2cAlt testMarketSegmentationTypeB2cAlt = marketSegmentationTypeB2cAltList.get(
            marketSegmentationTypeB2cAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2cAlt.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2cAlt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2cAlt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketSegmentationTypeB2cAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();
        marketSegmentationTypeB2cAlt.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketSegmentationTypeB2cAlt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketSegmentationTypeB2cAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();
        marketSegmentationTypeB2cAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketSegmentationTypeB2cAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2cAltRepository.findAll().size();
        marketSegmentationTypeB2cAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2cAlt))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2cAlt in the database
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketSegmentationTypeB2cAlt() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2cAltRepository.saveAndFlush(marketSegmentationTypeB2cAlt);

        int databaseSizeBeforeDelete = marketSegmentationTypeB2cAltRepository.findAll().size();

        // Delete the marketSegmentationTypeB2cAlt
        restMarketSegmentationTypeB2cAltMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketSegmentationTypeB2cAlt.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAltList = marketSegmentationTypeB2cAltRepository.findAll();
        assertThat(marketSegmentationTypeB2cAltList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
