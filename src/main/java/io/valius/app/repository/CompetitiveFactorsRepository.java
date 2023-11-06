package io.valius.app.repository;

import io.valius.app.domain.CompetitiveFactors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompetitiveFactors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitiveFactorsRepository extends JpaRepository<CompetitiveFactors, Long> {}
