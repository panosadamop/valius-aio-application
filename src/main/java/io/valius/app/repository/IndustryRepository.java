package io.valius.app.repository;

import io.valius.app.domain.Industry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Industry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {}
