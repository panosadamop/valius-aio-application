package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.LevelFour;
import io.valius.app.repository.LevelFourRepository;
import io.valius.app.service.LevelFourService;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LevelFourResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LevelFourResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_CRITICAL_SUCCESS_FACTORS = "AAAAAAAAAA";
    private static final String UPDATED_CRITICAL_SUCCESS_FACTORS = "BBBBBBBBBB";

    private static final String DEFAULT_POPULATION_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_POPULATION_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_STATISTICAL_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_STATISTICAL_ERROR = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIDENCE_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_CONFIDENCE_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIRED_SAMPLE_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRED_SAMPLE_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTIMATED_RESPONSE_RATE = "AAAAAAAAAA";
    private static final String UPDATED_ESTIMATED_RESPONSE_RATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SURVEY_PARTICIPANTS_NUMBER = 1;
    private static final Integer UPDATED_SURVEY_PARTICIPANTS_NUMBER = 2;

    private static final String ENTITY_API_URL = "/api/level-fours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelFourRepository levelFourRepository;

    @Mock
    private LevelFourRepository levelFourRepositoryMock;

    @Mock
    private LevelFourService levelFourServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelFourMockMvc;

    private LevelFour levelFour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelFour createEntity(EntityManager em) {
        LevelFour levelFour = new LevelFour()
            .identifier(DEFAULT_IDENTIFIER)
            .criticalSuccessFactors(DEFAULT_CRITICAL_SUCCESS_FACTORS)
            .populationSize(DEFAULT_POPULATION_SIZE)
            .statisticalError(DEFAULT_STATISTICAL_ERROR)
            .confidenceLevel(DEFAULT_CONFIDENCE_LEVEL)
            .requiredSampleSize(DEFAULT_REQUIRED_SAMPLE_SIZE)
            .estimatedResponseRate(DEFAULT_ESTIMATED_RESPONSE_RATE)
            .surveyParticipantsNumber(DEFAULT_SURVEY_PARTICIPANTS_NUMBER);
        return levelFour;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelFour createUpdatedEntity(EntityManager em) {
        LevelFour levelFour = new LevelFour()
            .identifier(UPDATED_IDENTIFIER)
            .criticalSuccessFactors(UPDATED_CRITICAL_SUCCESS_FACTORS)
            .populationSize(UPDATED_POPULATION_SIZE)
            .statisticalError(UPDATED_STATISTICAL_ERROR)
            .confidenceLevel(UPDATED_CONFIDENCE_LEVEL)
            .requiredSampleSize(UPDATED_REQUIRED_SAMPLE_SIZE)
            .estimatedResponseRate(UPDATED_ESTIMATED_RESPONSE_RATE)
            .surveyParticipantsNumber(UPDATED_SURVEY_PARTICIPANTS_NUMBER);
        return levelFour;
    }

    @BeforeEach
    public void initTest() {
        levelFour = createEntity(em);
    }

    @Test
    @Transactional
    void createLevelFour() throws Exception {
        int databaseSizeBeforeCreate = levelFourRepository.findAll().size();
        // Create the LevelFour
        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isCreated());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeCreate + 1);
        LevelFour testLevelFour = levelFourList.get(levelFourList.size() - 1);
        assertThat(testLevelFour.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLevelFour.getCriticalSuccessFactors()).isEqualTo(DEFAULT_CRITICAL_SUCCESS_FACTORS);
        assertThat(testLevelFour.getPopulationSize()).isEqualTo(DEFAULT_POPULATION_SIZE);
        assertThat(testLevelFour.getStatisticalError()).isEqualTo(DEFAULT_STATISTICAL_ERROR);
        assertThat(testLevelFour.getConfidenceLevel()).isEqualTo(DEFAULT_CONFIDENCE_LEVEL);
        assertThat(testLevelFour.getRequiredSampleSize()).isEqualTo(DEFAULT_REQUIRED_SAMPLE_SIZE);
        assertThat(testLevelFour.getEstimatedResponseRate()).isEqualTo(DEFAULT_ESTIMATED_RESPONSE_RATE);
        assertThat(testLevelFour.getSurveyParticipantsNumber()).isEqualTo(DEFAULT_SURVEY_PARTICIPANTS_NUMBER);
    }

    @Test
    @Transactional
    void createLevelFourWithExistingId() throws Exception {
        // Create the LevelFour with an existing ID
        levelFour.setId(1L);

        int databaseSizeBeforeCreate = levelFourRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setIdentifier(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCriticalSuccessFactorsIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setCriticalSuccessFactors(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPopulationSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setPopulationSize(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatisticalErrorIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setStatisticalError(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfidenceLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setConfidenceLevel(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequiredSampleSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setRequiredSampleSize(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstimatedResponseRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setEstimatedResponseRate(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSurveyParticipantsNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelFourRepository.findAll().size();
        // set the field null
        levelFour.setSurveyParticipantsNumber(null);

        // Create the LevelFour, which fails.

        restLevelFourMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLevelFours() throws Exception {
        // Initialize the database
        levelFourRepository.saveAndFlush(levelFour);

        // Get all the levelFourList
        restLevelFourMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelFour.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].criticalSuccessFactors").value(hasItem(DEFAULT_CRITICAL_SUCCESS_FACTORS)))
            .andExpect(jsonPath("$.[*].populationSize").value(hasItem(DEFAULT_POPULATION_SIZE)))
            .andExpect(jsonPath("$.[*].statisticalError").value(hasItem(DEFAULT_STATISTICAL_ERROR)))
            .andExpect(jsonPath("$.[*].confidenceLevel").value(hasItem(DEFAULT_CONFIDENCE_LEVEL)))
            .andExpect(jsonPath("$.[*].requiredSampleSize").value(hasItem(DEFAULT_REQUIRED_SAMPLE_SIZE)))
            .andExpect(jsonPath("$.[*].estimatedResponseRate").value(hasItem(DEFAULT_ESTIMATED_RESPONSE_RATE)))
            .andExpect(jsonPath("$.[*].surveyParticipantsNumber").value(hasItem(DEFAULT_SURVEY_PARTICIPANTS_NUMBER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelFoursWithEagerRelationshipsIsEnabled() throws Exception {
        when(levelFourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelFourMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(levelFourServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelFoursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(levelFourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelFourMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(levelFourRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLevelFour() throws Exception {
        // Initialize the database
        levelFourRepository.saveAndFlush(levelFour);

        // Get the levelFour
        restLevelFourMockMvc
            .perform(get(ENTITY_API_URL_ID, levelFour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelFour.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.criticalSuccessFactors").value(DEFAULT_CRITICAL_SUCCESS_FACTORS))
            .andExpect(jsonPath("$.populationSize").value(DEFAULT_POPULATION_SIZE))
            .andExpect(jsonPath("$.statisticalError").value(DEFAULT_STATISTICAL_ERROR))
            .andExpect(jsonPath("$.confidenceLevel").value(DEFAULT_CONFIDENCE_LEVEL))
            .andExpect(jsonPath("$.requiredSampleSize").value(DEFAULT_REQUIRED_SAMPLE_SIZE))
            .andExpect(jsonPath("$.estimatedResponseRate").value(DEFAULT_ESTIMATED_RESPONSE_RATE))
            .andExpect(jsonPath("$.surveyParticipantsNumber").value(DEFAULT_SURVEY_PARTICIPANTS_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingLevelFour() throws Exception {
        // Get the levelFour
        restLevelFourMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevelFour() throws Exception {
        // Initialize the database
        levelFourRepository.saveAndFlush(levelFour);

        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();

        // Update the levelFour
        LevelFour updatedLevelFour = levelFourRepository.findById(levelFour.getId()).get();
        // Disconnect from session so that the updates on updatedLevelFour are not directly saved in db
        em.detach(updatedLevelFour);
        updatedLevelFour
            .identifier(UPDATED_IDENTIFIER)
            .criticalSuccessFactors(UPDATED_CRITICAL_SUCCESS_FACTORS)
            .populationSize(UPDATED_POPULATION_SIZE)
            .statisticalError(UPDATED_STATISTICAL_ERROR)
            .confidenceLevel(UPDATED_CONFIDENCE_LEVEL)
            .requiredSampleSize(UPDATED_REQUIRED_SAMPLE_SIZE)
            .estimatedResponseRate(UPDATED_ESTIMATED_RESPONSE_RATE)
            .surveyParticipantsNumber(UPDATED_SURVEY_PARTICIPANTS_NUMBER);

        restLevelFourMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLevelFour.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLevelFour))
            )
            .andExpect(status().isOk());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
        LevelFour testLevelFour = levelFourList.get(levelFourList.size() - 1);
        assertThat(testLevelFour.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelFour.getCriticalSuccessFactors()).isEqualTo(UPDATED_CRITICAL_SUCCESS_FACTORS);
        assertThat(testLevelFour.getPopulationSize()).isEqualTo(UPDATED_POPULATION_SIZE);
        assertThat(testLevelFour.getStatisticalError()).isEqualTo(UPDATED_STATISTICAL_ERROR);
        assertThat(testLevelFour.getConfidenceLevel()).isEqualTo(UPDATED_CONFIDENCE_LEVEL);
        assertThat(testLevelFour.getRequiredSampleSize()).isEqualTo(UPDATED_REQUIRED_SAMPLE_SIZE);
        assertThat(testLevelFour.getEstimatedResponseRate()).isEqualTo(UPDATED_ESTIMATED_RESPONSE_RATE);
        assertThat(testLevelFour.getSurveyParticipantsNumber()).isEqualTo(UPDATED_SURVEY_PARTICIPANTS_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingLevelFour() throws Exception {
        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();
        levelFour.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelFourMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelFour.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevelFour() throws Exception {
        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();
        levelFour.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelFourMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevelFour() throws Exception {
        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();
        levelFour.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelFourMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLevelFourWithPatch() throws Exception {
        // Initialize the database
        levelFourRepository.saveAndFlush(levelFour);

        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();

        // Update the levelFour using partial update
        LevelFour partialUpdatedLevelFour = new LevelFour();
        partialUpdatedLevelFour.setId(levelFour.getId());

        partialUpdatedLevelFour
            .populationSize(UPDATED_POPULATION_SIZE)
            .statisticalError(UPDATED_STATISTICAL_ERROR)
            .estimatedResponseRate(UPDATED_ESTIMATED_RESPONSE_RATE)
            .surveyParticipantsNumber(UPDATED_SURVEY_PARTICIPANTS_NUMBER);

        restLevelFourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelFour.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelFour))
            )
            .andExpect(status().isOk());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
        LevelFour testLevelFour = levelFourList.get(levelFourList.size() - 1);
        assertThat(testLevelFour.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLevelFour.getCriticalSuccessFactors()).isEqualTo(DEFAULT_CRITICAL_SUCCESS_FACTORS);
        assertThat(testLevelFour.getPopulationSize()).isEqualTo(UPDATED_POPULATION_SIZE);
        assertThat(testLevelFour.getStatisticalError()).isEqualTo(UPDATED_STATISTICAL_ERROR);
        assertThat(testLevelFour.getConfidenceLevel()).isEqualTo(DEFAULT_CONFIDENCE_LEVEL);
        assertThat(testLevelFour.getRequiredSampleSize()).isEqualTo(DEFAULT_REQUIRED_SAMPLE_SIZE);
        assertThat(testLevelFour.getEstimatedResponseRate()).isEqualTo(UPDATED_ESTIMATED_RESPONSE_RATE);
        assertThat(testLevelFour.getSurveyParticipantsNumber()).isEqualTo(UPDATED_SURVEY_PARTICIPANTS_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateLevelFourWithPatch() throws Exception {
        // Initialize the database
        levelFourRepository.saveAndFlush(levelFour);

        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();

        // Update the levelFour using partial update
        LevelFour partialUpdatedLevelFour = new LevelFour();
        partialUpdatedLevelFour.setId(levelFour.getId());

        partialUpdatedLevelFour
            .identifier(UPDATED_IDENTIFIER)
            .criticalSuccessFactors(UPDATED_CRITICAL_SUCCESS_FACTORS)
            .populationSize(UPDATED_POPULATION_SIZE)
            .statisticalError(UPDATED_STATISTICAL_ERROR)
            .confidenceLevel(UPDATED_CONFIDENCE_LEVEL)
            .requiredSampleSize(UPDATED_REQUIRED_SAMPLE_SIZE)
            .estimatedResponseRate(UPDATED_ESTIMATED_RESPONSE_RATE)
            .surveyParticipantsNumber(UPDATED_SURVEY_PARTICIPANTS_NUMBER);

        restLevelFourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelFour.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelFour))
            )
            .andExpect(status().isOk());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
        LevelFour testLevelFour = levelFourList.get(levelFourList.size() - 1);
        assertThat(testLevelFour.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelFour.getCriticalSuccessFactors()).isEqualTo(UPDATED_CRITICAL_SUCCESS_FACTORS);
        assertThat(testLevelFour.getPopulationSize()).isEqualTo(UPDATED_POPULATION_SIZE);
        assertThat(testLevelFour.getStatisticalError()).isEqualTo(UPDATED_STATISTICAL_ERROR);
        assertThat(testLevelFour.getConfidenceLevel()).isEqualTo(UPDATED_CONFIDENCE_LEVEL);
        assertThat(testLevelFour.getRequiredSampleSize()).isEqualTo(UPDATED_REQUIRED_SAMPLE_SIZE);
        assertThat(testLevelFour.getEstimatedResponseRate()).isEqualTo(UPDATED_ESTIMATED_RESPONSE_RATE);
        assertThat(testLevelFour.getSurveyParticipantsNumber()).isEqualTo(UPDATED_SURVEY_PARTICIPANTS_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingLevelFour() throws Exception {
        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();
        levelFour.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelFourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelFour.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevelFour() throws Exception {
        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();
        levelFour.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelFourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevelFour() throws Exception {
        int databaseSizeBeforeUpdate = levelFourRepository.findAll().size();
        levelFour.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelFourMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelFour))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelFour in the database
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevelFour() throws Exception {
        // Initialize the database
        levelFourRepository.saveAndFlush(levelFour);

        int databaseSizeBeforeDelete = levelFourRepository.findAll().size();

        // Delete the levelFour
        restLevelFourMockMvc
            .perform(delete(ENTITY_API_URL_ID, levelFour.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelFour> levelFourList = levelFourRepository.findAll();
        assertThat(levelFourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
