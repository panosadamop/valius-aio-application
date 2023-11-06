package io.valius.app.service.impl;

import io.valius.app.domain.Revenues;
import io.valius.app.repository.RevenuesRepository;
import io.valius.app.service.RevenuesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Revenues}.
 */
@Service
@Transactional
public class RevenuesServiceImpl implements RevenuesService {

    private final Logger log = LoggerFactory.getLogger(RevenuesServiceImpl.class);

    private final RevenuesRepository revenuesRepository;

    public RevenuesServiceImpl(RevenuesRepository revenuesRepository) {
        this.revenuesRepository = revenuesRepository;
    }

    @Override
    public Revenues save(Revenues revenues) {
        log.debug("Request to save Revenues : {}", revenues);
        return revenuesRepository.save(revenues);
    }

    @Override
    public Revenues update(Revenues revenues) {
        log.debug("Request to update Revenues : {}", revenues);
        return revenuesRepository.save(revenues);
    }

    @Override
    public Optional<Revenues> partialUpdate(Revenues revenues) {
        log.debug("Request to partially update Revenues : {}", revenues);

        return revenuesRepository
            .findById(revenues.getId())
            .map(existingRevenues -> {
                if (revenues.getValue() != null) {
                    existingRevenues.setValue(revenues.getValue());
                }
                if (revenues.getDescription() != null) {
                    existingRevenues.setDescription(revenues.getDescription());
                }
                if (revenues.getLanguage() != null) {
                    existingRevenues.setLanguage(revenues.getLanguage());
                }

                return existingRevenues;
            })
            .map(revenuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Revenues> findAll(Pageable pageable) {
        log.debug("Request to get all Revenues");
        return revenuesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Revenues> findOne(Long id) {
        log.debug("Request to get Revenues : {}", id);
        return revenuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Revenues : {}", id);
        revenuesRepository.deleteById(id);
    }
}
