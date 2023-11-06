package io.valius.app.repository;

import io.valius.app.domain.StrategicFocus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StrategicFocus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrategicFocusRepository extends JpaRepository<StrategicFocus, Long> {}
