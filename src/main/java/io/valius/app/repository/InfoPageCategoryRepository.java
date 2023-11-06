package io.valius.app.repository;

import io.valius.app.domain.InfoPageCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InfoPageCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoPageCategoryRepository extends JpaRepository<InfoPageCategory, Long> {}
