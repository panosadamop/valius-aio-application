package io.valius.app.repository;

import io.valius.app.domain.Territory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Territory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerritoryRepository extends JpaRepository<Territory, Long> {}
