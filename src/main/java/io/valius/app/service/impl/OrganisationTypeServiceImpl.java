package io.valius.app.service.impl;

import io.valius.app.domain.OrganisationType;
import io.valius.app.repository.OrganisationTypeRepository;
import io.valius.app.service.OrganisationTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrganisationType}.
 */
@Service
@Transactional
public class OrganisationTypeServiceImpl implements OrganisationTypeService {

    private final Logger log = LoggerFactory.getLogger(OrganisationTypeServiceImpl.class);

    private final OrganisationTypeRepository organisationTypeRepository;

    public OrganisationTypeServiceImpl(OrganisationTypeRepository organisationTypeRepository) {
        this.organisationTypeRepository = organisationTypeRepository;
    }

    @Override
    public OrganisationType save(OrganisationType organisationType) {
        log.debug("Request to save OrganisationType : {}", organisationType);
        return organisationTypeRepository.save(organisationType);
    }

    @Override
    public OrganisationType update(OrganisationType organisationType) {
        log.debug("Request to update OrganisationType : {}", organisationType);
        return organisationTypeRepository.save(organisationType);
    }

    @Override
    public Optional<OrganisationType> partialUpdate(OrganisationType organisationType) {
        log.debug("Request to partially update OrganisationType : {}", organisationType);

        return organisationTypeRepository
            .findById(organisationType.getId())
            .map(existingOrganisationType -> {
                if (organisationType.getValue() != null) {
                    existingOrganisationType.setValue(organisationType.getValue());
                }
                if (organisationType.getDescription() != null) {
                    existingOrganisationType.setDescription(organisationType.getDescription());
                }
                if (organisationType.getLanguage() != null) {
                    existingOrganisationType.setLanguage(organisationType.getLanguage());
                }

                return existingOrganisationType;
            })
            .map(organisationTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganisationType> findAll(Pageable pageable) {
        log.debug("Request to get all OrganisationTypes");
        return organisationTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganisationType> findOne(Long id) {
        log.debug("Request to get OrganisationType : {}", id);
        return organisationTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganisationType : {}", id);
        organisationTypeRepository.deleteById(id);
    }
}
