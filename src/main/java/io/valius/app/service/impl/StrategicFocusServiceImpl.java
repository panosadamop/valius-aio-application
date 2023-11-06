package io.valius.app.service.impl;

import io.valius.app.domain.StrategicFocus;
import io.valius.app.repository.StrategicFocusRepository;
import io.valius.app.service.StrategicFocusService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrategicFocus}.
 */
@Service
@Transactional
public class StrategicFocusServiceImpl implements StrategicFocusService {

    private final Logger log = LoggerFactory.getLogger(StrategicFocusServiceImpl.class);

    private final StrategicFocusRepository strategicFocusRepository;

    public StrategicFocusServiceImpl(StrategicFocusRepository strategicFocusRepository) {
        this.strategicFocusRepository = strategicFocusRepository;
    }

    @Override
    public StrategicFocus save(StrategicFocus strategicFocus) {
        log.debug("Request to save StrategicFocus : {}", strategicFocus);
        return strategicFocusRepository.save(strategicFocus);
    }

    @Override
    public StrategicFocus update(StrategicFocus strategicFocus) {
        log.debug("Request to update StrategicFocus : {}", strategicFocus);
        return strategicFocusRepository.save(strategicFocus);
    }

    @Override
    public Optional<StrategicFocus> partialUpdate(StrategicFocus strategicFocus) {
        log.debug("Request to partially update StrategicFocus : {}", strategicFocus);

        return strategicFocusRepository
            .findById(strategicFocus.getId())
            .map(existingStrategicFocus -> {
                if (strategicFocus.getValue() != null) {
                    existingStrategicFocus.setValue(strategicFocus.getValue());
                }
                if (strategicFocus.getDescription() != null) {
                    existingStrategicFocus.setDescription(strategicFocus.getDescription());
                }
                if (strategicFocus.getLanguage() != null) {
                    existingStrategicFocus.setLanguage(strategicFocus.getLanguage());
                }

                return existingStrategicFocus;
            })
            .map(strategicFocusRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StrategicFocus> findAll(Pageable pageable) {
        log.debug("Request to get all StrategicFoci");
        return strategicFocusRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrategicFocus> findOne(Long id) {
        log.debug("Request to get StrategicFocus : {}", id);
        return strategicFocusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrategicFocus : {}", id);
        strategicFocusRepository.deleteById(id);
    }
}
