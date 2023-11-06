package io.valius.app.repository;

import io.valius.app.domain.PyramidData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PyramidData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PyramidDataRepository extends JpaRepository<PyramidData, Long> {}
