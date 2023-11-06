package io.valius.app.service;

import io.valius.app.domain.LevelOne;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LevelOne}.
 */
public interface LevelOneService {
    /**
     * Save a levelOne.
     *
     * @param levelOne the entity to save.
     * @return the persisted entity.
     */
    LevelOne save(LevelOne levelOne);

    /**
     * Updates a levelOne.
     *
     * @param levelOne the entity to update.
     * @return the persisted entity.
     */
    LevelOne update(LevelOne levelOne);

    /**
     * Partially updates a levelOne.
     *
     * @param levelOne the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelOne> partialUpdate(LevelOne levelOne);

    /**
     * Get all the levelOnes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelOne> findAll(Pageable pageable);

    /**
     * Get all the levelOnes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelOne> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" levelOne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelOne> findOne(Long id);

    /**
     * Delete the "id" levelOne.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
