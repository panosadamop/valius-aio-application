package io.valius.app.service;

import io.valius.app.domain.StrategicFocus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StrategicFocus}.
 */
public interface StrategicFocusService {
    /**
     * Save a strategicFocus.
     *
     * @param strategicFocus the entity to save.
     * @return the persisted entity.
     */
    StrategicFocus save(StrategicFocus strategicFocus);

    /**
     * Updates a strategicFocus.
     *
     * @param strategicFocus the entity to update.
     * @return the persisted entity.
     */
    StrategicFocus update(StrategicFocus strategicFocus);

    /**
     * Partially updates a strategicFocus.
     *
     * @param strategicFocus the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StrategicFocus> partialUpdate(StrategicFocus strategicFocus);

    /**
     * Get all the strategicFoci.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StrategicFocus> findAll(Pageable pageable);

    /**
     * Get the "id" strategicFocus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StrategicFocus> findOne(Long id);

    /**
     * Delete the "id" strategicFocus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
