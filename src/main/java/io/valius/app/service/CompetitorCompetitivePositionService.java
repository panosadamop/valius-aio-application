package io.valius.app.service;

import io.valius.app.domain.CompetitorCompetitivePosition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CompetitorCompetitivePosition}.
 */
public interface CompetitorCompetitivePositionService {
    /**
     * Save a competitorCompetitivePosition.
     *
     * @param competitorCompetitivePosition the entity to save.
     * @return the persisted entity.
     */
    CompetitorCompetitivePosition save(CompetitorCompetitivePosition competitorCompetitivePosition);

    /**
     * Updates a competitorCompetitivePosition.
     *
     * @param competitorCompetitivePosition the entity to update.
     * @return the persisted entity.
     */
    CompetitorCompetitivePosition update(CompetitorCompetitivePosition competitorCompetitivePosition);

    /**
     * Partially updates a competitorCompetitivePosition.
     *
     * @param competitorCompetitivePosition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetitorCompetitivePosition> partialUpdate(CompetitorCompetitivePosition competitorCompetitivePosition);

    /**
     * Get all the competitorCompetitivePositions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitorCompetitivePosition> findAll(Pageable pageable);

    /**
     * Get the "id" competitorCompetitivePosition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetitorCompetitivePosition> findOne(Long id);

    /**
     * Delete the "id" competitorCompetitivePosition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
