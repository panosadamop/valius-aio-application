package io.valius.app.service;

import io.valius.app.domain.FieldAttractivenessFactors;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FieldAttractivenessFactors}.
 */
public interface FieldAttractivenessFactorsService {
    /**
     * Save a fieldAttractivenessFactors.
     *
     * @param fieldAttractivenessFactors the entity to save.
     * @return the persisted entity.
     */
    FieldAttractivenessFactors save(FieldAttractivenessFactors fieldAttractivenessFactors);

    /**
     * Updates a fieldAttractivenessFactors.
     *
     * @param fieldAttractivenessFactors the entity to update.
     * @return the persisted entity.
     */
    FieldAttractivenessFactors update(FieldAttractivenessFactors fieldAttractivenessFactors);

    /**
     * Partially updates a fieldAttractivenessFactors.
     *
     * @param fieldAttractivenessFactors the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldAttractivenessFactors> partialUpdate(FieldAttractivenessFactors fieldAttractivenessFactors);

    /**
     * Get all the fieldAttractivenessFactors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldAttractivenessFactors> findAll(Pageable pageable);

    /**
     * Get the "id" fieldAttractivenessFactors.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldAttractivenessFactors> findOne(Long id);

    /**
     * Delete the "id" fieldAttractivenessFactors.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
