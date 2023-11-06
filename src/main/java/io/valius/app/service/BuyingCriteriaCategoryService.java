package io.valius.app.service;

import io.valius.app.domain.BuyingCriteriaCategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BuyingCriteriaCategory}.
 */
public interface BuyingCriteriaCategoryService {
    /**
     * Save a buyingCriteriaCategory.
     *
     * @param buyingCriteriaCategory the entity to save.
     * @return the persisted entity.
     */
    BuyingCriteriaCategory save(BuyingCriteriaCategory buyingCriteriaCategory);

    /**
     * Updates a buyingCriteriaCategory.
     *
     * @param buyingCriteriaCategory the entity to update.
     * @return the persisted entity.
     */
    BuyingCriteriaCategory update(BuyingCriteriaCategory buyingCriteriaCategory);

    /**
     * Partially updates a buyingCriteriaCategory.
     *
     * @param buyingCriteriaCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuyingCriteriaCategory> partialUpdate(BuyingCriteriaCategory buyingCriteriaCategory);

    /**
     * Get all the buyingCriteriaCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuyingCriteriaCategory> findAll(Pageable pageable);

    /**
     * Get the "id" buyingCriteriaCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyingCriteriaCategory> findOne(Long id);

    /**
     * Delete the "id" buyingCriteriaCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
