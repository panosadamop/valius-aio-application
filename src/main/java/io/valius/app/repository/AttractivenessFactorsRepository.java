package io.valius.app.repository;

import io.valius.app.domain.AttractivenessFactors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttractivenessFactors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttractivenessFactorsRepository extends JpaRepository<AttractivenessFactors, Long> {}
