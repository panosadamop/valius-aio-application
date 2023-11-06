package io.valius.app.repository;

import io.valius.app.domain.ConfidenceLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConfidenceLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfidenceLevelRepository extends JpaRepository<ConfidenceLevel, Long> {}
