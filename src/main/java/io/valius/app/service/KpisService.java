package io.valius.app.service;

import io.valius.app.domain.Kpis;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Kpis}.
 */
public interface KpisService {
    /**
     * Save a kpis.
     *
     * @param kpis the entity to save.
     * @return the persisted entity.
     */
    Kpis save(Kpis kpis);

    /**
     * Updates a kpis.
     *
     * @param kpis the entity to update.
     * @return the persisted entity.
     */
    Kpis update(Kpis kpis);

    /**
     * Partially updates a kpis.
     *
     * @param kpis the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Kpis> partialUpdate(Kpis kpis);

    /**
     * Get all the kpis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Kpis> findAll(Pageable pageable);

    /**
     * Get the "id" kpis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Kpis> findOne(Long id);

    /**
     * Delete the "id" kpis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
