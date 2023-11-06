package io.valius.app.service.impl;

import io.valius.app.domain.CompanyObjectives;
import io.valius.app.repository.CompanyObjectivesRepository;
import io.valius.app.service.CompanyObjectivesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyObjectives}.
 */
@Service
@Transactional
public class CompanyObjectivesServiceImpl implements CompanyObjectivesService {

    private final Logger log = LoggerFactory.getLogger(CompanyObjectivesServiceImpl.class);

    private final CompanyObjectivesRepository companyObjectivesRepository;

    public CompanyObjectivesServiceImpl(CompanyObjectivesRepository companyObjectivesRepository) {
        this.companyObjectivesRepository = companyObjectivesRepository;
    }

    @Override
    public CompanyObjectives save(CompanyObjectives companyObjectives) {
        log.debug("Request to save CompanyObjectives : {}", companyObjectives);
        return companyObjectivesRepository.save(companyObjectives);
    }

    @Override
    public CompanyObjectives update(CompanyObjectives companyObjectives) {
        log.debug("Request to update CompanyObjectives : {}", companyObjectives);
        return companyObjectivesRepository.save(companyObjectives);
    }

    @Override
    public Optional<CompanyObjectives> partialUpdate(CompanyObjectives companyObjectives) {
        log.debug("Request to partially update CompanyObjectives : {}", companyObjectives);

        return companyObjectivesRepository
            .findById(companyObjectives.getId())
            .map(existingCompanyObjectives -> {
                if (companyObjectives.getValue() != null) {
                    existingCompanyObjectives.setValue(companyObjectives.getValue());
                }
                if (companyObjectives.getPlaceholder() != null) {
                    existingCompanyObjectives.setPlaceholder(companyObjectives.getPlaceholder());
                }
                if (companyObjectives.getDescription() != null) {
                    existingCompanyObjectives.setDescription(companyObjectives.getDescription());
                }
                if (companyObjectives.getLanguage() != null) {
                    existingCompanyObjectives.setLanguage(companyObjectives.getLanguage());
                }

                return existingCompanyObjectives;
            })
            .map(companyObjectivesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyObjectives> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyObjectives");
        return companyObjectivesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyObjectives> findOne(Long id) {
        log.debug("Request to get CompanyObjectives : {}", id);
        return companyObjectivesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyObjectives : {}", id);
        companyObjectivesRepository.deleteById(id);
    }
}
