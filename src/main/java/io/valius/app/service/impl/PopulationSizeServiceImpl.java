package io.valius.app.service.impl;

import io.valius.app.domain.PopulationSize;
import io.valius.app.repository.PopulationSizeRepository;
import io.valius.app.service.PopulationSizeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PopulationSize}.
 */
@Service
@Transactional
public class PopulationSizeServiceImpl implements PopulationSizeService {

    private final Logger log = LoggerFactory.getLogger(PopulationSizeServiceImpl.class);

    private final PopulationSizeRepository populationSizeRepository;

    public PopulationSizeServiceImpl(PopulationSizeRepository populationSizeRepository) {
        this.populationSizeRepository = populationSizeRepository;
    }

    @Override
    public PopulationSize save(PopulationSize populationSize) {
        log.debug("Request to save PopulationSize : {}", populationSize);
        return populationSizeRepository.save(populationSize);
    }

    @Override
    public PopulationSize update(PopulationSize populationSize) {
        log.debug("Request to update PopulationSize : {}", populationSize);
        return populationSizeRepository.save(populationSize);
    }

    @Override
    public Optional<PopulationSize> partialUpdate(PopulationSize populationSize) {
        log.debug("Request to partially update PopulationSize : {}", populationSize);

        return populationSizeRepository
            .findById(populationSize.getId())
            .map(existingPopulationSize -> {
                if (populationSize.getValue() != null) {
                    existingPopulationSize.setValue(populationSize.getValue());
                }
                if (populationSize.getDescription() != null) {
                    existingPopulationSize.setDescription(populationSize.getDescription());
                }
                if (populationSize.getLanguage() != null) {
                    existingPopulationSize.setLanguage(populationSize.getLanguage());
                }

                return existingPopulationSize;
            })
            .map(populationSizeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PopulationSize> findAll(Pageable pageable) {
        log.debug("Request to get all PopulationSizes");
        return populationSizeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PopulationSize> findOne(Long id) {
        log.debug("Request to get PopulationSize : {}", id);
        return populationSizeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PopulationSize : {}", id);
        populationSizeRepository.deleteById(id);
    }
}
