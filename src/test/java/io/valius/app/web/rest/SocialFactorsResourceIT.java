package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.SocialFactors;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.SocialFactorsRepository;
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
 * Integration tests for the {@link SocialFactorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocialFactorsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/social-factors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocialFactorsRepository socialFactorsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialFactorsMockMvc;

    private SocialFactors socialFactors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialFactors createEntity(EntityManager em) {
        SocialFactors socialFactors = new SocialFactors().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return socialFactors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialFactors createUpdatedEntity(EntityManager em) {
        SocialFactors socialFactors = new SocialFactors().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return socialFactors;
    }

    @BeforeEach
    public void initTest() {
        socialFactors = createEntity(em);
    }

    @Test
    @Transactional
    void createSocialFactors() throws Exception {
        int databaseSizeBeforeCreate = socialFactorsRepository.findAll().size();
        // Create the SocialFactors
        restSocialFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isCreated());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeCreate + 1);
        SocialFactors testSocialFactors = socialFactorsList.get(socialFactorsList.size() - 1);
        assertThat(testSocialFactors.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSocialFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSocialFactors.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createSocialFactorsWithExistingId() throws Exception {
        // Create the SocialFactors with an existing ID
        socialFactors.setId(1L);

        int databaseSizeBeforeCreate = socialFactorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialFactorsRepository.findAll().size();
        // set the field null
        socialFactors.setValue(null);

        // Create the SocialFactors, which fails.

        restSocialFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isBadRequest());

        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialFactorsRepository.findAll().size();
        // set the field null
        socialFactors.setLanguage(null);

        // Create the SocialFactors, which fails.

        restSocialFactorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isBadRequest());

        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocialFactors() throws Exception {
        // Initialize the database
        socialFactorsRepository.saveAndFlush(socialFactors);

        // Get all the socialFactorsList
        restSocialFactorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialFactors.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getSocialFactors() throws Exception {
        // Initialize the database
        socialFactorsRepository.saveAndFlush(socialFactors);

        // Get the socialFactors
        restSocialFactorsMockMvc
            .perform(get(ENTITY_API_URL_ID, socialFactors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socialFactors.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSocialFactors() throws Exception {
        // Get the socialFactors
        restSocialFactorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSocialFactors() throws Exception {
        // Initialize the database
        socialFactorsRepository.saveAndFlush(socialFactors);

        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();

        // Update the socialFactors
        SocialFactors updatedSocialFactors = socialFactorsRepository.findById(socialFactors.getId()).get();
        // Disconnect from session so that the updates on updatedSocialFactors are not directly saved in db
        em.detach(updatedSocialFactors);
        updatedSocialFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restSocialFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSocialFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSocialFactors))
            )
            .andExpect(status().isOk());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
        SocialFactors testSocialFactors = socialFactorsList.get(socialFactorsList.size() - 1);
        assertThat(testSocialFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSocialFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSocialFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingSocialFactors() throws Exception {
        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();
        socialFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialFactors.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocialFactors() throws Exception {
        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();
        socialFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialFactorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocialFactors() throws Exception {
        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();
        socialFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialFactorsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocialFactorsWithPatch() throws Exception {
        // Initialize the database
        socialFactorsRepository.saveAndFlush(socialFactors);

        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();

        // Update the socialFactors using partial update
        SocialFactors partialUpdatedSocialFactors = new SocialFactors();
        partialUpdatedSocialFactors.setId(socialFactors.getId());

        partialUpdatedSocialFactors.language(UPDATED_LANGUAGE);

        restSocialFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialFactors))
            )
            .andExpect(status().isOk());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
        SocialFactors testSocialFactors = socialFactorsList.get(socialFactorsList.size() - 1);
        assertThat(testSocialFactors.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSocialFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSocialFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateSocialFactorsWithPatch() throws Exception {
        // Initialize the database
        socialFactorsRepository.saveAndFlush(socialFactors);

        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();

        // Update the socialFactors using partial update
        SocialFactors partialUpdatedSocialFactors = new SocialFactors();
        partialUpdatedSocialFactors.setId(socialFactors.getId());

        partialUpdatedSocialFactors.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restSocialFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialFactors))
            )
            .andExpect(status().isOk());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
        SocialFactors testSocialFactors = socialFactorsList.get(socialFactorsList.size() - 1);
        assertThat(testSocialFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSocialFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSocialFactors.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingSocialFactors() throws Exception {
        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();
        socialFactors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socialFactors.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocialFactors() throws Exception {
        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();
        socialFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocialFactors() throws Exception {
        int databaseSizeBeforeUpdate = socialFactorsRepository.findAll().size();
        socialFactors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialFactorsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialFactors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialFactors in the database
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocialFactors() throws Exception {
        // Initialize the database
        socialFactorsRepository.saveAndFlush(socialFactors);

        int databaseSizeBeforeDelete = socialFactorsRepository.findAll().size();

        // Delete the socialFactors
        restSocialFactorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, socialFactors.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocialFactors> socialFactorsList = socialFactorsRepository.findAll();
        assertThat(socialFactorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
