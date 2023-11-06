package io.valius.app.repository;

import io.valius.app.domain.NetPromoterScore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NetPromoterScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetPromoterScoreRepository extends JpaRepository<NetPromoterScore, Long> {}
