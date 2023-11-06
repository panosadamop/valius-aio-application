package io.valius.app.repository;

import io.valius.app.domain.SegmentUniqueCharacteristic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SegmentUniqueCharacteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentUniqueCharacteristicRepository extends JpaRepository<SegmentUniqueCharacteristic, Long> {}
