package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.NoOfEmployees;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.NoOfEmployeesRepository;
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
 * Integration tests for the {@link NoOfEmployeesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoOfEmployeesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/no-of-employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NoOfEmployeesRepository noOfEmployeesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoOfEmployeesMockMvc;

    private NoOfEmployees noOfEmployees;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoOfEmployees createEntity(EntityManager em) {
        NoOfEmployees noOfEmployees = new NoOfEmployees().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return noOfEmployees;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoOfEmployees createUpdatedEntity(EntityManager em) {
        NoOfEmployees noOfEmployees = new NoOfEmployees().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return noOfEmployees;
    }

    @BeforeEach
    public void initTest() {
        noOfEmployees = createEntity(em);
    }

    @Test
    @Transactional
    void createNoOfEmployees() throws Exception {
        int databaseSizeBeforeCreate = noOfEmployeesRepository.findAll().size();
        // Create the NoOfEmployees
        restNoOfEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isCreated());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeCreate + 1);
        NoOfEmployees testNoOfEmployees = noOfEmployeesList.get(noOfEmployeesList.size() - 1);
        assertThat(testNoOfEmployees.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testNoOfEmployees.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNoOfEmployees.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createNoOfEmployeesWithExistingId() throws Exception {
        // Create the NoOfEmployees with an existing ID
        noOfEmployees.setId(1L);

        int databaseSizeBeforeCreate = noOfEmployeesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoOfEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = noOfEmployeesRepository.findAll().size();
        // set the field null
        noOfEmployees.setValue(null);

        // Create the NoOfEmployees, which fails.

        restNoOfEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isBadRequest());

        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = noOfEmployeesRepository.findAll().size();
        // set the field null
        noOfEmployees.setLanguage(null);

        // Create the NoOfEmployees, which fails.

        restNoOfEmployeesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isBadRequest());

        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNoOfEmployees() throws Exception {
        // Initialize the database
        noOfEmployeesRepository.saveAndFlush(noOfEmployees);

        // Get all the noOfEmployeesList
        restNoOfEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noOfEmployees.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getNoOfEmployees() throws Exception {
        // Initialize the database
        noOfEmployeesRepository.saveAndFlush(noOfEmployees);

        // Get the noOfEmployees
        restNoOfEmployeesMockMvc
            .perform(get(ENTITY_API_URL_ID, noOfEmployees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noOfEmployees.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNoOfEmployees() throws Exception {
        // Get the noOfEmployees
        restNoOfEmployeesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNoOfEmployees() throws Exception {
        // Initialize the database
        noOfEmployeesRepository.saveAndFlush(noOfEmployees);

        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();

        // Update the noOfEmployees
        NoOfEmployees updatedNoOfEmployees = noOfEmployeesRepository.findById(noOfEmployees.getId()).get();
        // Disconnect from session so that the updates on updatedNoOfEmployees are not directly saved in db
        em.detach(updatedNoOfEmployees);
        updatedNoOfEmployees.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restNoOfEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNoOfEmployees.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNoOfEmployees))
            )
            .andExpect(status().isOk());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
        NoOfEmployees testNoOfEmployees = noOfEmployeesList.get(noOfEmployeesList.size() - 1);
        assertThat(testNoOfEmployees.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testNoOfEmployees.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNoOfEmployees.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingNoOfEmployees() throws Exception {
        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();
        noOfEmployees.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoOfEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noOfEmployees.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoOfEmployees() throws Exception {
        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();
        noOfEmployees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoOfEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoOfEmployees() throws Exception {
        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();
        noOfEmployees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoOfEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoOfEmployeesWithPatch() throws Exception {
        // Initialize the database
        noOfEmployeesRepository.saveAndFlush(noOfEmployees);

        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();

        // Update the noOfEmployees using partial update
        NoOfEmployees partialUpdatedNoOfEmployees = new NoOfEmployees();
        partialUpdatedNoOfEmployees.setId(noOfEmployees.getId());

        partialUpdatedNoOfEmployees.value(UPDATED_VALUE);

        restNoOfEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoOfEmployees.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoOfEmployees))
            )
            .andExpect(status().isOk());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
        NoOfEmployees testNoOfEmployees = noOfEmployeesList.get(noOfEmployeesList.size() - 1);
        assertThat(testNoOfEmployees.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testNoOfEmployees.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNoOfEmployees.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateNoOfEmployeesWithPatch() throws Exception {
        // Initialize the database
        noOfEmployeesRepository.saveAndFlush(noOfEmployees);

        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();

        // Update the noOfEmployees using partial update
        NoOfEmployees partialUpdatedNoOfEmployees = new NoOfEmployees();
        partialUpdatedNoOfEmployees.setId(noOfEmployees.getId());

        partialUpdatedNoOfEmployees.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restNoOfEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoOfEmployees.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoOfEmployees))
            )
            .andExpect(status().isOk());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
        NoOfEmployees testNoOfEmployees = noOfEmployeesList.get(noOfEmployeesList.size() - 1);
        assertThat(testNoOfEmployees.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testNoOfEmployees.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNoOfEmployees.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingNoOfEmployees() throws Exception {
        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();
        noOfEmployees.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoOfEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noOfEmployees.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoOfEmployees() throws Exception {
        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();
        noOfEmployees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoOfEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoOfEmployees() throws Exception {
        int databaseSizeBeforeUpdate = noOfEmployeesRepository.findAll().size();
        noOfEmployees.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoOfEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noOfEmployees))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoOfEmployees in the database
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoOfEmployees() throws Exception {
        // Initialize the database
        noOfEmployeesRepository.saveAndFlush(noOfEmployees);

        int databaseSizeBeforeDelete = noOfEmployeesRepository.findAll().size();

        // Delete the noOfEmployees
        restNoOfEmployeesMockMvc
            .perform(delete(ENTITY_API_URL_ID, noOfEmployees.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NoOfEmployees> noOfEmployeesList = noOfEmployeesRepository.findAll();
        assertThat(noOfEmployeesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
