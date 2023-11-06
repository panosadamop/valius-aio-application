package io.valius.app.repository;

import io.valius.app.domain.MarketSegmentationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MarketSegmentationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketSegmentationTypeRepository extends JpaRepository<MarketSegmentationType, Long> {}
