package io.valius.app.repository;

import io.valius.app.domain.FieldBuyingCriteria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldBuyingCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldBuyingCriteriaRepository extends JpaRepository<FieldBuyingCriteria, Long> {}
