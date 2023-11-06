package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.EconomicFactors;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.EconomicFactorsRepository;
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
 * Integration tests for the {@link EconomicFactorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EconomicFactorsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/economic-factors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EconomicFactorsRepository economicFactorsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEconomicFactorsMockMvc;

    private EconomicFactors economicFactors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EconomicFactors createEntity(EntityManager em) {
        EconomicFactors economicFactors = new EconomicFactors()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return economicFactors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EconomicFactors createUpdatedEntity(EntityManager em) {
        EconomicFactors economicFactors = new EconomicFactors()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return economicFactors;
    }

    @BeforeEach
    public void initTest() {
        economicFactors = createEntity(em);
    }

    @Test
    @Transactional
    void createEconomicFactors() throws Exception {
        int databaseSizeBeforeCreate = economicFactorsRepository.findAll().size();
        // Create the EconomicFactors
        restEconomicFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isCreated());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeCreate + 1);
        EconomicFactors testEconomicFactors = economicFactorsList.get(economicFactorsList.size() - 1);
        assertThat(testEconomicFactors.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testEconomicFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEconomicFactors.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createEconomicFactorsWithExistingId() throws Exception {
        // Create the EconomicFactors with an existing ID
        economicFactors.setId(1L);

        int databaseSizeBeforeCreate = economicFactorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEconomicFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = economicFactorsRepository.findAll().size();
        // set the field null
        economicFactors.setValue(null);

        // Create the EconomicFactors, which fails.

        restEconomicFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isBadRequest());

        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = economicFactorsRepository.findAll().size();
        // set the field null
        economicFactors.setLanguage(null);

        // Create the EconomicFactors, which fails.

        restEconomicFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isBadRequest());

        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEconomicFactors() throws Exception {
        // Initialize the database
        economicFactorsRepository.saveAndFlush(economicFactors);

        // Get all the economicFactorsList
        restEconomicFactorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(economicFactors.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getEconomicFactors() throws Exception {
        // Initialize the database
        economicFactorsRepository.saveAndFlush(economicFactors);

        // Get the economicFactors
        restEconomicFactorsMockMvc
            .perform(get(ENTITY_API_URL_ID, economicFactors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(economicFactors.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEconomicFactors() throws Exception {
        // Get the economicFactors
        restEconomicFactorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEconomicFactors() throws Exception {
        // Initialize the database
        economicFactorsRepository.saveAndFlush(economicFactors);

        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();

        // Update the economicFactors
        EconomicFactors updatedEconomicFactors = economicFactorsRepository.findById(economicFactors.getId()).get();
        // Disconnect from session so that the updates on updatedEconomicFactors are not directly saved in db
        em.detach(updatedEconomicFactors);
        updatedEconomicFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restEconomicFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEconomicFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEconomicFactors))
            )
            .andExpect(status().isOk());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
        EconomicFactors testEconomicFactors = economicFactorsList.get(economicFactorsList.size() - 1);
        assertThat(testEconomicFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEconomicFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEconomicFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingEconomicFactors() throws Exception {
        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();
        economicFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEconomicFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, economicFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEconomicFactors() throws Exception {
        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();
        economicFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEconomicFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEconomicFactors() throws Exception {
        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();
        economicFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEconomicFactorsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEconomicFactorsWithPatch() throws Exception {
        // Initialize the database
        economicFactorsRepository.saveAndFlush(economicFactors);

        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();

        // Update the economicFactors using partial update
        EconomicFactors partialUpdatedEconomicFactors = new EconomicFactors();
        partialUpdatedEconomicFactors.setId(economicFactors.getId());

        partialUpdatedEconomicFactors.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restEconomicFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEconomicFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEconomicFactors))
            )
            .andExpect(status().isOk());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
        EconomicFactors testEconomicFactors = economicFactorsList.get(economicFactorsList.size() - 1);
        assertThat(testEconomicFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEconomicFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEconomicFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateEconomicFactorsWithPatch() throws Exception {
        // Initialize the database
        economicFactorsRepository.saveAndFlush(economicFactors);

        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();

        // Update the economicFactors using partial update
        EconomicFactors partialUpdatedEconomicFactors = new EconomicFactors();
        partialUpdatedEconomicFactors.setId(economicFactors.getId());

        partialUpdatedEconomicFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restEconomicFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEconomicFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEconomicFactors))
            )
            .andExpect(status().isOk());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
        EconomicFactors testEconomicFactors = economicFactorsList.get(economicFactorsList.size() - 1);
        assertThat(testEconomicFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEconomicFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEconomicFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingEconomicFactors() throws Exception {
        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();
        economicFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEconomicFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, economicFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEconomicFactors() throws Exception {
        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();
        economicFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEconomicFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEconomicFactors() throws Exception {
        int databaseSizeBeforeUpdate = economicFactorsRepository.findAll().size();
        economicFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEconomicFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(economicFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EconomicFactors in the database
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEconomicFactors() throws Exception {
        // Initialize the database
        economicFactorsRepository.saveAndFlush(economicFactors);

        int databaseSizeBeforeDelete = economicFactorsRepository.findAll().size();

        // Delete the economicFactors
        restEconomicFactorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, economicFactors.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EconomicFactors> economicFactorsList = economicFactorsRepository.findAll();
        assertThat(economicFactorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
