package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldPreferredCommunicationChannel;
import io.valius.app.repository.FieldPreferredCommunicationChannelRepository;
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

/**
 * Integration tests for the {@link FieldPreferredCommunicationChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldPreferredCommunicationChannelResourceIT {

    private static final String DEFAULT_PREFERRED_COMMUNICATION_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_COMMUNICATION_CHANNEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-preferred-communication-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldPreferredCommunicationChannelRepository fieldPreferredCommunicationChannelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldPreferredCommunicationChannelMockMvc;

    private FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldPreferredCommunicationChannel createEntity(EntityManager em) {
        FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel = new FieldPreferredCommunicationChannel()
            .preferredCommunicationChannel(DEFAULT_PREFERRED_COMMUNICATION_CHANNEL);
        return fieldPreferredCommunicationChannel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldPreferredCommunicationChannel createUpdatedEntity(EntityManager em) {
        FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel = new FieldPreferredCommunicationChannel()
            .preferredCommunicationChannel(UPDATED_PREFERRED_COMMUNICATION_CHANNEL);
        return fieldPreferredCommunicationChannel;
    }

    @BeforeEach
    public void initTest() {
        fieldPreferredCommunicationChannel = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeCreate = fieldPreferredCommunicationChannelRepository.findAll().size();
        // Create the FieldPreferredCommunicationChannel
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isCreated());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeCreate + 1);
        FieldPreferredCommunicationChannel testFieldPreferredCommunicationChannel = fieldPreferredCommunicationChannelList.get(
            fieldPreferredCommunicationChannelList.size() - 1
        );
        assertThat(testFieldPreferredCommunicationChannel.getPreferredCommunicationChannel())
            .isEqualTo(DEFAULT_PREFERRED_COMMUNICATION_CHANNEL);
    }

    @Test
    @Transactional
    void createFieldPreferredCommunicationChannelWithExistingId() throws Exception {
        // Create the FieldPreferredCommunicationChannel with an existing ID
        fieldPreferredCommunicationChannel.setId(1L);

        int databaseSizeBeforeCreate = fieldPreferredCommunicationChannelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldPreferredCommunicationChannels() throws Exception {
        // Initialize the database
        fieldPreferredCommunicationChannelRepository.saveAndFlush(fieldPreferredCommunicationChannel);

        // Get all the fieldPreferredCommunicationChannelList
        restFieldPreferredCommunicationChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldPreferredCommunicationChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferredCommunicationChannel").value(hasItem(DEFAULT_PREFERRED_COMMUNICATION_CHANNEL)));
    }

    @Test
    @Transactional
    void getFieldPreferredCommunicationChannel() throws Exception {
        // Initialize the database
        fieldPreferredCommunicationChannelRepository.saveAndFlush(fieldPreferredCommunicationChannel);

        // Get the fieldPreferredCommunicationChannel
        restFieldPreferredCommunicationChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldPreferredCommunicationChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldPreferredCommunicationChannel.getId().intValue()))
            .andExpect(jsonPath("$.preferredCommunicationChannel").value(DEFAULT_PREFERRED_COMMUNICATION_CHANNEL));
    }

    @Test
    @Transactional
    void getNonExistingFieldPreferredCommunicationChannel() throws Exception {
        // Get the fieldPreferredCommunicationChannel
        restFieldPreferredCommunicationChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldPreferredCommunicationChannel() throws Exception {
        // Initialize the database
        fieldPreferredCommunicationChannelRepository.saveAndFlush(fieldPreferredCommunicationChannel);

        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();

        // Update the fieldPreferredCommunicationChannel
        FieldPreferredCommunicationChannel updatedFieldPreferredCommunicationChannel = fieldPreferredCommunicationChannelRepository
            .findById(fieldPreferredCommunicationChannel.getId())
            .get();
        // Disconnect from session so that the updates on updatedFieldPreferredCommunicationChannel are not directly saved in db
        em.detach(updatedFieldPreferredCommunicationChannel);
        updatedFieldPreferredCommunicationChannel.preferredCommunicationChannel(UPDATED_PREFERRED_COMMUNICATION_CHANNEL);

        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldPreferredCommunicationChannel))
            )
            .andExpect(status().isOk());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
        FieldPreferredCommunicationChannel testFieldPreferredCommunicationChannel = fieldPreferredCommunicationChannelList.get(
            fieldPreferredCommunicationChannelList.size() - 1
        );
        assertThat(testFieldPreferredCommunicationChannel.getPreferredCommunicationChannel())
            .isEqualTo(UPDATED_PREFERRED_COMMUNICATION_CHANNEL);
    }

    @Test
    @Transactional
    void putNonExistingFieldPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();
        fieldPreferredCommunicationChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();
        fieldPreferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();
        fieldPreferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldPreferredCommunicationChannelWithPatch() throws Exception {
        // Initialize the database
        fieldPreferredCommunicationChannelRepository.saveAndFlush(fieldPreferredCommunicationChannel);

        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();

        // Update the fieldPreferredCommunicationChannel using partial update
        FieldPreferredCommunicationChannel partialUpdatedFieldPreferredCommunicationChannel = new FieldPreferredCommunicationChannel();
        partialUpdatedFieldPreferredCommunicationChannel.setId(fieldPreferredCommunicationChannel.getId());

        partialUpdatedFieldPreferredCommunicationChannel.preferredCommunicationChannel(UPDATED_PREFERRED_COMMUNICATION_CHANNEL);

        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldPreferredCommunicationChannel))
            )
            .andExpect(status().isOk());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
        FieldPreferredCommunicationChannel testFieldPreferredCommunicationChannel = fieldPreferredCommunicationChannelList.get(
            fieldPreferredCommunicationChannelList.size() - 1
        );
        assertThat(testFieldPreferredCommunicationChannel.getPreferredCommunicationChannel())
            .isEqualTo(UPDATED_PREFERRED_COMMUNICATION_CHANNEL);
    }

    @Test
    @Transactional
    void fullUpdateFieldPreferredCommunicationChannelWithPatch() throws Exception {
        // Initialize the database
        fieldPreferredCommunicationChannelRepository.saveAndFlush(fieldPreferredCommunicationChannel);

        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();

        // Update the fieldPreferredCommunicationChannel using partial update
        FieldPreferredCommunicationChannel partialUpdatedFieldPreferredCommunicationChannel = new FieldPreferredCommunicationChannel();
        partialUpdatedFieldPreferredCommunicationChannel.setId(fieldPreferredCommunicationChannel.getId());

        partialUpdatedFieldPreferredCommunicationChannel.preferredCommunicationChannel(UPDATED_PREFERRED_COMMUNICATION_CHANNEL);

        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldPreferredCommunicationChannel))
            )
            .andExpect(status().isOk());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
        FieldPreferredCommunicationChannel testFieldPreferredCommunicationChannel = fieldPreferredCommunicationChannelList.get(
            fieldPreferredCommunicationChannelList.size() - 1
        );
        assertThat(testFieldPreferredCommunicationChannel.getPreferredCommunicationChannel())
            .isEqualTo(UPDATED_PREFERRED_COMMUNICATION_CHANNEL);
    }

    @Test
    @Transactional
    void patchNonExistingFieldPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();
        fieldPreferredCommunicationChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldPreferredCommunicationChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();
        fieldPreferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldPreferredCommunicationChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredCommunicationChannelRepository.findAll().size();
        fieldPreferredCommunicationChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredCommunicationChannelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredCommunicationChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldPreferredCommunicationChannel in the database
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldPreferredCommunicationChannel() throws Exception {
        // Initialize the database
        fieldPreferredCommunicationChannelRepository.saveAndFlush(fieldPreferredCommunicationChannel);

        int databaseSizeBeforeDelete = fieldPreferredCommunicationChannelRepository.findAll().size();

        // Delete the fieldPreferredCommunicationChannel
        restFieldPreferredCommunicationChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldPreferredCommunicationChannel.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannelList =
            fieldPreferredCommunicationChannelRepository.findAll();
        assertThat(fieldPreferredCommunicationChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
