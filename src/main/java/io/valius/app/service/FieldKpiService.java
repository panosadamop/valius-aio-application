package io.valius.app.service;

import io.valius.app.domain.FieldKpi;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldKpi}.
 */
public interface FieldKpiService {
    /**
     * Save a fieldKpi.
     *
     * @param fieldKpi the entity to save.
     * @return the persisted entity.
     */
    FieldKpi save(FieldKpi fieldKpi);

    /**
     * Updates a fieldKpi.
     *
     * @param fieldKpi the entity to update.
     * @return the persisted entity.
     */
    FieldKpi update(FieldKpi fieldKpi);

    /**
     * Partially updates a fieldKpi.
     *
     * @param fieldKpi the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldKpi> partialUpdate(FieldKpi fieldKpi);

    /**
     * Get all the fieldKpis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldKpi> findAll(Pageable pageable);

    /**
     * Get the "id" fieldKpi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldKpi> findOne(Long id);

    /**
     * Delete the "id" fieldKpi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
