package io.valius.app.service.impl;

import io.valius.app.domain.CompetitorCompetitivePosition;
import io.valius.app.repository.CompetitorCompetitivePositionRepository;
import io.valius.app.service.CompetitorCompetitivePositionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompetitorCompetitivePosition}.
 */
@Service
@Transactional
public class CompetitorCompetitivePositionServiceImpl implements CompetitorCompetitivePositionService {

    private final Logger log = LoggerFactory.getLogger(CompetitorCompetitivePositionServiceImpl.class);

    private final CompetitorCompetitivePositionRepository competitorCompetitivePositionRepository;

    public CompetitorCompetitivePositionServiceImpl(CompetitorCompetitivePositionRepository competitorCompetitivePositionRepository) {
        this.competitorCompetitivePositionRepository = competitorCompetitivePositionRepository;
    }

    @Override
    public CompetitorCompetitivePosition save(CompetitorCompetitivePosition competitorCompetitivePosition) {
        log.debug("Request to save CompetitorCompetitivePosition : {}", competitorCompetitivePosition);
        return competitorCompetitivePositionRepository.save(competitorCompetitivePosition);
    }

    @Override
    public CompetitorCompetitivePosition update(CompetitorCompetitivePosition competitorCompetitivePosition) {
        log.debug("Request to update CompetitorCompetitivePosition : {}", competitorCompetitivePosition);
        return competitorCompetitivePositionRepository.save(competitorCompetitivePosition);
    }

    @Override
    public Optional<CompetitorCompetitivePosition> partialUpdate(CompetitorCompetitivePosition competitorCompetitivePosition) {
        log.debug("Request to partially update CompetitorCompetitivePosition : {}", competitorCompetitivePosition);

        return competitorCompetitivePositionRepository
            .findById(competitorCompetitivePosition.getId())
            .map(existingCompetitorCompetitivePosition -> {
                if (competitorCompetitivePosition.getValue() != null) {
                    existingCompetitorCompetitivePosition.setValue(competitorCompetitivePosition.getValue());
                }
                if (competitorCompetitivePosition.getDescription() != null) {
                    existingCompetitorCompetitivePosition.setDescription(competitorCompetitivePosition.getDescription());
                }
                if (competitorCompetitivePosition.getLanguage() != null) {
                    existingCompetitorCompetitivePosition.setLanguage(competitorCompetitivePosition.getLanguage());
                }

                return existingCompetitorCompetitivePosition;
            })
            .map(competitorCompetitivePositionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetitorCompetitivePosition> findAll(Pageable pageable) {
        log.debug("Request to get all CompetitorCompetitivePositions");
        return competitorCompetitivePositionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitorCompetitivePosition> findOne(Long id) {
        log.debug("Request to get CompetitorCompetitivePosition : {}", id);
        return competitorCompetitivePositionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompetitorCompetitivePosition : {}", id);
        competitorCompetitivePositionRepository.deleteById(id);
    }
}
