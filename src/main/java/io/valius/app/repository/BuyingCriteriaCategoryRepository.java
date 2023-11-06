package io.valius.app.repository;

import io.valius.app.domain.BuyingCriteriaCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuyingCriteriaCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyingCriteriaCategoryRepository extends JpaRepository<BuyingCriteriaCategory, Long> {}
