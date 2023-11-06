package io.valius.app.service.impl;

import io.valius.app.domain.FieldBuyingCriteriaWeighting;
import io.valius.app.repository.FieldBuyingCriteriaWeightingRepository;
import io.valius.app.service.FieldBuyingCriteriaWeightingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldBuyingCriteriaWeighting}.
 */
@Service
@Transactional
public class FieldBuyingCriteriaWeightingServiceImpl implements FieldBuyingCriteriaWeightingService {

    private final Logger log = LoggerFactory.getLogger(FieldBuyingCriteriaWeightingServiceImpl.class);

    private final FieldBuyingCriteriaWeightingRepository fieldBuyingCriteriaWeightingRepository;

    public FieldBuyingCriteriaWeightingServiceImpl(FieldBuyingCriteriaWeightingRepository fieldBuyingCriteriaWeightingRepository) {
        this.fieldBuyingCriteriaWeightingRepository = fieldBuyingCriteriaWeightingRepository;
    }

    @Override
    public FieldBuyingCriteriaWeighting save(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting) {
        log.debug("Request to save FieldBuyingCriteriaWeighting : {}", fieldBuyingCriteriaWeighting);
        return fieldBuyingCriteriaWeightingRepository.save(fieldBuyingCriteriaWeighting);
    }

    @Override
    public FieldBuyingCriteriaWeighting update(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting) {
        log.debug("Request to update FieldBuyingCriteriaWeighting : {}", fieldBuyingCriteriaWeighting);
        return fieldBuyingCriteriaWeightingRepository.save(fieldBuyingCriteriaWeighting);
    }

    @Override
    public Optional<FieldBuyingCriteriaWeighting> partialUpdate(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting) {
        log.debug("Request to partially update FieldBuyingCriteriaWeighting : {}", fieldBuyingCriteriaWeighting);

        return fieldBuyingCriteriaWeightingRepository
            .findById(fieldBuyingCriteriaWeighting.getId())
            .map(existingFieldBuyingCriteriaWeighting -> {
                if (fieldBuyingCriteriaWeighting.getBuyingCriteriaWeighting() != null) {
                    existingFieldBuyingCriteriaWeighting.setBuyingCriteriaWeighting(
                        fieldBuyingCriteriaWeighting.getBuyingCriteriaWeighting()
                    );
                }

                return existingFieldBuyingCriteriaWeighting;
            })
            .map(fieldBuyingCriteriaWeightingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldBuyingCriteriaWeighting> findAll(Pageable pageable) {
        log.debug("Request to get all FieldBuyingCriteriaWeightings");
        return fieldBuyingCriteriaWeightingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldBuyingCriteriaWeighting> findOne(Long id) {
        log.debug("Request to get FieldBuyingCriteriaWeighting : {}", id);
        return fieldBuyingCriteriaWeightingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldBuyingCriteriaWeighting : {}", id);
        fieldBuyingCriteriaWeightingRepository.deleteById(id);
    }
}
