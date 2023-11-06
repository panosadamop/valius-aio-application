package io.valius.app.repository;

import io.valius.app.domain.MarketSegmentationTypeB2cCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MarketSegmentationTypeB2cCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketSegmentationTypeB2cCategoriesRepository extends JpaRepository<MarketSegmentationTypeB2cCategories, Long> {}
