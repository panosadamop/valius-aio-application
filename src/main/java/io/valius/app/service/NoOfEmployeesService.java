package io.valius.app.service;

import io.valius.app.domain.NoOfEmployees;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NoOfEmployees}.
 */
public interface NoOfEmployeesService {
    /**
     * Save a noOfEmployees.
     *
     * @param noOfEmployees the entity to save.
     * @return the persisted entity.
     */
    NoOfEmployees save(NoOfEmployees noOfEmployees);

    /**
     * Updates a noOfEmployees.
     *
     * @param noOfEmployees the entity to update.
     * @return the persisted entity.
     */
    NoOfEmployees update(NoOfEmployees noOfEmployees);

    /**
     * Partially updates a noOfEmployees.
     *
     * @param noOfEmployees the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NoOfEmployees> partialUpdate(NoOfEmployees noOfEmployees);

    /**
     * Get all the noOfEmployees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoOfEmployees> findAll(Pageable pageable);

    /**
     * Get the "id" noOfEmployees.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoOfEmployees> findOne(Long id);

    /**
     * Delete the "id" noOfEmployees.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
