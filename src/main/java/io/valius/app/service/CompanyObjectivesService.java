package io.valius.app.service;

import io.valius.app.domain.CompanyObjectives;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CompanyObjectives}.
 */
public interface CompanyObjectivesService {
    /**
     * Save a companyObjectives.
     *
     * @param companyObjectives the entity to save.
     * @return the persisted entity.
     */
    CompanyObjectives save(CompanyObjectives companyObjectives);

    /**
     * Updates a companyObjectives.
     *
     * @param companyObjectives the entity to update.
     * @return the persisted entity.
     */
    CompanyObjectives update(CompanyObjectives companyObjectives);

    /**
     * Partially updates a companyObjectives.
     *
     * @param companyObjectives the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyObjectives> partialUpdate(CompanyObjectives companyObjectives);

    /**
     * Get all the companyObjectives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyObjectives> findAll(Pageable pageable);

    /**
     * Get the "id" companyObjectives.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyObjectives> findOne(Long id);

    /**
     * Delete the "id" companyObjectives.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
