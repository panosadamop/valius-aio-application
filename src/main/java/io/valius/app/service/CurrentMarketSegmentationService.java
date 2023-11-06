package io.valius.app.service;

import io.valius.app.domain.CurrentMarketSegmentation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CurrentMarketSegmentation}.
 */
public interface CurrentMarketSegmentationService {
    /**
     * Save a currentMarketSegmentation.
     *
     * @param currentMarketSegmentation the entity to save.
     * @return the persisted entity.
     */
    CurrentMarketSegmentation save(CurrentMarketSegmentation currentMarketSegmentation);

    /**
     * Updates a currentMarketSegmentation.
     *
     * @param currentMarketSegmentation the entity to update.
     * @return the persisted entity.
     */
    CurrentMarketSegmentation update(CurrentMarketSegmentation currentMarketSegmentation);

    /**
     * Partially updates a currentMarketSegmentation.
     *
     * @param currentMarketSegmentation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CurrentMarketSegmentation> partialUpdate(CurrentMarketSegmentation currentMarketSegmentation);

    /**
     * Get all the currentMarketSegmentations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurrentMarketSegmentation> findAll(Pageable pageable);

    /**
     * Get the "id" currentMarketSegmentation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurrentMarketSegmentation> findOne(Long id);

    /**
     * Delete the "id" currentMarketSegmentation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
