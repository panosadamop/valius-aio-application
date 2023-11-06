package io.valius.app.repository;

import io.valius.app.domain.Kpis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kpis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KpisRepository extends JpaRepository<Kpis, Long> {}
