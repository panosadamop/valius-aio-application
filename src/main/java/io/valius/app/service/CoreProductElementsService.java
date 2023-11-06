package io.valius.app.service;

import io.valius.app.domain.CoreProductElements;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CoreProductElements}.
 */
public interface CoreProductElementsService {
    /**
     * Save a coreProductElements.
     *
     * @param coreProductElements the entity to save.
     * @return the persisted entity.
     */
    CoreProductElements save(CoreProductElements coreProductElements);

    /**
     * Updates a coreProductElements.
     *
     * @param coreProductElements the entity to update.
     * @return the persisted entity.
     */
    CoreProductElements update(CoreProductElements coreProductElements);

    /**
     * Partially updates a coreProductElements.
     *
     * @param coreProductElements the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CoreProductElements> partialUpdate(CoreProductElements coreProductElements);

    /**
     * Get all the coreProductElements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoreProductElements> findAll(Pageable pageable);

    /**
     * Get the "id" coreProductElements.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoreProductElements> findOne(Long id);

    /**
     * Delete the "id" coreProductElements.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
