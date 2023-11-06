package io.valius.app.service;

import io.valius.app.domain.CompetitivePosition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CompetitivePosition}.
 */
public interface CompetitivePositionService {
    /**
     * Save a competitivePosition.
     *
     * @param competitivePosition the entity to save.
     * @return the persisted entity.
     */
    CompetitivePosition save(CompetitivePosition competitivePosition);

    /**
     * Updates a competitivePosition.
     *
     * @param competitivePosition the entity to update.
     * @return the persisted entity.
     */
    CompetitivePosition update(CompetitivePosition competitivePosition);

    /**
     * Partially updates a competitivePosition.
     *
     * @param competitivePosition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetitivePosition> partialUpdate(CompetitivePosition competitivePosition);

    /**
     * Get all the competitivePositions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitivePosition> findAll(Pageable pageable);

    /**
     * Get the "id" competitivePosition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetitivePosition> findOne(Long id);

    /**
     * Delete the "id" competitivePosition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
