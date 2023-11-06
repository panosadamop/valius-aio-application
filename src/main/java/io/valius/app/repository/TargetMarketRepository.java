package io.valius.app.repository;

import io.valius.app.domain.TargetMarket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TargetMarket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetMarketRepository extends JpaRepository<TargetMarket, Long> {}
