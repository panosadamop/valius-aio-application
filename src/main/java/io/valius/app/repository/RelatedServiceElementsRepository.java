package io.valius.app.repository;

import io.valius.app.domain.RelatedServiceElements;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RelatedServiceElements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatedServiceElementsRepository extends JpaRepository<RelatedServiceElements, Long> {}
