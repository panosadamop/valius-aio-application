package io.valius.app.service.impl;

import io.valius.app.domain.LevelTwo;
import io.valius.app.repository.LevelTwoRepository;
import io.valius.app.service.LevelTwoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LevelTwo}.
 */
@Service
@Transactional
public class LevelTwoServiceImpl implements LevelTwoService {

    private final Logger log = LoggerFactory.getLogger(LevelTwoServiceImpl.class);

    private final LevelTwoRepository levelTwoRepository;

    public LevelTwoServiceImpl(LevelTwoRepository levelTwoRepository) {
        this.levelTwoRepository = levelTwoRepository;
    }

    @Override
    public LevelTwo save(LevelTwo levelTwo) {
        log.debug("Request to save LevelTwo : {}", levelTwo);
        return levelTwoRepository.save(levelTwo);
    }

    @Override
    public LevelTwo update(LevelTwo levelTwo) {
        log.debug("Request to update LevelTwo : {}", levelTwo);
        return levelTwoRepository.save(levelTwo);
    }

    @Override
    public Optional<LevelTwo> partialUpdate(LevelTwo levelTwo) {
        log.debug("Request to partially update LevelTwo : {}", levelTwo);

        return levelTwoRepository
            .findById(levelTwo.getId())
            .map(existingLevelTwo -> {
                if (levelTwo.getIdentifier() != null) {
                    existingLevelTwo.setIdentifier(levelTwo.getIdentifier());
                }
                if (levelTwo.getTargetMarket() != null) {
                    existingLevelTwo.setTargetMarket(levelTwo.getTargetMarket());
                }
                if (levelTwo.getCurrentMarketSegmentation() != null) {
                    existingLevelTwo.setCurrentMarketSegmentation(levelTwo.getCurrentMarketSegmentation());
                }
                if (levelTwo.getSegmentName() != null) {
                    existingLevelTwo.setSegmentName(levelTwo.getSegmentName());
                }
                if (levelTwo.getMarketSegmentationType() != null) {
                    existingLevelTwo.setMarketSegmentationType(levelTwo.getMarketSegmentationType());
                }
                if (levelTwo.getUniqueCharacteristic() != null) {
                    existingLevelTwo.setUniqueCharacteristic(levelTwo.getUniqueCharacteristic());
                }
                if (levelTwo.getSegmentDescription() != null) {
                    existingLevelTwo.setSegmentDescription(levelTwo.getSegmentDescription());
                }
                if (levelTwo.getBuyingCriteriaCategory() != null) {
                    existingLevelTwo.setBuyingCriteriaCategory(levelTwo.getBuyingCriteriaCategory());
                }
                if (levelTwo.getCompetitorProductName() != null) {
                    existingLevelTwo.setCompetitorProductName(levelTwo.getCompetitorProductName());
                }
                if (levelTwo.getCompetitorCompanyName() != null) {
                    existingLevelTwo.setCompetitorCompanyName(levelTwo.getCompetitorCompanyName());
                }
                if (levelTwo.getCompetitorBrandName() != null) {
                    existingLevelTwo.setCompetitorBrandName(levelTwo.getCompetitorBrandName());
                }
                if (levelTwo.getCompetitorProductDescription() != null) {
                    existingLevelTwo.setCompetitorProductDescription(levelTwo.getCompetitorProductDescription());
                }
                if (levelTwo.getCompetitorMaturityPhase() != null) {
                    existingLevelTwo.setCompetitorMaturityPhase(levelTwo.getCompetitorMaturityPhase());
                }
                if (levelTwo.getCompetitorCompetitivePosition() != null) {
                    existingLevelTwo.setCompetitorCompetitivePosition(levelTwo.getCompetitorCompetitivePosition());
                }

                return existingLevelTwo;
            })
            .map(levelTwoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelTwo> findAll(Pageable pageable) {
        log.debug("Request to get all LevelTwos");
        return levelTwoRepository.findAll(pageable);
    }

    public Page<LevelTwo> findAllWithEagerRelationships(Pageable pageable) {
        return levelTwoRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelTwo> findOne(Long id) {
        log.debug("Request to get LevelTwo : {}", id);
        return levelTwoRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelTwo : {}", id);
        levelTwoRepository.deleteById(id);
    }
}
