package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.AgeGroup;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.AgeGroupRepository;
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
 * Integration tests for the {@link AgeGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgeGroupResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/age-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgeGroupRepository ageGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgeGroupMockMvc;

    private AgeGroup ageGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgeGroup createEntity(EntityManager em) {
        AgeGroup ageGroup = new AgeGroup().value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return ageGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgeGroup createUpdatedEntity(EntityManager em) {
        AgeGroup ageGroup = new AgeGroup().value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return ageGroup;
    }

    @BeforeEach
    public void initTest() {
        ageGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createAgeGroup() throws Exception {
        int databaseSizeBeforeCreate = ageGroupRepository.findAll().size();
        // Create the AgeGroup
        restAgeGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isCreated());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeCreate + 1);
        AgeGroup testAgeGroup = ageGroupList.get(ageGroupList.size() - 1);
        assertThat(testAgeGroup.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAgeGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAgeGroup.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createAgeGroupWithExistingId() throws Exception {
        // Create the AgeGroup with an existing ID
        ageGroup.setId(1L);

        int databaseSizeBeforeCreate = ageGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgeGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageGroupRepository.findAll().size();
        // set the field null
        ageGroup.setValue(null);

        // Create the AgeGroup, which fails.

        restAgeGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isBadRequest());

        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageGroupRepository.findAll().size();
        // set the field null
        ageGroup.setLanguage(null);

        // Create the AgeGroup, which fails.

        restAgeGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isBadRequest());

        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAgeGroups() throws Exception {
        // Initialize the database
        ageGroupRepository.saveAndFlush(ageGroup);

        // Get all the ageGroupList
        restAgeGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ageGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getAgeGroup() throws Exception {
        // Initialize the database
        ageGroupRepository.saveAndFlush(ageGroup);

        // Get the ageGroup
        restAgeGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, ageGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ageGroup.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAgeGroup() throws Exception {
        // Get the ageGroup
        restAgeGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgeGroup() throws Exception {
        // Initialize the database
        ageGroupRepository.saveAndFlush(ageGroup);

        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();

        // Update the ageGroup
        AgeGroup updatedAgeGroup = ageGroupRepository.findById(ageGroup.getId()).get();
        // Disconnect from session so that the updates on updatedAgeGroup are not directly saved in db
        em.detach(updatedAgeGroup);
        updatedAgeGroup.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restAgeGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgeGroup.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAgeGroup))
            )
            .andExpect(status().isOk());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
        AgeGroup testAgeGroup = ageGroupList.get(ageGroupList.size() - 1);
        assertThat(testAgeGroup.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAgeGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAgeGroup.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingAgeGroup() throws Exception {
        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();
        ageGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgeGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ageGroup.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgeGroup() throws Exception {
        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();
        ageGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgeGroup() throws Exception {
        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();
        ageGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgeGroupWithPatch() throws Exception {
        // Initialize the database
        ageGroupRepository.saveAndFlush(ageGroup);

        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();

        // Update the ageGroup using partial update
        AgeGroup partialUpdatedAgeGroup = new AgeGroup();
        partialUpdatedAgeGroup.setId(ageGroup.getId());

        partialUpdatedAgeGroup.value(UPDATED_VALUE).language(UPDATED_LANGUAGE);

        restAgeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgeGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgeGroup))
            )
            .andExpect(status().isOk());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
        AgeGroup testAgeGroup = ageGroupList.get(ageGroupList.size() - 1);
        assertThat(testAgeGroup.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAgeGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAgeGroup.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateAgeGroupWithPatch() throws Exception {
        // Initialize the database
        ageGroupRepository.saveAndFlush(ageGroup);

        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();

        // Update the ageGroup using partial update
        AgeGroup partialUpdatedAgeGroup = new AgeGroup();
        partialUpdatedAgeGroup.setId(ageGroup.getId());

        partialUpdatedAgeGroup.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restAgeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgeGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgeGroup))
            )
            .andExpect(status().isOk());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
        AgeGroup testAgeGroup = ageGroupList.get(ageGroupList.size() - 1);
        assertThat(testAgeGroup.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAgeGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAgeGroup.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingAgeGroup() throws Exception {
        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();
        ageGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ageGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgeGroup() throws Exception {
        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();
        ageGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgeGroup() throws Exception {
        int databaseSizeBeforeUpdate = ageGroupRepository.findAll().size();
        ageGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ageGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgeGroup in the database
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgeGroup() throws Exception {
        // Initialize the database
        ageGroupRepository.saveAndFlush(ageGroup);

        int databaseSizeBeforeDelete = ageGroupRepository.findAll().size();

        // Delete the ageGroup
        restAgeGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, ageGroup.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgeGroup> ageGroupList = ageGroupRepository.findAll();
        assertThat(ageGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
