package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.ExternalReports;
import io.valius.app.repository.ExternalReportsRepository;
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
 * Integration tests for the {@link ExternalReportsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExternalReportsResourceIT {

    private static final String DEFAULT_REPORT_URL = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/external-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExternalReportsRepository externalReportsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExternalReportsMockMvc;

    private ExternalReports externalReports;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalReports createEntity(EntityManager em) {
        ExternalReports externalReports = new ExternalReports().reportUrl(DEFAULT_REPORT_URL).description(DEFAULT_DESCRIPTION);
        return externalReports;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalReports createUpdatedEntity(EntityManager em) {
        ExternalReports externalReports = new ExternalReports().reportUrl(UPDATED_REPORT_URL).description(UPDATED_DESCRIPTION);
        return externalReports;
    }

    @BeforeEach
    public void initTest() {
        externalReports = createEntity(em);
    }

    @Test
    @Transactional
    void createExternalReports() throws Exception {
        int databaseSizeBeforeCreate = externalReportsRepository.findAll().size();
        // Create the ExternalReports
        restExternalReportsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isCreated());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalReports testExternalReports = externalReportsList.get(externalReportsList.size() - 1);
        assertThat(testExternalReports.getReportUrl()).isEqualTo(DEFAULT_REPORT_URL);
        assertThat(testExternalReports.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createExternalReportsWithExistingId() throws Exception {
        // Create the ExternalReports with an existing ID
        externalReports.setId(1L);

        int databaseSizeBeforeCreate = externalReportsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalReportsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReportUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalReportsRepository.findAll().size();
        // set the field null
        externalReports.setReportUrl(null);

        // Create the ExternalReports, which fails.

        restExternalReportsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isBadRequest());

        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExternalReports() throws Exception {
        // Initialize the database
        externalReportsRepository.saveAndFlush(externalReports);

        // Get all the externalReportsList
        restExternalReportsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalReports.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportUrl").value(hasItem(DEFAULT_REPORT_URL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getExternalReports() throws Exception {
        // Initialize the database
        externalReportsRepository.saveAndFlush(externalReports);

        // Get the externalReports
        restExternalReportsMockMvc
            .perform(get(ENTITY_API_URL_ID, externalReports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(externalReports.getId().intValue()))
            .andExpect(jsonPath("$.reportUrl").value(DEFAULT_REPORT_URL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingExternalReports() throws Exception {
        // Get the externalReports
        restExternalReportsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExternalReports() throws Exception {
        // Initialize the database
        externalReportsRepository.saveAndFlush(externalReports);

        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();

        // Update the externalReports
        ExternalReports updatedExternalReports = externalReportsRepository.findById(externalReports.getId()).get();
        // Disconnect from session so that the updates on updatedExternalReports are not directly saved in db
        em.detach(updatedExternalReports);
        updatedExternalReports.reportUrl(UPDATED_REPORT_URL).description(UPDATED_DESCRIPTION);

        restExternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExternalReports.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedExternalReports))
            )
            .andExpect(status().isOk());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
        ExternalReports testExternalReports = externalReportsList.get(externalReportsList.size() - 1);
        assertThat(testExternalReports.getReportUrl()).isEqualTo(UPDATED_REPORT_URL);
        assertThat(testExternalReports.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingExternalReports() throws Exception {
        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();
        externalReports.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, externalReports.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExternalReports() throws Exception {
        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();
        externalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExternalReports() throws Exception {
        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();
        externalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalReportsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExternalReportsWithPatch() throws Exception {
        // Initialize the database
        externalReportsRepository.saveAndFlush(externalReports);

        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();

        // Update the externalReports using partial update
        ExternalReports partialUpdatedExternalReports = new ExternalReports();
        partialUpdatedExternalReports.setId(externalReports.getId());

        partialUpdatedExternalReports.reportUrl(UPDATED_REPORT_URL).description(UPDATED_DESCRIPTION);

        restExternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExternalReports.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExternalReports))
            )
            .andExpect(status().isOk());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
        ExternalReports testExternalReports = externalReportsList.get(externalReportsList.size() - 1);
        assertThat(testExternalReports.getReportUrl()).isEqualTo(UPDATED_REPORT_URL);
        assertThat(testExternalReports.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateExternalReportsWithPatch() throws Exception {
        // Initialize the database
        externalReportsRepository.saveAndFlush(externalReports);

        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();

        // Update the externalReports using partial update
        ExternalReports partialUpdatedExternalReports = new ExternalReports();
        partialUpdatedExternalReports.setId(externalReports.getId());

        partialUpdatedExternalReports.reportUrl(UPDATED_REPORT_URL).description(UPDATED_DESCRIPTION);

        restExternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExternalReports.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExternalReports))
            )
            .andExpect(status().isOk());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
        ExternalReports testExternalReports = externalReportsList.get(externalReportsList.size() - 1);
        assertThat(testExternalReports.getReportUrl()).isEqualTo(UPDATED_REPORT_URL);
        assertThat(testExternalReports.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingExternalReports() throws Exception {
        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();
        externalReports.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, externalReports.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExternalReports() throws Exception {
        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();
        externalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExternalReports() throws Exception {
        int databaseSizeBeforeUpdate = externalReportsRepository.findAll().size();
        externalReports.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternalReportsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(externalReports))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExternalReports in the database
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExternalReports() throws Exception {
        // Initialize the database
        externalReportsRepository.saveAndFlush(externalReports);

        int databaseSizeBeforeDelete = externalReportsRepository.findAll().size();

        // Delete the externalReports
        restExternalReportsMockMvc
            .perform(delete(ENTITY_API_URL_ID, externalReports.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExternalReports> externalReportsList = externalReportsRepository.findAll();
        assertThat(externalReportsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
