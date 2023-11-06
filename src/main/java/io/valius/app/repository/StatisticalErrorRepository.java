package io.valius.app.repository;

import io.valius.app.domain.StatisticalError;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StatisticalError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticalErrorRepository extends JpaRepository<StatisticalError, Long> {}
