package io.valius.app.service;

import io.valius.app.domain.IntangibleElements;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link IntangibleElements}.
 */
public interface IntangibleElementsService {
    /**
     * Save a intangibleElements.
     *
     * @param intangibleElements the entity to save.
     * @return the persisted entity.
     */
    IntangibleElements save(IntangibleElements intangibleElements);

    /**
     * Updates a intangibleElements.
     *
     * @param intangibleElements the entity to update.
     * @return the persisted entity.
     */
    IntangibleElements update(IntangibleElements intangibleElements);

    /**
     * Partially updates a intangibleElements.
     *
     * @param intangibleElements the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntangibleElements> partialUpdate(IntangibleElements intangibleElements);

    /**
     * Get all the intangibleElements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntangibleElements> findAll(Pageable pageable);

    /**
     * Get the "id" intangibleElements.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntangibleElements> findOne(Long id);

    /**
     * Delete the "id" intangibleElements.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
