package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.Survey;
import io.valius.app.repository.SurveyRepository;
import io.valius.app.service.SurveyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
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
 * Integration tests for the {@link SurveyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SurveyResourceIT {

    private static final String DEFAULT_CONSUMER_ASSESSED_BRANDS = "AAAAAAAAAA";
    private static final String UPDATED_CONSUMER_ASSESSED_BRANDS = "BBBBBBBBBB";

    private static final String DEFAULT_CRITICAL_SUCCESS_FACTORS = "AAAAAAAAAA";
    private static final String UPDATED_CRITICAL_SUCCESS_FACTORS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_BUYING_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_BUYING_CRITERIA = "BBBBBBBBBB";

    private static final String DEFAULT_CONSUMER_SEGMENT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_CONSUMER_SEGMENT_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_SEGMENT_CSF = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT_CSF = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_AGE_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_AGE_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_NET_PROMOTER_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_NET_PROMOTER_SCORE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/surveys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurveyRepository surveyRepository;

    @Mock
    private SurveyRepository surveyRepositoryMock;

    @Mock
    private SurveyService surveyServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyMockMvc;

    private Survey survey;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survey createEntity(EntityManager em) {
        Survey survey = new Survey()
            .consumerAssessedBrands(DEFAULT_CONSUMER_ASSESSED_BRANDS)
            .criticalSuccessFactors(DEFAULT_CRITICAL_SUCCESS_FACTORS)
            .additionalBuyingCriteria(DEFAULT_ADDITIONAL_BUYING_CRITERIA)
            .consumerSegmentGroup(DEFAULT_CONSUMER_SEGMENT_GROUP)
            .segmentCsf(DEFAULT_SEGMENT_CSF)
            .gender(DEFAULT_GENDER)
            .ageGroup(DEFAULT_AGE_GROUP)
            .location(DEFAULT_LOCATION)
            .education(DEFAULT_EDUCATION)
            .occupation(DEFAULT_OCCUPATION)
            .netPromoterScore(DEFAULT_NET_PROMOTER_SCORE);
        return survey;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survey createUpdatedEntity(EntityManager em) {
        Survey survey = new Survey()
            .consumerAssessedBrands(UPDATED_CONSUMER_ASSESSED_BRANDS)
            .criticalSuccessFactors(UPDATED_CRITICAL_SUCCESS_FACTORS)
            .additionalBuyingCriteria(UPDATED_ADDITIONAL_BUYING_CRITERIA)
            .consumerSegmentGroup(UPDATED_CONSUMER_SEGMENT_GROUP)
            .segmentCsf(UPDATED_SEGMENT_CSF)
            .gender(UPDATED_GENDER)
            .ageGroup(UPDATED_AGE_GROUP)
            .location(UPDATED_LOCATION)
            .education(UPDATED_EDUCATION)
            .occupation(UPDATED_OCCUPATION)
            .netPromoterScore(UPDATED_NET_PROMOTER_SCORE);
        return survey;
    }

    @BeforeEach
    public void initTest() {
        survey = createEntity(em);
    }

    @Test
    @Transactional
    void createSurvey() throws Exception {
        int databaseSizeBeforeCreate = surveyRepository.findAll().size();
        // Create the Survey
        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isCreated());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeCreate + 1);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getConsumerAssessedBrands()).isEqualTo(DEFAULT_CONSUMER_ASSESSED_BRANDS);
        assertThat(testSurvey.getCriticalSuccessFactors()).isEqualTo(DEFAULT_CRITICAL_SUCCESS_FACTORS);
        assertThat(testSurvey.getAdditionalBuyingCriteria()).isEqualTo(DEFAULT_ADDITIONAL_BUYING_CRITERIA);
        assertThat(testSurvey.getConsumerSegmentGroup()).isEqualTo(DEFAULT_CONSUMER_SEGMENT_GROUP);
        assertThat(testSurvey.getSegmentCsf()).isEqualTo(DEFAULT_SEGMENT_CSF);
        assertThat(testSurvey.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSurvey.getAgeGroup()).isEqualTo(DEFAULT_AGE_GROUP);
        assertThat(testSurvey.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testSurvey.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testSurvey.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testSurvey.getNetPromoterScore()).isEqualTo(DEFAULT_NET_PROMOTER_SCORE);
    }

    @Test
    @Transactional
    void createSurveyWithExistingId() throws Exception {
        // Create the Survey with an existing ID
        survey.setId(1L);

        int databaseSizeBeforeCreate = surveyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkConsumerAssessedBrandsIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setConsumerAssessedBrands(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCriticalSuccessFactorsIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setCriticalSuccessFactors(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdditionalBuyingCriteriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setAdditionalBuyingCriteria(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsumerSegmentGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setConsumerSegmentGroup(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSegmentCsfIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setSegmentCsf(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setGender(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setAgeGroup(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setLocation(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEducationIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setEducation(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setOccupation(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNetPromoterScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setNetPromoterScore(null);

        // Create the Survey, which fails.

        restSurveyMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSurveys() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(survey.getId().intValue())))
            .andExpect(jsonPath("$.[*].consumerAssessedBrands").value(hasItem(DEFAULT_CONSUMER_ASSESSED_BRANDS)))
            .andExpect(jsonPath("$.[*].criticalSuccessFactors").value(hasItem(DEFAULT_CRITICAL_SUCCESS_FACTORS)))
            .andExpect(jsonPath("$.[*].additionalBuyingCriteria").value(hasItem(DEFAULT_ADDITIONAL_BUYING_CRITERIA)))
            .andExpect(jsonPath("$.[*].consumerSegmentGroup").value(hasItem(DEFAULT_CONSUMER_SEGMENT_GROUP)))
            .andExpect(jsonPath("$.[*].segmentCsf").value(hasItem(DEFAULT_SEGMENT_CSF)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].ageGroup").value(hasItem(DEFAULT_AGE_GROUP)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].netPromoterScore").value(hasItem(DEFAULT_NET_PROMOTER_SCORE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSurveysWithEagerRelationshipsIsEnabled() throws Exception {
        when(surveyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSurveyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(surveyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSurveysWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(surveyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSurveyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(surveyRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get the survey
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL_ID, survey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(survey.getId().intValue()))
            .andExpect(jsonPath("$.consumerAssessedBrands").value(DEFAULT_CONSUMER_ASSESSED_BRANDS))
            .andExpect(jsonPath("$.criticalSuccessFactors").value(DEFAULT_CRITICAL_SUCCESS_FACTORS))
            .andExpect(jsonPath("$.additionalBuyingCriteria").value(DEFAULT_ADDITIONAL_BUYING_CRITERIA))
            .andExpect(jsonPath("$.consumerSegmentGroup").value(DEFAULT_CONSUMER_SEGMENT_GROUP))
            .andExpect(jsonPath("$.segmentCsf").value(DEFAULT_SEGMENT_CSF))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.ageGroup").value(DEFAULT_AGE_GROUP))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.netPromoterScore").value(DEFAULT_NET_PROMOTER_SCORE));
    }

    @Test
    @Transactional
    void getNonExistingSurvey() throws Exception {
        // Get the survey
        restSurveyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey
        Survey updatedSurvey = surveyRepository.findById(survey.getId()).get();
        // Disconnect from session so that the updates on updatedSurvey are not directly saved in db
        em.detach(updatedSurvey);
        updatedSurvey
            .consumerAssessedBrands(UPDATED_CONSUMER_ASSESSED_BRANDS)
            .criticalSuccessFactors(UPDATED_CRITICAL_SUCCESS_FACTORS)
            .additionalBuyingCriteria(UPDATED_ADDITIONAL_BUYING_CRITERIA)
            .consumerSegmentGroup(UPDATED_CONSUMER_SEGMENT_GROUP)
            .segmentCsf(UPDATED_SEGMENT_CSF)
            .gender(UPDATED_GENDER)
            .ageGroup(UPDATED_AGE_GROUP)
            .location(UPDATED_LOCATION)
            .education(UPDATED_EDUCATION)
            .occupation(UPDATED_OCCUPATION)
            .netPromoterScore(UPDATED_NET_PROMOTER_SCORE);

        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSurvey.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSurvey))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getConsumerAssessedBrands()).isEqualTo(UPDATED_CONSUMER_ASSESSED_BRANDS);
        assertThat(testSurvey.getCriticalSuccessFactors()).isEqualTo(UPDATED_CRITICAL_SUCCESS_FACTORS);
        assertThat(testSurvey.getAdditionalBuyingCriteria()).isEqualTo(UPDATED_ADDITIONAL_BUYING_CRITERIA);
        assertThat(testSurvey.getConsumerSegmentGroup()).isEqualTo(UPDATED_CONSUMER_SEGMENT_GROUP);
        assertThat(testSurvey.getSegmentCsf()).isEqualTo(UPDATED_SEGMENT_CSF);
        assertThat(testSurvey.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSurvey.getAgeGroup()).isEqualTo(UPDATED_AGE_GROUP);
        assertThat(testSurvey.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSurvey.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testSurvey.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testSurvey.getNetPromoterScore()).isEqualTo(UPDATED_NET_PROMOTER_SCORE);
    }

    @Test
    @Transactional
    void putNonExistingSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, survey.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurveyWithPatch() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey using partial update
        Survey partialUpdatedSurvey = new Survey();
        partialUpdatedSurvey.setId(survey.getId());

        partialUpdatedSurvey.segmentCsf(UPDATED_SEGMENT_CSF).ageGroup(UPDATED_AGE_GROUP).location(UPDATED_LOCATION);

        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvey.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvey))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getConsumerAssessedBrands()).isEqualTo(DEFAULT_CONSUMER_ASSESSED_BRANDS);
        assertThat(testSurvey.getCriticalSuccessFactors()).isEqualTo(DEFAULT_CRITICAL_SUCCESS_FACTORS);
        assertThat(testSurvey.getAdditionalBuyingCriteria()).isEqualTo(DEFAULT_ADDITIONAL_BUYING_CRITERIA);
        assertThat(testSurvey.getConsumerSegmentGroup()).isEqualTo(DEFAULT_CONSUMER_SEGMENT_GROUP);
        assertThat(testSurvey.getSegmentCsf()).isEqualTo(UPDATED_SEGMENT_CSF);
        assertThat(testSurvey.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSurvey.getAgeGroup()).isEqualTo(UPDATED_AGE_GROUP);
        assertThat(testSurvey.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSurvey.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testSurvey.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testSurvey.getNetPromoterScore()).isEqualTo(DEFAULT_NET_PROMOTER_SCORE);
    }

    @Test
    @Transactional
    void fullUpdateSurveyWithPatch() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey using partial update
        Survey partialUpdatedSurvey = new Survey();
        partialUpdatedSurvey.setId(survey.getId());

        partialUpdatedSurvey
            .consumerAssessedBrands(UPDATED_CONSUMER_ASSESSED_BRANDS)
            .criticalSuccessFactors(UPDATED_CRITICAL_SUCCESS_FACTORS)
            .additionalBuyingCriteria(UPDATED_ADDITIONAL_BUYING_CRITERIA)
            .consumerSegmentGroup(UPDATED_CONSUMER_SEGMENT_GROUP)
            .segmentCsf(UPDATED_SEGMENT_CSF)
            .gender(UPDATED_GENDER)
            .ageGroup(UPDATED_AGE_GROUP)
            .location(UPDATED_LOCATION)
            .education(UPDATED_EDUCATION)
            .occupation(UPDATED_OCCUPATION)
            .netPromoterScore(UPDATED_NET_PROMOTER_SCORE);

        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvey.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvey))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getConsumerAssessedBrands()).isEqualTo(UPDATED_CONSUMER_ASSESSED_BRANDS);
        assertThat(testSurvey.getCriticalSuccessFactors()).isEqualTo(UPDATED_CRITICAL_SUCCESS_FACTORS);
        assertThat(testSurvey.getAdditionalBuyingCriteria()).isEqualTo(UPDATED_ADDITIONAL_BUYING_CRITERIA);
        assertThat(testSurvey.getConsumerSegmentGroup()).isEqualTo(UPDATED_CONSUMER_SEGMENT_GROUP);
        assertThat(testSurvey.getSegmentCsf()).isEqualTo(UPDATED_SEGMENT_CSF);
        assertThat(testSurvey.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSurvey.getAgeGroup()).isEqualTo(UPDATED_AGE_GROUP);
        assertThat(testSurvey.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSurvey.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testSurvey.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testSurvey.getNetPromoterScore()).isEqualTo(UPDATED_NET_PROMOTER_SCORE);
    }

    @Test
    @Transactional
    void patchNonExistingSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, survey.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(survey))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeDelete = surveyRepository.findAll().size();

        // Delete the survey
        restSurveyMockMvc
            .perform(delete(ENTITY_API_URL_ID, survey.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
