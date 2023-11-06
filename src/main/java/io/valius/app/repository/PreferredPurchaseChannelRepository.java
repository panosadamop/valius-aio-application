package io.valius.app.repository;

import io.valius.app.domain.PreferredPurchaseChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PreferredPurchaseChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferredPurchaseChannelRepository extends JpaRepository<PreferredPurchaseChannel, Long> {}
