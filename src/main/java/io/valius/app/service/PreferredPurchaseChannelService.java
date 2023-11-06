package io.valius.app.service;

import io.valius.app.domain.PreferredPurchaseChannel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PreferredPurchaseChannel}.
 */
public interface PreferredPurchaseChannelService {
    /**
     * Save a preferredPurchaseChannel.
     *
     * @param preferredPurchaseChannel the entity to save.
     * @return the persisted entity.
     */
    PreferredPurchaseChannel save(PreferredPurchaseChannel preferredPurchaseChannel);

    /**
     * Updates a preferredPurchaseChannel.
     *
     * @param preferredPurchaseChannel the entity to update.
     * @return the persisted entity.
     */
    PreferredPurchaseChannel update(PreferredPurchaseChannel preferredPurchaseChannel);

    /**
     * Partially updates a preferredPurchaseChannel.
     *
     * @param preferredPurchaseChannel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferredPurchaseChannel> partialUpdate(PreferredPurchaseChannel preferredPurchaseChannel);

    /**
     * Get all the preferredPurchaseChannels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PreferredPurchaseChannel> findAll(Pageable pageable);

    /**
     * Get the "id" preferredPurchaseChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferredPurchaseChannel> findOne(Long id);

    /**
     * Delete the "id" preferredPurchaseChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
