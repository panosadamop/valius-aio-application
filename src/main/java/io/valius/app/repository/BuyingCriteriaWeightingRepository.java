package io.valius.app.repository;

import io.valius.app.domain.BuyingCriteriaWeighting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuyingCriteriaWeighting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyingCriteriaWeightingRepository extends JpaRepository<BuyingCriteriaWeighting, Long> {}
