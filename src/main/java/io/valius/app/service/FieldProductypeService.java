package io.valius.app.service;

import io.valius.app.domain.FieldProductype;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldProductype}.
 */
public interface FieldProductypeService {
    /**
     * Save a fieldProductype.
     *
     * @param fieldProductype the entity to save.
     * @return the persisted entity.
     */
    FieldProductype save(FieldProductype fieldProductype);

    /**
     * Updates a fieldProductype.
     *
     * @param fieldProductype the entity to update.
     * @return the persisted entity.
     */
    FieldProductype update(FieldProductype fieldProductype);

    /**
     * Partially updates a fieldProductype.
     *
     * @param fieldProductype the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldProductype> partialUpdate(FieldProductype fieldProductype);

    /**
     * Get all the fieldProductypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldProductype> findAll(Pageable pageable);

    /**
     * Get the "id" fieldProductype.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldProductype> findOne(Long id);

    /**
     * Delete the "id" fieldProductype.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
