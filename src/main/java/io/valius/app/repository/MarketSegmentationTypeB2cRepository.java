package io.valius.app.repository;

import io.valius.app.domain.MarketSegmentationTypeB2c;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MarketSegmentationTypeB2c entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketSegmentationTypeB2cRepository extends JpaRepository<MarketSegmentationTypeB2c, Long> {}
