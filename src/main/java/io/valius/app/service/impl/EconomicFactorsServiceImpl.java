package io.valius.app.service.impl;

import io.valius.app.domain.EconomicFactors;
import io.valius.app.repository.EconomicFactorsRepository;
import io.valius.app.service.EconomicFactorsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EconomicFactors}.
 */
@Service
@Transactional
public class EconomicFactorsServiceImpl implements EconomicFactorsService {

    private final Logger log = LoggerFactory.getLogger(EconomicFactorsServiceImpl.class);

    private final EconomicFactorsRepository economicFactorsRepository;

    public EconomicFactorsServiceImpl(EconomicFactorsRepository economicFactorsRepository) {
        this.economicFactorsRepository = economicFactorsRepository;
    }

    @Override
    public EconomicFactors save(EconomicFactors economicFactors) {
        log.debug("Request to save EconomicFactors : {}", economicFactors);
        return economicFactorsRepository.save(economicFactors);
    }

    @Override
    public EconomicFactors update(EconomicFactors economicFactors) {
        log.debug("Request to update EconomicFactors : {}", economicFactors);
        return economicFactorsRepository.save(economicFactors);
    }

    @Override
    public Optional<EconomicFactors> partialUpdate(EconomicFactors economicFactors) {
        log.debug("Request to partially update EconomicFactors : {}", economicFactors);

        return economicFactorsRepository
            .findById(economicFactors.getId())
            .map(existingEconomicFactors -> {
                if (economicFactors.getValue() != null) {
                    existingEconomicFactors.setValue(economicFactors.getValue());
                }
                if (economicFactors.getDescription() != null) {
                    existingEconomicFactors.setDescription(economicFactors.getDescription());
                }
                if (economicFactors.getLanguage() != null) {
                    existingEconomicFactors.setLanguage(economicFactors.getLanguage());
                }

                return existingEconomicFactors;
            })
            .map(economicFactorsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EconomicFactors> findAll(Pageable pageable) {
        log.debug("Request to get all EconomicFactors");
        return economicFactorsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EconomicFactors> findOne(Long id) {
        log.debug("Request to get EconomicFactors : {}", id);
        return economicFactorsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EconomicFactors : {}", id);
        economicFactorsRepository.deleteById(id);
    }
}
