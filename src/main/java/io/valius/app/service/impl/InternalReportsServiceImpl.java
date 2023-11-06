package io.valius.app.service.impl;

import io.valius.app.domain.InternalReports;
import io.valius.app.repository.InternalReportsRepository;
import io.valius.app.service.InternalReportsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InternalReports}.
 */
@Service
@Transactional
public class InternalReportsServiceImpl implements InternalReportsService {

    private final Logger log = LoggerFactory.getLogger(InternalReportsServiceImpl.class);

    private final InternalReportsRepository internalReportsRepository;

    public InternalReportsServiceImpl(InternalReportsRepository internalReportsRepository) {
        this.internalReportsRepository = internalReportsRepository;
    }

    @Override
    public InternalReports save(InternalReports internalReports) {
        log.debug("Request to save InternalReports : {}", internalReports);
        return internalReportsRepository.save(internalReports);
    }

    @Override
    public InternalReports update(InternalReports internalReports) {
        log.debug("Request to update InternalReports : {}", internalReports);
        // no save call needed as we have no fields that can be updated
        return internalReports;
    }

    @Override
    public Optional<InternalReports> partialUpdate(InternalReports internalReports) {
        log.debug("Request to partially update InternalReports : {}", internalReports);

        return internalReportsRepository
            .findById(internalReports.getId())
            .map(existingInternalReports -> {
                return existingInternalReports;
            })// .map(internalReportsRepository::save)
        ;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InternalReports> findAll(Pageable pageable) {
        log.debug("Request to get all InternalReports");
        return internalReportsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InternalReports> findOne(Long id) {
        log.debug("Request to get InternalReports : {}", id);
        return internalReportsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InternalReports : {}", id);
        internalReportsRepository.deleteById(id);
    }
}
