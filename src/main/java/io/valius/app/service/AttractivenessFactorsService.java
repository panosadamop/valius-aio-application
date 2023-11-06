package io.valius.app.service;

import io.valius.app.domain.AttractivenessFactors;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AttractivenessFactors}.
 */
public interface AttractivenessFactorsService {
    /**
     * Save a attractivenessFactors.
     *
     * @param attractivenessFactors the entity to save.
     * @return the persisted entity.
     */
    AttractivenessFactors save(AttractivenessFactors attractivenessFactors);

    /**
     * Updates a attractivenessFactors.
     *
     * @param attractivenessFactors the entity to update.
     * @return the persisted entity.
     */
    AttractivenessFactors update(AttractivenessFactors attractivenessFactors);

    /**
     * Partially updates a attractivenessFactors.
     *
     * @param attractivenessFactors the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttractivenessFactors> partialUpdate(AttractivenessFactors attractivenessFactors);

    /**
     * Get all the attractivenessFactors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttractivenessFactors> findAll(Pageable pageable);

    /**
     * Get the "id" attractivenessFactors.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttractivenessFactors> findOne(Long id);

    /**
     * Delete the "id" attractivenessFactors.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
