package io.valius.app.repository;

import io.valius.app.domain.MarketAttractivenessFactorsCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MarketAttractivenessFactorsCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketAttractivenessFactorsCategoryRepository extends JpaRepository<MarketAttractivenessFactorsCategory, Long> {}
