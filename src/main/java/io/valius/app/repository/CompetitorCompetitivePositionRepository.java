package io.valius.app.repository;

import io.valius.app.domain.CompetitorCompetitivePosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompetitorCompetitivePosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitorCompetitivePositionRepository extends JpaRepository<CompetitorCompetitivePosition, Long> {}
