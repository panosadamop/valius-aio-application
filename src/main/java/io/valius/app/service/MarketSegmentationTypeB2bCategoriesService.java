package io.valius.app.service;

import io.valius.app.domain.MarketSegmentationTypeB2bCategories;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketSegmentationTypeB2bCategories}.
 */
public interface MarketSegmentationTypeB2bCategoriesService {
    /**
     * Save a marketSegmentationTypeB2bCategories.
     *
     * @param marketSegmentationTypeB2bCategories the entity to save.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2bCategories save(MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories);

    /**
     * Updates a marketSegmentationTypeB2bCategories.
     *
     * @param marketSegmentationTypeB2bCategories the entity to update.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2bCategories update(MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories);

    /**
     * Partially updates a marketSegmentationTypeB2bCategories.
     *
     * @param marketSegmentationTypeB2bCategories the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketSegmentationTypeB2bCategories> partialUpdate(MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories);

    /**
     * Get all the marketSegmentationTypeB2bCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketSegmentationTypeB2bCategories> findAll(Pageable pageable);

    /**
     * Get the "id" marketSegmentationTypeB2bCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketSegmentationTypeB2bCategories> findOne(Long id);

    /**
     * Delete the "id" marketSegmentationTypeB2bCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
