package io.valius.app.service.impl;

import io.valius.app.domain.LevelThree;
import io.valius.app.repository.LevelThreeRepository;
import io.valius.app.service.LevelThreeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LevelThree}.
 */
@Service
@Transactional
public class LevelThreeServiceImpl implements LevelThreeService {

    private final Logger log = LoggerFactory.getLogger(LevelThreeServiceImpl.class);

    private final LevelThreeRepository levelThreeRepository;

    public LevelThreeServiceImpl(LevelThreeRepository levelThreeRepository) {
        this.levelThreeRepository = levelThreeRepository;
    }

    @Override
    public LevelThree save(LevelThree levelThree) {
        log.debug("Request to save LevelThree : {}", levelThree);
        return levelThreeRepository.save(levelThree);
    }

    @Override
    public LevelThree update(LevelThree levelThree) {
        log.debug("Request to update LevelThree : {}", levelThree);
        return levelThreeRepository.save(levelThree);
    }

    @Override
    public Optional<LevelThree> partialUpdate(LevelThree levelThree) {
        log.debug("Request to partially update LevelThree : {}", levelThree);

        return levelThreeRepository
            .findById(levelThree.getId())
            .map(existingLevelThree -> {
                if (levelThree.getIdentifier() != null) {
                    existingLevelThree.setIdentifier(levelThree.getIdentifier());
                }
                if (levelThree.getMafCategory() != null) {
                    existingLevelThree.setMafCategory(levelThree.getMafCategory());
                }
                if (levelThree.getWeightingMaf() != null) {
                    existingLevelThree.setWeightingMaf(levelThree.getWeightingMaf());
                }
                if (levelThree.getLowAttractivenessRangeMaf() != null) {
                    existingLevelThree.setLowAttractivenessRangeMaf(levelThree.getLowAttractivenessRangeMaf());
                }
                if (levelThree.getMediumAttractivenessRangeMaf() != null) {
                    existingLevelThree.setMediumAttractivenessRangeMaf(levelThree.getMediumAttractivenessRangeMaf());
                }
                if (levelThree.getHighAttractivenessRangeMaf() != null) {
                    existingLevelThree.setHighAttractivenessRangeMaf(levelThree.getHighAttractivenessRangeMaf());
                }
                if (levelThree.getSegmentScoreMaf() != null) {
                    existingLevelThree.setSegmentScoreMaf(levelThree.getSegmentScoreMaf());
                }

                return existingLevelThree;
            })
            .map(levelThreeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelThree> findAll(Pageable pageable) {
        log.debug("Request to get all LevelThrees");
        return levelThreeRepository.findAll(pageable);
    }

    public Page<LevelThree> findAllWithEagerRelationships(Pageable pageable) {
        return levelThreeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelThree> findOne(Long id) {
        log.debug("Request to get LevelThree : {}", id);
        return levelThreeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelThree : {}", id);
        levelThreeRepository.deleteById(id);
    }
}
