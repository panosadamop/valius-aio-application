package io.valius.app.repository;

import io.valius.app.domain.SocialFactors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocialFactors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialFactorsRepository extends JpaRepository<SocialFactors, Long> {}
