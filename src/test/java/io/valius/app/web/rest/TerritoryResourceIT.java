package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.Territory;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.TerritoryRepository;
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

/**
 * Integration tests for the {@link TerritoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TerritoryResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/territories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TerritoryRepository territoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerritoryMockMvc;

    private Territory territory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Territory createEntity(EntityManager em) {
        Territory territory = new Territory().value(DEFAULT_VALUE).language(DEFAULT_LANGUAGE);
        return territory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Territory createUpdatedEntity(EntityManager em) {
        Territory territory = new Territory().value(UPDATED_VALUE).language(UPDATED_LANGUAGE);
        return territory;
    }

    @BeforeEach
    public void initTest() {
        territory = createEntity(em);
    }

    @Test
    @Transactional
    void createTerritory() throws Exception {
        int databaseSizeBeforeCreate = territoryRepository.findAll().size();
        // Create the Territory
        restTerritoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isCreated());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeCreate + 1);
        Territory testTerritory = territoryList.get(territoryList.size() - 1);
        assertThat(testTerritory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTerritory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createTerritoryWithExistingId() throws Exception {
        // Create the Territory with an existing ID
        territory.setId(1L);

        int databaseSizeBeforeCreate = territoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerritoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = territoryRepository.findAll().size();
        // set the field null
        territory.setValue(null);

        // Create the Territory, which fails.

        restTerritoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isBadRequest());

        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = territoryRepository.findAll().size();
        // set the field null
        territory.setLanguage(null);

        // Create the Territory, which fails.

        restTerritoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isBadRequest());

        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTerritories() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        // Get all the territoryList
        restTerritoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(territory.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getTerritory() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        // Get the territory
        restTerritoryMockMvc
            .perform(get(ENTITY_API_URL_ID, territory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(territory.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTerritory() throws Exception {
        // Get the territory
        restTerritoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTerritory() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();

        // Update the territory
        Territory updatedTerritory = territoryRepository.findById(territory.getId()).get();
        // Disconnect from session so that the updates on updatedTerritory are not directly saved in db
        em.detach(updatedTerritory);
        updatedTerritory.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restTerritoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTerritory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTerritory))
            )
            .andExpect(status().isOk());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
        Territory testTerritory = territoryList.get(territoryList.size() - 1);
        assertThat(testTerritory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTerritory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingTerritory() throws Exception {
        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();
        territory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerritoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, territory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTerritory() throws Exception {
        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();
        territory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTerritory() throws Exception {
        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();
        territory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerritoryWithPatch() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();

        // Update the territory using partial update
        Territory partialUpdatedTerritory = new Territory();
        partialUpdatedTerritory.setId(territory.getId());

        restTerritoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerritory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerritory))
            )
            .andExpect(status().isOk());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
        Territory testTerritory = territoryList.get(territoryList.size() - 1);
        assertThat(testTerritory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTerritory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateTerritoryWithPatch() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();

        // Update the territory using partial update
        Territory partialUpdatedTerritory = new Territory();
        partialUpdatedTerritory.setId(territory.getId());

        partialUpdatedTerritory.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restTerritoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerritory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerritory))
            )
            .andExpect(status().isOk());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
        Territory testTerritory = territoryList.get(territoryList.size() - 1);
        assertThat(testTerritory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTerritory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingTerritory() throws Exception {
        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();
        territory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerritoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, territory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTerritory() throws Exception {
        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();
        territory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTerritory() throws Exception {
        int databaseSizeBeforeUpdate = territoryRepository.findAll().size();
        territory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(territory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Territory in the database
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTerritory() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        int databaseSizeBeforeDelete = territoryRepository.findAll().size();

        // Delete the territory
        restTerritoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, territory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Territory> territoryList = territoryRepository.findAll();
        assertThat(territoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
