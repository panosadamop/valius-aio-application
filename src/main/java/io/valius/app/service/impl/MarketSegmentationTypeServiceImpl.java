package io.valius.app.service.impl;

import io.valius.app.domain.MarketSegmentationType;
import io.valius.app.repository.MarketSegmentationTypeRepository;
import io.valius.app.service.MarketSegmentationTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketSegmentationType}.
 */
@Service
@Transactional
public class MarketSegmentationTypeServiceImpl implements MarketSegmentationTypeService {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeServiceImpl.class);

    private final MarketSegmentationTypeRepository marketSegmentationTypeRepository;

    public MarketSegmentationTypeServiceImpl(MarketSegmentationTypeRepository marketSegmentationTypeRepository) {
        this.marketSegmentationTypeRepository = marketSegmentationTypeRepository;
    }

    @Override
    public MarketSegmentationType save(MarketSegmentationType marketSegmentationType) {
        log.debug("Request to save MarketSegmentationType : {}", marketSegmentationType);
        return marketSegmentationTypeRepository.save(marketSegmentationType);
    }

    @Override
    public MarketSegmentationType update(MarketSegmentationType marketSegmentationType) {
        log.debug("Request to update MarketSegmentationType : {}", marketSegmentationType);
        return marketSegmentationTypeRepository.save(marketSegmentationType);
    }

    @Override
    public Optional<MarketSegmentationType> partialUpdate(MarketSegmentationType marketSegmentationType) {
        log.debug("Request to partially update MarketSegmentationType : {}", marketSegmentationType);

        return marketSegmentationTypeRepository
            .findById(marketSegmentationType.getId())
            .map(existingMarketSegmentationType -> {
                if (marketSegmentationType.getValue() != null) {
                    existingMarketSegmentationType.setValue(marketSegmentationType.getValue());
                }
                if (marketSegmentationType.getDescription() != null) {
                    existingMarketSegmentationType.setDescription(marketSegmentationType.getDescription());
                }
                if (marketSegmentationType.getLanguage() != null) {
                    existingMarketSegmentationType.setLanguage(marketSegmentationType.getLanguage());
                }

                return existingMarketSegmentationType;
            })
            .map(marketSegmentationTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketSegmentationType> findAll(Pageable pageable) {
        log.debug("Request to get all MarketSegmentationTypes");
        return marketSegmentationTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketSegmentationType> findOne(Long id) {
        log.debug("Request to get MarketSegmentationType : {}", id);
        return marketSegmentationTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketSegmentationType : {}", id);
        marketSegmentationTypeRepository.deleteById(id);
    }
}
