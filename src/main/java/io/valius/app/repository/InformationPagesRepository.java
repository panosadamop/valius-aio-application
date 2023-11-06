package io.valius.app.repository;

import io.valius.app.domain.InformationPages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InformationPages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformationPagesRepository extends JpaRepository<InformationPages, Long> {}
