package io.valius.app.service.impl;

import io.valius.app.domain.FieldProductype;
import io.valius.app.repository.FieldProductypeRepository;
import io.valius.app.service.FieldProductypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldProductype}.
 */
@Service
@Transactional
public class FieldProductypeServiceImpl implements FieldProductypeService {

    private final Logger log = LoggerFactory.getLogger(FieldProductypeServiceImpl.class);

    private final FieldProductypeRepository fieldProductypeRepository;

    public FieldProductypeServiceImpl(FieldProductypeRepository fieldProductypeRepository) {
        this.fieldProductypeRepository = fieldProductypeRepository;
    }

    @Override
    public FieldProductype save(FieldProductype fieldProductype) {
        log.debug("Request to save FieldProductype : {}", fieldProductype);
        return fieldProductypeRepository.save(fieldProductype);
    }

    @Override
    public FieldProductype update(FieldProductype fieldProductype) {
        log.debug("Request to update FieldProductype : {}", fieldProductype);
        return fieldProductypeRepository.save(fieldProductype);
    }

    @Override
    public Optional<FieldProductype> partialUpdate(FieldProductype fieldProductype) {
        log.debug("Request to partially update FieldProductype : {}", fieldProductype);

        return fieldProductypeRepository
            .findById(fieldProductype.getId())
            .map(existingFieldProductype -> {
                if (fieldProductype.getProductType() != null) {
                    existingFieldProductype.setProductType(fieldProductype.getProductType());
                }

                return existingFieldProductype;
            })
            .map(fieldProductypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldProductype> findAll(Pageable pageable) {
        log.debug("Request to get all FieldProductypes");
        return fieldProductypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldProductype> findOne(Long id) {
        log.debug("Request to get FieldProductype : {}", id);
        return fieldProductypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldProductype : {}", id);
        fieldProductypeRepository.deleteById(id);
    }
}
