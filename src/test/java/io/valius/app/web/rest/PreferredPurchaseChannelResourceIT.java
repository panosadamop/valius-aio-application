package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.PreferredPurchaseChannel;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.PreferredPurchaseChannelRepository;
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
 * Integration tests for the {@link PreferredPurchaseChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferredPurchaseChannelResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/preferred-purchase-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PreferredPurchaseChannelRepository preferredPurchaseChannelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreferredPurchaseChannelMockMvc;

    private PreferredPurchaseChannel preferredPurchaseChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredPurchaseChannel createEntity(EntityManager em) {
        PreferredPurchaseChannel preferredPurchaseChannel = new PreferredPurchaseChannel()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return preferredPurchaseChannel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredPurchaseChannel createUpdatedEntity(EntityManager em) {
        PreferredPurchaseChannel preferredPurchaseChannel = new PreferredPurchaseChannel()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return preferredPurchaseChannel;
    }

    @BeforeEach
    public void initTest() {
        preferredPurchaseChannel = createEntity(em);
    }

    @Test
    @Transactional
    void createPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeCreate = preferredPurchaseChannelRepository.findAll().size();
        // Create the PreferredPurchaseChannel
        restPreferredPurchaseChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isCreated());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredPurchaseChannel testPreferredPurchaseChannel = preferredPurchaseChannelList.get(preferredPurchaseChannelList.size() - 1);
        assertThat(testPreferredPurchaseChannel.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPreferredPurchaseChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPreferredPurchaseChannel.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createPreferredPurchaseChannelWithExistingId() throws Exception {
        // Create the PreferredPurchaseChannel with an existing ID
        preferredPurchaseChannel.setId(1L);

        int databaseSizeBeforeCreate = preferredPurchaseChannelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredPurchaseChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = preferredPurchaseChannelRepository.findAll().size();
        // set the field null
        preferredPurchaseChannel.setValue(null);

        // Create the PreferredPurchaseChannel, which fails.

        restPreferredPurchaseChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = preferredPurchaseChannelRepository.findAll().size();
        // set the field null
        preferredPurchaseChannel.setLanguage(null);

        // Create the PreferredPurchaseChannel, which fails.

        restPreferredPurchaseChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPreferredPurchaseChannels() throws Exception {
        // Initialize the database
        preferredPurchaseChannelRepository.saveAndFlush(preferredPurchaseChannel);

        // Get all the preferredPurchaseChannelList
        restPreferredPurchaseChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredPurchaseChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getPreferredPurchaseChannel() throws Exception {
        // Initialize the database
        preferredPurchaseChannelRepository.saveAndFlush(preferredPurchaseChannel);

        // Get the preferredPurchaseChannel
        restPreferredPurchaseChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, preferredPurchaseChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferredPurchaseChannel.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPreferredPurchaseChannel() throws Exception {
        // Get the preferredPurchaseChannel
        restPreferredPurchaseChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPreferredPurchaseChannel() throws Exception {
        // Initialize the database
        preferredPurchaseChannelRepository.saveAndFlush(preferredPurchaseChannel);

        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();

        // Update the preferredPurchaseChannel
        PreferredPurchaseChannel updatedPreferredPurchaseChannel = preferredPurchaseChannelRepository
            .findById(preferredPurchaseChannel.getId())
            .get();
        // Disconnect from session so that the updates on updatedPreferredPurchaseChannel are not directly saved in db
        em.detach(updatedPreferredPurchaseChannel);
        updatedPreferredPurchaseChannel.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferredPurchaseChannel))
            )
            .andExpect(status().isOk());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
        PreferredPurchaseChannel testPreferredPurchaseChannel = preferredPurchaseChannelList.get(preferredPurchaseChannelList.size() - 1);
        assertThat(testPreferredPurchaseChannel.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPreferredPurchaseChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPreferredPurchaseChannel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();
        preferredPurchaseChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();
        preferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();
        preferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePreferredPurchaseChannelWithPatch() throws Exception {
        // Initialize the database
        preferredPurchaseChannelRepository.saveAndFlush(preferredPurchaseChannel);

        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();

        // Update the preferredPurchaseChannel using partial update
        PreferredPurchaseChannel partialUpdatedPreferredPurchaseChannel = new PreferredPurchaseChannel();
        partialUpdatedPreferredPurchaseChannel.setId(preferredPurchaseChannel.getId());

        partialUpdatedPreferredPurchaseChannel.language(UPDATED_LANGUAGE);

        restPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredPurchaseChannel))
            )
            .andExpect(status().isOk());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
        PreferredPurchaseChannel testPreferredPurchaseChannel = preferredPurchaseChannelList.get(preferredPurchaseChannelList.size() - 1);
        assertThat(testPreferredPurchaseChannel.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPreferredPurchaseChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPreferredPurchaseChannel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdatePreferredPurchaseChannelWithPatch() throws Exception {
        // Initialize the database
        preferredPurchaseChannelRepository.saveAndFlush(preferredPurchaseChannel);

        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();

        // Update the preferredPurchaseChannel using partial update
        PreferredPurchaseChannel partialUpdatedPreferredPurchaseChannel = new PreferredPurchaseChannel();
        partialUpdatedPreferredPurchaseChannel.setId(preferredPurchaseChannel.getId());

        partialUpdatedPreferredPurchaseChannel.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredPurchaseChannel))
            )
            .andExpect(status().isOk());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
        PreferredPurchaseChannel testPreferredPurchaseChannel = preferredPurchaseChannelList.get(preferredPurchaseChannelList.size() - 1);
        assertThat(testPreferredPurchaseChannel.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPreferredPurchaseChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPreferredPurchaseChannel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();
        preferredPurchaseChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();
        preferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredPurchaseChannelRepository.findAll().size();
        preferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredPurchaseChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredPurchaseChannel in the database
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePreferredPurchaseChannel() throws Exception {
        // Initialize the database
        preferredPurchaseChannelRepository.saveAndFlush(preferredPurchaseChannel);

        int databaseSizeBeforeDelete = preferredPurchaseChannelRepository.findAll().size();

        // Delete the preferredPurchaseChannel
        restPreferredPurchaseChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferredPurchaseChannel.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreferredPurchaseChannel> preferredPurchaseChannelList = preferredPurchaseChannelRepository.findAll();
        assertThat(preferredPurchaseChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
