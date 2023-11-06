package io.valius.app.repository;

import io.valius.app.domain.PreferredCommunicationChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PreferredCommunicationChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferredCommunicationChannelRepository extends JpaRepository<PreferredCommunicationChannel, Long> {}
