package io.valius.app.repository;

import io.valius.app.domain.FieldPreferredCommunicationChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldPreferredCommunicationChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldPreferredCommunicationChannelRepository extends JpaRepository<FieldPreferredCommunicationChannel, Long> {}
