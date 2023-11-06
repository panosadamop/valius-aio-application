package io.valius.app.service.impl;

import io.valius.app.domain.ProductType;
import io.valius.app.repository.ProductTypeRepository;
import io.valius.app.service.ProductTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductType}.
 */
@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService {

    private final Logger log = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public ProductType save(ProductType productType) {
        log.debug("Request to save ProductType : {}", productType);
        return productTypeRepository.save(productType);
    }

    @Override
    public ProductType update(ProductType productType) {
        log.debug("Request to update ProductType : {}", productType);
        return productTypeRepository.save(productType);
    }

    @Override
    public Optional<ProductType> partialUpdate(ProductType productType) {
        log.debug("Request to partially update ProductType : {}", productType);

        return productTypeRepository
            .findById(productType.getId())
            .map(existingProductType -> {
                if (productType.getValue() != null) {
                    existingProductType.setValue(productType.getValue());
                }
                if (productType.getCheckBoxValue() != null) {
                    existingProductType.setCheckBoxValue(productType.getCheckBoxValue());
                }
                if (productType.getDescription() != null) {
                    existingProductType.setDescription(productType.getDescription());
                }
                if (productType.getLanguage() != null) {
                    existingProductType.setLanguage(productType.getLanguage());
                }

                return existingProductType;
            })
            .map(productTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductType> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTypes");
        return productTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductType> findOne(Long id) {
        log.debug("Request to get ProductType : {}", id);
        return productTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductType : {}", id);
        productTypeRepository.deleteById(id);
    }
}
