package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.RelatedServiceElements;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.RelatedServiceElementsRepository;
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
 * Integration tests for the {@link RelatedServiceElementsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatedServiceElementsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/related-service-elements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelatedServiceElementsRepository relatedServiceElementsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatedServiceElementsMockMvc;

    private RelatedServiceElements relatedServiceElements;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedServiceElements createEntity(EntityManager em) {
        RelatedServiceElements relatedServiceElements = new RelatedServiceElements()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return relatedServiceElements;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedServiceElements createUpdatedEntity(EntityManager em) {
        RelatedServiceElements relatedServiceElements = new RelatedServiceElements()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return relatedServiceElements;
    }

    @BeforeEach
    public void initTest() {
        relatedServiceElements = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatedServiceElements() throws Exception {
        int databaseSizeBeforeCreate = relatedServiceElementsRepository.findAll().size();
        // Create the RelatedServiceElements
        restRelatedServiceElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isCreated());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeCreate + 1);
        RelatedServiceElements testRelatedServiceElements = relatedServiceElementsList.get(relatedServiceElementsList.size() - 1);
        assertThat(testRelatedServiceElements.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testRelatedServiceElements.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRelatedServiceElements.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createRelatedServiceElementsWithExistingId() throws Exception {
        // Create the RelatedServiceElements with an existing ID
        relatedServiceElements.setId(1L);

        int databaseSizeBeforeCreate = relatedServiceElementsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatedServiceElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = relatedServiceElementsRepository.findAll().size();
        // set the field null
        relatedServiceElements.setValue(null);

        // Create the RelatedServiceElements, which fails.

        restRelatedServiceElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isBadRequest());

        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = relatedServiceElementsRepository.findAll().size();
        // set the field null
        relatedServiceElements.setLanguage(null);

        // Create the RelatedServiceElements, which fails.

        restRelatedServiceElementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isBadRequest());

        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRelatedServiceElements() throws Exception {
        // Initialize the database
        relatedServiceElementsRepository.saveAndFlush(relatedServiceElements);

        // Get all the relatedServiceElementsList
        restRelatedServiceElementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatedServiceElements.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getRelatedServiceElements() throws Exception {
        // Initialize the database
        relatedServiceElementsRepository.saveAndFlush(relatedServiceElements);

        // Get the relatedServiceElements
        restRelatedServiceElementsMockMvc
            .perform(get(ENTITY_API_URL_ID, relatedServiceElements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatedServiceElements.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRelatedServiceElements() throws Exception {
        // Get the relatedServiceElements
        restRelatedServiceElementsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRelatedServiceElements() throws Exception {
        // Initialize the database
        relatedServiceElementsRepository.saveAndFlush(relatedServiceElements);

        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();

        // Update the relatedServiceElements
        RelatedServiceElements updatedRelatedServiceElements = relatedServiceElementsRepository
            .findById(relatedServiceElements.getId())
            .get();
        // Disconnect from session so that the updates on updatedRelatedServiceElements are not directly saved in db
        em.detach(updatedRelatedServiceElements);
        updatedRelatedServiceElements.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restRelatedServiceElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelatedServiceElements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRelatedServiceElements))
            )
            .andExpect(status().isOk());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
        RelatedServiceElements testRelatedServiceElements = relatedServiceElementsList.get(relatedServiceElementsList.size() - 1);
        assertThat(testRelatedServiceElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRelatedServiceElements.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRelatedServiceElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingRelatedServiceElements() throws Exception {
        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();
        relatedServiceElements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedServiceElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedServiceElements.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelatedServiceElements() throws Exception {
        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();
        relatedServiceElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedServiceElementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelatedServiceElements() throws Exception {
        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();
        relatedServiceElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedServiceElementsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatedServiceElementsWithPatch() throws Exception {
        // Initialize the database
        relatedServiceElementsRepository.saveAndFlush(relatedServiceElements);

        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();

        // Update the relatedServiceElements using partial update
        RelatedServiceElements partialUpdatedRelatedServiceElements = new RelatedServiceElements();
        partialUpdatedRelatedServiceElements.setId(relatedServiceElements.getId());

        partialUpdatedRelatedServiceElements.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restRelatedServiceElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedServiceElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedServiceElements))
            )
            .andExpect(status().isOk());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
        RelatedServiceElements testRelatedServiceElements = relatedServiceElementsList.get(relatedServiceElementsList.size() - 1);
        assertThat(testRelatedServiceElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRelatedServiceElements.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRelatedServiceElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateRelatedServiceElementsWithPatch() throws Exception {
        // Initialize the database
        relatedServiceElementsRepository.saveAndFlush(relatedServiceElements);

        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();

        // Update the relatedServiceElements using partial update
        RelatedServiceElements partialUpdatedRelatedServiceElements = new RelatedServiceElements();
        partialUpdatedRelatedServiceElements.setId(relatedServiceElements.getId());

        partialUpdatedRelatedServiceElements.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restRelatedServiceElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedServiceElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedServiceElements))
            )
            .andExpect(status().isOk());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
        RelatedServiceElements testRelatedServiceElements = relatedServiceElementsList.get(relatedServiceElementsList.size() - 1);
        assertThat(testRelatedServiceElements.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRelatedServiceElements.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRelatedServiceElements.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingRelatedServiceElements() throws Exception {
        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();
        relatedServiceElements.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedServiceElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relatedServiceElements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelatedServiceElements() throws Exception {
        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();
        relatedServiceElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedServiceElementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelatedServiceElements() throws Exception {
        int databaseSizeBeforeUpdate = relatedServiceElementsRepository.findAll().size();
        relatedServiceElements.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedServiceElementsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedServiceElements))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedServiceElements in the database
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelatedServiceElements() throws Exception {
        // Initialize the database
        relatedServiceElementsRepository.saveAndFlush(relatedServiceElements);

        int databaseSizeBeforeDelete = relatedServiceElementsRepository.findAll().size();

        // Delete the relatedServiceElements
        restRelatedServiceElementsMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatedServiceElements.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelatedServiceElements> relatedServiceElementsList = relatedServiceElementsRepository.findAll();
        assertThat(relatedServiceElementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
