package io.valius.app.service.impl;

import io.valius.app.domain.MarketSegmentationTypeB2cAlt;
import io.valius.app.repository.MarketSegmentationTypeB2cAltRepository;
import io.valius.app.service.MarketSegmentationTypeB2cAltService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketSegmentationTypeB2cAlt}.
 */
@Service
@Transactional
public class MarketSegmentationTypeB2cAltServiceImpl implements MarketSegmentationTypeB2cAltService {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2cAltServiceImpl.class);

    private final MarketSegmentationTypeB2cAltRepository marketSegmentationTypeB2cAltRepository;

    public MarketSegmentationTypeB2cAltServiceImpl(MarketSegmentationTypeB2cAltRepository marketSegmentationTypeB2cAltRepository) {
        this.marketSegmentationTypeB2cAltRepository = marketSegmentationTypeB2cAltRepository;
    }

    @Override
    public MarketSegmentationTypeB2cAlt save(MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt) {
        log.debug("Request to save MarketSegmentationTypeB2cAlt : {}", marketSegmentationTypeB2cAlt);
        return marketSegmentationTypeB2cAltRepository.save(marketSegmentationTypeB2cAlt);
    }

    @Override
    public MarketSegmentationTypeB2cAlt update(MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt) {
        log.debug("Request to update MarketSegmentationTypeB2cAlt : {}", marketSegmentationTypeB2cAlt);
        return marketSegmentationTypeB2cAltRepository.save(marketSegmentationTypeB2cAlt);
    }

    @Override
    public Optional<MarketSegmentationTypeB2cAlt> partialUpdate(MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt) {
        log.debug("Request to partially update MarketSegmentationTypeB2cAlt : {}", marketSegmentationTypeB2cAlt);

        return marketSegmentationTypeB2cAltRepository
            .findById(marketSegmentationTypeB2cAlt.getId())
            .map(existingMarketSegmentationTypeB2cAlt -> {
                if (marketSegmentationTypeB2cAlt.getValue() != null) {
                    existingMarketSegmentationTypeB2cAlt.setValue(marketSegmentationTypeB2cAlt.getValue());
                }
                if (marketSegmentationTypeB2cAlt.getDescription() != null) {
                    existingMarketSegmentationTypeB2cAlt.setDescription(marketSegmentationTypeB2cAlt.getDescription());
                }
                if (marketSegmentationTypeB2cAlt.getLanguage() != null) {
                    existingMarketSegmentationTypeB2cAlt.setLanguage(marketSegmentationTypeB2cAlt.getLanguage());
                }

                return existingMarketSegmentationTypeB2cAlt;
            })
            .map(marketSegmentationTypeB2cAltRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketSegmentationTypeB2cAlt> findAll(Pageable pageable) {
        log.debug("Request to get all MarketSegmentationTypeB2cAlts");
        return marketSegmentationTypeB2cAltRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketSegmentationTypeB2cAlt> findOne(Long id) {
        log.debug("Request to get MarketSegmentationTypeB2cAlt : {}", id);
        return marketSegmentationTypeB2cAltRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketSegmentationTypeB2cAlt : {}", id);
        marketSegmentationTypeB2cAltRepository.deleteById(id);
    }
}
