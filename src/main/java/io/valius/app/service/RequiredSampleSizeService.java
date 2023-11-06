package io.valius.app.service;

import io.valius.app.domain.RequiredSampleSize;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RequiredSampleSize}.
 */
public interface RequiredSampleSizeService {
    /**
     * Save a requiredSampleSize.
     *
     * @param requiredSampleSize the entity to save.
     * @return the persisted entity.
     */
    RequiredSampleSize save(RequiredSampleSize requiredSampleSize);

    /**
     * Updates a requiredSampleSize.
     *
     * @param requiredSampleSize the entity to update.
     * @return the persisted entity.
     */
    RequiredSampleSize update(RequiredSampleSize requiredSampleSize);

    /**
     * Partially updates a requiredSampleSize.
     *
     * @param requiredSampleSize the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequiredSampleSize> partialUpdate(RequiredSampleSize requiredSampleSize);

    /**
     * Get all the requiredSampleSizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequiredSampleSize> findAll(Pageable pageable);

    /**
     * Get the "id" requiredSampleSize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequiredSampleSize> findOne(Long id);

    /**
     * Delete the "id" requiredSampleSize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
