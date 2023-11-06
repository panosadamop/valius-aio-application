package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketSegmentationTypeB2bAlt;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketSegmentationTypeB2bAltRepository;
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
 * Integration tests for the {@link MarketSegmentationTypeB2bAltResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketSegmentationTypeB2bAltResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/market-segmentation-type-b-2-b-alts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketSegmentationTypeB2bAltRepository marketSegmentationTypeB2bAltRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketSegmentationTypeB2bAltMockMvc;

    private MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2bAlt createEntity(EntityManager em) {
        MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt = new MarketSegmentationTypeB2bAlt()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return marketSegmentationTypeB2bAlt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2bAlt createUpdatedEntity(EntityManager em) {
        MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt = new MarketSegmentationTypeB2bAlt()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return marketSegmentationTypeB2bAlt;
    }

    @BeforeEach
    public void initTest() {
        marketSegmentationTypeB2bAlt = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2bAlt() throws Exception {
        int databaseSizeBeforeCreate = marketSegmentationTypeB2bAltRepository.findAll().size();
        // Create the MarketSegmentationTypeB2bAlt
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isCreated());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSegmentationTypeB2bAlt testMarketSegmentationTypeB2bAlt = marketSegmentationTypeB2bAltList.get(
            marketSegmentationTypeB2bAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bAlt.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2bAlt.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bAlt.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2bAltWithExistingId() throws Exception {
        // Create the MarketSegmentationTypeB2bAlt with an existing ID
        marketSegmentationTypeB2bAlt.setId(1L);

        int databaseSizeBeforeCreate = marketSegmentationTypeB2bAltRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2bAltRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2bAlt.setValue(null);

        // Create the MarketSegmentationTypeB2bAlt, which fails.

        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2bAltRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2bAlt.setLanguage(null);

        // Create the MarketSegmentationTypeB2bAlt, which fails.

        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketSegmentationTypeB2bAlts() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bAltRepository.saveAndFlush(marketSegmentationTypeB2bAlt);

        // Get all the marketSegmentationTypeB2bAltList
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSegmentationTypeB2bAlt.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketSegmentationTypeB2bAlt() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bAltRepository.saveAndFlush(marketSegmentationTypeB2bAlt);

        // Get the marketSegmentationTypeB2bAlt
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(get(ENTITY_API_URL_ID, marketSegmentationTypeB2bAlt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketSegmentationTypeB2bAlt.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketSegmentationTypeB2bAlt() throws Exception {
        // Get the marketSegmentationTypeB2bAlt
        restMarketSegmentationTypeB2bAltMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketSegmentationTypeB2bAlt() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bAltRepository.saveAndFlush(marketSegmentationTypeB2bAlt);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();

        // Update the marketSegmentationTypeB2bAlt
        MarketSegmentationTypeB2bAlt updatedMarketSegmentationTypeB2bAlt = marketSegmentationTypeB2bAltRepository
            .findById(marketSegmentationTypeB2bAlt.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketSegmentationTypeB2bAlt are not directly saved in db
        em.detach(updatedMarketSegmentationTypeB2bAlt);
        updatedMarketSegmentationTypeB2bAlt.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketSegmentationTypeB2bAlt.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2bAlt testMarketSegmentationTypeB2bAlt = marketSegmentationTypeB2bAltList.get(
            marketSegmentationTypeB2bAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bAlt.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2bAlt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bAlt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketSegmentationTypeB2bAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();
        marketSegmentationTypeB2bAlt.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketSegmentationTypeB2bAlt.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketSegmentationTypeB2bAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();
        marketSegmentationTypeB2bAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketSegmentationTypeB2bAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();
        marketSegmentationTypeB2bAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketSegmentationTypeB2bAltWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bAltRepository.saveAndFlush(marketSegmentationTypeB2bAlt);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();

        // Update the marketSegmentationTypeB2bAlt using partial update
        MarketSegmentationTypeB2bAlt partialUpdatedMarketSegmentationTypeB2bAlt = new MarketSegmentationTypeB2bAlt();
        partialUpdatedMarketSegmentationTypeB2bAlt.setId(marketSegmentationTypeB2bAlt.getId());

        partialUpdatedMarketSegmentationTypeB2bAlt.description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2bAlt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2bAlt testMarketSegmentationTypeB2bAlt = marketSegmentationTypeB2bAltList.get(
            marketSegmentationTypeB2bAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bAlt.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2bAlt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bAlt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketSegmentationTypeB2bAltWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bAltRepository.saveAndFlush(marketSegmentationTypeB2bAlt);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();

        // Update the marketSegmentationTypeB2bAlt using partial update
        MarketSegmentationTypeB2bAlt partialUpdatedMarketSegmentationTypeB2bAlt = new MarketSegmentationTypeB2bAlt();
        partialUpdatedMarketSegmentationTypeB2bAlt.setId(marketSegmentationTypeB2bAlt.getId());

        partialUpdatedMarketSegmentationTypeB2bAlt.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2bAlt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2bAlt testMarketSegmentationTypeB2bAlt = marketSegmentationTypeB2bAltList.get(
            marketSegmentationTypeB2bAltList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2bAlt.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2bAlt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2bAlt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketSegmentationTypeB2bAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();
        marketSegmentationTypeB2bAlt.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketSegmentationTypeB2bAlt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketSegmentationTypeB2bAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();
        marketSegmentationTypeB2bAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketSegmentationTypeB2bAlt() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bAltRepository.findAll().size();
        marketSegmentationTypeB2bAlt.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2bAlt))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2bAlt in the database
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketSegmentationTypeB2bAlt() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bAltRepository.saveAndFlush(marketSegmentationTypeB2bAlt);

        int databaseSizeBeforeDelete = marketSegmentationTypeB2bAltRepository.findAll().size();

        // Delete the marketSegmentationTypeB2bAlt
        restMarketSegmentationTypeB2bAltMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketSegmentationTypeB2bAlt.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAltList = marketSegmentationTypeB2bAltRepository.findAll();
        assertThat(marketSegmentationTypeB2bAltList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
