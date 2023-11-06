package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldCompanyObjectives;
import io.valius.app.repository.FieldCompanyObjectivesRepository;
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

/**
 * Integration tests for the {@link FieldCompanyObjectivesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldCompanyObjectivesResourceIT {

    private static final String DEFAULT_COMPANY_OBJECTIVES = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_OBJECTIVES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-company-objectives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldCompanyObjectivesRepository fieldCompanyObjectivesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldCompanyObjectivesMockMvc;

    private FieldCompanyObjectives fieldCompanyObjectives;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldCompanyObjectives createEntity(EntityManager em) {
        FieldCompanyObjectives fieldCompanyObjectives = new FieldCompanyObjectives().companyObjectives(DEFAULT_COMPANY_OBJECTIVES);
        return fieldCompanyObjectives;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldCompanyObjectives createUpdatedEntity(EntityManager em) {
        FieldCompanyObjectives fieldCompanyObjectives = new FieldCompanyObjectives().companyObjectives(UPDATED_COMPANY_OBJECTIVES);
        return fieldCompanyObjectives;
    }

    @BeforeEach
    public void initTest() {
        fieldCompanyObjectives = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldCompanyObjectives() throws Exception {
        int databaseSizeBeforeCreate = fieldCompanyObjectivesRepository.findAll().size();
        // Create the FieldCompanyObjectives
        restFieldCompanyObjectivesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isCreated());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeCreate + 1);
        FieldCompanyObjectives testFieldCompanyObjectives = fieldCompanyObjectivesList.get(fieldCompanyObjectivesList.size() - 1);
        assertThat(testFieldCompanyObjectives.getCompanyObjectives()).isEqualTo(DEFAULT_COMPANY_OBJECTIVES);
    }

    @Test
    @Transactional
    void createFieldCompanyObjectivesWithExistingId() throws Exception {
        // Create the FieldCompanyObjectives with an existing ID
        fieldCompanyObjectives.setId(1L);

        int databaseSizeBeforeCreate = fieldCompanyObjectivesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldCompanyObjectivesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldCompanyObjectives() throws Exception {
        // Initialize the database
        fieldCompanyObjectivesRepository.saveAndFlush(fieldCompanyObjectives);

        // Get all the fieldCompanyObjectivesList
        restFieldCompanyObjectivesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldCompanyObjectives.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyObjectives").value(hasItem(DEFAULT_COMPANY_OBJECTIVES)));
    }

    @Test
    @Transactional
    void getFieldCompanyObjectives() throws Exception {
        // Initialize the database
        fieldCompanyObjectivesRepository.saveAndFlush(fieldCompanyObjectives);

        // Get the fieldCompanyObjectives
        restFieldCompanyObjectivesMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldCompanyObjectives.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldCompanyObjectives.getId().intValue()))
            .andExpect(jsonPath("$.companyObjectives").value(DEFAULT_COMPANY_OBJECTIVES));
    }

    @Test
    @Transactional
    void getNonExistingFieldCompanyObjectives() throws Exception {
        // Get the fieldCompanyObjectives
        restFieldCompanyObjectivesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldCompanyObjectives() throws Exception {
        // Initialize the database
        fieldCompanyObjectivesRepository.saveAndFlush(fieldCompanyObjectives);

        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();

        // Update the fieldCompanyObjectives
        FieldCompanyObjectives updatedFieldCompanyObjectives = fieldCompanyObjectivesRepository
            .findById(fieldCompanyObjectives.getId())
            .get();
        // Disconnect from session so that the updates on updatedFieldCompanyObjectives are not directly saved in db
        em.detach(updatedFieldCompanyObjectives);
        updatedFieldCompanyObjectives.companyObjectives(UPDATED_COMPANY_OBJECTIVES);

        restFieldCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldCompanyObjectives.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldCompanyObjectives))
            )
            .andExpect(status().isOk());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
        FieldCompanyObjectives testFieldCompanyObjectives = fieldCompanyObjectivesList.get(fieldCompanyObjectivesList.size() - 1);
        assertThat(testFieldCompanyObjectives.getCompanyObjectives()).isEqualTo(UPDATED_COMPANY_OBJECTIVES);
    }

    @Test
    @Transactional
    void putNonExistingFieldCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();
        fieldCompanyObjectives.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldCompanyObjectives.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();
        fieldCompanyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();
        fieldCompanyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldCompanyObjectivesWithPatch() throws Exception {
        // Initialize the database
        fieldCompanyObjectivesRepository.saveAndFlush(fieldCompanyObjectives);

        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();

        // Update the fieldCompanyObjectives using partial update
        FieldCompanyObjectives partialUpdatedFieldCompanyObjectives = new FieldCompanyObjectives();
        partialUpdatedFieldCompanyObjectives.setId(fieldCompanyObjectives.getId());

        partialUpdatedFieldCompanyObjectives.companyObjectives(UPDATED_COMPANY_OBJECTIVES);

        restFieldCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldCompanyObjectives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldCompanyObjectives))
            )
            .andExpect(status().isOk());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
        FieldCompanyObjectives testFieldCompanyObjectives = fieldCompanyObjectivesList.get(fieldCompanyObjectivesList.size() - 1);
        assertThat(testFieldCompanyObjectives.getCompanyObjectives()).isEqualTo(UPDATED_COMPANY_OBJECTIVES);
    }

    @Test
    @Transactional
    void fullUpdateFieldCompanyObjectivesWithPatch() throws Exception {
        // Initialize the database
        fieldCompanyObjectivesRepository.saveAndFlush(fieldCompanyObjectives);

        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();

        // Update the fieldCompanyObjectives using partial update
        FieldCompanyObjectives partialUpdatedFieldCompanyObjectives = new FieldCompanyObjectives();
        partialUpdatedFieldCompanyObjectives.setId(fieldCompanyObjectives.getId());

        partialUpdatedFieldCompanyObjectives.companyObjectives(UPDATED_COMPANY_OBJECTIVES);

        restFieldCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldCompanyObjectives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldCompanyObjectives))
            )
            .andExpect(status().isOk());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
        FieldCompanyObjectives testFieldCompanyObjectives = fieldCompanyObjectivesList.get(fieldCompanyObjectivesList.size() - 1);
        assertThat(testFieldCompanyObjectives.getCompanyObjectives()).isEqualTo(UPDATED_COMPANY_OBJECTIVES);
    }

    @Test
    @Transactional
    void patchNonExistingFieldCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();
        fieldCompanyObjectives.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldCompanyObjectives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();
        fieldCompanyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = fieldCompanyObjectivesRepository.findAll().size();
        fieldCompanyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldCompanyObjectives))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldCompanyObjectives in the database
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldCompanyObjectives() throws Exception {
        // Initialize the database
        fieldCompanyObjectivesRepository.saveAndFlush(fieldCompanyObjectives);

        int databaseSizeBeforeDelete = fieldCompanyObjectivesRepository.findAll().size();

        // Delete the fieldCompanyObjectives
        restFieldCompanyObjectivesMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldCompanyObjectives.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldCompanyObjectives> fieldCompanyObjectivesList = fieldCompanyObjectivesRepository.findAll();
        assertThat(fieldCompanyObjectivesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
