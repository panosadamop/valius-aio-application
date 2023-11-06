package io.valius.app.service;

import io.valius.app.domain.PyramidData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PyramidData}.
 */
public interface PyramidDataService {
    /**
     * Save a pyramidData.
     *
     * @param pyramidData the entity to save.
     * @return the persisted entity.
     */
    PyramidData save(PyramidData pyramidData);

    /**
     * Updates a pyramidData.
     *
     * @param pyramidData the entity to update.
     * @return the persisted entity.
     */
    PyramidData update(PyramidData pyramidData);

    /**
     * Partially updates a pyramidData.
     *
     * @param pyramidData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PyramidData> partialUpdate(PyramidData pyramidData);

    /**
     * Get all the pyramidData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PyramidData> findAll(Pageable pageable);

    /**
     * Get the "id" pyramidData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PyramidData> findOne(Long id);

    /**
     * Delete the "id" pyramidData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
