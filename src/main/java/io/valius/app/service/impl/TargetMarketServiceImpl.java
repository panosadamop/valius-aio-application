package io.valius.app.service.impl;

import io.valius.app.domain.TargetMarket;
import io.valius.app.repository.TargetMarketRepository;
import io.valius.app.service.TargetMarketService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TargetMarket}.
 */
@Service
@Transactional
public class TargetMarketServiceImpl implements TargetMarketService {

    private final Logger log = LoggerFactory.getLogger(TargetMarketServiceImpl.class);

    private final TargetMarketRepository targetMarketRepository;

    public TargetMarketServiceImpl(TargetMarketRepository targetMarketRepository) {
        this.targetMarketRepository = targetMarketRepository;
    }

    @Override
    public TargetMarket save(TargetMarket targetMarket) {
        log.debug("Request to save TargetMarket : {}", targetMarket);
        return targetMarketRepository.save(targetMarket);
    }

    @Override
    public TargetMarket update(TargetMarket targetMarket) {
        log.debug("Request to update TargetMarket : {}", targetMarket);
        return targetMarketRepository.save(targetMarket);
    }

    @Override
    public Optional<TargetMarket> partialUpdate(TargetMarket targetMarket) {
        log.debug("Request to partially update TargetMarket : {}", targetMarket);

        return targetMarketRepository
            .findById(targetMarket.getId())
            .map(existingTargetMarket -> {
                if (targetMarket.getValue() != null) {
                    existingTargetMarket.setValue(targetMarket.getValue());
                }
                if (targetMarket.getDescription() != null) {
                    existingTargetMarket.setDescription(targetMarket.getDescription());
                }
                if (targetMarket.getLanguage() != null) {
                    existingTargetMarket.setLanguage(targetMarket.getLanguage());
                }

                return existingTargetMarket;
            })
            .map(targetMarketRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TargetMarket> findAll(Pageable pageable) {
        log.debug("Request to get all TargetMarkets");
        return targetMarketRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TargetMarket> findOne(Long id) {
        log.debug("Request to get TargetMarket : {}", id);
        return targetMarketRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TargetMarket : {}", id);
        targetMarketRepository.deleteById(id);
    }
}
