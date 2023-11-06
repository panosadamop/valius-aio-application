package io.valius.app.service;

import io.valius.app.domain.Revenues;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Revenues}.
 */
public interface RevenuesService {
    /**
     * Save a revenues.
     *
     * @param revenues the entity to save.
     * @return the persisted entity.
     */
    Revenues save(Revenues revenues);

    /**
     * Updates a revenues.
     *
     * @param revenues the entity to update.
     * @return the persisted entity.
     */
    Revenues update(Revenues revenues);

    /**
     * Partially updates a revenues.
     *
     * @param revenues the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Revenues> partialUpdate(Revenues revenues);

    /**
     * Get all the revenues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Revenues> findAll(Pageable pageable);

    /**
     * Get the "id" revenues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Revenues> findOne(Long id);

    /**
     * Delete the "id" revenues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
