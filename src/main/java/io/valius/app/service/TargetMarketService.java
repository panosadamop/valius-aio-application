package io.valius.app.service;

import io.valius.app.domain.TargetMarket;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TargetMarket}.
 */
public interface TargetMarketService {
    /**
     * Save a targetMarket.
     *
     * @param targetMarket the entity to save.
     * @return the persisted entity.
     */
    TargetMarket save(TargetMarket targetMarket);

    /**
     * Updates a targetMarket.
     *
     * @param targetMarket the entity to update.
     * @return the persisted entity.
     */
    TargetMarket update(TargetMarket targetMarket);

    /**
     * Partially updates a targetMarket.
     *
     * @param targetMarket the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TargetMarket> partialUpdate(TargetMarket targetMarket);

    /**
     * Get all the targetMarkets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TargetMarket> findAll(Pageable pageable);

    /**
     * Get the "id" targetMarket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TargetMarket> findOne(Long id);

    /**
     * Delete the "id" targetMarket.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
