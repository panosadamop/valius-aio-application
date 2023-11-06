package io.valius.app.repository;

import io.valius.app.domain.FieldBuyingCriteriaWeighting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldBuyingCriteriaWeighting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldBuyingCriteriaWeightingRepository extends JpaRepository<FieldBuyingCriteriaWeighting, Long> {}
