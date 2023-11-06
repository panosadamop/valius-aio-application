package io.valius.app.repository;

import io.valius.app.domain.CoreProductElements;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CoreProductElements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoreProductElementsRepository extends JpaRepository<CoreProductElements, Long> {}
