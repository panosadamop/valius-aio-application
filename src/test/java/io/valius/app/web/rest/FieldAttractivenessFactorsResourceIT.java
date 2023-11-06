package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldAttractivenessFactors;
import io.valius.app.repository.FieldAttractivenessFactorsRepository;
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

/**
 * Integration tests for the {@link FieldAttractivenessFactorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldAttractivenessFactorsResourceIT {

    private static final String DEFAULT_ATTRACTIVENESS_FACTORS = "AAAAAAAAAA";
    private static final String UPDATED_ATTRACTIVENESS_FACTORS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-attractiveness-factors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldAttractivenessFactorsRepository fieldAttractivenessFactorsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldAttractivenessFactorsMockMvc;

    private FieldAttractivenessFactors fieldAttractivenessFactors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldAttractivenessFactors createEntity(EntityManager em) {
        FieldAttractivenessFactors fieldAttractivenessFactors = new FieldAttractivenessFactors()
            .attractivenessFactors(DEFAULT_ATTRACTIVENESS_FACTORS);
        return fieldAttractivenessFactors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldAttractivenessFactors createUpdatedEntity(EntityManager em) {
        FieldAttractivenessFactors fieldAttractivenessFactors = new FieldAttractivenessFactors()
            .attractivenessFactors(UPDATED_ATTRACTIVENESS_FACTORS);
        return fieldAttractivenessFactors;
    }

    @BeforeEach
    public void initTest() {
        fieldAttractivenessFactors = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldAttractivenessFactors() throws Exception {
        int databaseSizeBeforeCreate = fieldAttractivenessFactorsRepository.findAll().size();
        // Create the FieldAttractivenessFactors
        restFieldAttractivenessFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isCreated());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeCreate + 1);
        FieldAttractivenessFactors testFieldAttractivenessFactors = fieldAttractivenessFactorsList.get(
            fieldAttractivenessFactorsList.size() - 1
        );
        assertThat(testFieldAttractivenessFactors.getAttractivenessFactors()).isEqualTo(DEFAULT_ATTRACTIVENESS_FACTORS);
    }

    @Test
    @Transactional
    void createFieldAttractivenessFactorsWithExistingId() throws Exception {
        // Create the FieldAttractivenessFactors with an existing ID
        fieldAttractivenessFactors.setId(1L);

        int databaseSizeBeforeCreate = fieldAttractivenessFactorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldAttractivenessFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldAttractivenessFactors() throws Exception {
        // Initialize the database
        fieldAttractivenessFactorsRepository.saveAndFlush(fieldAttractivenessFactors);

        // Get all the fieldAttractivenessFactorsList
        restFieldAttractivenessFactorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldAttractivenessFactors.getId().intValue())))
            .andExpect(jsonPath("$.[*].attractivenessFactors").value(hasItem(DEFAULT_ATTRACTIVENESS_FACTORS)));
    }

    @Test
    @Transactional
    void getFieldAttractivenessFactors() throws Exception {
        // Initialize the database
        fieldAttractivenessFactorsRepository.saveAndFlush(fieldAttractivenessFactors);

        // Get the fieldAttractivenessFactors
        restFieldAttractivenessFactorsMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldAttractivenessFactors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldAttractivenessFactors.getId().intValue()))
            .andExpect(jsonPath("$.attractivenessFactors").value(DEFAULT_ATTRACTIVENESS_FACTORS));
    }

    @Test
    @Transactional
    void getNonExistingFieldAttractivenessFactors() throws Exception {
        // Get the fieldAttractivenessFactors
        restFieldAttractivenessFactorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldAttractivenessFactors() throws Exception {
        // Initialize the database
        fieldAttractivenessFactorsRepository.saveAndFlush(fieldAttractivenessFactors);

        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();

        // Update the fieldAttractivenessFactors
        FieldAttractivenessFactors updatedFieldAttractivenessFactors = fieldAttractivenessFactorsRepository
            .findById(fieldAttractivenessFactors.getId())
            .get();
        // Disconnect from session so that the updates on updatedFieldAttractivenessFactors are not directly saved in db
        em.detach(updatedFieldAttractivenessFactors);
        updatedFieldAttractivenessFactors.attractivenessFactors(UPDATED_ATTRACTIVENESS_FACTORS);

        restFieldAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldAttractivenessFactors))
            )
            .andExpect(status().isOk());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
        FieldAttractivenessFactors testFieldAttractivenessFactors = fieldAttractivenessFactorsList.get(
            fieldAttractivenessFactorsList.size() - 1
        );
        assertThat(testFieldAttractivenessFactors.getAttractivenessFactors()).isEqualTo(UPDATED_ATTRACTIVENESS_FACTORS);
    }

    @Test
    @Transactional
    void putNonExistingFieldAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();
        fieldAttractivenessFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();
        fieldAttractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();
        fieldAttractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAttractivenessFactorsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldAttractivenessFactorsWithPatch() throws Exception {
        // Initialize the database
        fieldAttractivenessFactorsRepository.saveAndFlush(fieldAttractivenessFactors);

        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();

        // Update the fieldAttractivenessFactors using partial update
        FieldAttractivenessFactors partialUpdatedFieldAttractivenessFactors = new FieldAttractivenessFactors();
        partialUpdatedFieldAttractivenessFactors.setId(fieldAttractivenessFactors.getId());

        restFieldAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldAttractivenessFactors))
            )
            .andExpect(status().isOk());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
        FieldAttractivenessFactors testFieldAttractivenessFactors = fieldAttractivenessFactorsList.get(
            fieldAttractivenessFactorsList.size() - 1
        );
        assertThat(testFieldAttractivenessFactors.getAttractivenessFactors()).isEqualTo(DEFAULT_ATTRACTIVENESS_FACTORS);
    }

    @Test
    @Transactional
    void fullUpdateFieldAttractivenessFactorsWithPatch() throws Exception {
        // Initialize the database
        fieldAttractivenessFactorsRepository.saveAndFlush(fieldAttractivenessFactors);

        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();

        // Update the fieldAttractivenessFactors using partial update
        FieldAttractivenessFactors partialUpdatedFieldAttractivenessFactors = new FieldAttractivenessFactors();
        partialUpdatedFieldAttractivenessFactors.setId(fieldAttractivenessFactors.getId());

        partialUpdatedFieldAttractivenessFactors.attractivenessFactors(UPDATED_ATTRACTIVENESS_FACTORS);

        restFieldAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldAttractivenessFactors))
            )
            .andExpect(status().isOk());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
        FieldAttractivenessFactors testFieldAttractivenessFactors = fieldAttractivenessFactorsList.get(
            fieldAttractivenessFactorsList.size() - 1
        );
        assertThat(testFieldAttractivenessFactors.getAttractivenessFactors()).isEqualTo(UPDATED_ATTRACTIVENESS_FACTORS);
    }

    @Test
    @Transactional
    void patchNonExistingFieldAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();
        fieldAttractivenessFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldAttractivenessFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();
        fieldAttractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldAttractivenessFactors() throws Exception {
        int databaseSizeBeforeUpdate = fieldAttractivenessFactorsRepository.findAll().size();
        fieldAttractivenessFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAttractivenessFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldAttractivenessFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldAttractivenessFactors in the database
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldAttractivenessFactors() throws Exception {
        // Initialize the database
        fieldAttractivenessFactorsRepository.saveAndFlush(fieldAttractivenessFactors);

        int databaseSizeBeforeDelete = fieldAttractivenessFactorsRepository.findAll().size();

        // Delete the fieldAttractivenessFactors
        restFieldAttractivenessFactorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldAttractivenessFactors.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldAttractivenessFactors> fieldAttractivenessFactorsList = fieldAttractivenessFactorsRepository.findAll();
        assertThat(fieldAttractivenessFactorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
