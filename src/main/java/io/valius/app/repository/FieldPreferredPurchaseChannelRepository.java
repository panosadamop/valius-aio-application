package io.valius.app.repository;

import io.valius.app.domain.FieldPreferredPurchaseChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldPreferredPurchaseChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldPreferredPurchaseChannelRepository extends JpaRepository<FieldPreferredPurchaseChannel, Long> {}
