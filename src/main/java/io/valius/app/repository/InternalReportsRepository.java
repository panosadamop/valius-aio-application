package io.valius.app.repository;

import io.valius.app.domain.InternalReports;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InternalReports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternalReportsRepository extends JpaRepository<InternalReports, Long> {}
