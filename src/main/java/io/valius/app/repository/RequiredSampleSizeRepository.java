package io.valius.app.repository;

import io.valius.app.domain.RequiredSampleSize;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequiredSampleSize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequiredSampleSizeRepository extends JpaRepository<RequiredSampleSize, Long> {}
