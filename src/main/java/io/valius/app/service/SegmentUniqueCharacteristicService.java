package io.valius.app.service;

import io.valius.app.domain.SegmentUniqueCharacteristic;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SegmentUniqueCharacteristic}.
 */
public interface SegmentUniqueCharacteristicService {
    /**
     * Save a segmentUniqueCharacteristic.
     *
     * @param segmentUniqueCharacteristic the entity to save.
     * @return the persisted entity.
     */
    SegmentUniqueCharacteristic save(SegmentUniqueCharacteristic segmentUniqueCharacteristic);

    /**
     * Updates a segmentUniqueCharacteristic.
     *
     * @param segmentUniqueCharacteristic the entity to update.
     * @return the persisted entity.
     */
    SegmentUniqueCharacteristic update(SegmentUniqueCharacteristic segmentUniqueCharacteristic);

    /**
     * Partially updates a segmentUniqueCharacteristic.
     *
     * @param segmentUniqueCharacteristic the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SegmentUniqueCharacteristic> partialUpdate(SegmentUniqueCharacteristic segmentUniqueCharacteristic);

    /**
     * Get all the segmentUniqueCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SegmentUniqueCharacteristic> findAll(Pageable pageable);

    /**
     * Get the "id" segmentUniqueCharacteristic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SegmentUniqueCharacteristic> findOne(Long id);

    /**
     * Delete the "id" segmentUniqueCharacteristic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
