package io.valius.app.service;

import io.valius.app.domain.CompetitorMaturityPhase;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CompetitorMaturityPhase}.
 */
public interface CompetitorMaturityPhaseService {
    /**
     * Save a competitorMaturityPhase.
     *
     * @param competitorMaturityPhase the entity to save.
     * @return the persisted entity.
     */
    CompetitorMaturityPhase save(CompetitorMaturityPhase competitorMaturityPhase);

    /**
     * Updates a competitorMaturityPhase.
     *
     * @param competitorMaturityPhase the entity to update.
     * @return the persisted entity.
     */
    CompetitorMaturityPhase update(CompetitorMaturityPhase competitorMaturityPhase);

    /**
     * Partially updates a competitorMaturityPhase.
     *
     * @param competitorMaturityPhase the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetitorMaturityPhase> partialUpdate(CompetitorMaturityPhase competitorMaturityPhase);

    /**
     * Get all the competitorMaturityPhases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitorMaturityPhase> findAll(Pageable pageable);

    /**
     * Get the "id" competitorMaturityPhase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetitorMaturityPhase> findOne(Long id);

    /**
     * Delete the "id" competitorMaturityPhase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
