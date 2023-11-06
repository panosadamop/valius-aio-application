package io.valius.app.service;

import io.valius.app.domain.SegmentScoreMAF;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SegmentScoreMAF}.
 */
public interface SegmentScoreMAFService {
    /**
     * Save a segmentScoreMAF.
     *
     * @param segmentScoreMAF the entity to save.
     * @return the persisted entity.
     */
    SegmentScoreMAF save(SegmentScoreMAF segmentScoreMAF);

    /**
     * Updates a segmentScoreMAF.
     *
     * @param segmentScoreMAF the entity to update.
     * @return the persisted entity.
     */
    SegmentScoreMAF update(SegmentScoreMAF segmentScoreMAF);

    /**
     * Partially updates a segmentScoreMAF.
     *
     * @param segmentScoreMAF the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SegmentScoreMAF> partialUpdate(SegmentScoreMAF segmentScoreMAF);

    /**
     * Get all the segmentScoreMAFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SegmentScoreMAF> findAll(Pageable pageable);

    /**
     * Get the "id" segmentScoreMAF.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SegmentScoreMAF> findOne(Long id);

    /**
     * Delete the "id" segmentScoreMAF.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
