package io.valius.app.service;

import io.valius.app.domain.MarketAttractivenessFactorsCategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketAttractivenessFactorsCategory}.
 */
public interface MarketAttractivenessFactorsCategoryService {
    /**
     * Save a marketAttractivenessFactorsCategory.
     *
     * @param marketAttractivenessFactorsCategory the entity to save.
     * @return the persisted entity.
     */
    MarketAttractivenessFactorsCategory save(MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory);

    /**
     * Updates a marketAttractivenessFactorsCategory.
     *
     * @param marketAttractivenessFactorsCategory the entity to update.
     * @return the persisted entity.
     */
    MarketAttractivenessFactorsCategory update(MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory);

    /**
     * Partially updates a marketAttractivenessFactorsCategory.
     *
     * @param marketAttractivenessFactorsCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketAttractivenessFactorsCategory> partialUpdate(MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory);

    /**
     * Get all the marketAttractivenessFactorsCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketAttractivenessFactorsCategory> findAll(Pageable pageable);

    /**
     * Get the "id" marketAttractivenessFactorsCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketAttractivenessFactorsCategory> findOne(Long id);

    /**
     * Delete the "id" marketAttractivenessFactorsCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
