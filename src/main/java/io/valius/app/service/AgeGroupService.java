package io.valius.app.service;

import io.valius.app.domain.AgeGroup;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AgeGroup}.
 */
public interface AgeGroupService {
    /**
     * Save a ageGroup.
     *
     * @param ageGroup the entity to save.
     * @return the persisted entity.
     */
    AgeGroup save(AgeGroup ageGroup);

    /**
     * Updates a ageGroup.
     *
     * @param ageGroup the entity to update.
     * @return the persisted entity.
     */
    AgeGroup update(AgeGroup ageGroup);

    /**
     * Partially updates a ageGroup.
     *
     * @param ageGroup the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgeGroup> partialUpdate(AgeGroup ageGroup);

    /**
     * Get all the ageGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgeGroup> findAll(Pageable pageable);

    /**
     * Get the "id" ageGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgeGroup> findOne(Long id);

    /**
     * Delete the "id" ageGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
