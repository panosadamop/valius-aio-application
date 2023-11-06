package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldKpi;
import io.valius.app.repository.FieldKpiRepository;
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
 * Integration tests for the {@link FieldKpiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldKpiResourceIT {

    private static final String DEFAULT_KPIS = "AAAAAAAAAA";
    private static final String UPDATED_KPIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-kpis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldKpiRepository fieldKpiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldKpiMockMvc;

    private FieldKpi fieldKpi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldKpi createEntity(EntityManager em) {
        FieldKpi fieldKpi = new FieldKpi().kpis(DEFAULT_KPIS);
        return fieldKpi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldKpi createUpdatedEntity(EntityManager em) {
        FieldKpi fieldKpi = new FieldKpi().kpis(UPDATED_KPIS);
        return fieldKpi;
    }

    @BeforeEach
    public void initTest() {
        fieldKpi = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldKpi() throws Exception {
        int databaseSizeBeforeCreate = fieldKpiRepository.findAll().size();
        // Create the FieldKpi
        restFieldKpiMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isCreated());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeCreate + 1);
        FieldKpi testFieldKpi = fieldKpiList.get(fieldKpiList.size() - 1);
        assertThat(testFieldKpi.getKpis()).isEqualTo(DEFAULT_KPIS);
    }

    @Test
    @Transactional
    void createFieldKpiWithExistingId() throws Exception {
        // Create the FieldKpi with an existing ID
        fieldKpi.setId(1L);

        int databaseSizeBeforeCreate = fieldKpiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldKpiMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldKpis() throws Exception {
        // Initialize the database
        fieldKpiRepository.saveAndFlush(fieldKpi);

        // Get all the fieldKpiList
        restFieldKpiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldKpi.getId().intValue())))
            .andExpect(jsonPath("$.[*].kpis").value(hasItem(DEFAULT_KPIS)));
    }

    @Test
    @Transactional
    void getFieldKpi() throws Exception {
        // Initialize the database
        fieldKpiRepository.saveAndFlush(fieldKpi);

        // Get the fieldKpi
        restFieldKpiMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldKpi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldKpi.getId().intValue()))
            .andExpect(jsonPath("$.kpis").value(DEFAULT_KPIS));
    }

    @Test
    @Transactional
    void getNonExistingFieldKpi() throws Exception {
        // Get the fieldKpi
        restFieldKpiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldKpi() throws Exception {
        // Initialize the database
        fieldKpiRepository.saveAndFlush(fieldKpi);

        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();

        // Update the fieldKpi
        FieldKpi updatedFieldKpi = fieldKpiRepository.findById(fieldKpi.getId()).get();
        // Disconnect from session so that the updates on updatedFieldKpi are not directly saved in db
        em.detach(updatedFieldKpi);
        updatedFieldKpi.kpis(UPDATED_KPIS);

        restFieldKpiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldKpi.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldKpi))
            )
            .andExpect(status().isOk());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
        FieldKpi testFieldKpi = fieldKpiList.get(fieldKpiList.size() - 1);
        assertThat(testFieldKpi.getKpis()).isEqualTo(UPDATED_KPIS);
    }

    @Test
    @Transactional
    void putNonExistingFieldKpi() throws Exception {
        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();
        fieldKpi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldKpiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldKpi.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldKpi() throws Exception {
        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();
        fieldKpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldKpiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldKpi() throws Exception {
        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();
        fieldKpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldKpiMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldKpiWithPatch() throws Exception {
        // Initialize the database
        fieldKpiRepository.saveAndFlush(fieldKpi);

        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();

        // Update the fieldKpi using partial update
        FieldKpi partialUpdatedFieldKpi = new FieldKpi();
        partialUpdatedFieldKpi.setId(fieldKpi.getId());

        partialUpdatedFieldKpi.kpis(UPDATED_KPIS);

        restFieldKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldKpi.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldKpi))
            )
            .andExpect(status().isOk());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
        FieldKpi testFieldKpi = fieldKpiList.get(fieldKpiList.size() - 1);
        assertThat(testFieldKpi.getKpis()).isEqualTo(UPDATED_KPIS);
    }

    @Test
    @Transactional
    void fullUpdateFieldKpiWithPatch() throws Exception {
        // Initialize the database
        fieldKpiRepository.saveAndFlush(fieldKpi);

        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();

        // Update the fieldKpi using partial update
        FieldKpi partialUpdatedFieldKpi = new FieldKpi();
        partialUpdatedFieldKpi.setId(fieldKpi.getId());

        partialUpdatedFieldKpi.kpis(UPDATED_KPIS);

        restFieldKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldKpi.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldKpi))
            )
            .andExpect(status().isOk());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
        FieldKpi testFieldKpi = fieldKpiList.get(fieldKpiList.size() - 1);
        assertThat(testFieldKpi.getKpis()).isEqualTo(UPDATED_KPIS);
    }

    @Test
    @Transactional
    void patchNonExistingFieldKpi() throws Exception {
        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();
        fieldKpi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldKpi.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldKpi() throws Exception {
        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();
        fieldKpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldKpi() throws Exception {
        int databaseSizeBeforeUpdate = fieldKpiRepository.findAll().size();
        fieldKpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldKpiMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldKpi))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldKpi in the database
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldKpi() throws Exception {
        // Initialize the database
        fieldKpiRepository.saveAndFlush(fieldKpi);

        int databaseSizeBeforeDelete = fieldKpiRepository.findAll().size();

        // Delete the fieldKpi
        restFieldKpiMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldKpi.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldKpi> fieldKpiList = fieldKpiRepository.findAll();
        assertThat(fieldKpiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
