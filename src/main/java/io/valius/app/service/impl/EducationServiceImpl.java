package io.valius.app.service.impl;

import io.valius.app.domain.Education;
import io.valius.app.repository.EducationRepository;
import io.valius.app.service.EducationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Education}.
 */
@Service
@Transactional
public class EducationServiceImpl implements EducationService {

    private final Logger log = LoggerFactory.getLogger(EducationServiceImpl.class);

    private final EducationRepository educationRepository;

    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Override
    public Education save(Education education) {
        log.debug("Request to save Education : {}", education);
        return educationRepository.save(education);
    }

    @Override
    public Education update(Education education) {
        log.debug("Request to update Education : {}", education);
        return educationRepository.save(education);
    }

    @Override
    public Optional<Education> partialUpdate(Education education) {
        log.debug("Request to partially update Education : {}", education);

        return educationRepository
            .findById(education.getId())
            .map(existingEducation -> {
                if (education.getValue() != null) {
                    existingEducation.setValue(education.getValue());
                }
                if (education.getDescription() != null) {
                    existingEducation.setDescription(education.getDescription());
                }
                if (education.getLanguage() != null) {
                    existingEducation.setLanguage(education.getLanguage());
                }

                return existingEducation;
            })
            .map(educationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Education> findAll(Pageable pageable) {
        log.debug("Request to get all Educations");
        return educationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Education> findOne(Long id) {
        log.debug("Request to get Education : {}", id);
        return educationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Education : {}", id);
        educationRepository.deleteById(id);
    }
}
