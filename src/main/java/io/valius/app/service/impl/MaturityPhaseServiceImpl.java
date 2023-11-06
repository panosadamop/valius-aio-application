package io.valius.app.service.impl;

import io.valius.app.domain.MaturityPhase;
import io.valius.app.repository.MaturityPhaseRepository;
import io.valius.app.service.MaturityPhaseService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MaturityPhase}.
 */
@Service
@Transactional
public class MaturityPhaseServiceImpl implements MaturityPhaseService {

    private final Logger log = LoggerFactory.getLogger(MaturityPhaseServiceImpl.class);

    private final MaturityPhaseRepository maturityPhaseRepository;

    public MaturityPhaseServiceImpl(MaturityPhaseRepository maturityPhaseRepository) {
        this.maturityPhaseRepository = maturityPhaseRepository;
    }

    @Override
    public MaturityPhase save(MaturityPhase maturityPhase) {
        log.debug("Request to save MaturityPhase : {}", maturityPhase);
        return maturityPhaseRepository.save(maturityPhase);
    }

    @Override
    public MaturityPhase update(MaturityPhase maturityPhase) {
        log.debug("Request to update MaturityPhase : {}", maturityPhase);
        return maturityPhaseRepository.save(maturityPhase);
    }

    @Override
    public Optional<MaturityPhase> partialUpdate(MaturityPhase maturityPhase) {
        log.debug("Request to partially update MaturityPhase : {}", maturityPhase);

        return maturityPhaseRepository
            .findById(maturityPhase.getId())
            .map(existingMaturityPhase -> {
                if (maturityPhase.getValue() != null) {
                    existingMaturityPhase.setValue(maturityPhase.getValue());
                }
                if (maturityPhase.getDescription() != null) {
                    existingMaturityPhase.setDescription(maturityPhase.getDescription());
                }
                if (maturityPhase.getLanguage() != null) {
                    existingMaturityPhase.setLanguage(maturityPhase.getLanguage());
                }

                return existingMaturityPhase;
            })
            .map(maturityPhaseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaturityPhase> findAll(Pageable pageable) {
        log.debug("Request to get all MaturityPhases");
        return maturityPhaseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MaturityPhase> findOne(Long id) {
        log.debug("Request to get MaturityPhase : {}", id);
        return maturityPhaseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaturityPhase : {}", id);
        maturityPhaseRepository.deleteById(id);
    }
}
