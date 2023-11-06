package io.valius.app.service;

import io.valius.app.domain.Survey;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Survey}.
 */
public interface SurveyService {
    /**
     * Save a survey.
     *
     * @param survey the entity to save.
     * @return the persisted entity.
     */
    Survey save(Survey survey);

    /**
     * Updates a survey.
     *
     * @param survey the entity to update.
     * @return the persisted entity.
     */
    Survey update(Survey survey);

    /**
     * Partially updates a survey.
     *
     * @param survey the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Survey> partialUpdate(Survey survey);

    /**
     * Get all the surveys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Survey> findAll(Pageable pageable);

    /**
     * Get all the surveys with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Survey> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" survey.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Survey> findOne(Long id);

    /**
     * Delete the "id" survey.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
