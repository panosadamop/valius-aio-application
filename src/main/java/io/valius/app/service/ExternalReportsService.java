package io.valius.app.service;

import io.valius.app.domain.ExternalReports;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ExternalReports}.
 */
public interface ExternalReportsService {
    /**
     * Save a externalReports.
     *
     * @param externalReports the entity to save.
     * @return the persisted entity.
     */
    ExternalReports save(ExternalReports externalReports);

    /**
     * Updates a externalReports.
     *
     * @param externalReports the entity to update.
     * @return the persisted entity.
     */
    ExternalReports update(ExternalReports externalReports);

    /**
     * Partially updates a externalReports.
     *
     * @param externalReports the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExternalReports> partialUpdate(ExternalReports externalReports);

    /**
     * Get all the externalReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExternalReports> findAll(Pageable pageable);

    /**
     * Get the "id" externalReports.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExternalReports> findOne(Long id);

    /**
     * Delete the "id" externalReports.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
