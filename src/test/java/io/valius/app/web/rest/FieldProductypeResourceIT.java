package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldProductype;
import io.valius.app.repository.FieldProductypeRepository;
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
 * Integration tests for the {@link FieldProductypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldProductypeResourceIT {

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-productypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldProductypeRepository fieldProductypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldProductypeMockMvc;

    private FieldProductype fieldProductype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldProductype createEntity(EntityManager em) {
        FieldProductype fieldProductype = new FieldProductype().productType(DEFAULT_PRODUCT_TYPE);
        return fieldProductype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldProductype createUpdatedEntity(EntityManager em) {
        FieldProductype fieldProductype = new FieldProductype().productType(UPDATED_PRODUCT_TYPE);
        return fieldProductype;
    }

    @BeforeEach
    public void initTest() {
        fieldProductype = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldProductype() throws Exception {
        int databaseSizeBeforeCreate = fieldProductypeRepository.findAll().size();
        // Create the FieldProductype
        restFieldProductypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isCreated());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeCreate + 1);
        FieldProductype testFieldProductype = fieldProductypeList.get(fieldProductypeList.size() - 1);
        assertThat(testFieldProductype.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void createFieldProductypeWithExistingId() throws Exception {
        // Create the FieldProductype with an existing ID
        fieldProductype.setId(1L);

        int databaseSizeBeforeCreate = fieldProductypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldProductypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldProductypes() throws Exception {
        // Initialize the database
        fieldProductypeRepository.saveAndFlush(fieldProductype);

        // Get all the fieldProductypeList
        restFieldProductypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldProductype.getId().intValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)));
    }

    @Test
    @Transactional
    void getFieldProductype() throws Exception {
        // Initialize the database
        fieldProductypeRepository.saveAndFlush(fieldProductype);

        // Get the fieldProductype
        restFieldProductypeMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldProductype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldProductype.getId().intValue()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingFieldProductype() throws Exception {
        // Get the fieldProductype
        restFieldProductypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldProductype() throws Exception {
        // Initialize the database
        fieldProductypeRepository.saveAndFlush(fieldProductype);

        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();

        // Update the fieldProductype
        FieldProductype updatedFieldProductype = fieldProductypeRepository.findById(fieldProductype.getId()).get();
        // Disconnect from session so that the updates on updatedFieldProductype are not directly saved in db
        em.detach(updatedFieldProductype);
        updatedFieldProductype.productType(UPDATED_PRODUCT_TYPE);

        restFieldProductypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldProductype.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldProductype))
            )
            .andExpect(status().isOk());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
        FieldProductype testFieldProductype = fieldProductypeList.get(fieldProductypeList.size() - 1);
        assertThat(testFieldProductype.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFieldProductype() throws Exception {
        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();
        fieldProductype.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldProductypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldProductype.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldProductype() throws Exception {
        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();
        fieldProductype.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldProductypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldProductype() throws Exception {
        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();
        fieldProductype.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldProductypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldProductypeWithPatch() throws Exception {
        // Initialize the database
        fieldProductypeRepository.saveAndFlush(fieldProductype);

        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();

        // Update the fieldProductype using partial update
        FieldProductype partialUpdatedFieldProductype = new FieldProductype();
        partialUpdatedFieldProductype.setId(fieldProductype.getId());

        restFieldProductypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldProductype.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldProductype))
            )
            .andExpect(status().isOk());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
        FieldProductype testFieldProductype = fieldProductypeList.get(fieldProductypeList.size() - 1);
        assertThat(testFieldProductype.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFieldProductypeWithPatch() throws Exception {
        // Initialize the database
        fieldProductypeRepository.saveAndFlush(fieldProductype);

        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();

        // Update the fieldProductype using partial update
        FieldProductype partialUpdatedFieldProductype = new FieldProductype();
        partialUpdatedFieldProductype.setId(fieldProductype.getId());

        partialUpdatedFieldProductype.productType(UPDATED_PRODUCT_TYPE);

        restFieldProductypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldProductype.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldProductype))
            )
            .andExpect(status().isOk());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
        FieldProductype testFieldProductype = fieldProductypeList.get(fieldProductypeList.size() - 1);
        assertThat(testFieldProductype.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFieldProductype() throws Exception {
        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();
        fieldProductype.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldProductypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldProductype.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldProductype() throws Exception {
        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();
        fieldProductype.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldProductypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldProductype() throws Exception {
        int databaseSizeBeforeUpdate = fieldProductypeRepository.findAll().size();
        fieldProductype.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldProductypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldProductype))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldProductype in the database
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldProductype() throws Exception {
        // Initialize the database
        fieldProductypeRepository.saveAndFlush(fieldProductype);

        int databaseSizeBeforeDelete = fieldProductypeRepository.findAll().size();

        // Delete the fieldProductype
        restFieldProductypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldProductype.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldProductype> fieldProductypeList = fieldProductypeRepository.findAll();
        assertThat(fieldProductypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
