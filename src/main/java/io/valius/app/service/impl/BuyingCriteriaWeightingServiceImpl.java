package io.valius.app.service.impl;

import io.valius.app.domain.BuyingCriteriaWeighting;
import io.valius.app.repository.BuyingCriteriaWeightingRepository;
import io.valius.app.service.BuyingCriteriaWeightingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuyingCriteriaWeighting}.
 */
@Service
@Transactional
public class BuyingCriteriaWeightingServiceImpl implements BuyingCriteriaWeightingService {

    private final Logger log = LoggerFactory.getLogger(BuyingCriteriaWeightingServiceImpl.class);

    private final BuyingCriteriaWeightingRepository buyingCriteriaWeightingRepository;

    public BuyingCriteriaWeightingServiceImpl(BuyingCriteriaWeightingRepository buyingCriteriaWeightingRepository) {
        this.buyingCriteriaWeightingRepository = buyingCriteriaWeightingRepository;
    }

    @Override
    public BuyingCriteriaWeighting save(BuyingCriteriaWeighting buyingCriteriaWeighting) {
        log.debug("Request to save BuyingCriteriaWeighting : {}", buyingCriteriaWeighting);
        return buyingCriteriaWeightingRepository.save(buyingCriteriaWeighting);
    }

    @Override
    public BuyingCriteriaWeighting update(BuyingCriteriaWeighting buyingCriteriaWeighting) {
        log.debug("Request to update BuyingCriteriaWeighting : {}", buyingCriteriaWeighting);
        return buyingCriteriaWeightingRepository.save(buyingCriteriaWeighting);
    }

    @Override
    public Optional<BuyingCriteriaWeighting> partialUpdate(BuyingCriteriaWeighting buyingCriteriaWeighting) {
        log.debug("Request to partially update BuyingCriteriaWeighting : {}", buyingCriteriaWeighting);

        return buyingCriteriaWeightingRepository
            .findById(buyingCriteriaWeighting.getId())
            .map(existingBuyingCriteriaWeighting -> {
                if (buyingCriteriaWeighting.getValue() != null) {
                    existingBuyingCriteriaWeighting.setValue(buyingCriteriaWeighting.getValue());
                }
                if (buyingCriteriaWeighting.getDescription() != null) {
                    existingBuyingCriteriaWeighting.setDescription(buyingCriteriaWeighting.getDescription());
                }
                if (buyingCriteriaWeighting.getLanguage() != null) {
                    existingBuyingCriteriaWeighting.setLanguage(buyingCriteriaWeighting.getLanguage());
                }

                return existingBuyingCriteriaWeighting;
            })
            .map(buyingCriteriaWeightingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyingCriteriaWeighting> findAll(Pageable pageable) {
        log.debug("Request to get all BuyingCriteriaWeightings");
        return buyingCriteriaWeightingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyingCriteriaWeighting> findOne(Long id) {
        log.debug("Request to get BuyingCriteriaWeighting : {}", id);
        return buyingCriteriaWeightingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuyingCriteriaWeighting : {}", id);
        buyingCriteriaWeightingRepository.deleteById(id);
    }
}
