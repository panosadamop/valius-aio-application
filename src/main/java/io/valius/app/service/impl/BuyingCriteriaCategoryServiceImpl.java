package io.valius.app.service.impl;

import io.valius.app.domain.BuyingCriteriaCategory;
import io.valius.app.repository.BuyingCriteriaCategoryRepository;
import io.valius.app.service.BuyingCriteriaCategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuyingCriteriaCategory}.
 */
@Service
@Transactional
public class BuyingCriteriaCategoryServiceImpl implements BuyingCriteriaCategoryService {

    private final Logger log = LoggerFactory.getLogger(BuyingCriteriaCategoryServiceImpl.class);

    private final BuyingCriteriaCategoryRepository buyingCriteriaCategoryRepository;

    public BuyingCriteriaCategoryServiceImpl(BuyingCriteriaCategoryRepository buyingCriteriaCategoryRepository) {
        this.buyingCriteriaCategoryRepository = buyingCriteriaCategoryRepository;
    }

    @Override
    public BuyingCriteriaCategory save(BuyingCriteriaCategory buyingCriteriaCategory) {
        log.debug("Request to save BuyingCriteriaCategory : {}", buyingCriteriaCategory);
        return buyingCriteriaCategoryRepository.save(buyingCriteriaCategory);
    }

    @Override
    public BuyingCriteriaCategory update(BuyingCriteriaCategory buyingCriteriaCategory) {
        log.debug("Request to update BuyingCriteriaCategory : {}", buyingCriteriaCategory);
        return buyingCriteriaCategoryRepository.save(buyingCriteriaCategory);
    }

    @Override
    public Optional<BuyingCriteriaCategory> partialUpdate(BuyingCriteriaCategory buyingCriteriaCategory) {
        log.debug("Request to partially update BuyingCriteriaCategory : {}", buyingCriteriaCategory);

        return buyingCriteriaCategoryRepository
            .findById(buyingCriteriaCategory.getId())
            .map(existingBuyingCriteriaCategory -> {
                if (buyingCriteriaCategory.getValue() != null) {
                    existingBuyingCriteriaCategory.setValue(buyingCriteriaCategory.getValue());
                }
                if (buyingCriteriaCategory.getDescription() != null) {
                    existingBuyingCriteriaCategory.setDescription(buyingCriteriaCategory.getDescription());
                }
                if (buyingCriteriaCategory.getLanguage() != null) {
                    existingBuyingCriteriaCategory.setLanguage(buyingCriteriaCategory.getLanguage());
                }

                return existingBuyingCriteriaCategory;
            })
            .map(buyingCriteriaCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyingCriteriaCategory> findAll(Pageable pageable) {
        log.debug("Request to get all BuyingCriteriaCategories");
        return buyingCriteriaCategoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyingCriteriaCategory> findOne(Long id) {
        log.debug("Request to get BuyingCriteriaCategory : {}", id);
        return buyingCriteriaCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuyingCriteriaCategory : {}", id);
        buyingCriteriaCategoryRepository.deleteById(id);
    }
}
