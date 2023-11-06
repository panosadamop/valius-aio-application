package io.valius.app.service.impl;

import io.valius.app.domain.MarketSegmentationTypeB2bCategories;
import io.valius.app.repository.MarketSegmentationTypeB2bCategoriesRepository;
import io.valius.app.service.MarketSegmentationTypeB2bCategoriesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketSegmentationTypeB2bCategories}.
 */
@Service
@Transactional
public class MarketSegmentationTypeB2bCategoriesServiceImpl implements MarketSegmentationTypeB2bCategoriesService {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2bCategoriesServiceImpl.class);

    private final MarketSegmentationTypeB2bCategoriesRepository marketSegmentationTypeB2bCategoriesRepository;

    public MarketSegmentationTypeB2bCategoriesServiceImpl(
        MarketSegmentationTypeB2bCategoriesRepository marketSegmentationTypeB2bCategoriesRepository
    ) {
        this.marketSegmentationTypeB2bCategoriesRepository = marketSegmentationTypeB2bCategoriesRepository;
    }

    @Override
    public MarketSegmentationTypeB2bCategories save(MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories) {
        log.debug("Request to save MarketSegmentationTypeB2bCategories : {}", marketSegmentationTypeB2bCategories);
        return marketSegmentationTypeB2bCategoriesRepository.save(marketSegmentationTypeB2bCategories);
    }

    @Override
    public MarketSegmentationTypeB2bCategories update(MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories) {
        log.debug("Request to update MarketSegmentationTypeB2bCategories : {}", marketSegmentationTypeB2bCategories);
        return marketSegmentationTypeB2bCategoriesRepository.save(marketSegmentationTypeB2bCategories);
    }

    @Override
    public Optional<MarketSegmentationTypeB2bCategories> partialUpdate(
        MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories
    ) {
        log.debug("Request to partially update MarketSegmentationTypeB2bCategories : {}", marketSegmentationTypeB2bCategories);

        return marketSegmentationTypeB2bCategoriesRepository
            .findById(marketSegmentationTypeB2bCategories.getId())
            .map(existingMarketSegmentationTypeB2bCategories -> {
                if (marketSegmentationTypeB2bCategories.getValue() != null) {
                    existingMarketSegmentationTypeB2bCategories.setValue(marketSegmentationTypeB2bCategories.getValue());
                }
                if (marketSegmentationTypeB2bCategories.getDescription() != null) {
                    existingMarketSegmentationTypeB2bCategories.setDescription(marketSegmentationTypeB2bCategories.getDescription());
                }
                if (marketSegmentationTypeB2bCategories.getPlaceholder() != null) {
                    existingMarketSegmentationTypeB2bCategories.setPlaceholder(marketSegmentationTypeB2bCategories.getPlaceholder());
                }
                if (marketSegmentationTypeB2bCategories.getUniqueCharacteristic() != null) {
                    existingMarketSegmentationTypeB2bCategories.setUniqueCharacteristic(
                        marketSegmentationTypeB2bCategories.getUniqueCharacteristic()
                    );
                }
                if (marketSegmentationTypeB2bCategories.getLanguage() != null) {
                    existingMarketSegmentationTypeB2bCategories.setLanguage(marketSegmentationTypeB2bCategories.getLanguage());
                }

                return existingMarketSegmentationTypeB2bCategories;
            })
            .map(marketSegmentationTypeB2bCategoriesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketSegmentationTypeB2bCategories> findAll(Pageable pageable) {
        log.debug("Request to get all MarketSegmentationTypeB2bCategories");
        return marketSegmentationTypeB2bCategoriesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketSegmentationTypeB2bCategories> findOne(Long id) {
        log.debug("Request to get MarketSegmentationTypeB2bCategories : {}", id);
        return marketSegmentationTypeB2bCategoriesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketSegmentationTypeB2bCategories : {}", id);
        marketSegmentationTypeB2bCategoriesRepository.deleteById(id);
    }
}
