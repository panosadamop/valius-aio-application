package io.valius.app.service;

import io.valius.app.domain.FieldPreferredCommunicationChannel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldPreferredCommunicationChannel}.
 */
public interface FieldPreferredCommunicationChannelService {
    /**
     * Save a fieldPreferredCommunicationChannel.
     *
     * @param fieldPreferredCommunicationChannel the entity to save.
     * @return the persisted entity.
     */
    FieldPreferredCommunicationChannel save(FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel);

    /**
     * Updates a fieldPreferredCommunicationChannel.
     *
     * @param fieldPreferredCommunicationChannel the entity to update.
     * @return the persisted entity.
     */
    FieldPreferredCommunicationChannel update(FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel);

    /**
     * Partially updates a fieldPreferredCommunicationChannel.
     *
     * @param fieldPreferredCommunicationChannel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldPreferredCommunicationChannel> partialUpdate(FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel);

    /**
     * Get all the fieldPreferredCommunicationChannels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldPreferredCommunicationChannel> findAll(Pageable pageable);

    /**
     * Get the "id" fieldPreferredCommunicationChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldPreferredCommunicationChannel> findOne(Long id);

    /**
     * Delete the "id" fieldPreferredCommunicationChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
