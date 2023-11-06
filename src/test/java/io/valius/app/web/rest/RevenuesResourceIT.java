package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.Revenues;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.RevenuesRepository;
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
 * Integration tests for the {@link RevenuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RevenuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/revenues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RevenuesRepository revenuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRevenuesMockMvc;

    private Revenues revenues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revenues createEntity(EntityManager em) {
        Revenues revenues = new Revenues().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return revenues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revenues createUpdatedEntity(EntityManager em) {
        Revenues revenues = new Revenues().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return revenues;
    }

    @BeforeEach
    public void initTest() {
        revenues = createEntity(em);
    }

    @Test
    @Transactional
    void createRevenues() throws Exception {
        int databaseSizeBeforeCreate = revenuesRepository.findAll().size();
        // Create the Revenues
        restRevenuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isCreated());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeCreate + 1);
        Revenues testRevenues = revenuesList.get(revenuesList.size() - 1);
        assertThat(testRevenues.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testRevenues.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRevenues.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createRevenuesWithExistingId() throws Exception {
        // Create the Revenues with an existing ID
        revenues.setId(1L);

        int databaseSizeBeforeCreate = revenuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRevenuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = revenuesRepository.findAll().size();
        // set the field null
        revenues.setValue(null);

        // Create the Revenues, which fails.

        restRevenuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isBadRequest());

        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = revenuesRepository.findAll().size();
        // set the field null
        revenues.setLanguage(null);

        // Create the Revenues, which fails.

        restRevenuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isBadRequest());

        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRevenues() throws Exception {
        // Initialize the database
        revenuesRepository.saveAndFlush(revenues);

        // Get all the revenuesList
        restRevenuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revenues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getRevenues() throws Exception {
        // Initialize the database
        revenuesRepository.saveAndFlush(revenues);

        // Get the revenues
        restRevenuesMockMvc
            .perform(get(ENTITY_API_URL_ID, revenues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(revenues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRevenues() throws Exception {
        // Get the revenues
        restRevenuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRevenues() throws Exception {
        // Initialize the database
        revenuesRepository.saveAndFlush(revenues);

        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();

        // Update the revenues
        Revenues updatedRevenues = revenuesRepository.findById(revenues.getId()).get();
        // Disconnect from session so that the updates on updatedRevenues are not directly saved in db
        em.detach(updatedRevenues);
        updatedRevenues.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restRevenuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRevenues.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRevenues))
            )
            .andExpect(status().isOk());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
        Revenues testRevenues = revenuesList.get(revenuesList.size() - 1);
        assertThat(testRevenues.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRevenues.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRevenues.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingRevenues() throws Exception {
        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();
        revenues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevenuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, revenues.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRevenues() throws Exception {
        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();
        revenues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRevenues() throws Exception {
        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();
        revenues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRevenuesWithPatch() throws Exception {
        // Initialize the database
        revenuesRepository.saveAndFlush(revenues);

        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();

        // Update the revenues using partial update
        Revenues partialUpdatedRevenues = new Revenues();
        partialUpdatedRevenues.setId(revenues.getId());

        partialUpdatedRevenues.language(UPDATED_LANGUAGE);

        restRevenuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevenues.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRevenues))
            )
            .andExpect(status().isOk());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
        Revenues testRevenues = revenuesList.get(revenuesList.size() - 1);
        assertThat(testRevenues.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testRevenues.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRevenues.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateRevenuesWithPatch() throws Exception {
        // Initialize the database
        revenuesRepository.saveAndFlush(revenues);

        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();

        // Update the revenues using partial update
        Revenues partialUpdatedRevenues = new Revenues();
        partialUpdatedRevenues.setId(revenues.getId());

        partialUpdatedRevenues.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restRevenuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevenues.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRevenues))
            )
            .andExpect(status().isOk());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
        Revenues testRevenues = revenuesList.get(revenuesList.size() - 1);
        assertThat(testRevenues.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRevenues.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRevenues.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingRevenues() throws Exception {
        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();
        revenues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevenuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, revenues.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRevenues() throws Exception {
        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();
        revenues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRevenues() throws Exception {
        int databaseSizeBeforeUpdate = revenuesRepository.findAll().size();
        revenues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(revenues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Revenues in the database
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRevenues() throws Exception {
        // Initialize the database
        revenuesRepository.saveAndFlush(revenues);

        int databaseSizeBeforeDelete = revenuesRepository.findAll().size();

        // Delete the revenues
        restRevenuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, revenues.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Revenues> revenuesList = revenuesRepository.findAll();
        assertThat(revenuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
