package io.valius.app.repository;

import io.valius.app.domain.IntangibleElements;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntangibleElements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntangibleElementsRepository extends JpaRepository<IntangibleElements, Long> {}
