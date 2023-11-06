package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldBuyingCriteriaWeighting;
import io.valius.app.repository.FieldBuyingCriteriaWeightingRepository;
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
 * Integration tests for the {@link FieldBuyingCriteriaWeightingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldBuyingCriteriaWeightingResourceIT {

    private static final String DEFAULT_BUYING_CRITERIA_WEIGHTING = "AAAAAAAAAA";
    private static final String UPDATED_BUYING_CRITERIA_WEIGHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-buying-criteria-weightings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldBuyingCriteriaWeightingRepository fieldBuyingCriteriaWeightingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldBuyingCriteriaWeightingMockMvc;

    private FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldBuyingCriteriaWeighting createEntity(EntityManager em) {
        FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting = new FieldBuyingCriteriaWeighting()
            .buyingCriteriaWeighting(DEFAULT_BUYING_CRITERIA_WEIGHTING);
        return fieldBuyingCriteriaWeighting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldBuyingCriteriaWeighting createUpdatedEntity(EntityManager em) {
        FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting = new FieldBuyingCriteriaWeighting()
            .buyingCriteriaWeighting(UPDATED_BUYING_CRITERIA_WEIGHTING);
        return fieldBuyingCriteriaWeighting;
    }

    @BeforeEach
    public void initTest() {
        fieldBuyingCriteriaWeighting = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeCreate = fieldBuyingCriteriaWeightingRepository.findAll().size();
        // Create the FieldBuyingCriteriaWeighting
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isCreated());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeCreate + 1);
        FieldBuyingCriteriaWeighting testFieldBuyingCriteriaWeighting = fieldBuyingCriteriaWeightingList.get(
            fieldBuyingCriteriaWeightingList.size() - 1
        );
        assertThat(testFieldBuyingCriteriaWeighting.getBuyingCriteriaWeighting()).isEqualTo(DEFAULT_BUYING_CRITERIA_WEIGHTING);
    }

    @Test
    @Transactional
    void createFieldBuyingCriteriaWeightingWithExistingId() throws Exception {
        // Create the FieldBuyingCriteriaWeighting with an existing ID
        fieldBuyingCriteriaWeighting.setId(1L);

        int databaseSizeBeforeCreate = fieldBuyingCriteriaWeightingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldBuyingCriteriaWeightings() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaWeightingRepository.saveAndFlush(fieldBuyingCriteriaWeighting);

        // Get all the fieldBuyingCriteriaWeightingList
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldBuyingCriteriaWeighting.getId().intValue())))
            .andExpect(jsonPath("$.[*].buyingCriteriaWeighting").value(hasItem(DEFAULT_BUYING_CRITERIA_WEIGHTING)));
    }

    @Test
    @Transactional
    void getFieldBuyingCriteriaWeighting() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaWeightingRepository.saveAndFlush(fieldBuyingCriteriaWeighting);

        // Get the fieldBuyingCriteriaWeighting
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldBuyingCriteriaWeighting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldBuyingCriteriaWeighting.getId().intValue()))
            .andExpect(jsonPath("$.buyingCriteriaWeighting").value(DEFAULT_BUYING_CRITERIA_WEIGHTING));
    }

    @Test
    @Transactional
    void getNonExistingFieldBuyingCriteriaWeighting() throws Exception {
        // Get the fieldBuyingCriteriaWeighting
        restFieldBuyingCriteriaWeightingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldBuyingCriteriaWeighting() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaWeightingRepository.saveAndFlush(fieldBuyingCriteriaWeighting);

        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();

        // Update the fieldBuyingCriteriaWeighting
        FieldBuyingCriteriaWeighting updatedFieldBuyingCriteriaWeighting = fieldBuyingCriteriaWeightingRepository
            .findById(fieldBuyingCriteriaWeighting.getId())
            .get();
        // Disconnect from session so that the updates on updatedFieldBuyingCriteriaWeighting are not directly saved in db
        em.detach(updatedFieldBuyingCriteriaWeighting);
        updatedFieldBuyingCriteriaWeighting.buyingCriteriaWeighting(UPDATED_BUYING_CRITERIA_WEIGHTING);

        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isOk());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
        FieldBuyingCriteriaWeighting testFieldBuyingCriteriaWeighting = fieldBuyingCriteriaWeightingList.get(
            fieldBuyingCriteriaWeightingList.size() - 1
        );
        assertThat(testFieldBuyingCriteriaWeighting.getBuyingCriteriaWeighting()).isEqualTo(UPDATED_BUYING_CRITERIA_WEIGHTING);
    }

    @Test
    @Transactional
    void putNonExistingFieldBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();
        fieldBuyingCriteriaWeighting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();
        fieldBuyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();
        fieldBuyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldBuyingCriteriaWeightingWithPatch() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaWeightingRepository.saveAndFlush(fieldBuyingCriteriaWeighting);

        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();

        // Update the fieldBuyingCriteriaWeighting using partial update
        FieldBuyingCriteriaWeighting partialUpdatedFieldBuyingCriteriaWeighting = new FieldBuyingCriteriaWeighting();
        partialUpdatedFieldBuyingCriteriaWeighting.setId(fieldBuyingCriteriaWeighting.getId());

        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isOk());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
        FieldBuyingCriteriaWeighting testFieldBuyingCriteriaWeighting = fieldBuyingCriteriaWeightingList.get(
            fieldBuyingCriteriaWeightingList.size() - 1
        );
        assertThat(testFieldBuyingCriteriaWeighting.getBuyingCriteriaWeighting()).isEqualTo(DEFAULT_BUYING_CRITERIA_WEIGHTING);
    }

    @Test
    @Transactional
    void fullUpdateFieldBuyingCriteriaWeightingWithPatch() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaWeightingRepository.saveAndFlush(fieldBuyingCriteriaWeighting);

        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();

        // Update the fieldBuyingCriteriaWeighting using partial update
        FieldBuyingCriteriaWeighting partialUpdatedFieldBuyingCriteriaWeighting = new FieldBuyingCriteriaWeighting();
        partialUpdatedFieldBuyingCriteriaWeighting.setId(fieldBuyingCriteriaWeighting.getId());

        partialUpdatedFieldBuyingCriteriaWeighting.buyingCriteriaWeighting(UPDATED_BUYING_CRITERIA_WEIGHTING);

        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isOk());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
        FieldBuyingCriteriaWeighting testFieldBuyingCriteriaWeighting = fieldBuyingCriteriaWeightingList.get(
            fieldBuyingCriteriaWeightingList.size() - 1
        );
        assertThat(testFieldBuyingCriteriaWeighting.getBuyingCriteriaWeighting()).isEqualTo(UPDATED_BUYING_CRITERIA_WEIGHTING);
    }

    @Test
    @Transactional
    void patchNonExistingFieldBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();
        fieldBuyingCriteriaWeighting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldBuyingCriteriaWeighting.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();
        fieldBuyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldBuyingCriteriaWeighting() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaWeightingRepository.findAll().size();
        fieldBuyingCriteriaWeighting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteriaWeighting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldBuyingCriteriaWeighting in the database
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldBuyingCriteriaWeighting() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaWeightingRepository.saveAndFlush(fieldBuyingCriteriaWeighting);

        int databaseSizeBeforeDelete = fieldBuyingCriteriaWeightingRepository.findAll().size();

        // Delete the fieldBuyingCriteriaWeighting
        restFieldBuyingCriteriaWeightingMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldBuyingCriteriaWeighting.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeightingList = fieldBuyingCriteriaWeightingRepository.findAll();
        assertThat(fieldBuyingCriteriaWeightingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
