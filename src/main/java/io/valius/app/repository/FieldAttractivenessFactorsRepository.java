package io.valius.app.repository;

import io.valius.app.domain.FieldAttractivenessFactors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldAttractivenessFactors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldAttractivenessFactorsRepository extends JpaRepository<FieldAttractivenessFactors, Long> {}
