package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.IntangibleElements;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.IntangibleElementsRepository;
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
 * Integration tests for the {@link IntangibleElementsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntangibleElementsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/intangible-elements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntangibleElementsRepository intangibleElementsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntangibleElementsMockMvc;

    private IntangibleElements intangibleElements;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntangibleElements createEntity(EntityManager em) {
        IntangibleElements intangibleElements = new IntangibleElements()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return intangibleElements;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntangibleElements createUpdatedEntity(EntityManager em) {
        IntangibleElements intangibleElements = new IntangibleElements()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return intangibleElements;
    }

    @BeforeEach
    public void initTest() {
        intangibleElements = createEntity(em);
    }

    @Test
    @Transactional
    void createIntangibleElements() throws Exception {
        int databaseSizeBeforeCreate = intangibleElementsRepository.findAll().size();
        // Create the IntangibleElements
        restIntangibleElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isCreated());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeCreate + 1);
        IntangibleElements testIntangibleElements = intangibleElementsList.get(intangibleElementsList.size() - 1);
        assertThat(testIntangibleElements.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIntangibleElements.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIntangibleElements.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createIntangibleElementsWithExistingId() throws Exception {
        // Create the IntangibleElements with an existing ID
        intangibleElements.setId(1L);

        int databaseSizeBeforeCreate = intangibleElementsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntangibleElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = intangibleElementsRepository.findAll().size();
        // set the field null
        intangibleElements.setValue(null);

        // Create the IntangibleElements, which fails.

        restIntangibleElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isBadRequest());

        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = intangibleElementsRepository.findAll().size();
        // set the field null
        intangibleElements.setLanguage(null);

        // Create the IntangibleElements, which fails.

        restIntangibleElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isBadRequest());

        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIntangibleElements() throws Exception {
        // Initialize the database
        intangibleElementsRepository.saveAndFlush(intangibleElements);

        // Get all the intangibleElementsList
        restIntangibleElementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intangibleElements.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getIntangibleElements() throws Exception {
        // Initialize the database
        intangibleElementsRepository.saveAndFlush(intangibleElements);

        // Get the intangibleElements
        restIntangibleElementsMockMvc
            .perform(get(ENTITY_API_URL_ID, intangibleElements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intangibleElements.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIntangibleElements() throws Exception {
        // Get the intangibleElements
        restIntangibleElementsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntangibleElements() throws Exception {
        // Initialize the database
        intangibleElementsRepository.saveAndFlush(intangibleElements);

        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();

        // Update the intangibleElements
        IntangibleElements updatedIntangibleElements = intangibleElementsRepository.findById(intangibleElements.getId()).get();
        // Disconnect from session so that the updates on updatedIntangibleElements are not directly saved in db
        em.detach(updatedIntangibleElements);
        updatedIntangibleElements.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restIntangibleElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIntangibleElements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIntangibleElements))
            )
            .andExpect(status().isOk());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
        IntangibleElements testIntangibleElements = intangibleElementsList.get(intangibleElementsList.size() - 1);
        assertThat(testIntangibleElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIntangibleElements.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIntangibleElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingIntangibleElements() throws Exception {
        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();
        intangibleElements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntangibleElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intangibleElements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntangibleElements() throws Exception {
        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();
        intangibleElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntangibleElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntangibleElements() throws Exception {
        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();
        intangibleElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntangibleElementsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntangibleElementsWithPatch() throws Exception {
        // Initialize the database
        intangibleElementsRepository.saveAndFlush(intangibleElements);

        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();

        // Update the intangibleElements using partial update
        IntangibleElements partialUpdatedIntangibleElements = new IntangibleElements();
        partialUpdatedIntangibleElements.setId(intangibleElements.getId());

        partialUpdatedIntangibleElements.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restIntangibleElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntangibleElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntangibleElements))
            )
            .andExpect(status().isOk());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
        IntangibleElements testIntangibleElements = intangibleElementsList.get(intangibleElementsList.size() - 1);
        assertThat(testIntangibleElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIntangibleElements.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIntangibleElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateIntangibleElementsWithPatch() throws Exception {
        // Initialize the database
        intangibleElementsRepository.saveAndFlush(intangibleElements);

        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();

        // Update the intangibleElements using partial update
        IntangibleElements partialUpdatedIntangibleElements = new IntangibleElements();
        partialUpdatedIntangibleElements.setId(intangibleElements.getId());

        partialUpdatedIntangibleElements.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restIntangibleElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntangibleElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntangibleElements))
            )
            .andExpect(status().isOk());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
        IntangibleElements testIntangibleElements = intangibleElementsList.get(intangibleElementsList.size() - 1);
        assertThat(testIntangibleElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIntangibleElements.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIntangibleElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingIntangibleElements() throws Exception {
        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();
        intangibleElements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntangibleElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intangibleElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntangibleElements() throws Exception {
        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();
        intangibleElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntangibleElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntangibleElements() throws Exception {
        int databaseSizeBeforeUpdate = intangibleElementsRepository.findAll().size();
        intangibleElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntangibleElementsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intangibleElements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntangibleElements in the database
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntangibleElements() throws Exception {
        // Initialize the database
        intangibleElementsRepository.saveAndFlush(intangibleElements);

        int databaseSizeBeforeDelete = intangibleElementsRepository.findAll().size();

        // Delete the intangibleElements
        restIntangibleElementsMockMvc
            .perform(delete(ENTITY_API_URL_ID, intangibleElements.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntangibleElements> intangibleElementsList = intangibleElementsRepository.findAll();
        assertThat(intangibleElementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
