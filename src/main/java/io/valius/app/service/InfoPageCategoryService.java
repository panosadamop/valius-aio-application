package io.valius.app.service;

import io.valius.app.domain.InfoPageCategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link InfoPageCategory}.
 */
public interface InfoPageCategoryService {
    /**
     * Save a infoPageCategory.
     *
     * @param infoPageCategory the entity to save.
     * @return the persisted entity.
     */
    InfoPageCategory save(InfoPageCategory infoPageCategory);

    /**
     * Updates a infoPageCategory.
     *
     * @param infoPageCategory the entity to update.
     * @return the persisted entity.
     */
    InfoPageCategory update(InfoPageCategory infoPageCategory);

    /**
     * Partially updates a infoPageCategory.
     *
     * @param infoPageCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InfoPageCategory> partialUpdate(InfoPageCategory infoPageCategory);

    /**
     * Get all the infoPageCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InfoPageCategory> findAll(Pageable pageable);

    /**
     * Get the "id" infoPageCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InfoPageCategory> findOne(Long id);

    /**
     * Delete the "id" infoPageCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
