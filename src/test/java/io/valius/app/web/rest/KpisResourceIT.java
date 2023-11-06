package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.Kpis;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.KpisRepository;
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
 * Integration tests for the {@link KpisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KpisResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECK_BOX_VALUE = false;
    private static final Boolean UPDATED_CHECK_BOX_VALUE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/kpis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KpisRepository kpisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKpisMockMvc;

    private Kpis kpis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpis createEntity(EntityManager em) {
        Kpis kpis = new Kpis()
            .value(DEFAULT_VALUE)
            .checkBoxValue(DEFAULT_CHECK_BOX_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return kpis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpis createUpdatedEntity(EntityManager em) {
        Kpis kpis = new Kpis()
            .value(UPDATED_VALUE)
            .checkBoxValue(UPDATED_CHECK_BOX_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return kpis;
    }

    @BeforeEach
    public void initTest() {
        kpis = createEntity(em);
    }

    @Test
    @Transactional
    void createKpis() throws Exception {
        int databaseSizeBeforeCreate = kpisRepository.findAll().size();
        // Create the Kpis
        restKpisMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isCreated());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeCreate + 1);
        Kpis testKpis = kpisList.get(kpisList.size() - 1);
        assertThat(testKpis.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testKpis.getCheckBoxValue()).isEqualTo(DEFAULT_CHECK_BOX_VALUE);
        assertThat(testKpis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testKpis.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createKpisWithExistingId() throws Exception {
        // Create the Kpis with an existing ID
        kpis.setId(1L);

        int databaseSizeBeforeCreate = kpisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKpisMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpisRepository.findAll().size();
        // set the field null
        kpis.setValue(null);

        // Create the Kpis, which fails.

        restKpisMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCheckBoxValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpisRepository.findAll().size();
        // set the field null
        kpis.setCheckBoxValue(null);

        // Create the Kpis, which fails.

        restKpisMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpisRepository.findAll().size();
        // set the field null
        kpis.setLanguage(null);

        // Create the Kpis, which fails.

        restKpisMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKpis() throws Exception {
        // Initialize the database
        kpisRepository.saveAndFlush(kpis);

        // Get all the kpisList
        restKpisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpis.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].checkBoxValue").value(hasItem(DEFAULT_CHECK_BOX_VALUE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getKpis() throws Exception {
        // Initialize the database
        kpisRepository.saveAndFlush(kpis);

        // Get the kpis
        restKpisMockMvc
            .perform(get(ENTITY_API_URL_ID, kpis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kpis.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.checkBoxValue").value(DEFAULT_CHECK_BOX_VALUE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKpis() throws Exception {
        // Get the kpis
        restKpisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKpis() throws Exception {
        // Initialize the database
        kpisRepository.saveAndFlush(kpis);

        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();

        // Update the kpis
        Kpis updatedKpis = kpisRepository.findById(kpis.getId()).get();
        // Disconnect from session so that the updates on updatedKpis are not directly saved in db
        em.detach(updatedKpis);
        updatedKpis.value(UPDATED_VALUE).checkBoxValue(UPDATED_CHECK_BOX_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restKpisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKpis.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKpis))
            )
            .andExpect(status().isOk());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
        Kpis testKpis = kpisList.get(kpisList.size() - 1);
        assertThat(testKpis.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testKpis.getCheckBoxValue()).isEqualTo(UPDATED_CHECK_BOX_VALUE);
        assertThat(testKpis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testKpis.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingKpis() throws Exception {
        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();
        kpis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kpis.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKpis() throws Exception {
        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();
        kpis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKpis() throws Exception {
        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();
        kpis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpisMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKpisWithPatch() throws Exception {
        // Initialize the database
        kpisRepository.saveAndFlush(kpis);

        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();

        // Update the kpis using partial update
        Kpis partialUpdatedKpis = new Kpis();
        partialUpdatedKpis.setId(kpis.getId());

        partialUpdatedKpis.value(UPDATED_VALUE).checkBoxValue(UPDATED_CHECK_BOX_VALUE);

        restKpisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpis.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKpis))
            )
            .andExpect(status().isOk());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
        Kpis testKpis = kpisList.get(kpisList.size() - 1);
        assertThat(testKpis.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testKpis.getCheckBoxValue()).isEqualTo(UPDATED_CHECK_BOX_VALUE);
        assertThat(testKpis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testKpis.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateKpisWithPatch() throws Exception {
        // Initialize the database
        kpisRepository.saveAndFlush(kpis);

        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();

        // Update the kpis using partial update
        Kpis partialUpdatedKpis = new Kpis();
        partialUpdatedKpis.setId(kpis.getId());

        partialUpdatedKpis
            .value(UPDATED_VALUE)
            .checkBoxValue(UPDATED_CHECK_BOX_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);

        restKpisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpis.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKpis))
            )
            .andExpect(status().isOk());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
        Kpis testKpis = kpisList.get(kpisList.size() - 1);
        assertThat(testKpis.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testKpis.getCheckBoxValue()).isEqualTo(UPDATED_CHECK_BOX_VALUE);
        assertThat(testKpis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testKpis.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingKpis() throws Exception {
        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();
        kpis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kpis.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKpis() throws Exception {
        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();
        kpis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKpis() throws Exception {
        int databaseSizeBeforeUpdate = kpisRepository.findAll().size();
        kpis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpisMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kpis))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpis in the database
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKpis() throws Exception {
        // Initialize the database
        kpisRepository.saveAndFlush(kpis);

        int databaseSizeBeforeDelete = kpisRepository.findAll().size();

        // Delete the kpis
        restKpisMockMvc
            .perform(delete(ENTITY_API_URL_ID, kpis.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kpis> kpisList = kpisRepository.findAll();
        assertThat(kpisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
