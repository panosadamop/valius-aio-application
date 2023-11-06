package io.valius.app.service;

import io.valius.app.domain.PopulationSize;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PopulationSize}.
 */
public interface PopulationSizeService {
    /**
     * Save a populationSize.
     *
     * @param populationSize the entity to save.
     * @return the persisted entity.
     */
    PopulationSize save(PopulationSize populationSize);

    /**
     * Updates a populationSize.
     *
     * @param populationSize the entity to update.
     * @return the persisted entity.
     */
    PopulationSize update(PopulationSize populationSize);

    /**
     * Partially updates a populationSize.
     *
     * @param populationSize the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PopulationSize> partialUpdate(PopulationSize populationSize);

    /**
     * Get all the populationSizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PopulationSize> findAll(Pageable pageable);

    /**
     * Get the "id" populationSize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PopulationSize> findOne(Long id);

    /**
     * Delete the "id" populationSize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
