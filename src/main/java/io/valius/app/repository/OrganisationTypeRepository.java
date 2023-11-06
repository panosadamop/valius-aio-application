package io.valius.app.repository;

import io.valius.app.domain.OrganisationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganisationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationTypeRepository extends JpaRepository<OrganisationType, Long> {}
