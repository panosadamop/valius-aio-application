package io.valius.app.service;

import io.valius.app.domain.MarketSegmentationTypeB2bAlt;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MarketSegmentationTypeB2bAlt}.
 */
public interface MarketSegmentationTypeB2bAltService {
    /**
     * Save a marketSegmentationTypeB2bAlt.
     *
     * @param marketSegmentationTypeB2bAlt the entity to save.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2bAlt save(MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt);

    /**
     * Updates a marketSegmentationTypeB2bAlt.
     *
     * @param marketSegmentationTypeB2bAlt the entity to update.
     * @return the persisted entity.
     */
    MarketSegmentationTypeB2bAlt update(MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt);

    /**
     * Partially updates a marketSegmentationTypeB2bAlt.
     *
     * @param marketSegmentationTypeB2bAlt the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketSegmentationTypeB2bAlt> partialUpdate(MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt);

    /**
     * Get all the marketSegmentationTypeB2bAlts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketSegmentationTypeB2bAlt> findAll(Pageable pageable);

    /**
     * Get the "id" marketSegmentationTypeB2bAlt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketSegmentationTypeB2bAlt> findOne(Long id);

    /**
     * Delete the "id" marketSegmentationTypeB2bAlt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
