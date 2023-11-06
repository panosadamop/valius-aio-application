package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.Occupation;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.OccupationRepository;
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
 * Integration tests for the {@link OccupationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OccupationResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/occupations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OccupationRepository occupationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOccupationMockMvc;

    private Occupation occupation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Occupation createEntity(EntityManager em) {
        Occupation occupation = new Occupation().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return occupation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Occupation createUpdatedEntity(EntityManager em) {
        Occupation occupation = new Occupation().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return occupation;
    }

    @BeforeEach
    public void initTest() {
        occupation = createEntity(em);
    }

    @Test
    @Transactional
    void createOccupation() throws Exception {
        int databaseSizeBeforeCreate = occupationRepository.findAll().size();
        // Create the Occupation
        restOccupationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isCreated());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeCreate + 1);
        Occupation testOccupation = occupationList.get(occupationList.size() - 1);
        assertThat(testOccupation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOccupation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOccupation.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createOccupationWithExistingId() throws Exception {
        // Create the Occupation with an existing ID
        occupation.setId(1L);

        int databaseSizeBeforeCreate = occupationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccupationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = occupationRepository.findAll().size();
        // set the field null
        occupation.setValue(null);

        // Create the Occupation, which fails.

        restOccupationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isBadRequest());

        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = occupationRepository.findAll().size();
        // set the field null
        occupation.setLanguage(null);

        // Create the Occupation, which fails.

        restOccupationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isBadRequest());

        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOccupations() throws Exception {
        // Initialize the database
        occupationRepository.saveAndFlush(occupation);

        // Get all the occupationList
        restOccupationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occupation.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getOccupation() throws Exception {
        // Initialize the database
        occupationRepository.saveAndFlush(occupation);

        // Get the occupation
        restOccupationMockMvc
            .perform(get(ENTITY_API_URL_ID, occupation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(occupation.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOccupation() throws Exception {
        // Get the occupation
        restOccupationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOccupation() throws Exception {
        // Initialize the database
        occupationRepository.saveAndFlush(occupation);

        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();

        // Update the occupation
        Occupation updatedOccupation = occupationRepository.findById(occupation.getId()).get();
        // Disconnect from session so that the updates on updatedOccupation are not directly saved in db
        em.detach(updatedOccupation);
        updatedOccupation.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restOccupationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOccupation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOccupation))
            )
            .andExpect(status().isOk());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
        Occupation testOccupation = occupationList.get(occupationList.size() - 1);
        assertThat(testOccupation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOccupation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOccupation.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingOccupation() throws Exception {
        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();
        occupation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccupationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, occupation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOccupation() throws Exception {
        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();
        occupation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOccupation() throws Exception {
        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();
        occupation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOccupationWithPatch() throws Exception {
        // Initialize the database
        occupationRepository.saveAndFlush(occupation);

        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();

        // Update the occupation using partial update
        Occupation partialUpdatedOccupation = new Occupation();
        partialUpdatedOccupation.setId(occupation.getId());

        partialUpdatedOccupation.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restOccupationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOccupation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOccupation))
            )
            .andExpect(status().isOk());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
        Occupation testOccupation = occupationList.get(occupationList.size() - 1);
        assertThat(testOccupation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOccupation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOccupation.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateOccupationWithPatch() throws Exception {
        // Initialize the database
        occupationRepository.saveAndFlush(occupation);

        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();

        // Update the occupation using partial update
        Occupation partialUpdatedOccupation = new Occupation();
        partialUpdatedOccupation.setId(occupation.getId());

        partialUpdatedOccupation.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restOccupationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOccupation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOccupation))
            )
            .andExpect(status().isOk());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
        Occupation testOccupation = occupationList.get(occupationList.size() - 1);
        assertThat(testOccupation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOccupation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOccupation.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingOccupation() throws Exception {
        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();
        occupation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccupationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, occupation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOccupation() throws Exception {
        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();
        occupation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOccupation() throws Exception {
        int databaseSizeBeforeUpdate = occupationRepository.findAll().size();
        occupation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(occupation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Occupation in the database
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOccupation() throws Exception {
        // Initialize the database
        occupationRepository.saveAndFlush(occupation);

        int databaseSizeBeforeDelete = occupationRepository.findAll().size();

        // Delete the occupation
        restOccupationMockMvc
            .perform(delete(ENTITY_API_URL_ID, occupation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Occupation> occupationList = occupationRepository.findAll();
        assertThat(occupationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
