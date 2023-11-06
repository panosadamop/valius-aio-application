package io.valius.app.service.impl;

import io.valius.app.domain.MarketSegmentationTypeB2cCategories;
import io.valius.app.repository.MarketSegmentationTypeB2cCategoriesRepository;
import io.valius.app.service.MarketSegmentationTypeB2cCategoriesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketSegmentationTypeB2cCategories}.
 */
@Service
@Transactional
public class MarketSegmentationTypeB2cCategoriesServiceImpl implements MarketSegmentationTypeB2cCategoriesService {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2cCategoriesServiceImpl.class);

    private final MarketSegmentationTypeB2cCategoriesRepository marketSegmentationTypeB2cCategoriesRepository;

    public MarketSegmentationTypeB2cCategoriesServiceImpl(
        MarketSegmentationTypeB2cCategoriesRepository marketSegmentationTypeB2cCategoriesRepository
    ) {
        this.marketSegmentationTypeB2cCategoriesRepository = marketSegmentationTypeB2cCategoriesRepository;
    }

    @Override
    public MarketSegmentationTypeB2cCategories save(MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories) {
        log.debug("Request to save MarketSegmentationTypeB2cCategories : {}", marketSegmentationTypeB2cCategories);
        return marketSegmentationTypeB2cCategoriesRepository.save(marketSegmentationTypeB2cCategories);
    }

    @Override
    public MarketSegmentationTypeB2cCategories update(MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories) {
        log.debug("Request to update MarketSegmentationTypeB2cCategories : {}", marketSegmentationTypeB2cCategories);
        return marketSegmentationTypeB2cCategoriesRepository.save(marketSegmentationTypeB2cCategories);
    }

    @Override
    public Optional<MarketSegmentationTypeB2cCategories> partialUpdate(
        MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories
    ) {
        log.debug("Request to partially update MarketSegmentationTypeB2cCategories : {}", marketSegmentationTypeB2cCategories);

        return marketSegmentationTypeB2cCategoriesRepository
            .findById(marketSegmentationTypeB2cCategories.getId())
            .map(existingMarketSegmentationTypeB2cCategories -> {
                if (marketSegmentationTypeB2cCategories.getValue() != null) {
                    existingMarketSegmentationTypeB2cCategories.setValue(marketSegmentationTypeB2cCategories.getValue());
                }
                if (marketSegmentationTypeB2cCategories.getDescription() != null) {
                    existingMarketSegmentationTypeB2cCategories.setDescription(marketSegmentationTypeB2cCategories.getDescription());
                }
                if (marketSegmentationTypeB2cCategories.getPlaceholder() != null) {
                    existingMarketSegmentationTypeB2cCategories.setPlaceholder(marketSegmentationTypeB2cCategories.getPlaceholder());
                }
                if (marketSegmentationTypeB2cCategories.getUniqueCharacteristic() != null) {
                    existingMarketSegmentationTypeB2cCategories.setUniqueCharacteristic(
                        marketSegmentationTypeB2cCategories.getUniqueCharacteristic()
                    );
                }
                if (marketSegmentationTypeB2cCategories.getLanguage() != null) {
                    existingMarketSegmentationTypeB2cCategories.setLanguage(marketSegmentationTypeB2cCategories.getLanguage());
                }

                return existingMarketSegmentationTypeB2cCategories;
            })
            .map(marketSegmentationTypeB2cCategoriesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketSegmentationTypeB2cCategories> findAll(Pageable pageable) {
        log.debug("Request to get all MarketSegmentationTypeB2cCategories");
        return marketSegmentationTypeB2cCategoriesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketSegmentationTypeB2cCategories> findOne(Long id) {
        log.debug("Request to get MarketSegmentationTypeB2cCategories : {}", id);
        return marketSegmentationTypeB2cCategoriesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketSegmentationTypeB2cCategories : {}", id);
        marketSegmentationTypeB2cCategoriesRepository.deleteById(id);
    }
}
