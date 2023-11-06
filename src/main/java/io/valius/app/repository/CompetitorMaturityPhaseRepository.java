package io.valius.app.repository;

import io.valius.app.domain.CompetitorMaturityPhase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompetitorMaturityPhase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitorMaturityPhaseRepository extends JpaRepository<CompetitorMaturityPhase, Long> {}
