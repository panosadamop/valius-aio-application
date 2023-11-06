package io.valius.app.service;

import io.valius.app.domain.Industry;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Industry}.
 */
public interface IndustryService {
    /**
     * Save a industry.
     *
     * @param industry the entity to save.
     * @return the persisted entity.
     */
    Industry save(Industry industry);

    /**
     * Updates a industry.
     *
     * @param industry the entity to update.
     * @return the persisted entity.
     */
    Industry update(Industry industry);

    /**
     * Partially updates a industry.
     *
     * @param industry the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Industry> partialUpdate(Industry industry);

    /**
     * Get all the industries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Industry> findAll(Pageable pageable);

    /**
     * Get the "id" industry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Industry> findOne(Long id);

    /**
     * Delete the "id" industry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
