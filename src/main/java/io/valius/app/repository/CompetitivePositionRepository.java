package io.valius.app.repository;

import io.valius.app.domain.CompetitivePosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompetitivePosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitivePositionRepository extends JpaRepository<CompetitivePosition, Long> {}
