package io.valius.app.repository;

import io.valius.app.domain.CompanyObjectives;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompanyObjectives entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyObjectivesRepository extends JpaRepository<CompanyObjectives, Long> {}
