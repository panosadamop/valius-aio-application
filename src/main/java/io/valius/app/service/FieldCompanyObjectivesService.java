package io.valius.app.service;

import io.valius.app.domain.FieldCompanyObjectives;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldCompanyObjectives}.
 */
public interface FieldCompanyObjectivesService {
    /**
     * Save a fieldCompanyObjectives.
     *
     * @param fieldCompanyObjectives the entity to save.
     * @return the persisted entity.
     */
    FieldCompanyObjectives save(FieldCompanyObjectives fieldCompanyObjectives);

    /**
     * Updates a fieldCompanyObjectives.
     *
     * @param fieldCompanyObjectives the entity to update.
     * @return the persisted entity.
     */
    FieldCompanyObjectives update(FieldCompanyObjectives fieldCompanyObjectives);

    /**
     * Partially updates a fieldCompanyObjectives.
     *
     * @param fieldCompanyObjectives the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldCompanyObjectives> partialUpdate(FieldCompanyObjectives fieldCompanyObjectives);

    /**
     * Get all the fieldCompanyObjectives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldCompanyObjectives> findAll(Pageable pageable);

    /**
     * Get the "id" fieldCompanyObjectives.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldCompanyObjectives> findOne(Long id);

    /**
     * Delete the "id" fieldCompanyObjectives.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
