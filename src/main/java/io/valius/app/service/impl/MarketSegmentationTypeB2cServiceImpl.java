package io.valius.app.service.impl;

import io.valius.app.domain.MarketSegmentationTypeB2c;
import io.valius.app.repository.MarketSegmentationTypeB2cRepository;
import io.valius.app.service.MarketSegmentationTypeB2cService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketSegmentationTypeB2c}.
 */
@Service
@Transactional
public class MarketSegmentationTypeB2cServiceImpl implements MarketSegmentationTypeB2cService {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2cServiceImpl.class);

    private final MarketSegmentationTypeB2cRepository marketSegmentationTypeB2cRepository;

    public MarketSegmentationTypeB2cServiceImpl(MarketSegmentationTypeB2cRepository marketSegmentationTypeB2cRepository) {
        this.marketSegmentationTypeB2cRepository = marketSegmentationTypeB2cRepository;
    }

    @Override
    public MarketSegmentationTypeB2c save(MarketSegmentationTypeB2c marketSegmentationTypeB2c) {
        log.debug("Request to save MarketSegmentationTypeB2c : {}", marketSegmentationTypeB2c);
        return marketSegmentationTypeB2cRepository.save(marketSegmentationTypeB2c);
    }

    @Override
    public MarketSegmentationTypeB2c update(MarketSegmentationTypeB2c marketSegmentationTypeB2c) {
        log.debug("Request to update MarketSegmentationTypeB2c : {}", marketSegmentationTypeB2c);
        return marketSegmentationTypeB2cRepository.save(marketSegmentationTypeB2c);
    }

    @Override
    public Optional<MarketSegmentationTypeB2c> partialUpdate(MarketSegmentationTypeB2c marketSegmentationTypeB2c) {
        log.debug("Request to partially update MarketSegmentationTypeB2c : {}", marketSegmentationTypeB2c);

        return marketSegmentationTypeB2cRepository
            .findById(marketSegmentationTypeB2c.getId())
            .map(existingMarketSegmentationTypeB2c -> {
                if (marketSegmentationTypeB2c.getValue() != null) {
                    existingMarketSegmentationTypeB2c.setValue(marketSegmentationTypeB2c.getValue());
                }
                if (marketSegmentationTypeB2c.getDescription() != null) {
                    existingMarketSegmentationTypeB2c.setDescription(marketSegmentationTypeB2c.getDescription());
                }
                if (marketSegmentationTypeB2c.getLanguage() != null) {
                    existingMarketSegmentationTypeB2c.setLanguage(marketSegmentationTypeB2c.getLanguage());
                }

                return existingMarketSegmentationTypeB2c;
            })
            .map(marketSegmentationTypeB2cRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketSegmentationTypeB2c> findAll(Pageable pageable) {
        log.debug("Request to get all MarketSegmentationTypeB2cs");
        return marketSegmentationTypeB2cRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketSegmentationTypeB2c> findOne(Long id) {
        log.debug("Request to get MarketSegmentationTypeB2c : {}", id);
        return marketSegmentationTypeB2cRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketSegmentationTypeB2c : {}", id);
        marketSegmentationTypeB2cRepository.deleteById(id);
    }
}
