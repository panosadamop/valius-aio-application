package io.valius.app.service.impl;

import io.valius.app.domain.Kpis;
import io.valius.app.repository.KpisRepository;
import io.valius.app.service.KpisService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Kpis}.
 */
@Service
@Transactional
public class KpisServiceImpl implements KpisService {

    private final Logger log = LoggerFactory.getLogger(KpisServiceImpl.class);

    private final KpisRepository kpisRepository;

    public KpisServiceImpl(KpisRepository kpisRepository) {
        this.kpisRepository = kpisRepository;
    }

    @Override
    public Kpis save(Kpis kpis) {
        log.debug("Request to save Kpis : {}", kpis);
        return kpisRepository.save(kpis);
    }

    @Override
    public Kpis update(Kpis kpis) {
        log.debug("Request to update Kpis : {}", kpis);
        return kpisRepository.save(kpis);
    }

    @Override
    public Optional<Kpis> partialUpdate(Kpis kpis) {
        log.debug("Request to partially update Kpis : {}", kpis);

        return kpisRepository
            .findById(kpis.getId())
            .map(existingKpis -> {
                if (kpis.getValue() != null) {
                    existingKpis.setValue(kpis.getValue());
                }
                if (kpis.getCheckBoxValue() != null) {
                    existingKpis.setCheckBoxValue(kpis.getCheckBoxValue());
                }
                if (kpis.getDescription() != null) {
                    existingKpis.setDescription(kpis.getDescription());
                }
                if (kpis.getLanguage() != null) {
                    existingKpis.setLanguage(kpis.getLanguage());
                }

                return existingKpis;
            })
            .map(kpisRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kpis> findAll(Pageable pageable) {
        log.debug("Request to get all Kpis");
        return kpisRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kpis> findOne(Long id) {
        log.debug("Request to get Kpis : {}", id);
        return kpisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kpis : {}", id);
        kpisRepository.deleteById(id);
    }
}
