package io.valius.app.service;

import io.valius.app.domain.LevelThree;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LevelThree}.
 */
public interface LevelThreeService {
    /**
     * Save a levelThree.
     *
     * @param levelThree the entity to save.
     * @return the persisted entity.
     */
    LevelThree save(LevelThree levelThree);

    /**
     * Updates a levelThree.
     *
     * @param levelThree the entity to update.
     * @return the persisted entity.
     */
    LevelThree update(LevelThree levelThree);

    /**
     * Partially updates a levelThree.
     *
     * @param levelThree the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelThree> partialUpdate(LevelThree levelThree);

    /**
     * Get all the levelThrees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelThree> findAll(Pageable pageable);

    /**
     * Get all the levelThrees with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelThree> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" levelThree.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelThree> findOne(Long id);

    /**
     * Delete the "id" levelThree.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
