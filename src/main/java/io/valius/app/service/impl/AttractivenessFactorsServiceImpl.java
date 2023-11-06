package io.valius.app.service.impl;

import io.valius.app.domain.AttractivenessFactors;
import io.valius.app.repository.AttractivenessFactorsRepository;
import io.valius.app.service.AttractivenessFactorsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttractivenessFactors}.
 */
@Service
@Transactional
public class AttractivenessFactorsServiceImpl implements AttractivenessFactorsService {

    private final Logger log = LoggerFactory.getLogger(AttractivenessFactorsServiceImpl.class);

    private final AttractivenessFactorsRepository attractivenessFactorsRepository;

    public AttractivenessFactorsServiceImpl(AttractivenessFactorsRepository attractivenessFactorsRepository) {
        this.attractivenessFactorsRepository = attractivenessFactorsRepository;
    }

    @Override
    public AttractivenessFactors save(AttractivenessFactors attractivenessFactors) {
        log.debug("Request to save AttractivenessFactors : {}", attractivenessFactors);
        return attractivenessFactorsRepository.save(attractivenessFactors);
    }

    @Override
    public AttractivenessFactors update(AttractivenessFactors attractivenessFactors) {
        log.debug("Request to update AttractivenessFactors : {}", attractivenessFactors);
        return attractivenessFactorsRepository.save(attractivenessFactors);
    }

    @Override
    public Optional<AttractivenessFactors> partialUpdate(AttractivenessFactors attractivenessFactors) {
        log.debug("Request to partially update AttractivenessFactors : {}", attractivenessFactors);

        return attractivenessFactorsRepository
            .findById(attractivenessFactors.getId())
            .map(existingAttractivenessFactors -> {
                if (attractivenessFactors.getValue() != null) {
                    existingAttractivenessFactors.setValue(attractivenessFactors.getValue());
                }
                if (attractivenessFactors.getDescription() != null) {
                    existingAttractivenessFactors.setDescription(attractivenessFactors.getDescription());
                }
                if (attractivenessFactors.getLanguage() != null) {
                    existingAttractivenessFactors.setLanguage(attractivenessFactors.getLanguage());
                }

                return existingAttractivenessFactors;
            })
            .map(attractivenessFactorsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttractivenessFactors> findAll(Pageable pageable) {
        log.debug("Request to get all AttractivenessFactors");
        return attractivenessFactorsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttractivenessFactors> findOne(Long id) {
        log.debug("Request to get AttractivenessFactors : {}", id);
        return attractivenessFactorsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttractivenessFactors : {}", id);
        attractivenessFactorsRepository.deleteById(id);
    }
}
