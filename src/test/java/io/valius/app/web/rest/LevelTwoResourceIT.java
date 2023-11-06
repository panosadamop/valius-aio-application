package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.LevelTwo;
import io.valius.app.repository.LevelTwoRepository;
import io.valius.app.service.LevelTwoService;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link LevelTwoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LevelTwoResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_MARKET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_MARKET = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_MARKET_SEGMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_MARKET_SEGMENTATION = "BBBBBBBBBB";

    private static final String DEFAULT_SEGMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET_SEGMENTATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_SEGMENTATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIQUE_CHARACTERISTIC = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_CHARACTERISTIC = "BBBBBBBBBB";

    private static final String DEFAULT_SEGMENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUYING_CRITERIA_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_BUYING_CRITERIA_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITOR_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITOR_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITOR_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITOR_PRODUCT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_PRODUCT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITOR_MATURITY_PHASE = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_MATURITY_PHASE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITOR_COMPETITIVE_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_COMPETITIVE_POSITION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/level-twos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelTwoRepository levelTwoRepository;

    @Mock
    private LevelTwoRepository levelTwoRepositoryMock;

    @Mock
    private LevelTwoService levelTwoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelTwoMockMvc;

    private LevelTwo levelTwo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelTwo createEntity(EntityManager em) {
        LevelTwo levelTwo = new LevelTwo()
            .identifier(DEFAULT_IDENTIFIER)
            .targetMarket(DEFAULT_TARGET_MARKET)
            .currentMarketSegmentation(DEFAULT_CURRENT_MARKET_SEGMENTATION)
            .segmentName(DEFAULT_SEGMENT_NAME)
            .marketSegmentationType(DEFAULT_MARKET_SEGMENTATION_TYPE)
            .uniqueCharacteristic(DEFAULT_UNIQUE_CHARACTERISTIC)
            .segmentDescription(DEFAULT_SEGMENT_DESCRIPTION)
            .buyingCriteriaCategory(DEFAULT_BUYING_CRITERIA_CATEGORY)
            .competitorProductName(DEFAULT_COMPETITOR_PRODUCT_NAME)
            .competitorCompanyName(DEFAULT_COMPETITOR_COMPANY_NAME)
            .competitorBrandName(DEFAULT_COMPETITOR_BRAND_NAME)
            .competitorProductDescription(DEFAULT_COMPETITOR_PRODUCT_DESCRIPTION)
            .competitorMaturityPhase(DEFAULT_COMPETITOR_MATURITY_PHASE)
            .competitorCompetitivePosition(DEFAULT_COMPETITOR_COMPETITIVE_POSITION);
        return levelTwo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelTwo createUpdatedEntity(EntityManager em) {
        LevelTwo levelTwo = new LevelTwo()
            .identifier(UPDATED_IDENTIFIER)
            .targetMarket(UPDATED_TARGET_MARKET)
            .currentMarketSegmentation(UPDATED_CURRENT_MARKET_SEGMENTATION)
            .segmentName(UPDATED_SEGMENT_NAME)
            .marketSegmentationType(UPDATED_MARKET_SEGMENTATION_TYPE)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .segmentDescription(UPDATED_SEGMENT_DESCRIPTION)
            .buyingCriteriaCategory(UPDATED_BUYING_CRITERIA_CATEGORY)
            .competitorProductName(UPDATED_COMPETITOR_PRODUCT_NAME)
            .competitorCompanyName(UPDATED_COMPETITOR_COMPANY_NAME)
            .competitorBrandName(UPDATED_COMPETITOR_BRAND_NAME)
            .competitorProductDescription(UPDATED_COMPETITOR_PRODUCT_DESCRIPTION)
            .competitorMaturityPhase(UPDATED_COMPETITOR_MATURITY_PHASE)
            .competitorCompetitivePosition(UPDATED_COMPETITOR_COMPETITIVE_POSITION);
        return levelTwo;
    }

    @BeforeEach
    public void initTest() {
        levelTwo = createEntity(em);
    }

    @Test
    @Transactional
    void createLevelTwo() throws Exception {
        int databaseSizeBeforeCreate = levelTwoRepository.findAll().size();
        // Create the LevelTwo
        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isCreated());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeCreate + 1);
        LevelTwo testLevelTwo = levelTwoList.get(levelTwoList.size() - 1);
        assertThat(testLevelTwo.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLevelTwo.getTargetMarket()).isEqualTo(DEFAULT_TARGET_MARKET);
        assertThat(testLevelTwo.getCurrentMarketSegmentation()).isEqualTo(DEFAULT_CURRENT_MARKET_SEGMENTATION);
        assertThat(testLevelTwo.getSegmentName()).isEqualTo(DEFAULT_SEGMENT_NAME);
        assertThat(testLevelTwo.getMarketSegmentationType()).isEqualTo(DEFAULT_MARKET_SEGMENTATION_TYPE);
        assertThat(testLevelTwo.getUniqueCharacteristic()).isEqualTo(DEFAULT_UNIQUE_CHARACTERISTIC);
        assertThat(testLevelTwo.getSegmentDescription()).isEqualTo(DEFAULT_SEGMENT_DESCRIPTION);
        assertThat(testLevelTwo.getBuyingCriteriaCategory()).isEqualTo(DEFAULT_BUYING_CRITERIA_CATEGORY);
        assertThat(testLevelTwo.getCompetitorProductName()).isEqualTo(DEFAULT_COMPETITOR_PRODUCT_NAME);
        assertThat(testLevelTwo.getCompetitorCompanyName()).isEqualTo(DEFAULT_COMPETITOR_COMPANY_NAME);
        assertThat(testLevelTwo.getCompetitorBrandName()).isEqualTo(DEFAULT_COMPETITOR_BRAND_NAME);
        assertThat(testLevelTwo.getCompetitorProductDescription()).isEqualTo(DEFAULT_COMPETITOR_PRODUCT_DESCRIPTION);
        assertThat(testLevelTwo.getCompetitorMaturityPhase()).isEqualTo(DEFAULT_COMPETITOR_MATURITY_PHASE);
        assertThat(testLevelTwo.getCompetitorCompetitivePosition()).isEqualTo(DEFAULT_COMPETITOR_COMPETITIVE_POSITION);
    }

    @Test
    @Transactional
    void createLevelTwoWithExistingId() throws Exception {
        // Create the LevelTwo with an existing ID
        levelTwo.setId(1L);

        int databaseSizeBeforeCreate = levelTwoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setIdentifier(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTargetMarketIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setTargetMarket(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrentMarketSegmentationIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setCurrentMarketSegmentation(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSegmentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setSegmentName(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBuyingCriteriaCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setBuyingCriteriaCategory(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompetitorProductNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setCompetitorProductName(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompetitorCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setCompetitorCompanyName(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompetitorBrandNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setCompetitorBrandName(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompetitorMaturityPhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setCompetitorMaturityPhase(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompetitorCompetitivePositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelTwoRepository.findAll().size();
        // set the field null
        levelTwo.setCompetitorCompetitivePosition(null);

        // Create the LevelTwo, which fails.

        restLevelTwoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLevelTwos() throws Exception {
        // Initialize the database
        levelTwoRepository.saveAndFlush(levelTwo);

        // Get all the levelTwoList
        restLevelTwoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelTwo.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].targetMarket").value(hasItem(DEFAULT_TARGET_MARKET)))
            .andExpect(jsonPath("$.[*].currentMarketSegmentation").value(hasItem(DEFAULT_CURRENT_MARKET_SEGMENTATION)))
            .andExpect(jsonPath("$.[*].segmentName").value(hasItem(DEFAULT_SEGMENT_NAME)))
            .andExpect(jsonPath("$.[*].marketSegmentationType").value(hasItem(DEFAULT_MARKET_SEGMENTATION_TYPE)))
            .andExpect(jsonPath("$.[*].uniqueCharacteristic").value(hasItem(DEFAULT_UNIQUE_CHARACTERISTIC)))
            .andExpect(jsonPath("$.[*].segmentDescription").value(hasItem(DEFAULT_SEGMENT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].buyingCriteriaCategory").value(hasItem(DEFAULT_BUYING_CRITERIA_CATEGORY)))
            .andExpect(jsonPath("$.[*].competitorProductName").value(hasItem(DEFAULT_COMPETITOR_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].competitorCompanyName").value(hasItem(DEFAULT_COMPETITOR_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].competitorBrandName").value(hasItem(DEFAULT_COMPETITOR_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].competitorProductDescription").value(hasItem(DEFAULT_COMPETITOR_PRODUCT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].competitorMaturityPhase").value(hasItem(DEFAULT_COMPETITOR_MATURITY_PHASE)))
            .andExpect(jsonPath("$.[*].competitorCompetitivePosition").value(hasItem(DEFAULT_COMPETITOR_COMPETITIVE_POSITION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelTwosWithEagerRelationshipsIsEnabled() throws Exception {
        when(levelTwoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelTwoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(levelTwoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelTwosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(levelTwoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelTwoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(levelTwoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLevelTwo() throws Exception {
        // Initialize the database
        levelTwoRepository.saveAndFlush(levelTwo);

        // Get the levelTwo
        restLevelTwoMockMvc
            .perform(get(ENTITY_API_URL_ID, levelTwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelTwo.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.targetMarket").value(DEFAULT_TARGET_MARKET))
            .andExpect(jsonPath("$.currentMarketSegmentation").value(DEFAULT_CURRENT_MARKET_SEGMENTATION))
            .andExpect(jsonPath("$.segmentName").value(DEFAULT_SEGMENT_NAME))
            .andExpect(jsonPath("$.marketSegmentationType").value(DEFAULT_MARKET_SEGMENTATION_TYPE))
            .andExpect(jsonPath("$.uniqueCharacteristic").value(DEFAULT_UNIQUE_CHARACTERISTIC))
            .andExpect(jsonPath("$.segmentDescription").value(DEFAULT_SEGMENT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.buyingCriteriaCategory").value(DEFAULT_BUYING_CRITERIA_CATEGORY))
            .andExpect(jsonPath("$.competitorProductName").value(DEFAULT_COMPETITOR_PRODUCT_NAME))
            .andExpect(jsonPath("$.competitorCompanyName").value(DEFAULT_COMPETITOR_COMPANY_NAME))
            .andExpect(jsonPath("$.competitorBrandName").value(DEFAULT_COMPETITOR_BRAND_NAME))
            .andExpect(jsonPath("$.competitorProductDescription").value(DEFAULT_COMPETITOR_PRODUCT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.competitorMaturityPhase").value(DEFAULT_COMPETITOR_MATURITY_PHASE))
            .andExpect(jsonPath("$.competitorCompetitivePosition").value(DEFAULT_COMPETITOR_COMPETITIVE_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingLevelTwo() throws Exception {
        // Get the levelTwo
        restLevelTwoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevelTwo() throws Exception {
        // Initialize the database
        levelTwoRepository.saveAndFlush(levelTwo);

        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();

        // Update the levelTwo
        LevelTwo updatedLevelTwo = levelTwoRepository.findById(levelTwo.getId()).get();
        // Disconnect from session so that the updates on updatedLevelTwo are not directly saved in db
        em.detach(updatedLevelTwo);
        updatedLevelTwo
            .identifier(UPDATED_IDENTIFIER)
            .targetMarket(UPDATED_TARGET_MARKET)
            .currentMarketSegmentation(UPDATED_CURRENT_MARKET_SEGMENTATION)
            .segmentName(UPDATED_SEGMENT_NAME)
            .marketSegmentationType(UPDATED_MARKET_SEGMENTATION_TYPE)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .segmentDescription(UPDATED_SEGMENT_DESCRIPTION)
            .buyingCriteriaCategory(UPDATED_BUYING_CRITERIA_CATEGORY)
            .competitorProductName(UPDATED_COMPETITOR_PRODUCT_NAME)
            .competitorCompanyName(UPDATED_COMPETITOR_COMPANY_NAME)
            .competitorBrandName(UPDATED_COMPETITOR_BRAND_NAME)
            .competitorProductDescription(UPDATED_COMPETITOR_PRODUCT_DESCRIPTION)
            .competitorMaturityPhase(UPDATED_COMPETITOR_MATURITY_PHASE)
            .competitorCompetitivePosition(UPDATED_COMPETITOR_COMPETITIVE_POSITION);

        restLevelTwoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLevelTwo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLevelTwo))
            )
            .andExpect(status().isOk());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
        LevelTwo testLevelTwo = levelTwoList.get(levelTwoList.size() - 1);
        assertThat(testLevelTwo.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelTwo.getTargetMarket()).isEqualTo(UPDATED_TARGET_MARKET);
        assertThat(testLevelTwo.getCurrentMarketSegmentation()).isEqualTo(UPDATED_CURRENT_MARKET_SEGMENTATION);
        assertThat(testLevelTwo.getSegmentName()).isEqualTo(UPDATED_SEGMENT_NAME);
        assertThat(testLevelTwo.getMarketSegmentationType()).isEqualTo(UPDATED_MARKET_SEGMENTATION_TYPE);
        assertThat(testLevelTwo.getUniqueCharacteristic()).isEqualTo(UPDATED_UNIQUE_CHARACTERISTIC);
        assertThat(testLevelTwo.getSegmentDescription()).isEqualTo(UPDATED_SEGMENT_DESCRIPTION);
        assertThat(testLevelTwo.getBuyingCriteriaCategory()).isEqualTo(UPDATED_BUYING_CRITERIA_CATEGORY);
        assertThat(testLevelTwo.getCompetitorProductName()).isEqualTo(UPDATED_COMPETITOR_PRODUCT_NAME);
        assertThat(testLevelTwo.getCompetitorCompanyName()).isEqualTo(UPDATED_COMPETITOR_COMPANY_NAME);
        assertThat(testLevelTwo.getCompetitorBrandName()).isEqualTo(UPDATED_COMPETITOR_BRAND_NAME);
        assertThat(testLevelTwo.getCompetitorProductDescription()).isEqualTo(UPDATED_COMPETITOR_PRODUCT_DESCRIPTION);
        assertThat(testLevelTwo.getCompetitorMaturityPhase()).isEqualTo(UPDATED_COMPETITOR_MATURITY_PHASE);
        assertThat(testLevelTwo.getCompetitorCompetitivePosition()).isEqualTo(UPDATED_COMPETITOR_COMPETITIVE_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingLevelTwo() throws Exception {
        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();
        levelTwo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelTwoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelTwo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevelTwo() throws Exception {
        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();
        levelTwo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelTwoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevelTwo() throws Exception {
        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();
        levelTwo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelTwoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLevelTwoWithPatch() throws Exception {
        // Initialize the database
        levelTwoRepository.saveAndFlush(levelTwo);

        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();

        // Update the levelTwo using partial update
        LevelTwo partialUpdatedLevelTwo = new LevelTwo();
        partialUpdatedLevelTwo.setId(levelTwo.getId());

        partialUpdatedLevelTwo
            .targetMarket(UPDATED_TARGET_MARKET)
            .currentMarketSegmentation(UPDATED_CURRENT_MARKET_SEGMENTATION)
            .segmentName(UPDATED_SEGMENT_NAME)
            .segmentDescription(UPDATED_SEGMENT_DESCRIPTION)
            .competitorProductName(UPDATED_COMPETITOR_PRODUCT_NAME)
            .competitorCompanyName(UPDATED_COMPETITOR_COMPANY_NAME)
            .competitorBrandName(UPDATED_COMPETITOR_BRAND_NAME)
            .competitorProductDescription(UPDATED_COMPETITOR_PRODUCT_DESCRIPTION)
            .competitorCompetitivePosition(UPDATED_COMPETITOR_COMPETITIVE_POSITION);

        restLevelTwoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelTwo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelTwo))
            )
            .andExpect(status().isOk());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
        LevelTwo testLevelTwo = levelTwoList.get(levelTwoList.size() - 1);
        assertThat(testLevelTwo.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLevelTwo.getTargetMarket()).isEqualTo(UPDATED_TARGET_MARKET);
        assertThat(testLevelTwo.getCurrentMarketSegmentation()).isEqualTo(UPDATED_CURRENT_MARKET_SEGMENTATION);
        assertThat(testLevelTwo.getSegmentName()).isEqualTo(UPDATED_SEGMENT_NAME);
        assertThat(testLevelTwo.getMarketSegmentationType()).isEqualTo(DEFAULT_MARKET_SEGMENTATION_TYPE);
        assertThat(testLevelTwo.getUniqueCharacteristic()).isEqualTo(DEFAULT_UNIQUE_CHARACTERISTIC);
        assertThat(testLevelTwo.getSegmentDescription()).isEqualTo(UPDATED_SEGMENT_DESCRIPTION);
        assertThat(testLevelTwo.getBuyingCriteriaCategory()).isEqualTo(DEFAULT_BUYING_CRITERIA_CATEGORY);
        assertThat(testLevelTwo.getCompetitorProductName()).isEqualTo(UPDATED_COMPETITOR_PRODUCT_NAME);
        assertThat(testLevelTwo.getCompetitorCompanyName()).isEqualTo(UPDATED_COMPETITOR_COMPANY_NAME);
        assertThat(testLevelTwo.getCompetitorBrandName()).isEqualTo(UPDATED_COMPETITOR_BRAND_NAME);
        assertThat(testLevelTwo.getCompetitorProductDescription()).isEqualTo(UPDATED_COMPETITOR_PRODUCT_DESCRIPTION);
        assertThat(testLevelTwo.getCompetitorMaturityPhase()).isEqualTo(DEFAULT_COMPETITOR_MATURITY_PHASE);
        assertThat(testLevelTwo.getCompetitorCompetitivePosition()).isEqualTo(UPDATED_COMPETITOR_COMPETITIVE_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateLevelTwoWithPatch() throws Exception {
        // Initialize the database
        levelTwoRepository.saveAndFlush(levelTwo);

        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();

        // Update the levelTwo using partial update
        LevelTwo partialUpdatedLevelTwo = new LevelTwo();
        partialUpdatedLevelTwo.setId(levelTwo.getId());

        partialUpdatedLevelTwo
            .identifier(UPDATED_IDENTIFIER)
            .targetMarket(UPDATED_TARGET_MARKET)
            .currentMarketSegmentation(UPDATED_CURRENT_MARKET_SEGMENTATION)
            .segmentName(UPDATED_SEGMENT_NAME)
            .marketSegmentationType(UPDATED_MARKET_SEGMENTATION_TYPE)
            .uniqueCharacteristic(UPDATED_UNIQUE_CHARACTERISTIC)
            .segmentDescription(UPDATED_SEGMENT_DESCRIPTION)
            .buyingCriteriaCategory(UPDATED_BUYING_CRITERIA_CATEGORY)
            .competitorProductName(UPDATED_COMPETITOR_PRODUCT_NAME)
            .competitorCompanyName(UPDATED_COMPETITOR_COMPANY_NAME)
            .competitorBrandName(UPDATED_COMPETITOR_BRAND_NAME)
            .competitorProductDescription(UPDATED_COMPETITOR_PRODUCT_DESCRIPTION)
            .competitorMaturityPhase(UPDATED_COMPETITOR_MATURITY_PHASE)
            .competitorCompetitivePosition(UPDATED_COMPETITOR_COMPETITIVE_POSITION);

        restLevelTwoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelTwo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelTwo))
            )
            .andExpect(status().isOk());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
        LevelTwo testLevelTwo = levelTwoList.get(levelTwoList.size() - 1);
        assertThat(testLevelTwo.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelTwo.getTargetMarket()).isEqualTo(UPDATED_TARGET_MARKET);
        assertThat(testLevelTwo.getCurrentMarketSegmentation()).isEqualTo(UPDATED_CURRENT_MARKET_SEGMENTATION);
        assertThat(testLevelTwo.getSegmentName()).isEqualTo(UPDATED_SEGMENT_NAME);
        assertThat(testLevelTwo.getMarketSegmentationType()).isEqualTo(UPDATED_MARKET_SEGMENTATION_TYPE);
        assertThat(testLevelTwo.getUniqueCharacteristic()).isEqualTo(UPDATED_UNIQUE_CHARACTERISTIC);
        assertThat(testLevelTwo.getSegmentDescription()).isEqualTo(UPDATED_SEGMENT_DESCRIPTION);
        assertThat(testLevelTwo.getBuyingCriteriaCategory()).isEqualTo(UPDATED_BUYING_CRITERIA_CATEGORY);
        assertThat(testLevelTwo.getCompetitorProductName()).isEqualTo(UPDATED_COMPETITOR_PRODUCT_NAME);
        assertThat(testLevelTwo.getCompetitorCompanyName()).isEqualTo(UPDATED_COMPETITOR_COMPANY_NAME);
        assertThat(testLevelTwo.getCompetitorBrandName()).isEqualTo(UPDATED_COMPETITOR_BRAND_NAME);
        assertThat(testLevelTwo.getCompetitorProductDescription()).isEqualTo(UPDATED_COMPETITOR_PRODUCT_DESCRIPTION);
        assertThat(testLevelTwo.getCompetitorMaturityPhase()).isEqualTo(UPDATED_COMPETITOR_MATURITY_PHASE);
        assertThat(testLevelTwo.getCompetitorCompetitivePosition()).isEqualTo(UPDATED_COMPETITOR_COMPETITIVE_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingLevelTwo() throws Exception {
        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();
        levelTwo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelTwoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelTwo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevelTwo() throws Exception {
        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();
        levelTwo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelTwoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevelTwo() throws Exception {
        int databaseSizeBeforeUpdate = levelTwoRepository.findAll().size();
        levelTwo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelTwoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelTwo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelTwo in the database
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevelTwo() throws Exception {
        // Initialize the database
        levelTwoRepository.saveAndFlush(levelTwo);

        int databaseSizeBeforeDelete = levelTwoRepository.findAll().size();

        // Delete the levelTwo
        restLevelTwoMockMvc
            .perform(delete(ENTITY_API_URL_ID, levelTwo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelTwo> levelTwoList = levelTwoRepository.findAll();
        assertThat(levelTwoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
