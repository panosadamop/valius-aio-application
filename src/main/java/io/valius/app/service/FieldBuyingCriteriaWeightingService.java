package io.valius.app.service;

import io.valius.app.domain.FieldBuyingCriteriaWeighting;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldBuyingCriteriaWeighting}.
 */
public interface FieldBuyingCriteriaWeightingService {
    /**
     * Save a fieldBuyingCriteriaWeighting.
     *
     * @param fieldBuyingCriteriaWeighting the entity to save.
     * @return the persisted entity.
     */
    FieldBuyingCriteriaWeighting save(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting);

    /**
     * Updates a fieldBuyingCriteriaWeighting.
     *
     * @param fieldBuyingCriteriaWeighting the entity to update.
     * @return the persisted entity.
     */
    FieldBuyingCriteriaWeighting update(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting);

    /**
     * Partially updates a fieldBuyingCriteriaWeighting.
     *
     * @param fieldBuyingCriteriaWeighting the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldBuyingCriteriaWeighting> partialUpdate(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting);

    /**
     * Get all the fieldBuyingCriteriaWeightings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldBuyingCriteriaWeighting> findAll(Pageable pageable);

    /**
     * Get the "id" fieldBuyingCriteriaWeighting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldBuyingCriteriaWeighting> findOne(Long id);

    /**
     * Delete the "id" fieldBuyingCriteriaWeighting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
