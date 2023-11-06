package io.valius.app.service.impl;

import io.valius.app.domain.FieldAttractivenessFactors;
import io.valius.app.repository.FieldAttractivenessFactorsRepository;
import io.valius.app.service.FieldAttractivenessFactorsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldAttractivenessFactors}.
 */
@Service
@Transactional
public class FieldAttractivenessFactorsServiceImpl implements FieldAttractivenessFactorsService {

    private final Logger log = LoggerFactory.getLogger(FieldAttractivenessFactorsServiceImpl.class);

    private final FieldAttractivenessFactorsRepository fieldAttractivenessFactorsRepository;

    public FieldAttractivenessFactorsServiceImpl(FieldAttractivenessFactorsRepository fieldAttractivenessFactorsRepository) {
        this.fieldAttractivenessFactorsRepository = fieldAttractivenessFactorsRepository;
    }

    @Override
    public FieldAttractivenessFactors save(FieldAttractivenessFactors fieldAttractivenessFactors) {
        log.debug("Request to save FieldAttractivenessFactors : {}", fieldAttractivenessFactors);
        return fieldAttractivenessFactorsRepository.save(fieldAttractivenessFactors);
    }

    @Override
    public FieldAttractivenessFactors update(FieldAttractivenessFactors fieldAttractivenessFactors) {
        log.debug("Request to update FieldAttractivenessFactors : {}", fieldAttractivenessFactors);
        return fieldAttractivenessFactorsRepository.save(fieldAttractivenessFactors);
    }

    @Override
    public Optional<FieldAttractivenessFactors> partialUpdate(FieldAttractivenessFactors fieldAttractivenessFactors) {
        log.debug("Request to partially update FieldAttractivenessFactors : {}", fieldAttractivenessFactors);

        return fieldAttractivenessFactorsRepository
            .findById(fieldAttractivenessFactors.getId())
            .map(existingFieldAttractivenessFactors -> {
                if (fieldAttractivenessFactors.getAttractivenessFactors() != null) {
                    existingFieldAttractivenessFactors.setAttractivenessFactors(fieldAttractivenessFactors.getAttractivenessFactors());
                }

                return existingFieldAttractivenessFactors;
            })
            .map(fieldAttractivenessFactorsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldAttractivenessFactors> findAll(Pageable pageable) {
        log.debug("Request to get all FieldAttractivenessFactors");
        return fieldAttractivenessFactorsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldAttractivenessFactors> findOne(Long id) {
        log.debug("Request to get FieldAttractivenessFactors : {}", id);
        return fieldAttractivenessFactorsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldAttractivenessFactors : {}", id);
        fieldAttractivenessFactorsRepository.deleteById(id);
    }
}
