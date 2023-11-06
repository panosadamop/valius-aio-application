package io.valius.app.repository;

import io.valius.app.domain.CurrentMarketSegmentation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CurrentMarketSegmentation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentMarketSegmentationRepository extends JpaRepository<CurrentMarketSegmentation, Long> {}
