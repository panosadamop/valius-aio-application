package io.valius.app.repository;

import io.valius.app.domain.MarketingBudget;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MarketingBudget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketingBudgetRepository extends JpaRepository<MarketingBudget, Long> {}
