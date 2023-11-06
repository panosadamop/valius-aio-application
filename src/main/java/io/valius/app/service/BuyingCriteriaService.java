package io.valius.app.service;

import io.valius.app.domain.BuyingCriteria;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BuyingCriteria}.
 */
public interface BuyingCriteriaService {
    /**
     * Save a buyingCriteria.
     *
     * @param buyingCriteria the entity to save.
     * @return the persisted entity.
     */
    BuyingCriteria save(BuyingCriteria buyingCriteria);

    /**
     * Updates a buyingCriteria.
     *
     * @param buyingCriteria the entity to update.
     * @return the persisted entity.
     */
    BuyingCriteria update(BuyingCriteria buyingCriteria);

    /**
     * Partially updates a buyingCriteria.
     *
     * @param buyingCriteria the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuyingCriteria> partialUpdate(BuyingCriteria buyingCriteria);

    /**
     * Get all the buyingCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuyingCriteria> findAll(Pageable pageable);

    /**
     * Get the "id" buyingCriteria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyingCriteria> findOne(Long id);

    /**
     * Delete the "id" buyingCriteria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
