package io.valius.app.service;

import io.valius.app.domain.MarketSegmentationTypeB2cCategories;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketSegmentationTypeB2cCategories}.
 */
public interface MarketSegmentationTypeB2cCategoriesService {
    /**
     * Save a marketSegmentationTypeB2cCategories.
     *
     * @param marketSegmentationTypeB2cCategories the entity to save.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2cCategories save(MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories);

    /**
     * Updates a marketSegmentationTypeB2cCategories.
     *
     * @param marketSegmentationTypeB2cCategories the entity to update.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2cCategories update(MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories);

    /**
     * Partially updates a marketSegmentationTypeB2cCategories.
     *
     * @param marketSegmentationTypeB2cCategories the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketSegmentationTypeB2cCategories> partialUpdate(MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories);

    /**
     * Get all the marketSegmentationTypeB2cCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketSegmentationTypeB2cCategories> findAll(Pageable pageable);

    /**
     * Get the "id" marketSegmentationTypeB2cCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketSegmentationTypeB2cCategories> findOne(Long id);

    /**
     * Delete the "id" marketSegmentationTypeB2cCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
