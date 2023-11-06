package io.valius.app.service;

import io.valius.app.domain.StatisticalError;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StatisticalError}.
 */
public interface StatisticalErrorService {
    /**
     * Save a statisticalError.
     *
     * @param statisticalError the entity to save.
     * @return the persisted entity.
     */
    StatisticalError save(StatisticalError statisticalError);

    /**
     * Updates a statisticalError.
     *
     * @param statisticalError the entity to update.
     * @return the persisted entity.
     */
    StatisticalError update(StatisticalError statisticalError);

    /**
     * Partially updates a statisticalError.
     *
     * @param statisticalError the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatisticalError> partialUpdate(StatisticalError statisticalError);

    /**
     * Get all the statisticalErrors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatisticalError> findAll(Pageable pageable);

    /**
     * Get the "id" statisticalError.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatisticalError> findOne(Long id);

    /**
     * Delete the "id" statisticalError.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
