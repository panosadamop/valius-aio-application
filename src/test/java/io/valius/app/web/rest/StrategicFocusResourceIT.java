package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.StrategicFocus;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.StrategicFocusRepository;
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
 * Integration tests for the {@link StrategicFocusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrategicFocusResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/strategic-foci";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrategicFocusRepository strategicFocusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrategicFocusMockMvc;

    private StrategicFocus strategicFocus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategicFocus createEntity(EntityManager em) {
        StrategicFocus strategicFocus = new StrategicFocus()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return strategicFocus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategicFocus createUpdatedEntity(EntityManager em) {
        StrategicFocus strategicFocus = new StrategicFocus()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return strategicFocus;
    }

    @BeforeEach
    public void initTest() {
        strategicFocus = createEntity(em);
    }

    @Test
    @Transactional
    void createStrategicFocus() throws Exception {
        int databaseSizeBeforeCreate = strategicFocusRepository.findAll().size();
        // Create the StrategicFocus
        restStrategicFocusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isCreated());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeCreate + 1);
        StrategicFocus testStrategicFocus = strategicFocusList.get(strategicFocusList.size() - 1);
        assertThat(testStrategicFocus.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testStrategicFocus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStrategicFocus.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createStrategicFocusWithExistingId() throws Exception {
        // Create the StrategicFocus with an existing ID
        strategicFocus.setId(1L);

        int databaseSizeBeforeCreate = strategicFocusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategicFocusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategicFocusRepository.findAll().size();
        // set the field null
        strategicFocus.setValue(null);

        // Create the StrategicFocus, which fails.

        restStrategicFocusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isBadRequest());

        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategicFocusRepository.findAll().size();
        // set the field null
        strategicFocus.setLanguage(null);

        // Create the StrategicFocus, which fails.

        restStrategicFocusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isBadRequest());

        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrategicFoci() throws Exception {
        // Initialize the database
        strategicFocusRepository.saveAndFlush(strategicFocus);

        // Get all the strategicFocusList
        restStrategicFocusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategicFocus.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getStrategicFocus() throws Exception {
        // Initialize the database
        strategicFocusRepository.saveAndFlush(strategicFocus);

        // Get the strategicFocus
        restStrategicFocusMockMvc
            .perform(get(ENTITY_API_URL_ID, strategicFocus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strategicFocus.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStrategicFocus() throws Exception {
        // Get the strategicFocus
        restStrategicFocusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStrategicFocus() throws Exception {
        // Initialize the database
        strategicFocusRepository.saveAndFlush(strategicFocus);

        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();

        // Update the strategicFocus
        StrategicFocus updatedStrategicFocus = strategicFocusRepository.findById(strategicFocus.getId()).get();
        // Disconnect from session so that the updates on updatedStrategicFocus are not directly saved in db
        em.detach(updatedStrategicFocus);
        updatedStrategicFocus.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restStrategicFocusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrategicFocus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrategicFocus))
            )
            .andExpect(status().isOk());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
        StrategicFocus testStrategicFocus = strategicFocusList.get(strategicFocusList.size() - 1);
        assertThat(testStrategicFocus.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testStrategicFocus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStrategicFocus.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingStrategicFocus() throws Exception {
        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();
        strategicFocus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategicFocusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strategicFocus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrategicFocus() throws Exception {
        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();
        strategicFocus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategicFocusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrategicFocus() throws Exception {
        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();
        strategicFocus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategicFocusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrategicFocusWithPatch() throws Exception {
        // Initialize the database
        strategicFocusRepository.saveAndFlush(strategicFocus);

        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();

        // Update the strategicFocus using partial update
        StrategicFocus partialUpdatedStrategicFocus = new StrategicFocus();
        partialUpdatedStrategicFocus.setId(strategicFocus.getId());

        partialUpdatedStrategicFocus.language(UPDATED_LANGUAGE);

        restStrategicFocusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategicFocus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategicFocus))
            )
            .andExpect(status().isOk());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
        StrategicFocus testStrategicFocus = strategicFocusList.get(strategicFocusList.size() - 1);
        assertThat(testStrategicFocus.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testStrategicFocus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStrategicFocus.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateStrategicFocusWithPatch() throws Exception {
        // Initialize the database
        strategicFocusRepository.saveAndFlush(strategicFocus);

        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();

        // Update the strategicFocus using partial update
        StrategicFocus partialUpdatedStrategicFocus = new StrategicFocus();
        partialUpdatedStrategicFocus.setId(strategicFocus.getId());

        partialUpdatedStrategicFocus.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restStrategicFocusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategicFocus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategicFocus))
            )
            .andExpect(status().isOk());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
        StrategicFocus testStrategicFocus = strategicFocusList.get(strategicFocusList.size() - 1);
        assertThat(testStrategicFocus.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testStrategicFocus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStrategicFocus.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingStrategicFocus() throws Exception {
        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();
        strategicFocus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategicFocusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strategicFocus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrategicFocus() throws Exception {
        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();
        strategicFocus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategicFocusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrategicFocus() throws Exception {
        int databaseSizeBeforeUpdate = strategicFocusRepository.findAll().size();
        strategicFocus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategicFocusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategicFocus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategicFocus in the database
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrategicFocus() throws Exception {
        // Initialize the database
        strategicFocusRepository.saveAndFlush(strategicFocus);

        int databaseSizeBeforeDelete = strategicFocusRepository.findAll().size();

        // Delete the strategicFocus
        restStrategicFocusMockMvc
            .perform(delete(ENTITY_API_URL_ID, strategicFocus.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrategicFocus> strategicFocusList = strategicFocusRepository.findAll();
        assertThat(strategicFocusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
