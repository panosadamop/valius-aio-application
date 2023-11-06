package io.valius.app.service.impl;

import io.valius.app.domain.FieldBuyingCriteria;
import io.valius.app.repository.FieldBuyingCriteriaRepository;
import io.valius.app.service.FieldBuyingCriteriaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldBuyingCriteria}.
 */
@Service
@Transactional
public class FieldBuyingCriteriaServiceImpl implements FieldBuyingCriteriaService {

    private final Logger log = LoggerFactory.getLogger(FieldBuyingCriteriaServiceImpl.class);

    private final FieldBuyingCriteriaRepository fieldBuyingCriteriaRepository;

    public FieldBuyingCriteriaServiceImpl(FieldBuyingCriteriaRepository fieldBuyingCriteriaRepository) {
        this.fieldBuyingCriteriaRepository = fieldBuyingCriteriaRepository;
    }

    @Override
    public FieldBuyingCriteria save(FieldBuyingCriteria fieldBuyingCriteria) {
        log.debug("Request to save FieldBuyingCriteria : {}", fieldBuyingCriteria);
        return fieldBuyingCriteriaRepository.save(fieldBuyingCriteria);
    }

    @Override
    public FieldBuyingCriteria update(FieldBuyingCriteria fieldBuyingCriteria) {
        log.debug("Request to update FieldBuyingCriteria : {}", fieldBuyingCriteria);
        return fieldBuyingCriteriaRepository.save(fieldBuyingCriteria);
    }

    @Override
    public Optional<FieldBuyingCriteria> partialUpdate(FieldBuyingCriteria fieldBuyingCriteria) {
        log.debug("Request to partially update FieldBuyingCriteria : {}", fieldBuyingCriteria);

        return fieldBuyingCriteriaRepository
            .findById(fieldBuyingCriteria.getId())
            .map(existingFieldBuyingCriteria -> {
                if (fieldBuyingCriteria.getBuyingCriteria() != null) {
                    existingFieldBuyingCriteria.setBuyingCriteria(fieldBuyingCriteria.getBuyingCriteria());
                }

                return existingFieldBuyingCriteria;
            })
            .map(fieldBuyingCriteriaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldBuyingCriteria> findAll(Pageable pageable) {
        log.debug("Request to get all FieldBuyingCriteria");
        return fieldBuyingCriteriaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldBuyingCriteria> findOne(Long id) {
        log.debug("Request to get FieldBuyingCriteria : {}", id);
        return fieldBuyingCriteriaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldBuyingCriteria : {}", id);
        fieldBuyingCriteriaRepository.deleteById(id);
    }
}
