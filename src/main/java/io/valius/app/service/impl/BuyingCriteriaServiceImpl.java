package io.valius.app.service.impl;

import io.valius.app.domain.BuyingCriteria;
import io.valius.app.repository.BuyingCriteriaRepository;
import io.valius.app.service.BuyingCriteriaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuyingCriteria}.
 */
@Service
@Transactional
public class BuyingCriteriaServiceImpl implements BuyingCriteriaService {

    private final Logger log = LoggerFactory.getLogger(BuyingCriteriaServiceImpl.class);

    private final BuyingCriteriaRepository buyingCriteriaRepository;

    public BuyingCriteriaServiceImpl(BuyingCriteriaRepository buyingCriteriaRepository) {
        this.buyingCriteriaRepository = buyingCriteriaRepository;
    }

    @Override
    public BuyingCriteria save(BuyingCriteria buyingCriteria) {
        log.debug("Request to save BuyingCriteria : {}", buyingCriteria);
        return buyingCriteriaRepository.save(buyingCriteria);
    }

    @Override
    public BuyingCriteria update(BuyingCriteria buyingCriteria) {
        log.debug("Request to update BuyingCriteria : {}", buyingCriteria);
        return buyingCriteriaRepository.save(buyingCriteria);
    }

    @Override
    public Optional<BuyingCriteria> partialUpdate(BuyingCriteria buyingCriteria) {
        log.debug("Request to partially update BuyingCriteria : {}", buyingCriteria);

        return buyingCriteriaRepository
            .findById(buyingCriteria.getId())
            .map(existingBuyingCriteria -> {
                if (buyingCriteria.getValue() != null) {
                    existingBuyingCriteria.setValue(buyingCriteria.getValue());
                }
                if (buyingCriteria.getDescription() != null) {
                    existingBuyingCriteria.setDescription(buyingCriteria.getDescription());
                }
                if (buyingCriteria.getLanguage() != null) {
                    existingBuyingCriteria.setLanguage(buyingCriteria.getLanguage());
                }

                return existingBuyingCriteria;
            })
            .map(buyingCriteriaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyingCriteria> findAll(Pageable pageable) {
        log.debug("Request to get all BuyingCriteria");
        return buyingCriteriaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyingCriteria> findOne(Long id) {
        log.debug("Request to get BuyingCriteria : {}", id);
        return buyingCriteriaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuyingCriteria : {}", id);
        buyingCriteriaRepository.deleteById(id);
    }
}
