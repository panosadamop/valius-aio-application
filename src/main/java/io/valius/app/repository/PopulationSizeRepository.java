package io.valius.app.repository;

import io.valius.app.domain.PopulationSize;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PopulationSize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PopulationSizeRepository extends JpaRepository<PopulationSize, Long> {}
