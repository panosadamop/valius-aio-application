package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.TargetMarket;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.TargetMarketRepository;
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
 * Integration tests for the {@link TargetMarketResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TargetMarketResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/target-markets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TargetMarketRepository targetMarketRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTargetMarketMockMvc;

    private TargetMarket targetMarket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetMarket createEntity(EntityManager em) {
        TargetMarket targetMarket = new TargetMarket().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return targetMarket;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetMarket createUpdatedEntity(EntityManager em) {
        TargetMarket targetMarket = new TargetMarket().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return targetMarket;
    }

    @BeforeEach
    public void initTest() {
        targetMarket = createEntity(em);
    }

    @Test
    @Transactional
    void createTargetMarket() throws Exception {
        int databaseSizeBeforeCreate = targetMarketRepository.findAll().size();
        // Create the TargetMarket
        restTargetMarketMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isCreated());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeCreate + 1);
        TargetMarket testTargetMarket = targetMarketList.get(targetMarketList.size() - 1);
        assertThat(testTargetMarket.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTargetMarket.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTargetMarket.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createTargetMarketWithExistingId() throws Exception {
        // Create the TargetMarket with an existing ID
        targetMarket.setId(1L);

        int databaseSizeBeforeCreate = targetMarketRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTargetMarketMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetMarketRepository.findAll().size();
        // set the field null
        targetMarket.setValue(null);

        // Create the TargetMarket, which fails.

        restTargetMarketMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isBadRequest());

        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetMarketRepository.findAll().size();
        // set the field null
        targetMarket.setLanguage(null);

        // Create the TargetMarket, which fails.

        restTargetMarketMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isBadRequest());

        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTargetMarkets() throws Exception {
        // Initialize the database
        targetMarketRepository.saveAndFlush(targetMarket);

        // Get all the targetMarketList
        restTargetMarketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(targetMarket.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getTargetMarket() throws Exception {
        // Initialize the database
        targetMarketRepository.saveAndFlush(targetMarket);

        // Get the targetMarket
        restTargetMarketMockMvc
            .perform(get(ENTITY_API_URL_ID, targetMarket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(targetMarket.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTargetMarket() throws Exception {
        // Get the targetMarket
        restTargetMarketMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTargetMarket() throws Exception {
        // Initialize the database
        targetMarketRepository.saveAndFlush(targetMarket);

        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();

        // Update the targetMarket
        TargetMarket updatedTargetMarket = targetMarketRepository.findById(targetMarket.getId()).get();
        // Disconnect from session so that the updates on updatedTargetMarket are not directly saved in db
        em.detach(updatedTargetMarket);
        updatedTargetMarket.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restTargetMarketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTargetMarket.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTargetMarket))
            )
            .andExpect(status().isOk());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
        TargetMarket testTargetMarket = targetMarketList.get(targetMarketList.size() - 1);
        assertThat(testTargetMarket.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTargetMarket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTargetMarket.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingTargetMarket() throws Exception {
        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();
        targetMarket.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetMarketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, targetMarket.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTargetMarket() throws Exception {
        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();
        targetMarket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMarketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTargetMarket() throws Exception {
        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();
        targetMarket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMarketMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTargetMarketWithPatch() throws Exception {
        // Initialize the database
        targetMarketRepository.saveAndFlush(targetMarket);

        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();

        // Update the targetMarket using partial update
        TargetMarket partialUpdatedTargetMarket = new TargetMarket();
        partialUpdatedTargetMarket.setId(targetMarket.getId());

        partialUpdatedTargetMarket.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);

        restTargetMarketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTargetMarket.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetMarket))
            )
            .andExpect(status().isOk());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
        TargetMarket testTargetMarket = targetMarketList.get(targetMarketList.size() - 1);
        assertThat(testTargetMarket.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTargetMarket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTargetMarket.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateTargetMarketWithPatch() throws Exception {
        // Initialize the database
        targetMarketRepository.saveAndFlush(targetMarket);

        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();

        // Update the targetMarket using partial update
        TargetMarket partialUpdatedTargetMarket = new TargetMarket();
        partialUpdatedTargetMarket.setId(targetMarket.getId());

        partialUpdatedTargetMarket.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restTargetMarketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTargetMarket.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetMarket))
            )
            .andExpect(status().isOk());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
        TargetMarket testTargetMarket = targetMarketList.get(targetMarketList.size() - 1);
        assertThat(testTargetMarket.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTargetMarket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTargetMarket.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingTargetMarket() throws Exception {
        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();
        targetMarket.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetMarketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, targetMarket.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTargetMarket() throws Exception {
        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();
        targetMarket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMarketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTargetMarket() throws Exception {
        int databaseSizeBeforeUpdate = targetMarketRepository.findAll().size();
        targetMarket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMarketMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetMarket))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TargetMarket in the database
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTargetMarket() throws Exception {
        // Initialize the database
        targetMarketRepository.saveAndFlush(targetMarket);

        int databaseSizeBeforeDelete = targetMarketRepository.findAll().size();

        // Delete the targetMarket
        restTargetMarketMockMvc
            .perform(delete(ENTITY_API_URL_ID, targetMarket.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TargetMarket> targetMarketList = targetMarketRepository.findAll();
        assertThat(targetMarketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
