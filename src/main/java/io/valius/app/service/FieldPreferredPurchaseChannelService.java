package io.valius.app.service;

import io.valius.app.domain.FieldPreferredPurchaseChannel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldPreferredPurchaseChannel}.
 */
public interface FieldPreferredPurchaseChannelService {
    /**
     * Save a fieldPreferredPurchaseChannel.
     *
     * @param fieldPreferredPurchaseChannel the entity to save.
     * @return the persisted entity.
     */
    FieldPreferredPurchaseChannel save(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel);

    /**
     * Updates a fieldPreferredPurchaseChannel.
     *
     * @param fieldPreferredPurchaseChannel the entity to update.
     * @return the persisted entity.
     */
    FieldPreferredPurchaseChannel update(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel);

    /**
     * Partially updates a fieldPreferredPurchaseChannel.
     *
     * @param fieldPreferredPurchaseChannel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldPreferredPurchaseChannel> partialUpdate(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel);

    /**
     * Get all the fieldPreferredPurchaseChannels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldPreferredPurchaseChannel> findAll(Pageable pageable);

    /**
     * Get the "id" fieldPreferredPurchaseChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldPreferredPurchaseChannel> findOne(Long id);

    /**
     * Delete the "id" fieldPreferredPurchaseChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
