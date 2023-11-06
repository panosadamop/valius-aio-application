package io.valius.app.service;

import io.valius.app.domain.SocialFactors;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SocialFactors}.
 */
public interface SocialFactorsService {
    /**
     * Save a socialFactors.
     *
     * @param socialFactors the entity to save.
     * @return the persisted entity.
     */
    SocialFactors save(SocialFactors socialFactors);

    /**
     * Updates a socialFactors.
     *
     * @param socialFactors the entity to update.
     * @return the persisted entity.
     */
    SocialFactors update(SocialFactors socialFactors);

    /**
     * Partially updates a socialFactors.
     *
     * @param socialFactors the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocialFactors> partialUpdate(SocialFactors socialFactors);

    /**
     * Get all the socialFactors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SocialFactors> findAll(Pageable pageable);

    /**
     * Get the "id" socialFactors.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialFactors> findOne(Long id);

    /**
     * Delete the "id" socialFactors.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
