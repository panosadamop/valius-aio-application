package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.LevelOne;
import io.valius.app.repository.LevelOneRepository;
import io.valius.app.service.LevelOneService;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link LevelOneResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LevelOneResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMPANY_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPANY_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMPANY_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPANY_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PRODUCT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRODUCT_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRODUCT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRODUCT_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_INDUSTRY = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTS_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTS_SERVICES = "BBBBBBBBBB";

    private static final String DEFAULT_TERRITORY = "AAAAAAAAAA";
    private static final String UPDATED_TERRITORY = "BBBBBBBBBB";

    private static final String DEFAULT_NO_EMPLOYEES = "AAAAAAAAAA";
    private static final String UPDATED_NO_EMPLOYEES = "BBBBBBBBBB";

    private static final String DEFAULT_REVENUES = "AAAAAAAAAA";
    private static final String UPDATED_REVENUES = "BBBBBBBBBB";

    private static final String DEFAULT_MISSION = "AAAAAAAAAA";
    private static final String UPDATED_MISSION = "BBBBBBBBBB";

    private static final String DEFAULT_VISION = "AAAAAAAAAA";
    private static final String UPDATED_VISION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_VALUES = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_VALUES = "BBBBBBBBBB";

    private static final String DEFAULT_STRATEGIC_FOCUS = "AAAAAAAAAA";
    private static final String UPDATED_STRATEGIC_FOCUS = "BBBBBBBBBB";

    private static final String DEFAULT_MARKETING_BUDGET = "AAAAAAAAAA";
    private static final String UPDATED_MARKETING_BUDGET = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MATURITY_PHASE = "AAAAAAAAAA";
    private static final String UPDATED_MATURITY_PHASE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITIVE_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITIVE_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_AUDIENCE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_AUDIENCE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_POTENTIAL_CUSTOMERS_GROUPS = "AAAAAAAAAA";
    private static final String UPDATED_POTENTIAL_CUSTOMERS_GROUPS = "BBBBBBBBBB";

    private static final String DEFAULT_STRENGTHS = "AAAAAAAAAA";
    private static final String UPDATED_STRENGTHS = "BBBBBBBBBB";

    private static final String DEFAULT_WEAKNESSES = "AAAAAAAAAA";
    private static final String UPDATED_WEAKNESSES = "BBBBBBBBBB";

    private static final String DEFAULT_OPPORTUNITIES = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITIES = "BBBBBBBBBB";

    private static final String DEFAULT_THREATS = "AAAAAAAAAA";
    private static final String UPDATED_THREATS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/level-ones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelOneRepository levelOneRepository;

    @Mock
    private LevelOneRepository levelOneRepositoryMock;

    @Mock
    private LevelOneService levelOneServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelOneMockMvc;

    private LevelOne levelOne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelOne createEntity(EntityManager em) {
        LevelOne levelOne = new LevelOne()
            .identifier(DEFAULT_IDENTIFIER)
            .companyName(DEFAULT_COMPANY_NAME)
            .companyLogo(DEFAULT_COMPANY_LOGO)
            .companyLogoContentType(DEFAULT_COMPANY_LOGO_CONTENT_TYPE)
            .brandName(DEFAULT_BRAND_NAME)
            .productLogo(DEFAULT_PRODUCT_LOGO)
            .productLogoContentType(DEFAULT_PRODUCT_LOGO_CONTENT_TYPE)
            .industry(DEFAULT_INDUSTRY)
            .organizationType(DEFAULT_ORGANIZATION_TYPE)
            .productsServices(DEFAULT_PRODUCTS_SERVICES)
            .territory(DEFAULT_TERRITORY)
            .noEmployees(DEFAULT_NO_EMPLOYEES)
            .revenues(DEFAULT_REVENUES)
            .mission(DEFAULT_MISSION)
            .vision(DEFAULT_VISION)
            .companyValues(DEFAULT_COMPANY_VALUES)
            .strategicFocus(DEFAULT_STRATEGIC_FOCUS)
            .marketingBudget(DEFAULT_MARKETING_BUDGET)
            .productDescription(DEFAULT_PRODUCT_DESCRIPTION)
            .maturityPhase(DEFAULT_MATURITY_PHASE)
            .competitivePosition(DEFAULT_COMPETITIVE_POSITION)
            .targetAudienceDescription(DEFAULT_TARGET_AUDIENCE_DESCRIPTION)
            .potentialCustomersGroups(DEFAULT_POTENTIAL_CUSTOMERS_GROUPS)
            .strengths(DEFAULT_STRENGTHS)
            .weaknesses(DEFAULT_WEAKNESSES)
            .opportunities(DEFAULT_OPPORTUNITIES)
            .threats(DEFAULT_THREATS);
        return levelOne;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelOne createUpdatedEntity(EntityManager em) {
        LevelOne levelOne = new LevelOne()
            .identifier(UPDATED_IDENTIFIER)
            .companyName(UPDATED_COMPANY_NAME)
            .companyLogo(UPDATED_COMPANY_LOGO)
            .companyLogoContentType(UPDATED_COMPANY_LOGO_CONTENT_TYPE)
            .brandName(UPDATED_BRAND_NAME)
            .productLogo(UPDATED_PRODUCT_LOGO)
            .productLogoContentType(UPDATED_PRODUCT_LOGO_CONTENT_TYPE)
            .industry(UPDATED_INDUSTRY)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .productsServices(UPDATED_PRODUCTS_SERVICES)
            .territory(UPDATED_TERRITORY)
            .noEmployees(UPDATED_NO_EMPLOYEES)
            .revenues(UPDATED_REVENUES)
            .mission(UPDATED_MISSION)
            .vision(UPDATED_VISION)
            .companyValues(UPDATED_COMPANY_VALUES)
            .strategicFocus(UPDATED_STRATEGIC_FOCUS)
            .marketingBudget(UPDATED_MARKETING_BUDGET)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .maturityPhase(UPDATED_MATURITY_PHASE)
            .competitivePosition(UPDATED_COMPETITIVE_POSITION)
            .targetAudienceDescription(UPDATED_TARGET_AUDIENCE_DESCRIPTION)
            .potentialCustomersGroups(UPDATED_POTENTIAL_CUSTOMERS_GROUPS)
            .strengths(UPDATED_STRENGTHS)
            .weaknesses(UPDATED_WEAKNESSES)
            .opportunities(UPDATED_OPPORTUNITIES)
            .threats(UPDATED_THREATS);
        return levelOne;
    }

    @BeforeEach
    public void initTest() {
        levelOne = createEntity(em);
    }

    @Test
    @Transactional
    void createLevelOne() throws Exception {
        int databaseSizeBeforeCreate = levelOneRepository.findAll().size();
        // Create the LevelOne
        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isCreated());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeCreate + 1);
        LevelOne testLevelOne = levelOneList.get(levelOneList.size() - 1);
        assertThat(testLevelOne.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLevelOne.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testLevelOne.getCompanyLogo()).isEqualTo(DEFAULT_COMPANY_LOGO);
        assertThat(testLevelOne.getCompanyLogoContentType()).isEqualTo(DEFAULT_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testLevelOne.getProductLogo()).isEqualTo(DEFAULT_PRODUCT_LOGO);
        assertThat(testLevelOne.getProductLogoContentType()).isEqualTo(DEFAULT_PRODUCT_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testLevelOne.getOrganizationType()).isEqualTo(DEFAULT_ORGANIZATION_TYPE);
        assertThat(testLevelOne.getProductsServices()).isEqualTo(DEFAULT_PRODUCTS_SERVICES);
        assertThat(testLevelOne.getTerritory()).isEqualTo(DEFAULT_TERRITORY);
        assertThat(testLevelOne.getNoEmployees()).isEqualTo(DEFAULT_NO_EMPLOYEES);
        assertThat(testLevelOne.getRevenues()).isEqualTo(DEFAULT_REVENUES);
        assertThat(testLevelOne.getMission()).isEqualTo(DEFAULT_MISSION);
        assertThat(testLevelOne.getVision()).isEqualTo(DEFAULT_VISION);
        assertThat(testLevelOne.getCompanyValues()).isEqualTo(DEFAULT_COMPANY_VALUES);
        assertThat(testLevelOne.getStrategicFocus()).isEqualTo(DEFAULT_STRATEGIC_FOCUS);
        assertThat(testLevelOne.getMarketingBudget()).isEqualTo(DEFAULT_MARKETING_BUDGET);
        assertThat(testLevelOne.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);
        assertThat(testLevelOne.getMaturityPhase()).isEqualTo(DEFAULT_MATURITY_PHASE);
        assertThat(testLevelOne.getCompetitivePosition()).isEqualTo(DEFAULT_COMPETITIVE_POSITION);
        assertThat(testLevelOne.getTargetAudienceDescription()).isEqualTo(DEFAULT_TARGET_AUDIENCE_DESCRIPTION);
        assertThat(testLevelOne.getPotentialCustomersGroups()).isEqualTo(DEFAULT_POTENTIAL_CUSTOMERS_GROUPS);
        assertThat(testLevelOne.getStrengths()).isEqualTo(DEFAULT_STRENGTHS);
        assertThat(testLevelOne.getWeaknesses()).isEqualTo(DEFAULT_WEAKNESSES);
        assertThat(testLevelOne.getOpportunities()).isEqualTo(DEFAULT_OPPORTUNITIES);
        assertThat(testLevelOne.getThreats()).isEqualTo(DEFAULT_THREATS);
    }

    @Test
    @Transactional
    void createLevelOneWithExistingId() throws Exception {
        // Create the LevelOne with an existing ID
        levelOne.setId(1L);

        int databaseSizeBeforeCreate = levelOneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelOneRepository.findAll().size();
        // set the field null
        levelOne.setIdentifier(null);

        // Create the LevelOne, which fails.

        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelOneRepository.findAll().size();
        // set the field null
        levelOne.setCompanyName(null);

        // Create the LevelOne, which fails.

        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPotentialCustomersGroupsIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelOneRepository.findAll().size();
        // set the field null
        levelOne.setPotentialCustomersGroups(null);

        // Create the LevelOne, which fails.

        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStrengthsIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelOneRepository.findAll().size();
        // set the field null
        levelOne.setStrengths(null);

        // Create the LevelOne, which fails.

        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeaknessesIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelOneRepository.findAll().size();
        // set the field null
        levelOne.setWeaknesses(null);

        // Create the LevelOne, which fails.

        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOpportunitiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelOneRepository.findAll().size();
        // set the field null
        levelOne.setOpportunities(null);

        // Create the LevelOne, which fails.

        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThreatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelOneRepository.findAll().size();
        // set the field null
        levelOne.setThreats(null);

        // Create the LevelOne, which fails.

        restLevelOneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLevelOnes() throws Exception {
        // Initialize the database
        levelOneRepository.saveAndFlush(levelOne);

        // Get all the levelOneList
        restLevelOneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyLogoContentType").value(hasItem(DEFAULT_COMPANY_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].companyLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO))))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].productLogoContentType").value(hasItem(DEFAULT_PRODUCT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_LOGO))))
            .andExpect(jsonPath("$.[*].industry").value(hasItem(DEFAULT_INDUSTRY)))
            .andExpect(jsonPath("$.[*].organizationType").value(hasItem(DEFAULT_ORGANIZATION_TYPE)))
            .andExpect(jsonPath("$.[*].productsServices").value(hasItem(DEFAULT_PRODUCTS_SERVICES.toString())))
            .andExpect(jsonPath("$.[*].territory").value(hasItem(DEFAULT_TERRITORY)))
            .andExpect(jsonPath("$.[*].noEmployees").value(hasItem(DEFAULT_NO_EMPLOYEES)))
            .andExpect(jsonPath("$.[*].revenues").value(hasItem(DEFAULT_REVENUES)))
            .andExpect(jsonPath("$.[*].mission").value(hasItem(DEFAULT_MISSION.toString())))
            .andExpect(jsonPath("$.[*].vision").value(hasItem(DEFAULT_VISION.toString())))
            .andExpect(jsonPath("$.[*].companyValues").value(hasItem(DEFAULT_COMPANY_VALUES.toString())))
            .andExpect(jsonPath("$.[*].strategicFocus").value(hasItem(DEFAULT_STRATEGIC_FOCUS)))
            .andExpect(jsonPath("$.[*].marketingBudget").value(hasItem(DEFAULT_MARKETING_BUDGET)))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].maturityPhase").value(hasItem(DEFAULT_MATURITY_PHASE)))
            .andExpect(jsonPath("$.[*].competitivePosition").value(hasItem(DEFAULT_COMPETITIVE_POSITION)))
            .andExpect(jsonPath("$.[*].targetAudienceDescription").value(hasItem(DEFAULT_TARGET_AUDIENCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].potentialCustomersGroups").value(hasItem(DEFAULT_POTENTIAL_CUSTOMERS_GROUPS)))
            .andExpect(jsonPath("$.[*].strengths").value(hasItem(DEFAULT_STRENGTHS)))
            .andExpect(jsonPath("$.[*].weaknesses").value(hasItem(DEFAULT_WEAKNESSES)))
            .andExpect(jsonPath("$.[*].opportunities").value(hasItem(DEFAULT_OPPORTUNITIES)))
            .andExpect(jsonPath("$.[*].threats").value(hasItem(DEFAULT_THREATS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelOnesWithEagerRelationshipsIsEnabled() throws Exception {
        when(levelOneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelOneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(levelOneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelOnesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(levelOneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelOneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(levelOneRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLevelOne() throws Exception {
        // Initialize the database
        levelOneRepository.saveAndFlush(levelOne);

        // Get the levelOne
        restLevelOneMockMvc
            .perform(get(ENTITY_API_URL_ID, levelOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelOne.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.companyLogoContentType").value(DEFAULT_COMPANY_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.companyLogo").value(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO)))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME))
            .andExpect(jsonPath("$.productLogoContentType").value(DEFAULT_PRODUCT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.productLogo").value(Base64Utils.encodeToString(DEFAULT_PRODUCT_LOGO)))
            .andExpect(jsonPath("$.industry").value(DEFAULT_INDUSTRY))
            .andExpect(jsonPath("$.organizationType").value(DEFAULT_ORGANIZATION_TYPE))
            .andExpect(jsonPath("$.productsServices").value(DEFAULT_PRODUCTS_SERVICES.toString()))
            .andExpect(jsonPath("$.territory").value(DEFAULT_TERRITORY))
            .andExpect(jsonPath("$.noEmployees").value(DEFAULT_NO_EMPLOYEES))
            .andExpect(jsonPath("$.revenues").value(DEFAULT_REVENUES))
            .andExpect(jsonPath("$.mission").value(DEFAULT_MISSION.toString()))
            .andExpect(jsonPath("$.vision").value(DEFAULT_VISION.toString()))
            .andExpect(jsonPath("$.companyValues").value(DEFAULT_COMPANY_VALUES.toString()))
            .andExpect(jsonPath("$.strategicFocus").value(DEFAULT_STRATEGIC_FOCUS))
            .andExpect(jsonPath("$.marketingBudget").value(DEFAULT_MARKETING_BUDGET))
            .andExpect(jsonPath("$.productDescription").value(DEFAULT_PRODUCT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.maturityPhase").value(DEFAULT_MATURITY_PHASE))
            .andExpect(jsonPath("$.competitivePosition").value(DEFAULT_COMPETITIVE_POSITION))
            .andExpect(jsonPath("$.targetAudienceDescription").value(DEFAULT_TARGET_AUDIENCE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.potentialCustomersGroups").value(DEFAULT_POTENTIAL_CUSTOMERS_GROUPS))
            .andExpect(jsonPath("$.strengths").value(DEFAULT_STRENGTHS))
            .andExpect(jsonPath("$.weaknesses").value(DEFAULT_WEAKNESSES))
            .andExpect(jsonPath("$.opportunities").value(DEFAULT_OPPORTUNITIES))
            .andExpect(jsonPath("$.threats").value(DEFAULT_THREATS));
    }

    @Test
    @Transactional
    void getNonExistingLevelOne() throws Exception {
        // Get the levelOne
        restLevelOneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevelOne() throws Exception {
        // Initialize the database
        levelOneRepository.saveAndFlush(levelOne);

        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();

        // Update the levelOne
        LevelOne updatedLevelOne = levelOneRepository.findById(levelOne.getId()).get();
        // Disconnect from session so that the updates on updatedLevelOne are not directly saved in db
        em.detach(updatedLevelOne);
        updatedLevelOne
            .identifier(UPDATED_IDENTIFIER)
            .companyName(UPDATED_COMPANY_NAME)
            .companyLogo(UPDATED_COMPANY_LOGO)
            .companyLogoContentType(UPDATED_COMPANY_LOGO_CONTENT_TYPE)
            .brandName(UPDATED_BRAND_NAME)
            .productLogo(UPDATED_PRODUCT_LOGO)
            .productLogoContentType(UPDATED_PRODUCT_LOGO_CONTENT_TYPE)
            .industry(UPDATED_INDUSTRY)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .productsServices(UPDATED_PRODUCTS_SERVICES)
            .territory(UPDATED_TERRITORY)
            .noEmployees(UPDATED_NO_EMPLOYEES)
            .revenues(UPDATED_REVENUES)
            .mission(UPDATED_MISSION)
            .vision(UPDATED_VISION)
            .companyValues(UPDATED_COMPANY_VALUES)
            .strategicFocus(UPDATED_STRATEGIC_FOCUS)
            .marketingBudget(UPDATED_MARKETING_BUDGET)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .maturityPhase(UPDATED_MATURITY_PHASE)
            .competitivePosition(UPDATED_COMPETITIVE_POSITION)
            .targetAudienceDescription(UPDATED_TARGET_AUDIENCE_DESCRIPTION)
            .potentialCustomersGroups(UPDATED_POTENTIAL_CUSTOMERS_GROUPS)
            .strengths(UPDATED_STRENGTHS)
            .weaknesses(UPDATED_WEAKNESSES)
            .opportunities(UPDATED_OPPORTUNITIES)
            .threats(UPDATED_THREATS);

        restLevelOneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLevelOne.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLevelOne))
            )
            .andExpect(status().isOk());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
        LevelOne testLevelOne = levelOneList.get(levelOneList.size() - 1);
        assertThat(testLevelOne.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelOne.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testLevelOne.getCompanyLogo()).isEqualTo(UPDATED_COMPANY_LOGO);
        assertThat(testLevelOne.getCompanyLogoContentType()).isEqualTo(UPDATED_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testLevelOne.getProductLogo()).isEqualTo(UPDATED_PRODUCT_LOGO);
        assertThat(testLevelOne.getProductLogoContentType()).isEqualTo(UPDATED_PRODUCT_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testLevelOne.getOrganizationType()).isEqualTo(UPDATED_ORGANIZATION_TYPE);
        assertThat(testLevelOne.getProductsServices()).isEqualTo(UPDATED_PRODUCTS_SERVICES);
        assertThat(testLevelOne.getTerritory()).isEqualTo(UPDATED_TERRITORY);
        assertThat(testLevelOne.getNoEmployees()).isEqualTo(UPDATED_NO_EMPLOYEES);
        assertThat(testLevelOne.getRevenues()).isEqualTo(UPDATED_REVENUES);
        assertThat(testLevelOne.getMission()).isEqualTo(UPDATED_MISSION);
        assertThat(testLevelOne.getVision()).isEqualTo(UPDATED_VISION);
        assertThat(testLevelOne.getCompanyValues()).isEqualTo(UPDATED_COMPANY_VALUES);
        assertThat(testLevelOne.getStrategicFocus()).isEqualTo(UPDATED_STRATEGIC_FOCUS);
        assertThat(testLevelOne.getMarketingBudget()).isEqualTo(UPDATED_MARKETING_BUDGET);
        assertThat(testLevelOne.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
        assertThat(testLevelOne.getMaturityPhase()).isEqualTo(UPDATED_MATURITY_PHASE);
        assertThat(testLevelOne.getCompetitivePosition()).isEqualTo(UPDATED_COMPETITIVE_POSITION);
        assertThat(testLevelOne.getTargetAudienceDescription()).isEqualTo(UPDATED_TARGET_AUDIENCE_DESCRIPTION);
        assertThat(testLevelOne.getPotentialCustomersGroups()).isEqualTo(UPDATED_POTENTIAL_CUSTOMERS_GROUPS);
        assertThat(testLevelOne.getStrengths()).isEqualTo(UPDATED_STRENGTHS);
        assertThat(testLevelOne.getWeaknesses()).isEqualTo(UPDATED_WEAKNESSES);
        assertThat(testLevelOne.getOpportunities()).isEqualTo(UPDATED_OPPORTUNITIES);
        assertThat(testLevelOne.getThreats()).isEqualTo(UPDATED_THREATS);
    }

    @Test
    @Transactional
    void putNonExistingLevelOne() throws Exception {
        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();
        levelOne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelOneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelOne.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevelOne() throws Exception {
        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();
        levelOne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelOneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevelOne() throws Exception {
        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();
        levelOne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelOneMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLevelOneWithPatch() throws Exception {
        // Initialize the database
        levelOneRepository.saveAndFlush(levelOne);

        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();

        // Update the levelOne using partial update
        LevelOne partialUpdatedLevelOne = new LevelOne();
        partialUpdatedLevelOne.setId(levelOne.getId());

        partialUpdatedLevelOne
            .companyName(UPDATED_COMPANY_NAME)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .territory(UPDATED_TERRITORY)
            .noEmployees(UPDATED_NO_EMPLOYEES)
            .revenues(UPDATED_REVENUES)
            .strategicFocus(UPDATED_STRATEGIC_FOCUS)
            .marketingBudget(UPDATED_MARKETING_BUDGET)
            .maturityPhase(UPDATED_MATURITY_PHASE)
            .potentialCustomersGroups(UPDATED_POTENTIAL_CUSTOMERS_GROUPS)
            .strengths(UPDATED_STRENGTHS);

        restLevelOneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelOne.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelOne))
            )
            .andExpect(status().isOk());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
        LevelOne testLevelOne = levelOneList.get(levelOneList.size() - 1);
        assertThat(testLevelOne.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLevelOne.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testLevelOne.getCompanyLogo()).isEqualTo(DEFAULT_COMPANY_LOGO);
        assertThat(testLevelOne.getCompanyLogoContentType()).isEqualTo(DEFAULT_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testLevelOne.getProductLogo()).isEqualTo(DEFAULT_PRODUCT_LOGO);
        assertThat(testLevelOne.getProductLogoContentType()).isEqualTo(DEFAULT_PRODUCT_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testLevelOne.getOrganizationType()).isEqualTo(UPDATED_ORGANIZATION_TYPE);
        assertThat(testLevelOne.getProductsServices()).isEqualTo(DEFAULT_PRODUCTS_SERVICES);
        assertThat(testLevelOne.getTerritory()).isEqualTo(UPDATED_TERRITORY);
        assertThat(testLevelOne.getNoEmployees()).isEqualTo(UPDATED_NO_EMPLOYEES);
        assertThat(testLevelOne.getRevenues()).isEqualTo(UPDATED_REVENUES);
        assertThat(testLevelOne.getMission()).isEqualTo(DEFAULT_MISSION);
        assertThat(testLevelOne.getVision()).isEqualTo(DEFAULT_VISION);
        assertThat(testLevelOne.getCompanyValues()).isEqualTo(DEFAULT_COMPANY_VALUES);
        assertThat(testLevelOne.getStrategicFocus()).isEqualTo(UPDATED_STRATEGIC_FOCUS);
        assertThat(testLevelOne.getMarketingBudget()).isEqualTo(UPDATED_MARKETING_BUDGET);
        assertThat(testLevelOne.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);
        assertThat(testLevelOne.getMaturityPhase()).isEqualTo(UPDATED_MATURITY_PHASE);
        assertThat(testLevelOne.getCompetitivePosition()).isEqualTo(DEFAULT_COMPETITIVE_POSITION);
        assertThat(testLevelOne.getTargetAudienceDescription()).isEqualTo(DEFAULT_TARGET_AUDIENCE_DESCRIPTION);
        assertThat(testLevelOne.getPotentialCustomersGroups()).isEqualTo(UPDATED_POTENTIAL_CUSTOMERS_GROUPS);
        assertThat(testLevelOne.getStrengths()).isEqualTo(UPDATED_STRENGTHS);
        assertThat(testLevelOne.getWeaknesses()).isEqualTo(DEFAULT_WEAKNESSES);
        assertThat(testLevelOne.getOpportunities()).isEqualTo(DEFAULT_OPPORTUNITIES);
        assertThat(testLevelOne.getThreats()).isEqualTo(DEFAULT_THREATS);
    }

    @Test
    @Transactional
    void fullUpdateLevelOneWithPatch() throws Exception {
        // Initialize the database
        levelOneRepository.saveAndFlush(levelOne);

        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();

        // Update the levelOne using partial update
        LevelOne partialUpdatedLevelOne = new LevelOne();
        partialUpdatedLevelOne.setId(levelOne.getId());

        partialUpdatedLevelOne
            .identifier(UPDATED_IDENTIFIER)
            .companyName(UPDATED_COMPANY_NAME)
            .companyLogo(UPDATED_COMPANY_LOGO)
            .companyLogoContentType(UPDATED_COMPANY_LOGO_CONTENT_TYPE)
            .brandName(UPDATED_BRAND_NAME)
            .productLogo(UPDATED_PRODUCT_LOGO)
            .productLogoContentType(UPDATED_PRODUCT_LOGO_CONTENT_TYPE)
            .industry(UPDATED_INDUSTRY)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .productsServices(UPDATED_PRODUCTS_SERVICES)
            .territory(UPDATED_TERRITORY)
            .noEmployees(UPDATED_NO_EMPLOYEES)
            .revenues(UPDATED_REVENUES)
            .mission(UPDATED_MISSION)
            .vision(UPDATED_VISION)
            .companyValues(UPDATED_COMPANY_VALUES)
            .strategicFocus(UPDATED_STRATEGIC_FOCUS)
            .marketingBudget(UPDATED_MARKETING_BUDGET)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .maturityPhase(UPDATED_MATURITY_PHASE)
            .competitivePosition(UPDATED_COMPETITIVE_POSITION)
            .targetAudienceDescription(UPDATED_TARGET_AUDIENCE_DESCRIPTION)
            .potentialCustomersGroups(UPDATED_POTENTIAL_CUSTOMERS_GROUPS)
            .strengths(UPDATED_STRENGTHS)
            .weaknesses(UPDATED_WEAKNESSES)
            .opportunities(UPDATED_OPPORTUNITIES)
            .threats(UPDATED_THREATS);

        restLevelOneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelOne.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelOne))
            )
            .andExpect(status().isOk());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
        LevelOne testLevelOne = levelOneList.get(levelOneList.size() - 1);
        assertThat(testLevelOne.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelOne.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testLevelOne.getCompanyLogo()).isEqualTo(UPDATED_COMPANY_LOGO);
        assertThat(testLevelOne.getCompanyLogoContentType()).isEqualTo(UPDATED_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testLevelOne.getProductLogo()).isEqualTo(UPDATED_PRODUCT_LOGO);
        assertThat(testLevelOne.getProductLogoContentType()).isEqualTo(UPDATED_PRODUCT_LOGO_CONTENT_TYPE);
        assertThat(testLevelOne.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testLevelOne.getOrganizationType()).isEqualTo(UPDATED_ORGANIZATION_TYPE);
        assertThat(testLevelOne.getProductsServices()).isEqualTo(UPDATED_PRODUCTS_SERVICES);
        assertThat(testLevelOne.getTerritory()).isEqualTo(UPDATED_TERRITORY);
        assertThat(testLevelOne.getNoEmployees()).isEqualTo(UPDATED_NO_EMPLOYEES);
        assertThat(testLevelOne.getRevenues()).isEqualTo(UPDATED_REVENUES);
        assertThat(testLevelOne.getMission()).isEqualTo(UPDATED_MISSION);
        assertThat(testLevelOne.getVision()).isEqualTo(UPDATED_VISION);
        assertThat(testLevelOne.getCompanyValues()).isEqualTo(UPDATED_COMPANY_VALUES);
        assertThat(testLevelOne.getStrategicFocus()).isEqualTo(UPDATED_STRATEGIC_FOCUS);
        assertThat(testLevelOne.getMarketingBudget()).isEqualTo(UPDATED_MARKETING_BUDGET);
        assertThat(testLevelOne.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
        assertThat(testLevelOne.getMaturityPhase()).isEqualTo(UPDATED_MATURITY_PHASE);
        assertThat(testLevelOne.getCompetitivePosition()).isEqualTo(UPDATED_COMPETITIVE_POSITION);
        assertThat(testLevelOne.getTargetAudienceDescription()).isEqualTo(UPDATED_TARGET_AUDIENCE_DESCRIPTION);
        assertThat(testLevelOne.getPotentialCustomersGroups()).isEqualTo(UPDATED_POTENTIAL_CUSTOMERS_GROUPS);
        assertThat(testLevelOne.getStrengths()).isEqualTo(UPDATED_STRENGTHS);
        assertThat(testLevelOne.getWeaknesses()).isEqualTo(UPDATED_WEAKNESSES);
        assertThat(testLevelOne.getOpportunities()).isEqualTo(UPDATED_OPPORTUNITIES);
        assertThat(testLevelOne.getThreats()).isEqualTo(UPDATED_THREATS);
    }

    @Test
    @Transactional
    void patchNonExistingLevelOne() throws Exception {
        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();
        levelOne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelOneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelOne.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevelOne() throws Exception {
        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();
        levelOne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelOneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevelOne() throws Exception {
        int databaseSizeBeforeUpdate = levelOneRepository.findAll().size();
        levelOne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelOneMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelOne))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelOne in the database
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevelOne() throws Exception {
        // Initialize the database
        levelOneRepository.saveAndFlush(levelOne);

        int databaseSizeBeforeDelete = levelOneRepository.findAll().size();

        // Delete the levelOne
        restLevelOneMockMvc
            .perform(delete(ENTITY_API_URL_ID, levelOne.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelOne> levelOneList = levelOneRepository.findAll();
        assertThat(levelOneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
