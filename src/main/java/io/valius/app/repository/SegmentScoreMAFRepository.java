package io.valius.app.repository;

import io.valius.app.domain.SegmentScoreMAF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SegmentScoreMAF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentScoreMAFRepository extends JpaRepository<SegmentScoreMAF, Long> {}
