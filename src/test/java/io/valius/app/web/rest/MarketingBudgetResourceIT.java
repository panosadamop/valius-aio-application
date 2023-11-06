package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MarketingBudget;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MarketingBudgetRepository;
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
 * Integration tests for the {@link MarketingBudgetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketingBudgetResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/marketing-budgets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketingBudgetRepository marketingBudgetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketingBudgetMockMvc;

    private MarketingBudget marketingBudget;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketingBudget createEntity(EntityManager em) {
        MarketingBudget marketingBudget = new MarketingBudget()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return marketingBudget;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketingBudget createUpdatedEntity(EntityManager em) {
        MarketingBudget marketingBudget = new MarketingBudget()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return marketingBudget;
    }

    @BeforeEach
    public void initTest() {
        marketingBudget = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketingBudget() throws Exception {
        int databaseSizeBeforeCreate = marketingBudgetRepository.findAll().size();
        // Create the MarketingBudget
        restMarketingBudgetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isCreated());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeCreate + 1);
        MarketingBudget testMarketingBudget = marketingBudgetList.get(marketingBudgetList.size() - 1);
        assertThat(testMarketingBudget.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketingBudget.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketingBudget.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMarketingBudgetWithExistingId() throws Exception {
        // Create the MarketingBudget with an existing ID
        marketingBudget.setId(1L);

        int databaseSizeBeforeCreate = marketingBudgetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketingBudgetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketingBudgetRepository.findAll().size();
        // set the field null
        marketingBudget.setValue(null);

        // Create the MarketingBudget, which fails.

        restMarketingBudgetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isBadRequest());

        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketingBudgetRepository.findAll().size();
        // set the field null
        marketingBudget.setLanguage(null);

        // Create the MarketingBudget, which fails.

        restMarketingBudgetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isBadRequest());

        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketingBudgets() throws Exception {
        // Initialize the database
        marketingBudgetRepository.saveAndFlush(marketingBudget);

        // Get all the marketingBudgetList
        restMarketingBudgetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketingBudget.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMarketingBudget() throws Exception {
        // Initialize the database
        marketingBudgetRepository.saveAndFlush(marketingBudget);

        // Get the marketingBudget
        restMarketingBudgetMockMvc
            .perform(get(ENTITY_API_URL_ID, marketingBudget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketingBudget.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketingBudget() throws Exception {
        // Get the marketingBudget
        restMarketingBudgetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketingBudget() throws Exception {
        // Initialize the database
        marketingBudgetRepository.saveAndFlush(marketingBudget);

        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();

        // Update the marketingBudget
        MarketingBudget updatedMarketingBudget = marketingBudgetRepository.findById(marketingBudget.getId()).get();
        // Disconnect from session so that the updates on updatedMarketingBudget are not directly saved in db
        em.detach(updatedMarketingBudget);
        updatedMarketingBudget.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketingBudgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketingBudget.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketingBudget))
            )
            .andExpect(status().isOk());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
        MarketingBudget testMarketingBudget = marketingBudgetList.get(marketingBudgetList.size() - 1);
        assertThat(testMarketingBudget.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketingBudget.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketingBudget.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMarketingBudget() throws Exception {
        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();
        marketingBudget.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketingBudgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketingBudget.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketingBudget() throws Exception {
        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();
        marketingBudget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingBudgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketingBudget() throws Exception {
        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();
        marketingBudget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingBudgetMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketingBudgetWithPatch() throws Exception {
        // Initialize the database
        marketingBudgetRepository.saveAndFlush(marketingBudget);

        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();

        // Update the marketingBudget using partial update
        MarketingBudget partialUpdatedMarketingBudget = new MarketingBudget();
        partialUpdatedMarketingBudget.setId(marketingBudget.getId());

        partialUpdatedMarketingBudget.description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketingBudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketingBudget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketingBudget))
            )
            .andExpect(status().isOk());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
        MarketingBudget testMarketingBudget = marketingBudgetList.get(marketingBudgetList.size() - 1);
        assertThat(testMarketingBudget.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMarketingBudget.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketingBudget.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMarketingBudgetWithPatch() throws Exception {
        // Initialize the database
        marketingBudgetRepository.saveAndFlush(marketingBudget);

        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();

        // Update the marketingBudget using partial update
        MarketingBudget partialUpdatedMarketingBudget = new MarketingBudget();
        partialUpdatedMarketingBudget.setId(marketingBudget.getId());

        partialUpdatedMarketingBudget.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMarketingBudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketingBudget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketingBudget))
            )
            .andExpect(status().isOk());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
        MarketingBudget testMarketingBudget = marketingBudgetList.get(marketingBudgetList.size() - 1);
        assertThat(testMarketingBudget.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMarketingBudget.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketingBudget.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMarketingBudget() throws Exception {
        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();
        marketingBudget.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketingBudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketingBudget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketingBudget() throws Exception {
        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();
        marketingBudget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingBudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketingBudget() throws Exception {
        int databaseSizeBeforeUpdate = marketingBudgetRepository.findAll().size();
        marketingBudget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingBudgetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketingBudget))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketingBudget in the database
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketingBudget() throws Exception {
        // Initialize the database
        marketingBudgetRepository.saveAndFlush(marketingBudget);

        int databaseSizeBeforeDelete = marketingBudgetRepository.findAll().size();

        // Delete the marketingBudget
        restMarketingBudgetMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketingBudget.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketingBudget> marketingBudgetList = marketingBudgetRepository.findAll();
        assertThat(marketingBudgetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
