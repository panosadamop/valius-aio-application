package io.valius.app.repository;

import io.valius.app.domain.EconomicFactors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EconomicFactors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EconomicFactorsRepository extends JpaRepository<EconomicFactors, Long> {}
