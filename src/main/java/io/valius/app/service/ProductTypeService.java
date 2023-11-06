package io.valius.app.service;

import io.valius.app.domain.ProductType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductType}.
 */
public interface ProductTypeService {
    /**
     * Save a productType.
     *
     * @param productType the entity to save.
     * @return the persisted entity.
     */
    ProductType save(ProductType productType);

    /**
     * Updates a productType.
     *
     * @param productType the entity to update.
     * @return the persisted entity.
     */
    ProductType update(ProductType productType);

    /**
     * Partially updates a productType.
     *
     * @param productType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductType> partialUpdate(ProductType productType);

    /**
     * Get all the productTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductType> findAll(Pageable pageable);

    /**
     * Get the "id" productType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductType> findOne(Long id);

    /**
     * Delete the "id" productType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
