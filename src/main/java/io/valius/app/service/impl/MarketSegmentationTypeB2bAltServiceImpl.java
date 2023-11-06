package io.valius.app.service.impl;

import io.valius.app.domain.MarketSegmentationTypeB2bAlt;
import io.valius.app.repository.MarketSegmentationTypeB2bAltRepository;
import io.valius.app.service.MarketSegmentationTypeB2bAltService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketSegmentationTypeB2bAlt}.
 */
@Service
@Transactional
public class MarketSegmentationTypeB2bAltServiceImpl implements MarketSegmentationTypeB2bAltService {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2bAltServiceImpl.class);

    private final MarketSegmentationTypeB2bAltRepository marketSegmentationTypeB2bAltRepository;

    public MarketSegmentationTypeB2bAltServiceImpl(MarketSegmentationTypeB2bAltRepository marketSegmentationTypeB2bAltRepository) {
        this.marketSegmentationTypeB2bAltRepository = marketSegmentationTypeB2bAltRepository;
    }

    @Override
    public MarketSegmentationTypeB2bAlt save(MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt) {
        log.debug("Request to save MarketSegmentationTypeB2bAlt : {}", marketSegmentationTypeB2bAlt);
        return marketSegmentationTypeB2bAltRepository.save(marketSegmentationTypeB2bAlt);
    }

    @Override
    public MarketSegmentationTypeB2bAlt update(MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt) {
        log.debug("Request to update MarketSegmentationTypeB2bAlt : {}", marketSegmentationTypeB2bAlt);
        return marketSegmentationTypeB2bAltRepository.save(marketSegmentationTypeB2bAlt);
    }

    @Override
    public Optional<MarketSegmentationTypeB2bAlt> partialUpdate(MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt) {
        log.debug("Request to partially update MarketSegmentationTypeB2bAlt : {}", marketSegmentationTypeB2bAlt);

        return marketSegmentationTypeB2bAltRepository
            .findById(marketSegmentationTypeB2bAlt.getId())
            .map(existingMarketSegmentationTypeB2bAlt -> {
                if (marketSegmentationTypeB2bAlt.getValue() != null) {
                    existingMarketSegmentationTypeB2bAlt.setValue(marketSegmentationTypeB2bAlt.getValue());
                }
                if (marketSegmentationTypeB2bAlt.getDescription() != null) {
                    existingMarketSegmentationTypeB2bAlt.setDescription(marketSegmentationTypeB2bAlt.getDescription());
                }
                if (marketSegmentationTypeB2bAlt.getLanguage() != null) {
                    existingMarketSegmentationTypeB2bAlt.setLanguage(marketSegmentationTypeB2bAlt.getLanguage());
                }

                return existingMarketSegmentationTypeB2bAlt;
            })
            .map(marketSegmentationTypeB2bAltRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketSegmentationTypeB2bAlt> findAll(Pageable pageable) {
        log.debug("Request to get all MarketSegmentationTypeB2bAlts");
        return marketSegmentationTypeB2bAltRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketSegmentationTypeB2bAlt> findOne(Long id) {
        log.debug("Request to get MarketSegmentationTypeB2bAlt : {}", id);
        return marketSegmentationTypeB2bAltRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketSegmentationTypeB2bAlt : {}", id);
        marketSegmentationTypeB2bAltRepository.deleteById(id);
    }
}
