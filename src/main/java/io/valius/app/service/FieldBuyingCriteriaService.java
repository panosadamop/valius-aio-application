package io.valius.app.service;

import io.valius.app.domain.FieldBuyingCriteria;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldBuyingCriteria}.
 */
public interface FieldBuyingCriteriaService {
    /**
     * Save a fieldBuyingCriteria.
     *
     * @param fieldBuyingCriteria the entity to save.
     * @return the persisted entity.
     */
    FieldBuyingCriteria save(FieldBuyingCriteria fieldBuyingCriteria);

    /**
     * Updates a fieldBuyingCriteria.
     *
     * @param fieldBuyingCriteria the entity to update.
     * @return the persisted entity.
     */
    FieldBuyingCriteria update(FieldBuyingCriteria fieldBuyingCriteria);

    /**
     * Partially updates a fieldBuyingCriteria.
     *
     * @param fieldBuyingCriteria the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldBuyingCriteria> partialUpdate(FieldBuyingCriteria fieldBuyingCriteria);

    /**
     * Get all the fieldBuyingCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldBuyingCriteria> findAll(Pageable pageable);

    /**
     * Get the "id" fieldBuyingCriteria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldBuyingCriteria> findOne(Long id);

    /**
     * Delete the "id" fieldBuyingCriteria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
