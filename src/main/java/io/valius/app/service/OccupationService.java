package io.valius.app.service;

import io.valius.app.domain.Occupation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Occupation}.
 */
public interface OccupationService {
    /**
     * Save a occupation.
     *
     * @param occupation the entity to save.
     * @return the persisted entity.
     */
    Occupation save(Occupation occupation);

    /**
     * Updates a occupation.
     *
     * @param occupation the entity to update.
     * @return the persisted entity.
     */
    Occupation update(Occupation occupation);

    /**
     * Partially updates a occupation.
     *
     * @param occupation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Occupation> partialUpdate(Occupation occupation);

    /**
     * Get all the occupations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Occupation> findAll(Pageable pageable);

    /**
     * Get the "id" occupation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Occupation> findOne(Long id);

    /**
     * Delete the "id" occupation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
