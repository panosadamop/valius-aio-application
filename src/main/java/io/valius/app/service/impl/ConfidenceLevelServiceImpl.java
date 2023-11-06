package io.valius.app.service.impl;

import io.valius.app.domain.ConfidenceLevel;
import io.valius.app.repository.ConfidenceLevelRepository;
import io.valius.app.service.ConfidenceLevelService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConfidenceLevel}.
 */
@Service
@Transactional
public class ConfidenceLevelServiceImpl implements ConfidenceLevelService {

    private final Logger log = LoggerFactory.getLogger(ConfidenceLevelServiceImpl.class);

    private final ConfidenceLevelRepository confidenceLevelRepository;

    public ConfidenceLevelServiceImpl(ConfidenceLevelRepository confidenceLevelRepository) {
        this.confidenceLevelRepository = confidenceLevelRepository;
    }

    @Override
    public ConfidenceLevel save(ConfidenceLevel confidenceLevel) {
        log.debug("Request to save ConfidenceLevel : {}", confidenceLevel);
        return confidenceLevelRepository.save(confidenceLevel);
    }

    @Override
    public ConfidenceLevel update(ConfidenceLevel confidenceLevel) {
        log.debug("Request to update ConfidenceLevel : {}", confidenceLevel);
        return confidenceLevelRepository.save(confidenceLevel);
    }

    @Override
    public Optional<ConfidenceLevel> partialUpdate(ConfidenceLevel confidenceLevel) {
        log.debug("Request to partially update ConfidenceLevel : {}", confidenceLevel);

        return confidenceLevelRepository
            .findById(confidenceLevel.getId())
            .map(existingConfidenceLevel -> {
                if (confidenceLevel.getValue() != null) {
                    existingConfidenceLevel.setValue(confidenceLevel.getValue());
                }
                if (confidenceLevel.getDescription() != null) {
                    existingConfidenceLevel.setDescription(confidenceLevel.getDescription());
                }
                if (confidenceLevel.getLanguage() != null) {
                    existingConfidenceLevel.setLanguage(confidenceLevel.getLanguage());
                }

                return existingConfidenceLevel;
            })
            .map(confidenceLevelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfidenceLevel> findAll(Pageable pageable) {
        log.debug("Request to get all ConfidenceLevels");
        return confidenceLevelRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfidenceLevel> findOne(Long id) {
        log.debug("Request to get ConfidenceLevel : {}", id);
        return confidenceLevelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfidenceLevel : {}", id);
        confidenceLevelRepository.deleteById(id);
    }
}
