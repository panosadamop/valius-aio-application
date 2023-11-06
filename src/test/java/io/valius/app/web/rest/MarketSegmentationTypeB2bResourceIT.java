package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketSegmentationTypeB2b;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketSegmentationTypeB2bRepository;
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
 * Integration tests for the {@link MarketSegmentationTypeB2bResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketSegmentationTypeB2bResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/market-segmentation-type-b-2-bs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketSegmentationTypeB2bRepository marketSegmentationTypeB2bRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketSegmentationTypeB2bMockMvc;

    private MarketSegmentationTypeB2b marketSegmentationTypeB2b;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2b createEntity(EntityManager em) {
        MarketSegmentationTypeB2b marketSegmentationTypeB2b = new MarketSegmentationTypeB2b()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return marketSegmentationTypeB2b;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketSegmentationTypeB2b createUpdatedEntity(EntityManager em) {
        MarketSegmentationTypeB2b marketSegmentationTypeB2b = new MarketSegmentationTypeB2b()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return marketSegmentationTypeB2b;
    }

    @BeforeEach
    public void initTest() {
        marketSegmentationTypeB2b = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2b() throws Exception {
        int databaseSizeBeforeCreate = marketSegmentationTypeB2bRepository.findAll().size();
        // Create the MarketSegmentationTypeB2b
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isCreated());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSegmentationTypeB2b testMarketSegmentationTypeB2b = marketSegmentationTypeB2bList.get(
            marketSegmentationTypeB2bList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2b.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2b.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2b.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketSegmentationTypeB2bWithExistingId() throws Exception {
        // Create the MarketSegmentationTypeB2b with an existing ID
        marketSegmentationTypeB2b.setId(1L);

        int databaseSizeBeforeCreate = marketSegmentationTypeB2bRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2bRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2b.setValue(null);

        // Create the MarketSegmentationTypeB2b, which fails.

        restMarketSegmentationTypeB2bMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketSegmentationTypeB2bRepository.findAll().size();
        // set the field null
        marketSegmentationTypeB2b.setLanguage(null);

        // Create the MarketSegmentationTypeB2b, which fails.

        restMarketSegmentationTypeB2bMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isBadRequest());

        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketSegmentationTypeB2bs() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bRepository.saveAndFlush(marketSegmentationTypeB2b);

        // Get all the marketSegmentationTypeB2bList
        restMarketSegmentationTypeB2bMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSegmentationTypeB2b.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketSegmentationTypeB2b() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bRepository.saveAndFlush(marketSegmentationTypeB2b);

        // Get the marketSegmentationTypeB2b
        restMarketSegmentationTypeB2bMockMvc
            .perform(get(ENTITY_API_URL_ID, marketSegmentationTypeB2b.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketSegmentationTypeB2b.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketSegmentationTypeB2b() throws Exception {
        // Get the marketSegmentationTypeB2b
        restMarketSegmentationTypeB2bMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketSegmentationTypeB2b() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bRepository.saveAndFlush(marketSegmentationTypeB2b);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();

        // Update the marketSegmentationTypeB2b
        MarketSegmentationTypeB2b updatedMarketSegmentationTypeB2b = marketSegmentationTypeB2bRepository
            .findById(marketSegmentationTypeB2b.getId())
            .get();
        // Disconnect from session so that the updates on updatedMarketSegmentationTypeB2b are not directly saved in db
        em.detach(updatedMarketSegmentationTypeB2b);
        updatedMarketSegmentationTypeB2b.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketSegmentationTypeB2b.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketSegmentationTypeB2b))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2b testMarketSegmentationTypeB2b = marketSegmentationTypeB2bList.get(
            marketSegmentationTypeB2bList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2b.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2b.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2b.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketSegmentationTypeB2b() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();
        marketSegmentationTypeB2b.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketSegmentationTypeB2b.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketSegmentationTypeB2b() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();
        marketSegmentationTypeB2b.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketSegmentationTypeB2b() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();
        marketSegmentationTypeB2b.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketSegmentationTypeB2bWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bRepository.saveAndFlush(marketSegmentationTypeB2b);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();

        // Update the marketSegmentationTypeB2b using partial update
        MarketSegmentationTypeB2b partialUpdatedMarketSegmentationTypeB2b = new MarketSegmentationTypeB2b();
        partialUpdatedMarketSegmentationTypeB2b.setId(marketSegmentationTypeB2b.getId());

        partialUpdatedMarketSegmentationTypeB2b.language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2b.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2b))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2b testMarketSegmentationTypeB2b = marketSegmentationTypeB2bList.get(
            marketSegmentationTypeB2bList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2b.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketSegmentationTypeB2b.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2b.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketSegmentationTypeB2bWithPatch() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bRepository.saveAndFlush(marketSegmentationTypeB2b);

        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();

        // Update the marketSegmentationTypeB2b using partial update
        MarketSegmentationTypeB2b partialUpdatedMarketSegmentationTypeB2b = new MarketSegmentationTypeB2b();
        partialUpdatedMarketSegmentationTypeB2b.setId(marketSegmentationTypeB2b.getId());

        partialUpdatedMarketSegmentationTypeB2b.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketSegmentationTypeB2bMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketSegmentationTypeB2b.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketSegmentationTypeB2b))
            )
            .andExpect(status().isOk());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
        MarketSegmentationTypeB2b testMarketSegmentationTypeB2b = marketSegmentationTypeB2bList.get(
            marketSegmentationTypeB2bList.size() - 1
        );
        assertThat(testMarketSegmentationTypeB2b.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketSegmentationTypeB2b.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketSegmentationTypeB2b.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketSegmentationTypeB2b() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();
        marketSegmentationTypeB2b.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketSegmentationTypeB2b.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketSegmentationTypeB2b() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();
        marketSegmentationTypeB2b.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketSegmentationTypeB2b() throws Exception {
        int databaseSizeBeforeUpdate = marketSegmentationTypeB2bRepository.findAll().size();
        marketSegmentationTypeB2b.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketSegmentationTypeB2bMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketSegmentationTypeB2b))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketSegmentationTypeB2b in the database
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketSegmentationTypeB2b() throws Exception {
        // Initialize the database
        marketSegmentationTypeB2bRepository.saveAndFlush(marketSegmentationTypeB2b);

        int databaseSizeBeforeDelete = marketSegmentationTypeB2bRepository.findAll().size();

        // Delete the marketSegmentationTypeB2b
        restMarketSegmentationTypeB2bMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketSegmentationTypeB2b.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketSegmentationTypeB2b> marketSegmentationTypeB2bList = marketSegmentationTypeB2bRepository.findAll();
        assertThat(marketSegmentationTypeB2bList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
