package io.valius.app.service;

import io.valius.app.domain.CompetitiveFactors;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CompetitiveFactors}.
 */
public interface CompetitiveFactorsService {
    /**
     * Save a competitiveFactors.
     *
     * @param competitiveFactors the entity to save.
     * @return the persisted entity.
     */
    CompetitiveFactors save(CompetitiveFactors competitiveFactors);

    /**
     * Updates a competitiveFactors.
     *
     * @param competitiveFactors the entity to update.
     * @return the persisted entity.
     */
    CompetitiveFactors update(CompetitiveFactors competitiveFactors);

    /**
     * Partially updates a competitiveFactors.
     *
     * @param competitiveFactors the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetitiveFactors> partialUpdate(CompetitiveFactors competitiveFactors);

    /**
     * Get all the competitiveFactors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitiveFactors> findAll(Pageable pageable);

    /**
     * Get the "id" competitiveFactors.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetitiveFactors> findOne(Long id);

    /**
     * Delete the "id" competitiveFactors.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
