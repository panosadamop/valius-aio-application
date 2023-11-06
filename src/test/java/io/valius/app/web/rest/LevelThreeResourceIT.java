package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.LevelThree;
import io.valius.app.repository.LevelThreeRepository;
import io.valius.app.service.LevelThreeService;
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
 * Integration tests for the {@link LevelThreeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LevelThreeResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_MAF_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_MAF_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_WEIGHTING_MAF = "AAAAAAAAAA";
    private static final String UPDATED_WEIGHTING_MAF = "BBBBBBBBBB";

    private static final String DEFAULT_LOW_ATTRACTIVENESS_RANGE_MAF = "AAAAAAAAAA";
    private static final String UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIUM_ATTRACTIVENESS_RANGE_MAF = "AAAAAAAAAA";
    private static final String UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH_ATTRACTIVENESS_RANGE_MAF = "AAAAAAAAAA";
    private static final String UPDATED_HIGH_ATTRACTIVENESS_RANGE_MAF = "BBBBBBBBBB";

    private static final String DEFAULT_SEGMENT_SCORE_MAF = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT_SCORE_MAF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/level-threes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelThreeRepository levelThreeRepository;

    @Mock
    private LevelThreeRepository levelThreeRepositoryMock;

    @Mock
    private LevelThreeService levelThreeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelThreeMockMvc;

    private LevelThree levelThree;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelThree createEntity(EntityManager em) {
        LevelThree levelThree = new LevelThree()
            .identifier(DEFAULT_IDENTIFIER)
            .mafCategory(DEFAULT_MAF_CATEGORY)
            .weightingMaf(DEFAULT_WEIGHTING_MAF)
            .lowAttractivenessRangeMaf(DEFAULT_LOW_ATTRACTIVENESS_RANGE_MAF)
            .mediumAttractivenessRangeMaf(DEFAULT_MEDIUM_ATTRACTIVENESS_RANGE_MAF)
            .highAttractivenessRangeMaf(DEFAULT_HIGH_ATTRACTIVENESS_RANGE_MAF)
            .segmentScoreMaf(DEFAULT_SEGMENT_SCORE_MAF);
        return levelThree;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelThree createUpdatedEntity(EntityManager em) {
        LevelThree levelThree = new LevelThree()
            .identifier(UPDATED_IDENTIFIER)
            .mafCategory(UPDATED_MAF_CATEGORY)
            .weightingMaf(UPDATED_WEIGHTING_MAF)
            .lowAttractivenessRangeMaf(UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF)
            .mediumAttractivenessRangeMaf(UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF)
            .highAttractivenessRangeMaf(UPDATED_HIGH_ATTRACTIVENESS_RANGE_MAF)
            .segmentScoreMaf(UPDATED_SEGMENT_SCORE_MAF);
        return levelThree;
    }

    @BeforeEach
    public void initTest() {
        levelThree = createEntity(em);
    }

    @Test
    @Transactional
    void createLevelThree() throws Exception {
        int databaseSizeBeforeCreate = levelThreeRepository.findAll().size();
        // Create the LevelThree
        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isCreated());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeCreate + 1);
        LevelThree testLevelThree = levelThreeList.get(levelThreeList.size() - 1);
        assertThat(testLevelThree.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLevelThree.getMafCategory()).isEqualTo(DEFAULT_MAF_CATEGORY);
        assertThat(testLevelThree.getWeightingMaf()).isEqualTo(DEFAULT_WEIGHTING_MAF);
        assertThat(testLevelThree.getLowAttractivenessRangeMaf()).isEqualTo(DEFAULT_LOW_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getMediumAttractivenessRangeMaf()).isEqualTo(DEFAULT_MEDIUM_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getHighAttractivenessRangeMaf()).isEqualTo(DEFAULT_HIGH_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getSegmentScoreMaf()).isEqualTo(DEFAULT_SEGMENT_SCORE_MAF);
    }

    @Test
    @Transactional
    void createLevelThreeWithExistingId() throws Exception {
        // Create the LevelThree with an existing ID
        levelThree.setId(1L);

        int databaseSizeBeforeCreate = levelThreeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelThreeRepository.findAll().size();
        // set the field null
        levelThree.setIdentifier(null);

        // Create the LevelThree, which fails.

        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMafCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelThreeRepository.findAll().size();
        // set the field null
        levelThree.setMafCategory(null);

        // Create the LevelThree, which fails.

        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeightingMafIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelThreeRepository.findAll().size();
        // set the field null
        levelThree.setWeightingMaf(null);

        // Create the LevelThree, which fails.

        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLowAttractivenessRangeMafIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelThreeRepository.findAll().size();
        // set the field null
        levelThree.setLowAttractivenessRangeMaf(null);

        // Create the LevelThree, which fails.

        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMediumAttractivenessRangeMafIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelThreeRepository.findAll().size();
        // set the field null
        levelThree.setMediumAttractivenessRangeMaf(null);

        // Create the LevelThree, which fails.

        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHighAttractivenessRangeMafIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelThreeRepository.findAll().size();
        // set the field null
        levelThree.setHighAttractivenessRangeMaf(null);

        // Create the LevelThree, which fails.

        restLevelThreeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLevelThrees() throws Exception {
        // Initialize the database
        levelThreeRepository.saveAndFlush(levelThree);

        // Get all the levelThreeList
        restLevelThreeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelThree.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].mafCategory").value(hasItem(DEFAULT_MAF_CATEGORY)))
            .andExpect(jsonPath("$.[*].weightingMaf").value(hasItem(DEFAULT_WEIGHTING_MAF)))
            .andExpect(jsonPath("$.[*].lowAttractivenessRangeMaf").value(hasItem(DEFAULT_LOW_ATTRACTIVENESS_RANGE_MAF)))
            .andExpect(jsonPath("$.[*].mediumAttractivenessRangeMaf").value(hasItem(DEFAULT_MEDIUM_ATTRACTIVENESS_RANGE_MAF)))
            .andExpect(jsonPath("$.[*].highAttractivenessRangeMaf").value(hasItem(DEFAULT_HIGH_ATTRACTIVENESS_RANGE_MAF)))
            .andExpect(jsonPath("$.[*].segmentScoreMaf").value(hasItem(DEFAULT_SEGMENT_SCORE_MAF)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelThreesWithEagerRelationshipsIsEnabled() throws Exception {
        when(levelThreeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelThreeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(levelThreeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelThreesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(levelThreeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelThreeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(levelThreeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLevelThree() throws Exception {
        // Initialize the database
        levelThreeRepository.saveAndFlush(levelThree);

        // Get the levelThree
        restLevelThreeMockMvc
            .perform(get(ENTITY_API_URL_ID, levelThree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelThree.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.mafCategory").value(DEFAULT_MAF_CATEGORY))
            .andExpect(jsonPath("$.weightingMaf").value(DEFAULT_WEIGHTING_MAF))
            .andExpect(jsonPath("$.lowAttractivenessRangeMaf").value(DEFAULT_LOW_ATTRACTIVENESS_RANGE_MAF))
            .andExpect(jsonPath("$.mediumAttractivenessRangeMaf").value(DEFAULT_MEDIUM_ATTRACTIVENESS_RANGE_MAF))
            .andExpect(jsonPath("$.highAttractivenessRangeMaf").value(DEFAULT_HIGH_ATTRACTIVENESS_RANGE_MAF))
            .andExpect(jsonPath("$.segmentScoreMaf").value(DEFAULT_SEGMENT_SCORE_MAF));
    }

    @Test
    @Transactional
    void getNonExistingLevelThree() throws Exception {
        // Get the levelThree
        restLevelThreeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevelThree() throws Exception {
        // Initialize the database
        levelThreeRepository.saveAndFlush(levelThree);

        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();

        // Update the levelThree
        LevelThree updatedLevelThree = levelThreeRepository.findById(levelThree.getId()).get();
        // Disconnect from session so that the updates on updatedLevelThree are not directly saved in db
        em.detach(updatedLevelThree);
        updatedLevelThree
            .identifier(UPDATED_IDENTIFIER)
            .mafCategory(UPDATED_MAF_CATEGORY)
            .weightingMaf(UPDATED_WEIGHTING_MAF)
            .lowAttractivenessRangeMaf(UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF)
            .mediumAttractivenessRangeMaf(UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF)
            .highAttractivenessRangeMaf(UPDATED_HIGH_ATTRACTIVENESS_RANGE_MAF)
            .segmentScoreMaf(UPDATED_SEGMENT_SCORE_MAF);

        restLevelThreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLevelThree.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLevelThree))
            )
            .andExpect(status().isOk());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
        LevelThree testLevelThree = levelThreeList.get(levelThreeList.size() - 1);
        assertThat(testLevelThree.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelThree.getMafCategory()).isEqualTo(UPDATED_MAF_CATEGORY);
        assertThat(testLevelThree.getWeightingMaf()).isEqualTo(UPDATED_WEIGHTING_MAF);
        assertThat(testLevelThree.getLowAttractivenessRangeMaf()).isEqualTo(UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getMediumAttractivenessRangeMaf()).isEqualTo(UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getHighAttractivenessRangeMaf()).isEqualTo(UPDATED_HIGH_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getSegmentScoreMaf()).isEqualTo(UPDATED_SEGMENT_SCORE_MAF);
    }

    @Test
    @Transactional
    void putNonExistingLevelThree() throws Exception {
        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();
        levelThree.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelThreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelThree.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevelThree() throws Exception {
        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();
        levelThree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelThreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevelThree() throws Exception {
        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();
        levelThree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelThreeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLevelThreeWithPatch() throws Exception {
        // Initialize the database
        levelThreeRepository.saveAndFlush(levelThree);

        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();

        // Update the levelThree using partial update
        LevelThree partialUpdatedLevelThree = new LevelThree();
        partialUpdatedLevelThree.setId(levelThree.getId());

        partialUpdatedLevelThree
            .identifier(UPDATED_IDENTIFIER)
            .mafCategory(UPDATED_MAF_CATEGORY)
            .lowAttractivenessRangeMaf(UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF)
            .mediumAttractivenessRangeMaf(UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF)
            .segmentScoreMaf(UPDATED_SEGMENT_SCORE_MAF);

        restLevelThreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelThree.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelThree))
            )
            .andExpect(status().isOk());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
        LevelThree testLevelThree = levelThreeList.get(levelThreeList.size() - 1);
        assertThat(testLevelThree.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelThree.getMafCategory()).isEqualTo(UPDATED_MAF_CATEGORY);
        assertThat(testLevelThree.getWeightingMaf()).isEqualTo(DEFAULT_WEIGHTING_MAF);
        assertThat(testLevelThree.getLowAttractivenessRangeMaf()).isEqualTo(UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getMediumAttractivenessRangeMaf()).isEqualTo(UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getHighAttractivenessRangeMaf()).isEqualTo(DEFAULT_HIGH_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getSegmentScoreMaf()).isEqualTo(UPDATED_SEGMENT_SCORE_MAF);
    }

    @Test
    @Transactional
    void fullUpdateLevelThreeWithPatch() throws Exception {
        // Initialize the database
        levelThreeRepository.saveAndFlush(levelThree);

        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();

        // Update the levelThree using partial update
        LevelThree partialUpdatedLevelThree = new LevelThree();
        partialUpdatedLevelThree.setId(levelThree.getId());

        partialUpdatedLevelThree
            .identifier(UPDATED_IDENTIFIER)
            .mafCategory(UPDATED_MAF_CATEGORY)
            .weightingMaf(UPDATED_WEIGHTING_MAF)
            .lowAttractivenessRangeMaf(UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF)
            .mediumAttractivenessRangeMaf(UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF)
            .highAttractivenessRangeMaf(UPDATED_HIGH_ATTRACTIVENESS_RANGE_MAF)
            .segmentScoreMaf(UPDATED_SEGMENT_SCORE_MAF);

        restLevelThreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelThree.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelThree))
            )
            .andExpect(status().isOk());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
        LevelThree testLevelThree = levelThreeList.get(levelThreeList.size() - 1);
        assertThat(testLevelThree.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLevelThree.getMafCategory()).isEqualTo(UPDATED_MAF_CATEGORY);
        assertThat(testLevelThree.getWeightingMaf()).isEqualTo(UPDATED_WEIGHTING_MAF);
        assertThat(testLevelThree.getLowAttractivenessRangeMaf()).isEqualTo(UPDATED_LOW_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getMediumAttractivenessRangeMaf()).isEqualTo(UPDATED_MEDIUM_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getHighAttractivenessRangeMaf()).isEqualTo(UPDATED_HIGH_ATTRACTIVENESS_RANGE_MAF);
        assertThat(testLevelThree.getSegmentScoreMaf()).isEqualTo(UPDATED_SEGMENT_SCORE_MAF);
    }

    @Test
    @Transactional
    void patchNonExistingLevelThree() throws Exception {
        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();
        levelThree.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelThreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelThree.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevelThree() throws Exception {
        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();
        levelThree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelThreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevelThree() throws Exception {
        int databaseSizeBeforeUpdate = levelThreeRepository.findAll().size();
        levelThree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelThreeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelThree))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelThree in the database
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevelThree() throws Exception {
        // Initialize the database
        levelThreeRepository.saveAndFlush(levelThree);

        int databaseSizeBeforeDelete = levelThreeRepository.findAll().size();

        // Delete the levelThree
        restLevelThreeMockMvc
            .perform(delete(ENTITY_API_URL_ID, levelThree.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelThree> levelThreeList = levelThreeRepository.findAll();
        assertThat(levelThreeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
