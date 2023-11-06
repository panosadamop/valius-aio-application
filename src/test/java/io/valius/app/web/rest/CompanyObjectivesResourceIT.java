package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.CompanyObjectives;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.CompanyObjectivesRepository;
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
 * Integration tests for the {@link CompanyObjectivesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyObjectivesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_PLACEHOLDER = "AAAAAAAAAA";
    private static final String UPDATED_PLACEHOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/company-objectives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyObjectivesRepository companyObjectivesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyObjectivesMockMvc;

    private CompanyObjectives companyObjectives;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyObjectives createEntity(EntityManager em) {
        CompanyObjectives companyObjectives = new CompanyObjectives()
            .value(DEFAULT_VALUE)
            .placeholder(DEFAULT_PLACEHOLDER)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return companyObjectives;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyObjectives createUpdatedEntity(EntityManager em) {
        CompanyObjectives companyObjectives = new CompanyObjectives()
            .value(UPDATED_VALUE)
            .placeholder(UPDATED_PLACEHOLDER)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return companyObjectives;
    }

    @BeforeEach
    public void initTest() {
        companyObjectives = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyObjectives() throws Exception {
        int databaseSizeBeforeCreate = companyObjectivesRepository.findAll().size();
        // Create the CompanyObjectives
        restCompanyObjectivesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyObjectives testCompanyObjectives = companyObjectivesList.get(companyObjectivesList.size() - 1);
        assertThat(testCompanyObjectives.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCompanyObjectives.getPlaceholder()).isEqualTo(DEFAULT_PLACEHOLDER);
        assertThat(testCompanyObjectives.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompanyObjectives.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCompanyObjectivesWithExistingId() throws Exception {
        // Create the CompanyObjectives with an existing ID
        companyObjectives.setId(1L);

        int databaseSizeBeforeCreate = companyObjectivesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyObjectivesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyObjectivesRepository.findAll().size();
        // set the field null
        companyObjectives.setValue(null);

        // Create the CompanyObjectives, which fails.

        restCompanyObjectivesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isBadRequest());

        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyObjectivesRepository.findAll().size();
        // set the field null
        companyObjectives.setLanguage(null);

        // Create the CompanyObjectives, which fails.

        restCompanyObjectivesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isBadRequest());

        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanyObjectives() throws Exception {
        // Initialize the database
        companyObjectivesRepository.saveAndFlush(companyObjectives);

        // Get all the companyObjectivesList
        restCompanyObjectivesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyObjectives.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].placeholder").value(hasItem(DEFAULT_PLACEHOLDER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getCompanyObjectives() throws Exception {
        // Initialize the database
        companyObjectivesRepository.saveAndFlush(companyObjectives);

        // Get the companyObjectives
        restCompanyObjectivesMockMvc
            .perform(get(ENTITY_API_URL_ID, companyObjectives.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyObjectives.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.placeholder").value(DEFAULT_PLACEHOLDER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompanyObjectives() throws Exception {
        // Get the companyObjectives
        restCompanyObjectivesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompanyObjectives() throws Exception {
        // Initialize the database
        companyObjectivesRepository.saveAndFlush(companyObjectives);

        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();

        // Update the companyObjectives
        CompanyObjectives updatedCompanyObjectives = companyObjectivesRepository.findById(companyObjectives.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyObjectives are not directly saved in db
        em.detach(updatedCompanyObjectives);
        updatedCompanyObjectives
            .value(UPDATED_VALUE)
            .placeholder(UPDATED_PLACEHOLDER)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);

        restCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompanyObjectives.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompanyObjectives))
            )
            .andExpect(status().isOk());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
        CompanyObjectives testCompanyObjectives = companyObjectivesList.get(companyObjectivesList.size() - 1);
        assertThat(testCompanyObjectives.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompanyObjectives.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testCompanyObjectives.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompanyObjectives.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();
        companyObjectives.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyObjectives.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();
        companyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();
        companyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyObjectivesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyObjectivesWithPatch() throws Exception {
        // Initialize the database
        companyObjectivesRepository.saveAndFlush(companyObjectives);

        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();

        // Update the companyObjectives using partial update
        CompanyObjectives partialUpdatedCompanyObjectives = new CompanyObjectives();
        partialUpdatedCompanyObjectives.setId(companyObjectives.getId());

        partialUpdatedCompanyObjectives.value(UPDATED_VALUE).placeholder(UPDATED_PLACEHOLDER).language(UPDATED_LANGUAGE);

        restCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyObjectives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyObjectives))
            )
            .andExpect(status().isOk());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
        CompanyObjectives testCompanyObjectives = companyObjectivesList.get(companyObjectivesList.size() - 1);
        assertThat(testCompanyObjectives.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompanyObjectives.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testCompanyObjectives.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompanyObjectives.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCompanyObjectivesWithPatch() throws Exception {
        // Initialize the database
        companyObjectivesRepository.saveAndFlush(companyObjectives);

        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();

        // Update the companyObjectives using partial update
        CompanyObjectives partialUpdatedCompanyObjectives = new CompanyObjectives();
        partialUpdatedCompanyObjectives.setId(companyObjectives.getId());

        partialUpdatedCompanyObjectives
            .value(UPDATED_VALUE)
            .placeholder(UPDATED_PLACEHOLDER)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);

        restCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyObjectives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyObjectives))
            )
            .andExpect(status().isOk());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
        CompanyObjectives testCompanyObjectives = companyObjectivesList.get(companyObjectivesList.size() - 1);
        assertThat(testCompanyObjectives.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCompanyObjectives.getPlaceholder()).isEqualTo(UPDATED_PLACEHOLDER);
        assertThat(testCompanyObjectives.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompanyObjectives.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();
        companyObjectives.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyObjectives.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();
        companyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyObjectives() throws Exception {
        int databaseSizeBeforeUpdate = companyObjectivesRepository.findAll().size();
        companyObjectives.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyObjectivesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyObjectives))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyObjectives in the database
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyObjectives() throws Exception {
        // Initialize the database
        companyObjectivesRepository.saveAndFlush(companyObjectives);

        int databaseSizeBeforeDelete = companyObjectivesRepository.findAll().size();

        // Delete the companyObjectives
        restCompanyObjectivesMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyObjectives.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyObjectives> companyObjectivesList = companyObjectivesRepository.findAll();
        assertThat(companyObjectivesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
