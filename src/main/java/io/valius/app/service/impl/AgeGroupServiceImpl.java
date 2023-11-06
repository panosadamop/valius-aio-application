package io.valius.app.service.impl;

import io.valius.app.domain.AgeGroup;
import io.valius.app.repository.AgeGroupRepository;
import io.valius.app.service.AgeGroupService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AgeGroup}.
 */
@Service
@Transactional
public class AgeGroupServiceImpl implements AgeGroupService {

    private final Logger log = LoggerFactory.getLogger(AgeGroupServiceImpl.class);

    private final AgeGroupRepository ageGroupRepository;

    public AgeGroupServiceImpl(AgeGroupRepository ageGroupRepository) {
        this.ageGroupRepository = ageGroupRepository;
    }

    @Override
    public AgeGroup save(AgeGroup ageGroup) {
        log.debug("Request to save AgeGroup : {}", ageGroup);
        return ageGroupRepository.save(ageGroup);
    }

    @Override
    public AgeGroup update(AgeGroup ageGroup) {
        log.debug("Request to update AgeGroup : {}", ageGroup);
        return ageGroupRepository.save(ageGroup);
    }

    @Override
    public Optional<AgeGroup> partialUpdate(AgeGroup ageGroup) {
        log.debug("Request to partially update AgeGroup : {}", ageGroup);

        return ageGroupRepository
            .findById(ageGroup.getId())
            .map(existingAgeGroup -> {
                if (ageGroup.getValue() != null) {
                    existingAgeGroup.setValue(ageGroup.getValue());
                }
                if (ageGroup.getDescription() != null) {
                    existingAgeGroup.setDescription(ageGroup.getDescription());
                }
                if (ageGroup.getLanguage() != null) {
                    existingAgeGroup.setLanguage(ageGroup.getLanguage());
                }

                return existingAgeGroup;
            })
            .map(ageGroupRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgeGroup> findAll(Pageable pageable) {
        log.debug("Request to get all AgeGroups");
        return ageGroupRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgeGroup> findOne(Long id) {
        log.debug("Request to get AgeGroup : {}", id);
        return ageGroupRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgeGroup : {}", id);
        ageGroupRepository.deleteById(id);
    }
}
