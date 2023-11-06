package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.MaturityPhase;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.MaturityPhaseRepository;
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
 * Integration tests for the {@link MaturityPhaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaturityPhaseResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/maturity-phases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaturityPhaseRepository maturityPhaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaturityPhaseMockMvc;

    private MaturityPhase maturityPhase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaturityPhase createEntity(EntityManager em) {
        MaturityPhase maturityPhase = new MaturityPhase().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return maturityPhase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaturityPhase createUpdatedEntity(EntityManager em) {
        MaturityPhase maturityPhase = new MaturityPhase().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return maturityPhase;
    }

    @BeforeEach
    public void initTest() {
        maturityPhase = createEntity(em);
    }

    @Test
    @Transactional
    void createMaturityPhase() throws Exception {
        int databaseSizeBeforeCreate = maturityPhaseRepository.findAll().size();
        // Create the MaturityPhase
        restMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isCreated());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeCreate + 1);
        MaturityPhase testMaturityPhase = maturityPhaseList.get(maturityPhaseList.size() - 1);
        assertThat(testMaturityPhase.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMaturityPhase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMaturityPhase.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createMaturityPhaseWithExistingId() throws Exception {
        // Create the MaturityPhase with an existing ID
        maturityPhase.setId(1L);

        int databaseSizeBeforeCreate = maturityPhaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = maturityPhaseRepository.findAll().size();
        // set the field null
        maturityPhase.setValue(null);

        // Create the MaturityPhase, which fails.

        restMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isBadRequest());

        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = maturityPhaseRepository.findAll().size();
        // set the field null
        maturityPhase.setLanguage(null);

        // Create the MaturityPhase, which fails.

        restMaturityPhaseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isBadRequest());

        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaturityPhases() throws Exception {
        // Initialize the database
        maturityPhaseRepository.saveAndFlush(maturityPhase);

        // Get all the maturityPhaseList
        restMaturityPhaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maturityPhase.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getMaturityPhase() throws Exception {
        // Initialize the database
        maturityPhaseRepository.saveAndFlush(maturityPhase);

        // Get the maturityPhase
        restMaturityPhaseMockMvc
            .perform(get(ENTITY_API_URL_ID, maturityPhase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maturityPhase.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMaturityPhase() throws Exception {
        // Get the maturityPhase
        restMaturityPhaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaturityPhase() throws Exception {
        // Initialize the database
        maturityPhaseRepository.saveAndFlush(maturityPhase);

        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();

        // Update the maturityPhase
        MaturityPhase updatedMaturityPhase = maturityPhaseRepository.findById(maturityPhase.getId()).get();
        // Disconnect from session so that the updates on updatedMaturityPhase are not directly saved in db
        em.detach(updatedMaturityPhase);
        updatedMaturityPhase.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaturityPhase.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaturityPhase))
            )
            .andExpect(status().isOk());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
        MaturityPhase testMaturityPhase = maturityPhaseList.get(maturityPhaseList.size() - 1);
        assertThat(testMaturityPhase.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMaturityPhase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMaturityPhase.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();
        maturityPhase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maturityPhase.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();
        maturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();
        maturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaturityPhaseMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaturityPhaseWithPatch() throws Exception {
        // Initialize the database
        maturityPhaseRepository.saveAndFlush(maturityPhase);

        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();

        // Update the maturityPhase using partial update
        MaturityPhase partialUpdatedMaturityPhase = new MaturityPhase();
        partialUpdatedMaturityPhase.setId(maturityPhase.getId());

        partialUpdatedMaturityPhase.value(UPDATED_VALUE);

        restMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaturityPhase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaturityPhase))
            )
            .andExpect(status().isOk());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
        MaturityPhase testMaturityPhase = maturityPhaseList.get(maturityPhaseList.size() - 1);
        assertThat(testMaturityPhase.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMaturityPhase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMaturityPhase.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateMaturityPhaseWithPatch() throws Exception {
        // Initialize the database
        maturityPhaseRepository.saveAndFlush(maturityPhase);

        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();

        // Update the maturityPhase using partial update
        MaturityPhase partialUpdatedMaturityPhase = new MaturityPhase();
        partialUpdatedMaturityPhase.setId(maturityPhase.getId());

        partialUpdatedMaturityPhase.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaturityPhase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaturityPhase))
            )
            .andExpect(status().isOk());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
        MaturityPhase testMaturityPhase = maturityPhaseList.get(maturityPhaseList.size() - 1);
        assertThat(testMaturityPhase.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMaturityPhase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMaturityPhase.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();
        maturityPhase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, maturityPhase.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();
        maturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaturityPhase() throws Exception {
        int databaseSizeBeforeUpdate = maturityPhaseRepository.findAll().size();
        maturityPhase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaturityPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maturityPhase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaturityPhase in the database
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaturityPhase() throws Exception {
        // Initialize the database
        maturityPhaseRepository.saveAndFlush(maturityPhase);

        int databaseSizeBeforeDelete = maturityPhaseRepository.findAll().size();

        // Delete the maturityPhase
        restMaturityPhaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, maturityPhase.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaturityPhase> maturityPhaseList = maturityPhaseRepository.findAll();
        assertThat(maturityPhaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
