package io.valius.app.service;

import io.valius.app.domain.ConfidenceLevel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ConfidenceLevel}.
 */
public interface ConfidenceLevelService {
    /**
     * Save a confidenceLevel.
     *
     * @param confidenceLevel the entity to save.
     * @return the persisted entity.
     */
    ConfidenceLevel save(ConfidenceLevel confidenceLevel);

    /**
     * Updates a confidenceLevel.
     *
     * @param confidenceLevel the entity to update.
     * @return the persisted entity.
     */
    ConfidenceLevel update(ConfidenceLevel confidenceLevel);

    /**
     * Partially updates a confidenceLevel.
     *
     * @param confidenceLevel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConfidenceLevel> partialUpdate(ConfidenceLevel confidenceLevel);

    /**
     * Get all the confidenceLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfidenceLevel> findAll(Pageable pageable);

    /**
     * Get the "id" confidenceLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfidenceLevel> findOne(Long id);

    /**
     * Delete the "id" confidenceLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
