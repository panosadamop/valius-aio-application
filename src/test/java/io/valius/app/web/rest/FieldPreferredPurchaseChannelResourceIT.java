package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.FieldPreferredPurchaseChannel;
import io.valius.app.repository.FieldPreferredPurchaseChannelRepository;
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

/**
 * Integration tests for the {@link FieldPreferredPurchaseChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldPreferredPurchaseChannelResourceIT {

    private static final String DEFAULT_PREFERRED_PURCHASE_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_PURCHASE_CHANNEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-preferred-purchase-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldPreferredPurchaseChannelRepository fieldPreferredPurchaseChannelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldPreferredPurchaseChannelMockMvc;

    private FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldPreferredPurchaseChannel createEntity(EntityManager em) {
        FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel = new FieldPreferredPurchaseChannel()
            .preferredPurchaseChannel(DEFAULT_PREFERRED_PURCHASE_CHANNEL);
        return fieldPreferredPurchaseChannel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldPreferredPurchaseChannel createUpdatedEntity(EntityManager em) {
        FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel = new FieldPreferredPurchaseChannel()
            .preferredPurchaseChannel(UPDATED_PREFERRED_PURCHASE_CHANNEL);
        return fieldPreferredPurchaseChannel;
    }

    @BeforeEach
    public void initTest() {
        fieldPreferredPurchaseChannel = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeCreate = fieldPreferredPurchaseChannelRepository.findAll().size();
        // Create the FieldPreferredPurchaseChannel
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isCreated());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeCreate + 1);
        FieldPreferredPurchaseChannel testFieldPreferredPurchaseChannel = fieldPreferredPurchaseChannelList.get(
            fieldPreferredPurchaseChannelList.size() - 1
        );
        assertThat(testFieldPreferredPurchaseChannel.getPreferredPurchaseChannel()).isEqualTo(DEFAULT_PREFERRED_PURCHASE_CHANNEL);
    }

    @Test
    @Transactional
    void createFieldPreferredPurchaseChannelWithExistingId() throws Exception {
        // Create the FieldPreferredPurchaseChannel with an existing ID
        fieldPreferredPurchaseChannel.setId(1L);

        int databaseSizeBeforeCreate = fieldPreferredPurchaseChannelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldPreferredPurchaseChannels() throws Exception {
        // Initialize the database
        fieldPreferredPurchaseChannelRepository.saveAndFlush(fieldPreferredPurchaseChannel);

        // Get all the fieldPreferredPurchaseChannelList
        restFieldPreferredPurchaseChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldPreferredPurchaseChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferredPurchaseChannel").value(hasItem(DEFAULT_PREFERRED_PURCHASE_CHANNEL)));
    }

    @Test
    @Transactional
    void getFieldPreferredPurchaseChannel() throws Exception {
        // Initialize the database
        fieldPreferredPurchaseChannelRepository.saveAndFlush(fieldPreferredPurchaseChannel);

        // Get the fieldPreferredPurchaseChannel
        restFieldPreferredPurchaseChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldPreferredPurchaseChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldPreferredPurchaseChannel.getId().intValue()))
            .andExpect(jsonPath("$.preferredPurchaseChannel").value(DEFAULT_PREFERRED_PURCHASE_CHANNEL));
    }

    @Test
    @Transactional
    void getNonExistingFieldPreferredPurchaseChannel() throws Exception {
        // Get the fieldPreferredPurchaseChannel
        restFieldPreferredPurchaseChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldPreferredPurchaseChannel() throws Exception {
        // Initialize the database
        fieldPreferredPurchaseChannelRepository.saveAndFlush(fieldPreferredPurchaseChannel);

        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();

        // Update the fieldPreferredPurchaseChannel
        FieldPreferredPurchaseChannel updatedFieldPreferredPurchaseChannel = fieldPreferredPurchaseChannelRepository
            .findById(fieldPreferredPurchaseChannel.getId())
            .get();
        // Disconnect from session so that the updates on updatedFieldPreferredPurchaseChannel are not directly saved in db
        em.detach(updatedFieldPreferredPurchaseChannel);
        updatedFieldPreferredPurchaseChannel.preferredPurchaseChannel(UPDATED_PREFERRED_PURCHASE_CHANNEL);

        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFieldPreferredPurchaseChannel))
            )
            .andExpect(status().isOk());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
        FieldPreferredPurchaseChannel testFieldPreferredPurchaseChannel = fieldPreferredPurchaseChannelList.get(
            fieldPreferredPurchaseChannelList.size() - 1
        );
        assertThat(testFieldPreferredPurchaseChannel.getPreferredPurchaseChannel()).isEqualTo(UPDATED_PREFERRED_PURCHASE_CHANNEL);
    }

    @Test
    @Transactional
    void putNonExistingFieldPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();
        fieldPreferredPurchaseChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();
        fieldPreferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();
        fieldPreferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldPreferredPurchaseChannelWithPatch() throws Exception {
        // Initialize the database
        fieldPreferredPurchaseChannelRepository.saveAndFlush(fieldPreferredPurchaseChannel);

        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();

        // Update the fieldPreferredPurchaseChannel using partial update
        FieldPreferredPurchaseChannel partialUpdatedFieldPreferredPurchaseChannel = new FieldPreferredPurchaseChannel();
        partialUpdatedFieldPreferredPurchaseChannel.setId(fieldPreferredPurchaseChannel.getId());

        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldPreferredPurchaseChannel))
            )
            .andExpect(status().isOk());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
        FieldPreferredPurchaseChannel testFieldPreferredPurchaseChannel = fieldPreferredPurchaseChannelList.get(
            fieldPreferredPurchaseChannelList.size() - 1
        );
        assertThat(testFieldPreferredPurchaseChannel.getPreferredPurchaseChannel()).isEqualTo(DEFAULT_PREFERRED_PURCHASE_CHANNEL);
    }

    @Test
    @Transactional
    void fullUpdateFieldPreferredPurchaseChannelWithPatch() throws Exception {
        // Initialize the database
        fieldPreferredPurchaseChannelRepository.saveAndFlush(fieldPreferredPurchaseChannel);

        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();

        // Update the fieldPreferredPurchaseChannel using partial update
        FieldPreferredPurchaseChannel partialUpdatedFieldPreferredPurchaseChannel = new FieldPreferredPurchaseChannel();
        partialUpdatedFieldPreferredPurchaseChannel.setId(fieldPreferredPurchaseChannel.getId());

        partialUpdatedFieldPreferredPurchaseChannel.preferredPurchaseChannel(UPDATED_PREFERRED_PURCHASE_CHANNEL);

        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldPreferredPurchaseChannel))
            )
            .andExpect(status().isOk());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
        FieldPreferredPurchaseChannel testFieldPreferredPurchaseChannel = fieldPreferredPurchaseChannelList.get(
            fieldPreferredPurchaseChannelList.size() - 1
        );
        assertThat(testFieldPreferredPurchaseChannel.getPreferredPurchaseChannel()).isEqualTo(UPDATED_PREFERRED_PURCHASE_CHANNEL);
    }

    @Test
    @Transactional
    void patchNonExistingFieldPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();
        fieldPreferredPurchaseChannel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldPreferredPurchaseChannel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();
        fieldPreferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldPreferredPurchaseChannel() throws Exception {
        int databaseSizeBeforeUpdate = fieldPreferredPurchaseChannelRepository.findAll().size();
        fieldPreferredPurchaseChannel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPreferredPurchaseChannelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPreferredPurchaseChannel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldPreferredPurchaseChannel in the database
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldPreferredPurchaseChannel() throws Exception {
        // Initialize the database
        fieldPreferredPurchaseChannelRepository.saveAndFlush(fieldPreferredPurchaseChannel);

        int databaseSizeBeforeDelete = fieldPreferredPurchaseChannelRepository.findAll().size();

        // Delete the fieldPreferredPurchaseChannel
        restFieldPreferredPurchaseChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldPreferredPurchaseChannel.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannelList = fieldPreferredPurchaseChannelRepository.findAll();
        assertThat(fieldPreferredPurchaseChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
