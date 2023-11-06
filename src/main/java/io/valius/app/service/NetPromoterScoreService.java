package io.valius.app.service;

import io.valius.app.domain.NetPromoterScore;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NetPromoterScore}.
 */
public interface NetPromoterScoreService {
    /**
     * Save a netPromoterScore.
     *
     * @param netPromoterScore the entity to save.
     * @return the persisted entity.
     */
    NetPromoterScore save(NetPromoterScore netPromoterScore);

    /**
     * Updates a netPromoterScore.
     *
     * @param netPromoterScore the entity to update.
     * @return the persisted entity.
     */
    NetPromoterScore update(NetPromoterScore netPromoterScore);

    /**
     * Partially updates a netPromoterScore.
     *
     * @param netPromoterScore the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NetPromoterScore> partialUpdate(NetPromoterScore netPromoterScore);

    /**
     * Get all the netPromoterScores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NetPromoterScore> findAll(Pageable pageable);

    /**
     * Get the "id" netPromoterScore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NetPromoterScore> findOne(Long id);

    /**
     * Delete the "id" netPromoterScore.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
