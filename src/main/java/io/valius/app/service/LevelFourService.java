package io.valius.app.service;

import io.valius.app.domain.LevelFour;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LevelFour}.
 */
public interface LevelFourService {
    /**
     * Save a levelFour.
     *
     * @param levelFour the entity to save.
     * @return the persisted entity.
     */
    LevelFour save(LevelFour levelFour);

    /**
     * Updates a levelFour.
     *
     * @param levelFour the entity to update.
     * @return the persisted entity.
     */
    LevelFour update(LevelFour levelFour);

    /**
     * Partially updates a levelFour.
     *
     * @param levelFour the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelFour> partialUpdate(LevelFour levelFour);

    /**
     * Get all the levelFours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelFour> findAll(Pageable pageable);

    /**
     * Get all the levelFours with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelFour> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" levelFour.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelFour> findOne(Long id);

    /**
     * Delete the "id" levelFour.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
