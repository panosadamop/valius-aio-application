package io.valius.app.service;

import io.valius.app.domain.Education;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Education}.
 */
public interface EducationService {
    /**
     * Save a education.
     *
     * @param education the entity to save.
     * @return the persisted entity.
     */
    Education save(Education education);

    /**
     * Updates a education.
     *
     * @param education the entity to update.
     * @return the persisted entity.
     */
    Education update(Education education);

    /**
     * Partially updates a education.
     *
     * @param education the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Education> partialUpdate(Education education);

    /**
     * Get all the educations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Education> findAll(Pageable pageable);

    /**
     * Get the "id" education.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Education> findOne(Long id);

    /**
     * Delete the "id" education.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
