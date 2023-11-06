package io.valius.app.repository;

import io.valius.app.domain.MarketSegmentationTypeB2b;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MarketSegmentationTypeB2b entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketSegmentationTypeB2bRepository extends JpaRepository<MarketSegmentationTypeB2b, Long> {}
