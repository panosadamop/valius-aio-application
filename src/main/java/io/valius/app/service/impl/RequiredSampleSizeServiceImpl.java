package io.valius.app.service.impl;

import io.valius.app.domain.RequiredSampleSize;
import io.valius.app.repository.RequiredSampleSizeRepository;
import io.valius.app.service.RequiredSampleSizeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequiredSampleSize}.
 */
@Service
@Transactional
public class RequiredSampleSizeServiceImpl implements RequiredSampleSizeService {

    private final Logger log = LoggerFactory.getLogger(RequiredSampleSizeServiceImpl.class);

    private final RequiredSampleSizeRepository requiredSampleSizeRepository;

    public RequiredSampleSizeServiceImpl(RequiredSampleSizeRepository requiredSampleSizeRepository) {
        this.requiredSampleSizeRepository = requiredSampleSizeRepository;
    }

    @Override
    public RequiredSampleSize save(RequiredSampleSize requiredSampleSize) {
        log.debug("Request to save RequiredSampleSize : {}", requiredSampleSize);
        return requiredSampleSizeRepository.save(requiredSampleSize);
    }

    @Override
    public RequiredSampleSize update(RequiredSampleSize requiredSampleSize) {
        log.debug("Request to update RequiredSampleSize : {}", requiredSampleSize);
        return requiredSampleSizeRepository.save(requiredSampleSize);
    }

    @Override
    public Optional<RequiredSampleSize> partialUpdate(RequiredSampleSize requiredSampleSize) {
        log.debug("Request to partially update RequiredSampleSize : {}", requiredSampleSize);

        return requiredSampleSizeRepository
            .findById(requiredSampleSize.getId())
            .map(existingRequiredSampleSize -> {
                if (requiredSampleSize.getValue() != null) {
                    existingRequiredSampleSize.setValue(requiredSampleSize.getValue());
                }
                if (requiredSampleSize.getLanguage() != null) {
                    existingRequiredSampleSize.setLanguage(requiredSampleSize.getLanguage());
                }

                return existingRequiredSampleSize;
            })
            .map(requiredSampleSizeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequiredSampleSize> findAll(Pageable pageable) {
        log.debug("Request to get all RequiredSampleSizes");
        return requiredSampleSizeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequiredSampleSize> findOne(Long id) {
        log.debug("Request to get RequiredSampleSize : {}", id);
        return requiredSampleSizeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequiredSampleSize : {}", id);
        requiredSampleSizeRepository.deleteById(id);
    }
}
