package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldBuyingCriteria;
import io.valius.app.repository.FieldBuyingCriteriaRepository;
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
 * Integration tests for the {@link FieldBuyingCriteriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldBuyingCriteriaResourceIT {

    private static final String DEFAULT_BUYING_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_BUYING_CRITERIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-buying-criteria";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldBuyingCriteriaRepository fieldBuyingCriteriaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldBuyingCriteriaMockMvc;

    private FieldBuyingCriteria fieldBuyingCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldBuyingCriteria createEntity(EntityManager em) {
        FieldBuyingCriteria fieldBuyingCriteria = new FieldBuyingCriteria().buyingCriteria(DEFAULT_BUYING_CRITERIA);
        return fieldBuyingCriteria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldBuyingCriteria createUpdatedEntity(EntityManager em) {
        FieldBuyingCriteria fieldBuyingCriteria = new FieldBuyingCriteria().buyingCriteria(UPDATED_BUYING_CRITERIA);
        return fieldBuyingCriteria;
    }

    @BeforeEach
    public void initTest() {
        fieldBuyingCriteria = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldBuyingCriteria() throws Exception {
        int databaseSizeBeforeCreate = fieldBuyingCriteriaRepository.findAll().size();
        // Create the FieldBuyingCriteria
        restFieldBuyingCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isCreated());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        FieldBuyingCriteria testFieldBuyingCriteria = fieldBuyingCriteriaList.get(fieldBuyingCriteriaList.size() - 1);
        assertThat(testFieldBuyingCriteria.getBuyingCriteria()).isEqualTo(DEFAULT_BUYING_CRITERIA);
    }

    @Test
    @Transactional
    void createFieldBuyingCriteriaWithExistingId() throws Exception {
        // Create the FieldBuyingCriteria with an existing ID
        fieldBuyingCriteria.setId(1L);

        int databaseSizeBeforeCreate = fieldBuyingCriteriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldBuyingCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldBuyingCriteria() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaRepository.saveAndFlush(fieldBuyingCriteria);

        // Get all the fieldBuyingCriteriaList
        restFieldBuyingCriteriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldBuyingCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].buyingCriteria").value(hasItem(DEFAULT_BUYING_CRITERIA)));
    }

    @Test
    @Transactional
    void getFieldBuyingCriteria() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaRepository.saveAndFlush(fieldBuyingCriteria);

        // Get the fieldBuyingCriteria
        restFieldBuyingCriteriaMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldBuyingCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldBuyingCriteria.getId().intValue()))
            .andExpect(jsonPath("$.buyingCriteria").value(DEFAULT_BUYING_CRITERIA));
    }

    @Test
    @Transactional
    void getNonExistingFieldBuyingCriteria() throws Exception {
        // Get the fieldBuyingCriteria
        restFieldBuyingCriteriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldBuyingCriteria() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaRepository.saveAndFlush(fieldBuyingCriteria);

        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();

        // Update the fieldBuyingCriteria
        FieldBuyingCriteria updatedFieldBuyingCriteria = fieldBuyingCriteriaRepository.findById(fieldBuyingCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedFieldBuyingCriteria are not directly saved in db
        em.detach(updatedFieldBuyingCriteria);
        updatedFieldBuyingCriteria.buyingCriteria(UPDATED_BUYING_CRITERIA);

        restFieldBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldBuyingCriteria.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldBuyingCriteria))
            )
            .andExpect(status().isOk());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
        FieldBuyingCriteria testFieldBuyingCriteria = fieldBuyingCriteriaList.get(fieldBuyingCriteriaList.size() - 1);
        assertThat(testFieldBuyingCriteria.getBuyingCriteria()).isEqualTo(UPDATED_BUYING_CRITERIA);
    }

    @Test
    @Transactional
    void putNonExistingFieldBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();
        fieldBuyingCriteria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldBuyingCriteria.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();
        fieldBuyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();
        fieldBuyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldBuyingCriteriaWithPatch() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaRepository.saveAndFlush(fieldBuyingCriteria);

        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();

        // Update the fieldBuyingCriteria using partial update
        FieldBuyingCriteria partialUpdatedFieldBuyingCriteria = new FieldBuyingCriteria();
        partialUpdatedFieldBuyingCriteria.setId(fieldBuyingCriteria.getId());

        restFieldBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldBuyingCriteria.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldBuyingCriteria))
            )
            .andExpect(status().isOk());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
        FieldBuyingCriteria testFieldBuyingCriteria = fieldBuyingCriteriaList.get(fieldBuyingCriteriaList.size() - 1);
        assertThat(testFieldBuyingCriteria.getBuyingCriteria()).isEqualTo(DEFAULT_BUYING_CRITERIA);
    }

    @Test
    @Transactional
    void fullUpdateFieldBuyingCriteriaWithPatch() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaRepository.saveAndFlush(fieldBuyingCriteria);

        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();

        // Update the fieldBuyingCriteria using partial update
        FieldBuyingCriteria partialUpdatedFieldBuyingCriteria = new FieldBuyingCriteria();
        partialUpdatedFieldBuyingCriteria.setId(fieldBuyingCriteria.getId());

        partialUpdatedFieldBuyingCriteria.buyingCriteria(UPDATED_BUYING_CRITERIA);

        restFieldBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldBuyingCriteria.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldBuyingCriteria))
            )
            .andExpect(status().isOk());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
        FieldBuyingCriteria testFieldBuyingCriteria = fieldBuyingCriteriaList.get(fieldBuyingCriteriaList.size() - 1);
        assertThat(testFieldBuyingCriteria.getBuyingCriteria()).isEqualTo(UPDATED_BUYING_CRITERIA);
    }

    @Test
    @Transactional
    void patchNonExistingFieldBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();
        fieldBuyingCriteria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldBuyingCriteria.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();
        fieldBuyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldBuyingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = fieldBuyingCriteriaRepository.findAll().size();
        fieldBuyingCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldBuyingCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldBuyingCriteria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldBuyingCriteria in the database
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldBuyingCriteria() throws Exception {
        // Initialize the database
        fieldBuyingCriteriaRepository.saveAndFlush(fieldBuyingCriteria);

        int databaseSizeBeforeDelete = fieldBuyingCriteriaRepository.findAll().size();

        // Delete the fieldBuyingCriteria
        restFieldBuyingCriteriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldBuyingCriteria.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldBuyingCriteria> fieldBuyingCriteriaList = fieldBuyingCriteriaRepository.findAll();
        assertThat(fieldBuyingCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
