package io.valius.app.service;

import io.valius.app.domain.PreferredCommunicationChannel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PreferredCommunicationChannel}.
 */
public interface PreferredCommunicationChannelService {
    /**
     * Save a preferredCommunicationChannel.
     *
     * @param preferredCommunicationChannel the entity to save.
     * @return the persisted entity.
     */
    PreferredCommunicationChannel save(PreferredCommunicationChannel preferredCommunicationChannel);

    /**
     * Updates a preferredCommunicationChannel.
     *
     * @param preferredCommunicationChannel the entity to update.
     * @return the persisted entity.
     */
    PreferredCommunicationChannel update(PreferredCommunicationChannel preferredCommunicationChannel);

    /**
     * Partially updates a preferredCommunicationChannel.
     *
     * @param preferredCommunicationChannel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferredCommunicationChannel> partialUpdate(PreferredCommunicationChannel preferredCommunicationChannel);

    /**
     * Get all the preferredCommunicationChannels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PreferredCommunicationChannel> findAll(Pageable pageable);

    /**
     * Get the "id" preferredCommunicationChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferredCommunicationChannel> findOne(Long id);

    /**
     * Delete the "id" preferredCommunicationChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
