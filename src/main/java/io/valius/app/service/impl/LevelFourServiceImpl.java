package io.valius.app.service.impl;

import io.valius.app.domain.LevelFour;
import io.valius.app.repository.LevelFourRepository;
import io.valius.app.service.LevelFourService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LevelFour}.
 */
@Service
@Transactional
public class LevelFourServiceImpl implements LevelFourService {

    private final Logger log = LoggerFactory.getLogger(LevelFourServiceImpl.class);

    private final LevelFourRepository levelFourRepository;

    public LevelFourServiceImpl(LevelFourRepository levelFourRepository) {
        this.levelFourRepository = levelFourRepository;
    }

    @Override
    public LevelFour save(LevelFour levelFour) {
        log.debug("Request to save LevelFour : {}", levelFour);
        return levelFourRepository.save(levelFour);
    }

    @Override
    public LevelFour update(LevelFour levelFour) {
        log.debug("Request to update LevelFour : {}", levelFour);
        return levelFourRepository.save(levelFour);
    }

    @Override
    public Optional<LevelFour> partialUpdate(LevelFour levelFour) {
        log.debug("Request to partially update LevelFour : {}", levelFour);

        return levelFourRepository
            .findById(levelFour.getId())
            .map(existingLevelFour -> {
                if (levelFour.getIdentifier() != null) {
                    existingLevelFour.setIdentifier(levelFour.getIdentifier());
                }
                if (levelFour.getCriticalSuccessFactors() != null) {
                    existingLevelFour.setCriticalSuccessFactors(levelFour.getCriticalSuccessFactors());
                }
                if (levelFour.getPopulationSize() != null) {
                    existingLevelFour.setPopulationSize(levelFour.getPopulationSize());
                }
                if (levelFour.getStatisticalError() != null) {
                    existingLevelFour.setStatisticalError(levelFour.getStatisticalError());
                }
                if (levelFour.getConfidenceLevel() != null) {
                    existingLevelFour.setConfidenceLevel(levelFour.getConfidenceLevel());
                }
                if (levelFour.getRequiredSampleSize() != null) {
                    existingLevelFour.setRequiredSampleSize(levelFour.getRequiredSampleSize());
                }
                if (levelFour.getEstimatedResponseRate() != null) {
                    existingLevelFour.setEstimatedResponseRate(levelFour.getEstimatedResponseRate());
                }
                if (levelFour.getSurveyParticipantsNumber() != null) {
                    existingLevelFour.setSurveyParticipantsNumber(levelFour.getSurveyParticipantsNumber());
                }

                return existingLevelFour;
            })
            .map(levelFourRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelFour> findAll(Pageable pageable) {
        log.debug("Request to get all LevelFours");
        return levelFourRepository.findAll(pageable);
    }

    public Page<LevelFour> findAllWithEagerRelationships(Pageable pageable) {
        return levelFourRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelFour> findOne(Long id) {
        log.debug("Request to get LevelFour : {}", id);
        return levelFourRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelFour : {}", id);
        levelFourRepository.deleteById(id);
    }
}
