package io.valius.app.service;

import io.valius.app.domain.MarketSegmentationTypeB2c;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketSegmentationTypeB2c}.
 */
public interface MarketSegmentationTypeB2cService {
    /**
     * Save a marketSegmentationTypeB2c.
     *
     * @param marketSegmentationTypeB2c the entity to save.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2c save(MarketSegmentationTypeB2c marketSegmentationTypeB2c);

    /**
     * Updates a marketSegmentationTypeB2c.
     *
     * @param marketSegmentationTypeB2c the entity to update.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2c update(MarketSegmentationTypeB2c marketSegmentationTypeB2c);

    /**
     * Partially updates a marketSegmentationTypeB2c.
     *
     * @param marketSegmentationTypeB2c the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketSegmentationTypeB2c> partialUpdate(MarketSegmentationTypeB2c marketSegmentationTypeB2c);

    /**
     * Get all the marketSegmentationTypeB2cs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketSegmentationTypeB2c> findAll(Pageable pageable);

    /**
     * Get the "id" marketSegmentationTypeB2c.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketSegmentationTypeB2c> findOne(Long id);

    /**
     * Delete the "id" marketSegmentationTypeB2c.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
