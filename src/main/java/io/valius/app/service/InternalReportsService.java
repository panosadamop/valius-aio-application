package io.valius.app.service;

import io.valius.app.domain.InternalReports;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link InternalReports}.
 */
public interface InternalReportsService {
    /**
     * Save a internalReports.
     *
     * @param internalReports the entity to save.
     * @return the persisted entity.
     */
    InternalReports save(InternalReports internalReports);

    /**
     * Updates a internalReports.
     *
     * @param internalReports the entity to update.
     * @return the persisted entity.
     */
    InternalReports update(InternalReports internalReports);

    /**
     * Partially updates a internalReports.
     *
     * @param internalReports the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InternalReports> partialUpdate(InternalReports internalReports);

    /**
     * Get all the internalReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InternalReports> findAll(Pageable pageable);

    /**
     * Get the "id" internalReports.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InternalReports> findOne(Long id);

    /**
     * Delete the "id" internalReports.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
