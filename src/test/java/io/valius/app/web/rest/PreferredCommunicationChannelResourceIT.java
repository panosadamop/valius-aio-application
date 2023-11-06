package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.PreferredCommunicationChannel;
import io.valius.app.domain.enumeration.Language;
import io.valius.app.repository.PreferredCommunicationChannelRepository;
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
 * Integration tests for the {@link PreferredCommunicationChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferredCommunicationChannelResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.GREEK;

    private static final String ENTITY_API_URL = "/api/preferred-communication-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PreferredCommunicationChannelRepository preferredCommunicationChannelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreferredCommunicationChannelMockMvc;

    private PreferredCommunicationChannel preferredCommunicationChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredCommunicationChannel createEntity(EntityManager em) {
        PreferredCommunicationChannel preferredCommunicationChannel = new PreferredCommunicationChannel()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE);
        return preferredCommunicationChannel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredCommunicationChannel createUpdatedEntity(EntityManager em) {
        PreferredCommunicationChannel preferredCommunicationChannel = new PreferredCommunicationChannel()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE);
        return preferredCommunicationChannel;
    }

    @BeforeEach
    public void initTest() {
        preferredCommunicationChannel = createEntity(em);
    }

    @Test
    @Transactional
    void createPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeCreate = preferredCommunicationChannelRepository.findAll().size();
        // Create the PreferredCommunicationChannel
        restPreferredCommunicationChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isCreated());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredCommunicationChannel testPreferredCommunicationChannel = preferredCommunicationChannelList.get(
            preferredCommunicationChannelList.size() - 1
        );
        assertThat(testPreferredCommunicationChannel.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPreferredCommunicationChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPreferredCommunicationChannel.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createPreferredCommunicationChannelWithExistingId() throws Exception {
        // Create the PreferredCommunicationChannel with an existing ID
        preferredCommunicationChannel.setId(1L);

        int databaseSizeBeforeCreate = preferredCommunicationChannelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredCommunicationChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = preferredCommunicationChannelRepository.findAll().size();
        // set the field null
        preferredCommunicationChannel.setValue(null);

        // Create the PreferredCommunicationChannel, which fails.

        restPreferredCommunicationChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = preferredCommunicationChannelRepository.findAll().size();
        // set the field null
        preferredCommunicationChannel.setLanguage(null);

        // Create the PreferredCommunicationChannel, which fails.

        restPreferredCommunicationChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPreferredCommunicationChannels() throws Exception {
        // Initialize the database
        preferredCommunicationChannelRepository.saveAndFlush(preferredCommunicationChannel);

        // Get all the preferredCommunicationChannelList
        restPreferredCommunicationChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredCommunicationChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getPreferredCommunicationChannel() throws Exception {
        // Initialize the database
        preferredCommunicationChannelRepository.saveAndFlush(preferredCommunicationChannel);

        // Get the preferredCommunicationChannel
        restPreferredCommunicationChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, preferredCommunicationChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferredCommunicationChannel.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPreferredCommunicationChannel() throws Exception {
        // Get the preferredCommunicationChannel
        restPreferredCommunicationChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPreferredCommunicationChannel() throws Exception {
        // Initialize the database
        preferredCommunicationChannelRepository.saveAndFlush(preferredCommunicationChannel);

        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();

        // Update the preferredCommunicationChannel
        PreferredCommunicationChannel updatedPreferredCommunicationChannel = preferredCommunicationChannelRepository
            .findById(preferredCommunicationChannel.getId())
            .get();
        // Disconnect from session so that the updates on updatedPreferredCommunicationChannel are not directly saved in db
        em.detach(updatedPreferredCommunicationChannel);
        updatedPreferredCommunicationChannel.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferredCommunicationChannel))
            )
            .andExpect(status().isOk());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
        PreferredCommunicationChannel testPreferredCommunicationChannel = preferredCommunicationChannelList.get(
            preferredCommunicationChannelList.size() - 1
        );
        assertThat(testPreferredCommunicationChannel.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPreferredCommunicationChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPreferredCommunicationChannel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();
        preferredCommunicationChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();
        preferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();
        preferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePreferredCommunicationChannelWithPatch() throws Exception {
        // Initialize the database
        preferredCommunicationChannelRepository.saveAndFlush(preferredCommunicationChannel);

        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();

        // Update the preferredCommunicationChannel using partial update
        PreferredCommunicationChannel partialUpdatedPreferredCommunicationChannel = new PreferredCommunicationChannel();
        partialUpdatedPreferredCommunicationChannel.setId(preferredCommunicationChannel.getId());

        partialUpdatedPreferredCommunicationChannel.value(UPDATED_VALUE);

        restPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredCommunicationChannel))
            )
            .andExpect(status().isOk());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
        PreferredCommunicationChannel testPreferredCommunicationChannel = preferredCommunicationChannelList.get(
            preferredCommunicationChannelList.size() - 1
        );
        assertThat(testPreferredCommunicationChannel.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPreferredCommunicationChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPreferredCommunicationChannel.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdatePreferredCommunicationChannelWithPatch() throws Exception {
        // Initialize the database
        preferredCommunicationChannelRepository.saveAndFlush(preferredCommunicationChannel);

        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();

        // Update the preferredCommunicationChannel using partial update
        PreferredCommunicationChannel partialUpdatedPreferredCommunicationChannel = new PreferredCommunicationChannel();
        partialUpdatedPreferredCommunicationChannel.setId(preferredCommunicationChannel.getId());

        partialUpdatedPreferredCommunicationChannel.value(UPDATED_VALUE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredCommunicationChannel))
            )
            .andExpect(status().isOk());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
        PreferredCommunicationChannel testPreferredCommunicationChannel = preferredCommunicationChannelList.get(
            preferredCommunicationChannelList.size() - 1
        );
        assertThat(testPreferredCommunicationChannel.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPreferredCommunicationChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPreferredCommunicationChannel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();
        preferredCommunicationChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();
        preferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = preferredCommunicationChannelRepository.findAll().size();
        preferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredCommunicationChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredCommunicationChannel in the database
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePreferredCommunicationChannel() throws Exception {
        // Initialize the database
        preferredCommunicationChannelRepository.saveAndFlush(preferredCommunicationChannel);

        int databaseSizeBeforeDelete = preferredCommunicationChannelRepository.findAll().size();

        // Delete the preferredCommunicationChannel
        restPreferredCommunicationChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferredCommunicationChannel.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreferredCommunicationChannel> preferredCommunicationChannelList = preferredCommunicationChannelRepository.findAll();
        assertThat(preferredCommunicationChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
