package io.valius.app.service;

import io.valius.app.domain.LevelTwo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LevelTwo}.
 */
public interface LevelTwoService {
    /**
     * Save a levelTwo.
     *
     * @param levelTwo the entity to save.
     * @return the persisted entity.
     */
    LevelTwo save(LevelTwo levelTwo);

    /**
     * Updates a levelTwo.
     *
     * @param levelTwo the entity to update.
     * @return the persisted entity.
     */
    LevelTwo update(LevelTwo levelTwo);

    /**
     * Partially updates a levelTwo.
     *
     * @param levelTwo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelTwo> partialUpdate(LevelTwo levelTwo);

    /**
     * Get all the levelTwos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelTwo> findAll(Pageable pageable);

    /**
     * Get all the levelTwos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelTwo> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" levelTwo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelTwo> findOne(Long id);

    /**
     * Delete the "id" levelTwo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
