package io.valius.app.service.impl;

import io.valius.app.domain.MarketSegmentationTypeB2b;
import io.valius.app.repository.MarketSegmentationTypeB2bRepository;
import io.valius.app.service.MarketSegmentationTypeB2bService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketSegmentationTypeB2b}.
 */
@Service
@Transactional
public class MarketSegmentationTypeB2bServiceImpl implements MarketSegmentationTypeB2bService {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2bServiceImpl.class);

    private final MarketSegmentationTypeB2bRepository marketSegmentationTypeB2bRepository;

    public MarketSegmentationTypeB2bServiceImpl(MarketSegmentationTypeB2bRepository marketSegmentationTypeB2bRepository) {
        this.marketSegmentationTypeB2bRepository = marketSegmentationTypeB2bRepository;
    }

    @Override
    public MarketSegmentationTypeB2b save(MarketSegmentationTypeB2b marketSegmentationTypeB2b) {
        log.debug("Request to save MarketSegmentationTypeB2b : {}", marketSegmentationTypeB2b);
        return marketSegmentationTypeB2bRepository.save(marketSegmentationTypeB2b);
    }

    @Override
    public MarketSegmentationTypeB2b update(MarketSegmentationTypeB2b marketSegmentationTypeB2b) {
        log.debug("Request to update MarketSegmentationTypeB2b : {}", marketSegmentationTypeB2b);
        return marketSegmentationTypeB2bRepository.save(marketSegmentationTypeB2b);
    }

    @Override
    public Optional<MarketSegmentationTypeB2b> partialUpdate(MarketSegmentationTypeB2b marketSegmentationTypeB2b) {
        log.debug("Request to partially update MarketSegmentationTypeB2b : {}", marketSegmentationTypeB2b);

        return marketSegmentationTypeB2bRepository
            .findById(marketSegmentationTypeB2b.getId())
            .map(existingMarketSegmentationTypeB2b -> {
                if (marketSegmentationTypeB2b.getValue() != null) {
                    existingMarketSegmentationTypeB2b.setValue(marketSegmentationTypeB2b.getValue());
                }
                if (marketSegmentationTypeB2b.getDescription() != null) {
                    existingMarketSegmentationTypeB2b.setDescription(marketSegmentationTypeB2b.getDescription());
                }
                if (marketSegmentationTypeB2b.getLanguage() != null) {
                    existingMarketSegmentationTypeB2b.setLanguage(marketSegmentationTypeB2b.getLanguage());
                }

                return existingMarketSegmentationTypeB2b;
            })
            .map(marketSegmentationTypeB2bRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketSegmentationTypeB2b> findAll(Pageable pageable) {
        log.debug("Request to get all MarketSegmentationTypeB2bs");
        return marketSegmentationTypeB2bRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketSegmentationTypeB2b> findOne(Long id) {
        log.debug("Request to get MarketSegmentationTypeB2b : {}", id);
        return marketSegmentationTypeB2bRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketSegmentationTypeB2b : {}", id);
        marketSegmentationTypeB2bRepository.deleteById(id);
    }
}
