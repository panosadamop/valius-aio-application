package io.valius.app.service;

import io.valius.app.domain.MaturityPhase;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MaturityPhase}.
 */
public interface MaturityPhaseService {
    /**
     * Save a maturityPhase.
     *
     * @param maturityPhase the entity to save.
     * @return the persisted entity.
     */
    MaturityPhase save(MaturityPhase maturityPhase);

    /**
     * Updates a maturityPhase.
     *
     * @param maturityPhase the entity to update.
     * @return the persisted entity.
     */
    MaturityPhase update(MaturityPhase maturityPhase);

    /**
     * Partially updates a maturityPhase.
     *
     * @param maturityPhase the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MaturityPhase> partialUpdate(MaturityPhase maturityPhase);

    /**
     * Get all the maturityPhases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MaturityPhase> findAll(Pageable pageable);

    /**
     * Get the "id" maturityPhase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaturityPhase> findOne(Long id);

    /**
     * Delete the "id" maturityPhase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
