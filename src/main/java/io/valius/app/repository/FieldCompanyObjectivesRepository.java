package io.valius.app.repository;

import io.valius.app.domain.FieldCompanyObjectives;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldCompanyObjectives entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldCompanyObjectivesRepository extends JpaRepository<FieldCompanyObjectives, Long> {}
