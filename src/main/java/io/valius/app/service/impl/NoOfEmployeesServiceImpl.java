package io.valius.app.service.impl;

import io.valius.app.domain.NoOfEmployees;
import io.valius.app.repository.NoOfEmployeesRepository;
import io.valius.app.service.NoOfEmployeesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NoOfEmployees}.
 */
@Service
@Transactional
public class NoOfEmployeesServiceImpl implements NoOfEmployeesService {

    private final Logger log = LoggerFactory.getLogger(NoOfEmployeesServiceImpl.class);

    private final NoOfEmployeesRepository noOfEmployeesRepository;

    public NoOfEmployeesServiceImpl(NoOfEmployeesRepository noOfEmployeesRepository) {
        this.noOfEmployeesRepository = noOfEmployeesRepository;
    }

    @Override
    public NoOfEmployees save(NoOfEmployees noOfEmployees) {
        log.debug("Request to save NoOfEmployees : {}", noOfEmployees);
        return noOfEmployeesRepository.save(noOfEmployees);
    }

    @Override
    public NoOfEmployees update(NoOfEmployees noOfEmployees) {
        log.debug("Request to update NoOfEmployees : {}", noOfEmployees);
        return noOfEmployeesRepository.save(noOfEmployees);
    }

    @Override
    public Optional<NoOfEmployees> partialUpdate(NoOfEmployees noOfEmployees) {
        log.debug("Request to partially update NoOfEmployees : {}", noOfEmployees);

        return noOfEmployeesRepository
            .findById(noOfEmployees.getId())
            .map(existingNoOfEmployees -> {
                if (noOfEmployees.getValue() != null) {
                    existingNoOfEmployees.setValue(noOfEmployees.getValue());
                }
                if (noOfEmployees.getDescription() != null) {
                    existingNoOfEmployees.setDescription(noOfEmployees.getDescription());
                }
                if (noOfEmployees.getLanguage() != null) {
                    existingNoOfEmployees.setLanguage(noOfEmployees.getLanguage());
                }

                return existingNoOfEmployees;
            })
            .map(noOfEmployeesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoOfEmployees> findAll(Pageable pageable) {
        log.debug("Request to get all NoOfEmployees");
        return noOfEmployeesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoOfEmployees> findOne(Long id) {
        log.debug("Request to get NoOfEmployees : {}", id);
        return noOfEmployeesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NoOfEmployees : {}", id);
        noOfEmployeesRepository.deleteById(id);
    }
}
