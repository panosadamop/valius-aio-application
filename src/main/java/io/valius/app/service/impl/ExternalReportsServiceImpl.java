package io.valius.app.service.impl;

import io.valius.app.domain.ExternalReports;
import io.valius.app.repository.ExternalReportsRepository;
import io.valius.app.service.ExternalReportsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExternalReports}.
 */
@Service
@Transactional
public class ExternalReportsServiceImpl implements ExternalReportsService {

    private final Logger log = LoggerFactory.getLogger(ExternalReportsServiceImpl.class);

    private final ExternalReportsRepository externalReportsRepository;

    public ExternalReportsServiceImpl(ExternalReportsRepository externalReportsRepository) {
        this.externalReportsRepository = externalReportsRepository;
    }

    @Override
    public ExternalReports save(ExternalReports externalReports) {
        log.debug("Request to save ExternalReports : {}", externalReports);
        return externalReportsRepository.save(externalReports);
    }

    @Override
    public ExternalReports update(ExternalReports externalReports) {
        log.debug("Request to update ExternalReports : {}", externalReports);
        return externalReportsRepository.save(externalReports);
    }

    @Override
    public Optional<ExternalReports> partialUpdate(ExternalReports externalReports) {
        log.debug("Request to partially update ExternalReports : {}", externalReports);

        return externalReportsRepository
            .findById(externalReports.getId())
            .map(existingExternalReports -> {
                if (externalReports.getReportUrl() != null) {
                    existingExternalReports.setReportUrl(externalReports.getReportUrl());
                }
                if (externalReports.getDescription() != null) {
                    existingExternalReports.setDescription(externalReports.getDescription());
                }

                return existingExternalReports;
            })
            .map(externalReportsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExternalReports> findAll(Pageable pageable) {
        log.debug("Request to get all ExternalReports");
        return externalReportsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExternalReports> findOne(Long id) {
        log.debug("Request to get ExternalReports : {}", id);
        return externalReportsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExternalReports : {}", id);
        externalReportsRepository.deleteById(id);
    }
}
