package io.valius.app.service;

import io.valius.app.domain.InformationPages;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link InformationPages}.
 */
public interface InformationPagesService {
    /**
     * Save a informationPages.
     *
     * @param informationPages the entity to save.
     * @return the persisted entity.
     */
    InformationPages save(InformationPages informationPages);

    /**
     * Updates a informationPages.
     *
     * @param informationPages the entity to update.
     * @return the persisted entity.
     */
    InformationPages update(InformationPages informationPages);

    /**
     * Partially updates a informationPages.
     *
     * @param informationPages the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InformationPages> partialUpdate(InformationPages informationPages);

    /**
     * Get all the informationPages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InformationPages> findAll(Pageable pageable);

    /**
     * Get the "id" informationPages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InformationPages> findOne(Long id);

    /**
     * Delete the "id" informationPages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
