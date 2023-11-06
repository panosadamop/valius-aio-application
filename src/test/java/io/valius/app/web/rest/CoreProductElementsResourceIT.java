package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.CoreProductElements;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.CoreProductElementsRepository;
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
 * Integration tests for the {@link CoreProductElementsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoreProductElementsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/core-product-elements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CoreProductElementsRepository coreProductElementsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoreProductElementsMockMvc;

    private CoreProductElements coreProductElements;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoreProductElements createEntity(EntityManager em) {
        CoreProductElements coreProductElements = new CoreProductElements()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return coreProductElements;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoreProductElements createUpdatedEntity(EntityManager em) {
        CoreProductElements coreProductElements = new CoreProductElements()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return coreProductElements;
    }

    @BeforeEach
    public void initTest() {
        coreProductElements = createEntity(em);
    }

    @Test
    @Transactional
    void createCoreProductElements() throws Exception {
        int databaseSizeBeforeCreate = coreProductElementsRepository.findAll().size();
        // Create the CoreProductElements
        restCoreProductElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isCreated());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeCreate + 1);
        CoreProductElements testCoreProductElements = coreProductElementsList.get(coreProductElementsList.size() - 1);
        assertThat(testCoreProductElements.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCoreProductElements.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCoreProductElements.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCoreProductElementsWithExistingId() throws Exception {
        // Create the CoreProductElements with an existing ID
        coreProductElements.setId(1L);

        int databaseSizeBeforeCreate = coreProductElementsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoreProductElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreProductElementsRepository.findAll().size();
        // set the field null
        coreProductElements.setValue(null);

        // Create the CoreProductElements, which fails.

        restCoreProductElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isBadRequest());

        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreProductElementsRepository.findAll().size();
        // set the field null
        coreProductElements.setLanguage(null);

        // Create the CoreProductElements, which fails.

        restCoreProductElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isBadRequest());

        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoreProductElements() throws Exception {
        // Initialize the database
        coreProductElementsRepository.saveAndFlush(coreProductElements);

        // Get all the coreProductElementsList
        restCoreProductElementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coreProductElements.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getCoreProductElements() throws Exception {
        // Initialize the database
        coreProductElementsRepository.saveAndFlush(coreProductElements);

        // Get the coreProductElements
        restCoreProductElementsMockMvc
            .perform(get(ENTITY_API_URL_ID, coreProductElements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coreProductElements.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCoreProductElements() throws Exception {
        // Get the coreProductElements
        restCoreProductElementsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCoreProductElements() throws Exception {
        // Initialize the database
        coreProductElementsRepository.saveAndFlush(coreProductElements);

        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();

        // Update the coreProductElements
        CoreProductElements updatedCoreProductElements = coreProductElementsRepository.findById(coreProductElements.getId()).get();
        // Disconnect from session so that the updates on updatedCoreProductElements are not directly saved in db
        em.detach(updatedCoreProductElements);
        updatedCoreProductElements.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCoreProductElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCoreProductElements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCoreProductElements))
            )
            .andExpect(status().isOk());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
        CoreProductElements testCoreProductElements = coreProductElementsList.get(coreProductElementsList.size() - 1);
        assertThat(testCoreProductElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCoreProductElements.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCoreProductElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCoreProductElements() throws Exception {
        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();
        coreProductElements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoreProductElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coreProductElements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoreProductElements() throws Exception {
        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();
        coreProductElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreProductElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoreProductElements() throws Exception {
        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();
        coreProductElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreProductElementsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoreProductElementsWithPatch() throws Exception {
        // Initialize the database
        coreProductElementsRepository.saveAndFlush(coreProductElements);

        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();

        // Update the coreProductElements using partial update
        CoreProductElements partialUpdatedCoreProductElements = new CoreProductElements();
        partialUpdatedCoreProductElements.setId(coreProductElements.getId());

        partialUpdatedCoreProductElements.language(UPDATED_LANGUAGE);

        restCoreProductElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoreProductElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoreProductElements))
            )
            .andExpect(status().isOk());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
        CoreProductElements testCoreProductElements = coreProductElementsList.get(coreProductElementsList.size() - 1);
        assertThat(testCoreProductElements.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCoreProductElements.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCoreProductElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCoreProductElementsWithPatch() throws Exception {
        // Initialize the database
        coreProductElementsRepository.saveAndFlush(coreProductElements);

        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();

        // Update the coreProductElements using partial update
        CoreProductElements partialUpdatedCoreProductElements = new CoreProductElements();
        partialUpdatedCoreProductElements.setId(coreProductElements.getId());

        partialUpdatedCoreProductElements.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restCoreProductElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoreProductElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoreProductElements))
            )
            .andExpect(status().isOk());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
        CoreProductElements testCoreProductElements = coreProductElementsList.get(coreProductElementsList.size() - 1);
        assertThat(testCoreProductElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCoreProductElements.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCoreProductElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCoreProductElements() throws Exception {
        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();
        coreProductElements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoreProductElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coreProductElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoreProductElements() throws Exception {
        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();
        coreProductElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreProductElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoreProductElements() throws Exception {
        int databaseSizeBeforeUpdate = coreProductElementsRepository.findAll().size();
        coreProductElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreProductElementsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coreProductElements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoreProductElements in the database
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoreProductElements() throws Exception {
        // Initialize the database
        coreProductElementsRepository.saveAndFlush(coreProductElements);

        int databaseSizeBeforeDelete = coreProductElementsRepository.findAll().size();

        // Delete the coreProductElements
        restCoreProductElementsMockMvc
            .perform(delete(ENTITY_API_URL_ID, coreProductElements.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoreProductElements> coreProductElementsList = coreProductElementsRepository.findAll();
        assertThat(coreProductElementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
