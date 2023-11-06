package io.valius.app.service.impl;

import io.valius.app.domain.FieldCompanyObjectives;
import io.valius.app.repository.FieldCompanyObjectivesRepository;
import io.valius.app.service.FieldCompanyObjectivesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldCompanyObjectives}.
 */
@Service
@Transactional
public class FieldCompanyObjectivesServiceImpl implements FieldCompanyObjectivesService {

    private final Logger log = LoggerFactory.getLogger(FieldCompanyObjectivesServiceImpl.class);

    private final FieldCompanyObjectivesRepository fieldCompanyObjectivesRepository;

    public FieldCompanyObjectivesServiceImpl(FieldCompanyObjectivesRepository fieldCompanyObjectivesRepository) {
        this.fieldCompanyObjectivesRepository = fieldCompanyObjectivesRepository;
    }

    @Override
    public FieldCompanyObjectives save(FieldCompanyObjectives fieldCompanyObjectives) {
        log.debug("Request to save FieldCompanyObjectives : {}", fieldCompanyObjectives);
        return fieldCompanyObjectivesRepository.save(fieldCompanyObjectives);
    }

    @Override
    public FieldCompanyObjectives update(FieldCompanyObjectives fieldCompanyObjectives) {
        log.debug("Request to update FieldCompanyObjectives : {}", fieldCompanyObjectives);
        return fieldCompanyObjectivesRepository.save(fieldCompanyObjectives);
    }

    @Override
    public Optional<FieldCompanyObjectives> partialUpdate(FieldCompanyObjectives fieldCompanyObjectives) {
        log.debug("Request to partially update FieldCompanyObjectives : {}", fieldCompanyObjectives);

        return fieldCompanyObjectivesRepository
            .findById(fieldCompanyObjectives.getId())
            .map(existingFieldCompanyObjectives -> {
                if (fieldCompanyObjectives.getCompanyObjectives() != null) {
                    existingFieldCompanyObjectives.setCompanyObjectives(fieldCompanyObjectives.getCompanyObjectives());
                }

                return existingFieldCompanyObjectives;
            })
            .map(fieldCompanyObjectivesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldCompanyObjectives> findAll(Pageable pageable) {
        log.debug("Request to get all FieldCompanyObjectives");
        return fieldCompanyObjectivesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldCompanyObjectives> findOne(Long id) {
        log.debug("Request to get FieldCompanyObjectives : {}", id);
        return fieldCompanyObjectivesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldCompanyObjectives : {}", id);
        fieldCompanyObjectivesRepository.deleteById(id);
    }
}
