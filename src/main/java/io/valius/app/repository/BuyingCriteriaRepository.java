package io.valius.app.repository;

import io.valius.app.domain.BuyingCriteria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuyingCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyingCriteriaRepository extends JpaRepository<BuyingCriteria, Long> {}
