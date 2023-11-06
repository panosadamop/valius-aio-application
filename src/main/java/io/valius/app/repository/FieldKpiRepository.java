package io.valius.app.repository;

import io.valius.app.domain.FieldKpi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldKpi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldKpiRepository extends JpaRepository<FieldKpi, Long> {}
