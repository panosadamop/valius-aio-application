package io.valius.app.repository;

import io.valius.app.domain.Revenues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Revenues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevenuesRepository extends JpaRepository<Revenues, Long> {}
