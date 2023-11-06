package io.valius.app.service;

import io.valius.app.domain.OrganisationType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OrganisationType}.
 */
public interface OrganisationTypeService {
    /**
     * Save a organisationType.
     *
     * @param organisationType the entity to save.
     * @return the persisted entity.
     */
    OrganisationType save(OrganisationType organisationType);

    /**
     * Updates a organisationType.
     *
     * @param organisationType the entity to update.
     * @return the persisted entity.
     */
    OrganisationType update(OrganisationType organisationType);

    /**
     * Partially updates a organisationType.
     *
     * @param organisationType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganisationType> partialUpdate(OrganisationType organisationType);

    /**
     * Get all the organisationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganisationType> findAll(Pageable pageable);

    /**
     * Get the "id" organisationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganisationType> findOne(Long id);

    /**
     * Delete the "id" organisationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
