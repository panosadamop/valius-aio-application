package io.valius.app.repository;

import io.valius.app.domain.ExternalReports;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ExternalReports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalReportsRepository extends JpaRepository<ExternalReports, Long> {}
