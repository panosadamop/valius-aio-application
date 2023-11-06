package io.valius.app.service;

import io.valius.app.domain.BuyingCriteriaWeighting;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BuyingCriteriaWeighting}.
 */
public interface BuyingCriteriaWeightingService {
    /**
     * Save a buyingCriteriaWeighting.
     *
     * @param buyingCriteriaWeighting the entity to save.
     * @return the persisted entity.
     */
    BuyingCriteriaWeighting save(BuyingCriteriaWeighting buyingCriteriaWeighting);

    /**
     * Updates a buyingCriteriaWeighting.
     *
     * @param buyingCriteriaWeighting the entity to update.
     * @return the persisted entity.
     */
    BuyingCriteriaWeighting update(BuyingCriteriaWeighting buyingCriteriaWeighting);

    /**
     * Partially updates a buyingCriteriaWeighting.
     *
     * @param buyingCriteriaWeighting the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuyingCriteriaWeighting> partialUpdate(BuyingCriteriaWeighting buyingCriteriaWeighting);

    /**
     * Get all the buyingCriteriaWeightings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuyingCriteriaWeighting> findAll(Pageable pageable);

    /**
     * Get the "id" buyingCriteriaWeighting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyingCriteriaWeighting> findOne(Long id);

    /**
     * Delete the "id" buyingCriteriaWeighting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
