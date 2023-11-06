package io.valius.app.service;

import io.valius.app.domain.MarketSegmentationTypeB2cAlt;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketSegmentationTypeB2cAlt}.
 */
public interface MarketSegmentationTypeB2cAltService {
    /**
     * Save a marketSegmentationTypeB2cAlt.
     *
     * @param marketSegmentationTypeB2cAlt the entity to save.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2cAlt save(MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt);

    /**
     * Updates a marketSegmentationTypeB2cAlt.
     *
     * @param marketSegmentationTypeB2cAlt the entity to update.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2cAlt update(MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt);

    /**
     * Partially updates a marketSegmentationTypeB2cAlt.
     *
     * @param marketSegmentationTypeB2cAlt the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketSegmentationTypeB2cAlt> partialUpdate(MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt);

    /**
     * Get all the marketSegmentationTypeB2cAlts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketSegmentationTypeB2cAlt> findAll(Pageable pageable);

    /**
     * Get the "id" marketSegmentationTypeB2cAlt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketSegmentationTypeB2cAlt> findOne(Long id);

    /**
     * Delete the "id" marketSegmentationTypeB2cAlt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
