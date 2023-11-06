package io.valius.app.service;

import io.valius.app.domain.RelatedServiceElements;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RelatedServiceElements}.
 */
public interface RelatedServiceElementsService {
    /**
     * Save a relatedServiceElements.
     *
     * @param relatedServiceElements the entity to save.
     * @return the persisted entity.
     */
    RelatedServiceElements save(RelatedServiceElements relatedServiceElements);

    /**
     * Updates a relatedServiceElements.
     *
     * @param relatedServiceElements the entity to update.
     * @return the persisted entity.
     */
    RelatedServiceElements update(RelatedServiceElements relatedServiceElements);

    /**
     * Partially updates a relatedServiceElements.
     *
     * @param relatedServiceElements the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RelatedServiceElements> partialUpdate(RelatedServiceElements relatedServiceElements);

    /**
     * Get all the relatedServiceElements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RelatedServiceElements> findAll(Pageable pageable);

    /**
     * Get the "id" relatedServiceElements.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RelatedServiceElements> findOne(Long id);

    /**
     * Delete the "id" relatedServiceElements.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
