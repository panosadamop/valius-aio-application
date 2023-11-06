package io.valius.app.repository;

import io.valius.app.domain.MarketSegmentationTypeB2bCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MarketSegmentationTypeB2bCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketSegmentationTypeB2bCategoriesRepository extends JpaRepository<MarketSegmentationTypeB2bCategories, Long> {}
