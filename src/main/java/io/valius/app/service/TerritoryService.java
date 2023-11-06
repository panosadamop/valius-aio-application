package io.valius.app.service;

import io.valius.app.domain.Territory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Territory}.
 */
public interface TerritoryService {
    /**
     * Save a territory.
     *
     * @param territory the entity to save.
     * @return the persisted entity.
     */
    Territory save(Territory territory);

    /**
     * Updates a territory.
     *
     * @param territory the entity to update.
     * @return the persisted entity.
     */
    Territory update(Territory territory);

    /**
     * Partially updates a territory.
     *
     * @param territory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Territory> partialUpdate(Territory territory);

    /**
     * Get all the territories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Territory> findAll(Pageable pageable);

    /**
     * Get the "id" territory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Territory> findOne(Long id);

    /**
     * Delete the "id" territory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
