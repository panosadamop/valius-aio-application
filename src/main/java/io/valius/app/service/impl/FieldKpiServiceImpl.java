package io.valius.app.service.impl;

import io.valius.app.domain.FieldKpi;
import io.valius.app.repository.FieldKpiRepository;
import io.valius.app.service.FieldKpiService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldKpi}.
 */
@Service
@Transactional
public class FieldKpiServiceImpl implements FieldKpiService {

    private final Logger log = LoggerFactory.getLogger(FieldKpiServiceImpl.class);

    private final FieldKpiRepository fieldKpiRepository;

    public FieldKpiServiceImpl(FieldKpiRepository fieldKpiRepository) {
        this.fieldKpiRepository = fieldKpiRepository;
    }

    @Override
    public FieldKpi save(FieldKpi fieldKpi) {
        log.debug("Request to save FieldKpi : {}", fieldKpi);
        return fieldKpiRepository.save(fieldKpi);
    }

    @Override
    public FieldKpi update(FieldKpi fieldKpi) {
        log.debug("Request to update FieldKpi : {}", fieldKpi);
        return fieldKpiRepository.save(fieldKpi);
    }

    @Override
    public Optional<FieldKpi> partialUpdate(FieldKpi fieldKpi) {
        log.debug("Request to partially update FieldKpi : {}", fieldKpi);

        return fieldKpiRepository
            .findById(fieldKpi.getId())
            .map(existingFieldKpi -> {
                if (fieldKpi.getKpis() != null) {
                    existingFieldKpi.setKpis(fieldKpi.getKpis());
                }

                return existingFieldKpi;
            })
            .map(fieldKpiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldKpi> findAll(Pageable pageable) {
        log.debug("Request to get all FieldKpis");
        return fieldKpiRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldKpi> findOne(Long id) {
        log.debug("Request to get FieldKpi : {}", id);
        return fieldKpiRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldKpi : {}", id);
        fieldKpiRepository.deleteById(id);
    }
}
