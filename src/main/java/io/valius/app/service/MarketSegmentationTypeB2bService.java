package io.valius.app.service;

import io.valius.app.domain.MarketSegmentationTypeB2b;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketSegmentationTypeB2b}.
 */
public interface MarketSegmentationTypeB2bService {
    /**
     * Save a marketSegmentationTypeB2b.
     *
     * @param marketSegmentationTypeB2b the entity to save.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2b save(MarketSegmentationTypeB2b marketSegmentationTypeB2b);

    /**
     * Updates a marketSegmentationTypeB2b.
     *
     * @param marketSegmentationTypeB2b the entity to update.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2b update(MarketSegmentationTypeB2b marketSegmentationTypeB2b);

    /**
     * Partially updates a marketSegmentationTypeB2b.
     *
     * @param marketSegmentationTypeB2b the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketSegmentationTypeB2b> partialUpdate(MarketSegmentationTypeB2b marketSegmentationTypeB2b);

    /**
     * Get all the marketSegmentationTypeB2bs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketSegmentationTypeB2b> findAll(Pageable pageable);

    /**
     * Get the "id" marketSegmentationTypeB2b.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketSegmentationTypeB2b> findOne(Long id);

    /**
     * Delete the "id" marketSegmentationTypeB2b.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
