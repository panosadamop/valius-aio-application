package io.valius.app.repository;

import io.valius.app.domain.MaturityPhase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaturityPhase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaturityPhaseRepository extends JpaRepository<MaturityPhase, Long> {}
