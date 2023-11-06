package io.valius.app.repository;

import io.valius.app.domain.Occupation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Occupation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {}
