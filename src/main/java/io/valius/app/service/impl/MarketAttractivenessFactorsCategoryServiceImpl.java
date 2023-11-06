package io.valius.app.service.impl;

import io.valius.app.domain.MarketAttractivenessFactorsCategory;
import io.valius.app.repository.MarketAttractivenessFactorsCategoryRepository;
import io.valius.app.service.MarketAttractivenessFactorsCategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketAttractivenessFactorsCategory}.
 */
@Service
@Transactional
public class MarketAttractivenessFactorsCategoryServiceImpl implements MarketAttractivenessFactorsCategoryService {

    private final Logger log = LoggerFactory.getLogger(MarketAttractivenessFactorsCategoryServiceImpl.class);

    private final MarketAttractivenessFactorsCategoryRepository marketAttractivenessFactorsCategoryRepository;

    public MarketAttractivenessFactorsCategoryServiceImpl(
        MarketAttractivenessFactorsCategoryRepository marketAttractivenessFactorsCategoryRepository
    ) {
        this.marketAttractivenessFactorsCategoryRepository = marketAttractivenessFactorsCategoryRepository;
    }

    @Override
    public MarketAttractivenessFactorsCategory save(MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory) {
        log.debug("Request to save MarketAttractivenessFactorsCategory : {}", marketAttractivenessFactorsCategory);
        return marketAttractivenessFactorsCategoryRepository.save(marketAttractivenessFactorsCategory);
    }

    @Override
    public MarketAttractivenessFactorsCategory update(MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory) {
        log.debug("Request to update MarketAttractivenessFactorsCategory : {}", marketAttractivenessFactorsCategory);
        return marketAttractivenessFactorsCategoryRepository.save(marketAttractivenessFactorsCategory);
    }

    @Override
    public Optional<MarketAttractivenessFactorsCategory> partialUpdate(
        MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory
    ) {
        log.debug("Request to partially update MarketAttractivenessFactorsCategory : {}", marketAttractivenessFactorsCategory);

        return marketAttractivenessFactorsCategoryRepository
            .findById(marketAttractivenessFactorsCategory.getId())
            .map(existingMarketAttractivenessFactorsCategory -> {
                if (marketAttractivenessFactorsCategory.getValue() != null) {
                    existingMarketAttractivenessFactorsCategory.setValue(marketAttractivenessFactorsCategory.getValue());
                }
                if (marketAttractivenessFactorsCategory.getTab() != null) {
                    existingMarketAttractivenessFactorsCategory.setTab(marketAttractivenessFactorsCategory.getTab());
                }
                if (marketAttractivenessFactorsCategory.getDescription() != null) {
                    existingMarketAttractivenessFactorsCategory.setDescription(marketAttractivenessFactorsCategory.getDescription());
                }
                if (marketAttractivenessFactorsCategory.getLanguage() != null) {
                    existingMarketAttractivenessFactorsCategory.setLanguage(marketAttractivenessFactorsCategory.getLanguage());
                }

                return existingMarketAttractivenessFactorsCategory;
            })
            .map(marketAttractivenessFactorsCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketAttractivenessFactorsCategory> findAll(Pageable pageable) {
        log.debug("Request to get all MarketAttractivenessFactorsCategories");
        return marketAttractivenessFactorsCategoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketAttractivenessFactorsCategory> findOne(Long id) {
        log.debug("Request to get MarketAttractivenessFactorsCategory : {}", id);
        return marketAttractivenessFactorsCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketAttractivenessFactorsCategory : {}", id);
        marketAttractivenessFactorsCategoryRepository.deleteById(id);
    }
}
