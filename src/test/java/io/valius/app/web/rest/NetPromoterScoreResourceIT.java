package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.NetPromoterScore;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.NetPromoterScoreRepository;
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
 * Integration tests for the {@link NetPromoterScoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NetPromoterScoreResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/net-promoter-scores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NetPromoterScoreRepository netPromoterScoreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNetPromoterScoreMockMvc;

    private NetPromoterScore netPromoterScore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetPromoterScore createEntity(EntityManager em) {
        NetPromoterScore netPromoterScore = new NetPromoterScore()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return netPromoterScore;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetPromoterScore createUpdatedEntity(EntityManager em) {
        NetPromoterScore netPromoterScore = new NetPromoterScore()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return netPromoterScore;
    }

    @BeforeEach
    public void initTest() {
        netPromoterScore = createEntity(em);
    }

    @Test
    @Transactional
    void createNetPromoterScore() throws Exception {
        int databaseSizeBeforeCreate = netPromoterScoreRepository.findAll().size();
        // Create the NetPromoterScore
        restNetPromoterScoreMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isCreated());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeCreate + 1);
        NetPromoterScore testNetPromoterScore = netPromoterScoreList.get(netPromoterScoreList.size() - 1);
        assertThat(testNetPromoterScore.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testNetPromoterScore.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNetPromoterScore.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createNetPromoterScoreWithExistingId() throws Exception {
        // Create the NetPromoterScore with an existing ID
        netPromoterScore.setId(1L);

        int databaseSizeBeforeCreate = netPromoterScoreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetPromoterScoreMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = netPromoterScoreRepository.findAll().size();
        // set the field null
        netPromoterScore.setValue(null);

        // Create the NetPromoterScore, which fails.

        restNetPromoterScoreMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isBadRequest());

        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = netPromoterScoreRepository.findAll().size();
        // set the field null
        netPromoterScore.setLanguage(null);

        // Create the NetPromoterScore, which fails.

        restNetPromoterScoreMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isBadRequest());

        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNetPromoterScores() throws Exception {
        // Initialize the database
        netPromoterScoreRepository.saveAndFlush(netPromoterScore);

        // Get all the netPromoterScoreList
        restNetPromoterScoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(netPromoterScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getNetPromoterScore() throws Exception {
        // Initialize the database
        netPromoterScoreRepository.saveAndFlush(netPromoterScore);

        // Get the netPromoterScore
        restNetPromoterScoreMockMvc
            .perform(get(ENTITY_API_URL_ID, netPromoterScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(netPromoterScore.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNetPromoterScore() throws Exception {
        // Get the netPromoterScore
        restNetPromoterScoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNetPromoterScore() throws Exception {
        // Initialize the database
        netPromoterScoreRepository.saveAndFlush(netPromoterScore);

        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();

        // Update the netPromoterScore
        NetPromoterScore updatedNetPromoterScore = netPromoterScoreRepository.findById(netPromoterScore.getId()).get();
        // Disconnect from session so that the updates on updatedNetPromoterScore are not directly saved in db
        em.detach(updatedNetPromoterScore);
        updatedNetPromoterScore.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restNetPromoterScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNetPromoterScore.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNetPromoterScore))
            )
            .andExpect(status().isOk());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
        NetPromoterScore testNetPromoterScore = netPromoterScoreList.get(netPromoterScoreList.size() - 1);
        assertThat(testNetPromoterScore.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testNetPromoterScore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNetPromoterScore.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingNetPromoterScore() throws Exception {
        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();
        netPromoterScore.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetPromoterScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, netPromoterScore.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNetPromoterScore() throws Exception {
        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();
        netPromoterScore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetPromoterScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNetPromoterScore() throws Exception {
        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();
        netPromoterScore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetPromoterScoreMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNetPromoterScoreWithPatch() throws Exception {
        // Initialize the database
        netPromoterScoreRepository.saveAndFlush(netPromoterScore);

        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();

        // Update the netPromoterScore using partial update
        NetPromoterScore partialUpdatedNetPromoterScore = new NetPromoterScore();
        partialUpdatedNetPromoterScore.setId(netPromoterScore.getId());

        partialUpdatedNetPromoterScore.language(UPDATED_LANGUAGE);

        restNetPromoterScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetPromoterScore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetPromoterScore))
            )
            .andExpect(status().isOk());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
        NetPromoterScore testNetPromoterScore = netPromoterScoreList.get(netPromoterScoreList.size() - 1);
        assertThat(testNetPromoterScore.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testNetPromoterScore.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNetPromoterScore.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateNetPromoterScoreWithPatch() throws Exception {
        // Initialize the database
        netPromoterScoreRepository.saveAndFlush(netPromoterScore);

        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();

        // Update the netPromoterScore using partial update
        NetPromoterScore partialUpdatedNetPromoterScore = new NetPromoterScore();
        partialUpdatedNetPromoterScore.setId(netPromoterScore.getId());

        partialUpdatedNetPromoterScore.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restNetPromoterScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetPromoterScore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetPromoterScore))
            )
            .andExpect(status().isOk());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
        NetPromoterScore testNetPromoterScore = netPromoterScoreList.get(netPromoterScoreList.size() - 1);
        assertThat(testNetPromoterScore.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testNetPromoterScore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNetPromoterScore.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingNetPromoterScore() throws Exception {
        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();
        netPromoterScore.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetPromoterScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, netPromoterScore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNetPromoterScore() throws Exception {
        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();
        netPromoterScore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetPromoterScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNetPromoterScore() throws Exception {
        int databaseSizeBeforeUpdate = netPromoterScoreRepository.findAll().size();
        netPromoterScore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetPromoterScoreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(netPromoterScore))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetPromoterScore in the database
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNetPromoterScore() throws Exception {
        // Initialize the database
        netPromoterScoreRepository.saveAndFlush(netPromoterScore);

        int databaseSizeBeforeDelete = netPromoterScoreRepository.findAll().size();

        // Delete the netPromoterScore
        restNetPromoterScoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, netPromoterScore.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NetPromoterScore> netPromoterScoreList = netPromoterScoreRepository.findAll();
        assertThat(netPromoterScoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
