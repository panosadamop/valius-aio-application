package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.StatisticalError;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.StatisticalErrorRepository;
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
 * Integration tests for the {@link StatisticalErrorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatisticalErrorResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/statistical-errors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatisticalErrorRepository statisticalErrorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatisticalErrorMockMvc;

    private StatisticalError statisticalError;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticalError createEntity(EntityManager em) {
        StatisticalError statisticalError = new StatisticalError()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return statisticalError;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticalError createUpdatedEntity(EntityManager em) {
        StatisticalError statisticalError = new StatisticalError()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return statisticalError;
    }

    @BeforeEach
    public void initTest() {
        statisticalError = createEntity(em);
    }

    @Test
    @Transactional
    void createStatisticalError() throws Exception {
        int databaseSizeBeforeCreate = statisticalErrorRepository.findAll().size();
        // Create the StatisticalError
        restStatisticalErrorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isCreated());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeCreate + 1);
        StatisticalError testStatisticalError = statisticalErrorList.get(statisticalErrorList.size() - 1);
        assertThat(testStatisticalError.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testStatisticalError.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStatisticalError.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createStatisticalErrorWithExistingId() throws Exception {
        // Create the StatisticalError with an existing ID
        statisticalError.setId(1L);

        int databaseSizeBeforeCreate = statisticalErrorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticalErrorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = statisticalErrorRepository.findAll().size();
        // set the field null
        statisticalError.setValue(null);

        // Create the StatisticalError, which fails.

        restStatisticalErrorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isBadRequest());

        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = statisticalErrorRepository.findAll().size();
        // set the field null
        statisticalError.setLanguage(null);

        // Create the StatisticalError, which fails.

        restStatisticalErrorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isBadRequest());

        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStatisticalErrors() throws Exception {
        // Initialize the database
        statisticalErrorRepository.saveAndFlush(statisticalError);

        // Get all the statisticalErrorList
        restStatisticalErrorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statisticalError.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getStatisticalError() throws Exception {
        // Initialize the database
        statisticalErrorRepository.saveAndFlush(statisticalError);

        // Get the statisticalError
        restStatisticalErrorMockMvc
            .perform(get(ENTITY_API_URL_ID, statisticalError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statisticalError.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStatisticalError() throws Exception {
        // Get the statisticalError
        restStatisticalErrorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatisticalError() throws Exception {
        // Initialize the database
        statisticalErrorRepository.saveAndFlush(statisticalError);

        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();

        // Update the statisticalError
        StatisticalError updatedStatisticalError = statisticalErrorRepository.findById(statisticalError.getId()).get();
        // Disconnect from session so that the updates on updatedStatisticalError are not directly saved in db
        em.detach(updatedStatisticalError);
        updatedStatisticalError.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restStatisticalErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStatisticalError.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStatisticalError))
            )
            .andExpect(status().isOk());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
        StatisticalError testStatisticalError = statisticalErrorList.get(statisticalErrorList.size() - 1);
        assertThat(testStatisticalError.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testStatisticalError.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatisticalError.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingStatisticalError() throws Exception {
        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();
        statisticalError.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticalErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticalError.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatisticalError() throws Exception {
        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();
        statisticalError.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticalErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatisticalError() throws Exception {
        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();
        statisticalError.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticalErrorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatisticalErrorWithPatch() throws Exception {
        // Initialize the database
        statisticalErrorRepository.saveAndFlush(statisticalError);

        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();

        // Update the statisticalError using partial update
        StatisticalError partialUpdatedStatisticalError = new StatisticalError();
        partialUpdatedStatisticalError.setId(statisticalError.getId());

        partialUpdatedStatisticalError.description(UPDATED_DESCRIPTION);

        restStatisticalErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticalError.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatisticalError))
            )
            .andExpect(status().isOk());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
        StatisticalError testStatisticalError = statisticalErrorList.get(statisticalErrorList.size() - 1);
        assertThat(testStatisticalError.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testStatisticalError.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatisticalError.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateStatisticalErrorWithPatch() throws Exception {
        // Initialize the database
        statisticalErrorRepository.saveAndFlush(statisticalError);

        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();

        // Update the statisticalError using partial update
        StatisticalError partialUpdatedStatisticalError = new StatisticalError();
        partialUpdatedStatisticalError.setId(statisticalError.getId());

        partialUpdatedStatisticalError.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restStatisticalErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticalError.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatisticalError))
            )
            .andExpect(status().isOk());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
        StatisticalError testStatisticalError = statisticalErrorList.get(statisticalErrorList.size() - 1);
        assertThat(testStatisticalError.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testStatisticalError.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatisticalError.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingStatisticalError() throws Exception {
        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();
        statisticalError.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticalErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statisticalError.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatisticalError() throws Exception {
        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();
        statisticalError.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticalErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatisticalError() throws Exception {
        int databaseSizeBeforeUpdate = statisticalErrorRepository.findAll().size();
        statisticalError.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticalErrorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statisticalError))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticalError in the database
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatisticalError() throws Exception {
        // Initialize the database
        statisticalErrorRepository.saveAndFlush(statisticalError);

        int databaseSizeBeforeDelete = statisticalErrorRepository.findAll().size();

        // Delete the statisticalError
        restStatisticalErrorMockMvc
            .perform(delete(ENTITY_API_URL_ID, statisticalError.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatisticalError> statisticalErrorList = statisticalErrorRepository.findAll();
        assertThat(statisticalErrorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
