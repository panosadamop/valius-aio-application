package io.valius.app.service.impl;

import io.valius.app.domain.CompetitivePosition;
import io.valius.app.repository.CompetitivePositionRepository;
import io.valius.app.service.CompetitivePositionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompetitivePosition}.
 */
@Service
@Transactional
public class CompetitivePositionServiceImpl implements CompetitivePositionService {

    private final Logger log = LoggerFactory.getLogger(CompetitivePositionServiceImpl.class);

    private final CompetitivePositionRepository competitivePositionRepository;

    public CompetitivePositionServiceImpl(CompetitivePositionRepository competitivePositionRepository) {
        this.competitivePositionRepository = competitivePositionRepository;
    }

    @Override
    public CompetitivePosition save(CompetitivePosition competitivePosition) {
        log.debug("Request to save CompetitivePosition : {}", competitivePosition);
        return competitivePositionRepository.save(competitivePosition);
    }

    @Override
    public CompetitivePosition update(CompetitivePosition competitivePosition) {
        log.debug("Request to update CompetitivePosition : {}", competitivePosition);
        return competitivePositionRepository.save(competitivePosition);
    }

    @Override
    public Optional<CompetitivePosition> partialUpdate(CompetitivePosition competitivePosition) {
        log.debug("Request to partially update CompetitivePosition : {}", competitivePosition);

        return competitivePositionRepository
            .findById(competitivePosition.getId())
            .map(existingCompetitivePosition -> {
                if (competitivePosition.getValue() != null) {
                    existingCompetitivePosition.setValue(competitivePosition.getValue());
                }
                if (competitivePosition.getDescription() != null) {
                    existingCompetitivePosition.setDescription(competitivePosition.getDescription());
                }
                if (competitivePosition.getLanguage() != null) {
                    existingCompetitivePosition.setLanguage(competitivePosition.getLanguage());
                }

                return existingCompetitivePosition;
            })
            .map(competitivePositionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetitivePosition> findAll(Pageable pageable) {
        log.debug("Request to get all CompetitivePositions");
        return competitivePositionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitivePosition> findOne(Long id) {
        log.debug("Request to get CompetitivePosition : {}", id);
        return competitivePositionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompetitivePosition : {}", id);
        competitivePositionRepository.deleteById(id);
    }
}
