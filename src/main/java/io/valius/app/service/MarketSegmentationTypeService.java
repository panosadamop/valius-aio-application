package io.valius.app.service;

import io.valius.app.domain.MarketSegmentationType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketSegmentationType}.
 */
public interface MarketSegmentationTypeService {
    /**
     * Save a marketSegmentationType.
     *
     * @param marketSegmentationType the entity to save.
     * @return the persisted entity.
     */
    MarketSegmentationType save(MarketSegmentationType marketSegmentationType);

    /**
     * Updates a marketSegmentationType.
     *
     * @param marketSegmentationType the entity to update.
     * @return the persisted entity.
     */
    MarketSegmentationType update(MarketSegmentationType marketSegmentationType);

    /**
     * Partially updates a marketSegmentationType.
     *
     * @param marketSegmentationType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketSegmentationType> partialUpdate(MarketSegmentationType marketSegmentationType);

    /**
     * Get all the marketSegmentationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketSegmentationType> findAll(Pageable pageable);

    /**
     * Get the "id" marketSegmentationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketSegmentationType> findOne(Long id);

    /**
     * Delete the "id" marketSegmentationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
