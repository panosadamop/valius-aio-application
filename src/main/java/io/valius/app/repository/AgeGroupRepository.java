package io.valius.app.repository;

import io.valius.app.domain.AgeGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AgeGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgeGroupRepository extends JpaRepository<AgeGroup, Long> {}
