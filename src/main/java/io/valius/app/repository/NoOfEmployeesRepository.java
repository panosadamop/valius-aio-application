package io.valius.app.repository;

import io.valius.app.domain.NoOfEmployees;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NoOfEmployees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoOfEmployeesRepository extends JpaRepository<NoOfEmployees, Long> {}
