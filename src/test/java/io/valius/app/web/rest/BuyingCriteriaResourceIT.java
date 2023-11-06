package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.BuyingCriteria;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.BuyingCriteriaRepository;
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
 * Integration tests for the {@link BuyingCriteriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyingCriteriaResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/buying-criteria";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuyingCriteriaRepository buyingCriteriaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyingCriteriaMockMvc;

    private BuyingCriteria buyingCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyingCriteria createEntity(EntityManager em) {
        BuyingCriteria buyingCriteria = new BuyingCriteria()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return buyingCriteria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyingCriteria createUpdatedEntity(EntityManager em) {
        BuyingCriteria buyingCriteria = new BuyingCriteria()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return buyingCriteria;
    }

    @BeforeEach
    public void initTest() {
        buyingCriteria = createEntity(em);
    }

    @Test
    @Transactional
    void createBuyingCriteria() throws Exception {
        int databaseSizeBeforeCreate = buyingCriteriaRepository.findAll().size();
        // Create the BuyingCriteria
        restBuyingCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isCreated());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        BuyingCriteria testBuyingCriteria = buyingCriteriaList.get(buyingCriteriaList.size() - 1);
        assertThat(testBuyingCriteria.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testBuyingCriteria.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBuyingCriteria.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createBuyingCriteriaWithExistingId() throws Exception {
        // Create the BuyingCriteria with an existing ID
        buyingCriteria.setId(1L);

        int databaseSizeBeforeCreate = buyingCriteriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyingCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingCriteriaRepository.findAll().size();
        // set the field null
        buyingCriteria.setValue(null);

        // Create the BuyingCriteria, which fails.

        restBuyingCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isBadRequest());

        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingCriteriaRepository.findAll().size();
        // set the field null
        buyingCriteria.setLanguage(null);

        // Create the BuyingCriteria, which fails.

        restBuyingCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isBadRequest());

        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuyingCriteria() throws Exception {
        // Initialize the database
        buyingCriteriaRepository.saveAndFlush(buyingCriteria);

        // Get all the buyingCriteriaList
        restBuyingCriteriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyingCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getBuyingCriteria() throws Exception {
        // Initialize the database
        buyingCriteriaRepository.saveAndFlush(buyingCriteria);

        // Get the buyingCriteria
        restBuyingCriteriaMockMvc
            .perform(get(ENTITY_API_URL_ID, buyingCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyingCriteria.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBuyingCriteria() throws Exception {
        // Get the buyingCriteria
        restBuyingCriteriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyingCriteria() throws Exception {
        // Initialize the database
        buyingCriteriaRepository.saveAndFlush(buyingCriteria);

        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();

        // Update the buyingCriteria
        BuyingCriteria updatedBuyingCriteria = buyingCriteriaRepository.findById(buyingCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedBuyingCriteria are not directly saved in db
        em.detach(updatedBuyingCriteria);
        updatedBuyingCriteria.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBuyingCriteria.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBuyingCriteria))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteria testBuyingCriteria = buyingCriteriaList.get(buyingCriteriaList.size() - 1);
        assertThat(testBuyingCriteria.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBuyingCriteria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteria.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();
        buyingCriteria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyingCriteria.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();
        buyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();
        buyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyingCriteriaWithPatch() throws Exception {
        // Initialize the database
        buyingCriteriaRepository.saveAndFlush(buyingCriteria);

        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();

        // Update the buyingCriteria using partial update
        BuyingCriteria partialUpdatedBuyingCriteria = new BuyingCriteria();
        partialUpdatedBuyingCriteria.setId(buyingCriteria.getId());

        partialUpdatedBuyingCriteria.language(UPDATED_LANGUAGE);

        restBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyingCriteria.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyingCriteria))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteria testBuyingCriteria = buyingCriteriaList.get(buyingCriteriaList.size() - 1);
        assertThat(testBuyingCriteria.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testBuyingCriteria.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBuyingCriteria.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateBuyingCriteriaWithPatch() throws Exception {
        // Initialize the database
        buyingCriteriaRepository.saveAndFlush(buyingCriteria);

        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();

        // Update the buyingCriteria using partial update
        BuyingCriteria partialUpdatedBuyingCriteria = new BuyingCriteria();
        partialUpdatedBuyingCriteria.setId(buyingCriteria.getId());

        partialUpdatedBuyingCriteria.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyingCriteria.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyingCriteria))
            )
            .andExpect(status().isOk());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
        BuyingCriteria testBuyingCriteria = buyingCriteriaList.get(buyingCriteriaList.size() - 1);
        assertThat(testBuyingCriteria.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBuyingCriteria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyingCriteria.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();
        buyingCriteria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyingCriteria.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();
        buyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = buyingCriteriaRepository.findAll().size();
        buyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyingCriteria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyingCriteria in the database
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyingCriteria() throws Exception {
        // Initialize the database
        buyingCriteriaRepository.saveAndFlush(buyingCriteria);

        int databaseSizeBeforeDelete = buyingCriteriaRepository.findAll().size();

        // Delete the buyingCriteria
        restBuyingCriteriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyingCriteria.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuyingCriteria> buyingCriteriaList = buyingCriteriaRepository.findAll();
        assertThat(buyingCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
