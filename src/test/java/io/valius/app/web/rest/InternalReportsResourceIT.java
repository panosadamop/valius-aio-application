package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.InternalReports;
import io.valius.app.repository.InternalReportsRepository;
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
 * Integration tests for the {@link InternalReportsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InternalReportsResourceIT {

    private static final String ENTITY_API_URL = "/api/internal-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InternalReportsRepository internalReportsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInternalReportsMockMvc;

    private InternalReports internalReports;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternalReports createEntity(EntityManager em) {
        InternalReports internalReports = new InternalReports();
        return internalReports;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternalReports createUpdatedEntity(EntityManager em) {
        InternalReports internalReports = new InternalReports();
        return internalReports;
    }

    @BeforeEach
    public void initTest() {
        internalReports = createEntity(em);
    }

    @Test
    @Transactional
    void createInternalReports() throws Exception {
        int databaseSizeBeforeCreate = internalReportsRepository.findAll().size();
        // Create the InternalReports
        restInternalReportsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isCreated());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeCreate + 1);
        InternalReports testInternalReports = internalReportsList.get(internalReportsList.size() - 1);
    }

    @Test
    @Transactional
    void createInternalReportsWithExistingId() throws Exception {
        // Create the InternalReports with an existing ID
        internalReports.setId(1L);

        int databaseSizeBeforeCreate = internalReportsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternalReportsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInternalReports() throws Exception {
        // Initialize the database
        internalReportsRepository.saveAndFlush(internalReports);

        // Get all the internalReportsList
        restInternalReportsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internalReports.getId().intValue())));
    }

    @Test
    @Transactional
    void getInternalReports() throws Exception {
        // Initialize the database
        internalReportsRepository.saveAndFlush(internalReports);

        // Get the internalReports
        restInternalReportsMockMvc
            .perform(get(ENTITY_API_URL_ID, internalReports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(internalReports.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingInternalReports() throws Exception {
        // Get the internalReports
        restInternalReportsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInternalReports() throws Exception {
        // Initialize the database
        internalReportsRepository.saveAndFlush(internalReports);

        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();

        // Update the internalReports
        InternalReports updatedInternalReports = internalReportsRepository.findById(internalReports.getId()).get();
        // Disconnect from session so that the updates on updatedInternalReports are not directly saved in db
        em.detach(updatedInternalReports);

        restInternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInternalReports.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInternalReports))
            )
            .andExpect(status().isOk());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
        InternalReports testInternalReports = internalReportsList.get(internalReportsList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingInternalReports() throws Exception {
        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();
        internalReports.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, internalReports.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInternalReports() throws Exception {
        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();
        internalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInternalReports() throws Exception {
        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();
        internalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInternalReportsWithPatch() throws Exception {
        // Initialize the database
        internalReportsRepository.saveAndFlush(internalReports);

        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();

        // Update the internalReports using partial update
        InternalReports partialUpdatedInternalReports = new InternalReports();
        partialUpdatedInternalReports.setId(internalReports.getId());

        restInternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInternalReports.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInternalReports))
            )
            .andExpect(status().isOk());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
        InternalReports testInternalReports = internalReportsList.get(internalReportsList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateInternalReportsWithPatch() throws Exception {
        // Initialize the database
        internalReportsRepository.saveAndFlush(internalReports);

        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();

        // Update the internalReports using partial update
        InternalReports partialUpdatedInternalReports = new InternalReports();
        partialUpdatedInternalReports.setId(internalReports.getId());

        restInternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInternalReports.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInternalReports))
            )
            .andExpect(status().isOk());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
        InternalReports testInternalReports = internalReportsList.get(internalReportsList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingInternalReports() throws Exception {
        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();
        internalReports.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, internalReports.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInternalReports() throws Exception {
        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();
        internalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInternalReports() throws Exception {
        int databaseSizeBeforeUpdate = internalReportsRepository.findAll().size();
        internalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internalReports))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InternalReports in the database
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInternalReports() throws Exception {
        // Initialize the database
        internalReportsRepository.saveAndFlush(internalReports);

        int databaseSizeBeforeDelete = internalReportsRepository.findAll().size();

        // Delete the internalReports
        restInternalReportsMockMvc
            .perform(delete(ENTITY_API_URL_ID, internalReports.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InternalReports> internalReportsList = internalReportsRepository.findAll();
        assertThat(internalReportsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
