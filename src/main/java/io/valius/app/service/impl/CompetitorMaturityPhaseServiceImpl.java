package io.valius.app.service.impl;

import io.valius.app.domain.CompetitorMaturityPhase;
import io.valius.app.repository.CompetitorMaturityPhaseRepository;
import io.valius.app.service.CompetitorMaturityPhaseService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompetitorMaturityPhase}.
 */
@Service
@Transactional
public class CompetitorMaturityPhaseServiceImpl implements CompetitorMaturityPhaseService {

    private final Logger log = LoggerFactory.getLogger(CompetitorMaturityPhaseServiceImpl.class);

    private final CompetitorMaturityPhaseRepository competitorMaturityPhaseRepository;

    public CompetitorMaturityPhaseServiceImpl(CompetitorMaturityPhaseRepository competitorMaturityPhaseRepository) {
        this.competitorMaturityPhaseRepository = competitorMaturityPhaseRepository;
    }

    @Override
    public CompetitorMaturityPhase save(CompetitorMaturityPhase competitorMaturityPhase) {
        log.debug("Request to save CompetitorMaturityPhase : {}", competitorMaturityPhase);
        return competitorMaturityPhaseRepository.save(competitorMaturityPhase);
    }

    @Override
    public CompetitorMaturityPhase update(CompetitorMaturityPhase competitorMaturityPhase) {
        log.debug("Request to update CompetitorMaturityPhase : {}", competitorMaturityPhase);
        return competitorMaturityPhaseRepository.save(competitorMaturityPhase);
    }

    @Override
    public Optional<CompetitorMaturityPhase> partialUpdate(CompetitorMaturityPhase competitorMaturityPhase) {
        log.debug("Request to partially update CompetitorMaturityPhase : {}", competitorMaturityPhase);

        return competitorMaturityPhaseRepository
            .findById(competitorMaturityPhase.getId())
            .map(existingCompetitorMaturityPhase -> {
                if (competitorMaturityPhase.getValue() != null) {
                    existingCompetitorMaturityPhase.setValue(competitorMaturityPhase.getValue());
                }
                if (competitorMaturityPhase.getDescription() != null) {
                    existingCompetitorMaturityPhase.setDescription(competitorMaturityPhase.getDescription());
                }
                if (competitorMaturityPhase.getLanguage() != null) {
                    existingCompetitorMaturityPhase.setLanguage(competitorMaturityPhase.getLanguage());
                }

                return existingCompetitorMaturityPhase;
            })
            .map(competitorMaturityPhaseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetitorMaturityPhase> findAll(Pageable pageable) {
        log.debug("Request to get all CompetitorMaturityPhases");
        return competitorMaturityPhaseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitorMaturityPhase> findOne(Long id) {
        log.debug("Request to get CompetitorMaturityPhase : {}", id);
        return competitorMaturityPhaseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompetitorMaturityPhase : {}", id);
        competitorMaturityPhaseRepository.deleteById(id);
    }
}
