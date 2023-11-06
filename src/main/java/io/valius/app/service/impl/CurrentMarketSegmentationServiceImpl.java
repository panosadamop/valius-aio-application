package io.valius.app.service.impl;

import io.valius.app.domain.CurrentMarketSegmentation;
import io.valius.app.repository.CurrentMarketSegmentationRepository;
import io.valius.app.service.CurrentMarketSegmentationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CurrentMarketSegmentation}.
 */
@Service
@Transactional
public class CurrentMarketSegmentationServiceImpl implements CurrentMarketSegmentationService {

    private final Logger log = LoggerFactory.getLogger(CurrentMarketSegmentationServiceImpl.class);

    private final CurrentMarketSegmentationRepository currentMarketSegmentationRepository;

    public CurrentMarketSegmentationServiceImpl(CurrentMarketSegmentationRepository currentMarketSegmentationRepository) {
        this.currentMarketSegmentationRepository = currentMarketSegmentationRepository;
    }

    @Override
    public CurrentMarketSegmentation save(CurrentMarketSegmentation currentMarketSegmentation) {
        log.debug("Request to save CurrentMarketSegmentation : {}", currentMarketSegmentation);
        return currentMarketSegmentationRepository.save(currentMarketSegmentation);
    }

    @Override
    public CurrentMarketSegmentation update(CurrentMarketSegmentation currentMarketSegmentation) {
        log.debug("Request to update CurrentMarketSegmentation : {}", currentMarketSegmentation);
        return currentMarketSegmentationRepository.save(currentMarketSegmentation);
    }

    @Override
    public Optional<CurrentMarketSegmentation> partialUpdate(CurrentMarketSegmentation currentMarketSegmentation) {
        log.debug("Request to partially update CurrentMarketSegmentation : {}", currentMarketSegmentation);

        return currentMarketSegmentationRepository
            .findById(currentMarketSegmentation.getId())
            .map(existingCurrentMarketSegmentation -> {
                if (currentMarketSegmentation.getValue() != null) {
                    existingCurrentMarketSegmentation.setValue(currentMarketSegmentation.getValue());
                }
                if (currentMarketSegmentation.getDescription() != null) {
                    existingCurrentMarketSegmentation.setDescription(currentMarketSegmentation.getDescription());
                }
                if (currentMarketSegmentation.getLanguage() != null) {
                    existingCurrentMarketSegmentation.setLanguage(currentMarketSegmentation.getLanguage());
                }

                return existingCurrentMarketSegmentation;
            })
            .map(currentMarketSegmentationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurrentMarketSegmentation> findAll(Pageable pageable) {
        log.debug("Request to get all CurrentMarketSegmentations");
        return currentMarketSegmentationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CurrentMarketSegmentation> findOne(Long id) {
        log.debug("Request to get CurrentMarketSegmentation : {}", id);
        return currentMarketSegmentationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrentMarketSegmentation : {}", id);
        currentMarketSegmentationRepository.deleteById(id);
    }
}
