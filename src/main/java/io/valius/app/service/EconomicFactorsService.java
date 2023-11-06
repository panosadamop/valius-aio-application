package io.valius.app.service;

import io.valius.app.domain.EconomicFactors;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EconomicFactors}.
 */
public interface EconomicFactorsService {
    /**
     * Save a economicFactors.
     *
     * @param economicFactors the entity to save.
     * @return the persisted entity.
     */
    EconomicFactors save(EconomicFactors economicFactors);

    /**
     * Updates a economicFactors.
     *
     * @param economicFactors the entity to update.
     * @return the persisted entity.
     */
    EconomicFactors update(EconomicFactors economicFactors);

    /**
     * Partially updates a economicFactors.
     *
     * @param economicFactors the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EconomicFactors> partialUpdate(EconomicFactors economicFactors);

    /**
     * Get all the economicFactors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EconomicFactors> findAll(Pageable pageable);

    /**
     * Get the "id" economicFactors.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EconomicFactors> findOne(Long id);

    /**
     * Delete the "id" economicFactors.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
