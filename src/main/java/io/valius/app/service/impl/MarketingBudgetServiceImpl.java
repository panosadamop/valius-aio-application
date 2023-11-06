package io.valius.app.service.impl;

import io.valius.app.domain.MarketingBudget;
import io.valius.app.repository.MarketingBudgetRepository;
import io.valius.app.service.MarketingBudgetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketingBudget}.
 */
@Service
@Transactional
public class MarketingBudgetServiceImpl implements MarketingBudgetService {

    private final Logger log = LoggerFactory.getLogger(MarketingBudgetServiceImpl.class);

    private final MarketingBudgetRepository marketingBudgetRepository;

    public MarketingBudgetServiceImpl(MarketingBudgetRepository marketingBudgetRepository) {
        this.marketingBudgetRepository = marketingBudgetRepository;
    }

    @Override
    public MarketingBudget save(MarketingBudget marketingBudget) {
        log.debug("Request to save MarketingBudget : {}", marketingBudget);
        return marketingBudgetRepository.save(marketingBudget);
    }

    @Override
    public MarketingBudget update(MarketingBudget marketingBudget) {
        log.debug("Request to update MarketingBudget : {}", marketingBudget);
        return marketingBudgetRepository.save(marketingBudget);
    }

    @Override
    public Optional<MarketingBudget> partialUpdate(MarketingBudget marketingBudget) {
        log.debug("Request to partially update MarketingBudget : {}", marketingBudget);

        return marketingBudgetRepository
            .findById(marketingBudget.getId())
            .map(existingMarketingBudget -> {
                if (marketingBudget.getValue() != null) {
                    existingMarketingBudget.setValue(marketingBudget.getValue());
                }
                if (marketingBudget.getDescription() != null) {
                    existingMarketingBudget.setDescription(marketingBudget.getDescription());
                }
                if (marketingBudget.getLanguage() != null) {
                    existingMarketingBudget.setLanguage(marketingBudget.getLanguage());
                }

                return existingMarketingBudget;
            })
            .map(marketingBudgetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketingBudget> findAll(Pageable pageable) {
        log.debug("Request to get all MarketingBudgets");
        return marketingBudgetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketingBudget> findOne(Long id) {
        log.debug("Request to get MarketingBudget : {}", id);
        return marketingBudgetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketingBudget : {}", id);
        marketingBudgetRepository.deleteById(id);
    }
}
