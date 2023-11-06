package io.valius.app.service;

import io.valius.app.domain.MarketingBudget;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketingBudget}.
 */
public interface MarketingBudgetService {
    /**
     * Save a marketingBudget.
     *
     * @param marketingBudget the entity to save.
     * @return the persisted entity.
     */
    MarketingBudget save(MarketingBudget marketingBudget);

    /**
     * Updates a marketingBudget.
     *
     * @param marketingBudget the entity to update.
     * @return the persisted entity.
     */
    MarketingBudget update(MarketingBudget marketingBudget);

    /**
     * Partially updates a marketingBudget.
     *
     * @param marketingBudget the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketingBudget> partialUpdate(MarketingBudget marketingBudget);

    /**
     * Get all the marketingBudgets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketingBudget> findAll(Pageable pageable);

    /**
     * Get the "id" marketingBudget.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketingBudget> findOne(Long id);

    /**
     * Delete the "id" marketingBudget.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
