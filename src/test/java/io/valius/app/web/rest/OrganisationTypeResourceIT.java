package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.OrganisationType;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.OrganisationTypeRepository;
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
 * Integration tests for the {@link OrganisationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganisationTypeResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/organisation-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganisationTypeRepository organisationTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisationTypeMockMvc;

    private OrganisationType organisationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationType createEntity(EntityManager em) {
        OrganisationType organisationType = new OrganisationType()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return organisationType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationType createUpdatedEntity(EntityManager em) {
        OrganisationType organisationType = new OrganisationType()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return organisationType;
    }

    @BeforeEach
    public void initTest() {
        organisationType = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganisationType() throws Exception {
        int databaseSizeBeforeCreate = organisationTypeRepository.findAll().size();
        // Create the OrganisationType
        restOrganisationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isCreated());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OrganisationType testOrganisationType = organisationTypeList.get(organisationTypeList.size() - 1);
        assertThat(testOrganisationType.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrganisationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganisationType.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createOrganisationTypeWithExistingId() throws Exception {
        // Create the OrganisationType with an existing ID
        organisationType.setId(1L);

        int databaseSizeBeforeCreate = organisationTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationTypeRepository.findAll().size();
        // set the field null
        organisationType.setValue(null);

        // Create the OrganisationType, which fails.

        restOrganisationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isBadRequest());

        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationTypeRepository.findAll().size();
        // set the field null
        organisationType.setLanguage(null);

        // Create the OrganisationType, which fails.

        restOrganisationTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isBadRequest());

        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganisationTypes() throws Exception {
        // Initialize the database
        organisationTypeRepository.saveAndFlush(organisationType);

        // Get all the organisationTypeList
        restOrganisationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getOrganisationType() throws Exception {
        // Initialize the database
        organisationTypeRepository.saveAndFlush(organisationType);

        // Get the organisationType
        restOrganisationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, organisationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisationType.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganisationType() throws Exception {
        // Get the organisationType
        restOrganisationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganisationType() throws Exception {
        // Initialize the database
        organisationTypeRepository.saveAndFlush(organisationType);

        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();

        // Update the organisationType
        OrganisationType updatedOrganisationType = organisationTypeRepository.findById(organisationType.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisationType are not directly saved in db
        em.detach(updatedOrganisationType);
        updatedOrganisationType.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restOrganisationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganisationType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrganisationType))
            )
            .andExpect(status().isOk());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
        OrganisationType testOrganisationType = organisationTypeList.get(organisationTypeList.size() - 1);
        assertThat(testOrganisationType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrganisationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganisationType.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingOrganisationType() throws Exception {
        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();
        organisationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisationType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganisationType() throws Exception {
        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();
        organisationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganisationType() throws Exception {
        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();
        organisationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganisationTypeWithPatch() throws Exception {
        // Initialize the database
        organisationTypeRepository.saveAndFlush(organisationType);

        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();

        // Update the organisationType using partial update
        OrganisationType partialUpdatedOrganisationType = new OrganisationType();
        partialUpdatedOrganisationType.setId(organisationType.getId());

        partialUpdatedOrganisationType.value(UPDATED_VALUE);

        restOrganisationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisationType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisationType))
            )
            .andExpect(status().isOk());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
        OrganisationType testOrganisationType = organisationTypeList.get(organisationTypeList.size() - 1);
        assertThat(testOrganisationType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrganisationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganisationType.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateOrganisationTypeWithPatch() throws Exception {
        // Initialize the database
        organisationTypeRepository.saveAndFlush(organisationType);

        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();

        // Update the organisationType using partial update
        OrganisationType partialUpdatedOrganisationType = new OrganisationType();
        partialUpdatedOrganisationType.setId(organisationType.getId());

        partialUpdatedOrganisationType.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restOrganisationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisationType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisationType))
            )
            .andExpect(status().isOk());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
        OrganisationType testOrganisationType = organisationTypeList.get(organisationTypeList.size() - 1);
        assertThat(testOrganisationType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrganisationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganisationType.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingOrganisationType() throws Exception {
        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();
        organisationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organisationType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganisationType() throws Exception {
        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();
        organisationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganisationType() throws Exception {
        int databaseSizeBeforeUpdate = organisationTypeRepository.findAll().size();
        organisationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganisationType in the database
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganisationType() throws Exception {
        // Initialize the database
        organisationTypeRepository.saveAndFlush(organisationType);

        int databaseSizeBeforeDelete = organisationTypeRepository.findAll().size();

        // Delete the organisationType
        restOrganisationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, organisationType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganisationType> organisationTypeList = organisationTypeRepository.findAll();
        assertThat(organisationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
