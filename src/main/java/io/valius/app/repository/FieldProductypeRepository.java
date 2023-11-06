package io.valius.app.repository;

import io.valius.app.domain.FieldProductype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldProductype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldProductypeRepository extends JpaRepository<FieldProductype, Long> {}
