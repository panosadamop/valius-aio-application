package io.valius.app.service.impl;

import io.valius.app.domain.LevelOne;
import io.valius.app.repository.LevelOneRepository;
import io.valius.app.service.LevelOneService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LevelOne}.
 */
@Service
@Transactional
public class LevelOneServiceImpl implements LevelOneService {

    private final Logger log = LoggerFactory.getLogger(LevelOneServiceImpl.class);

    private final LevelOneRepository levelOneRepository;

    public LevelOneServiceImpl(LevelOneRepository levelOneRepository) {
        this.levelOneRepository = levelOneRepository;
    }

    @Override
    public LevelOne save(LevelOne levelOne) {
        log.debug("Request to save LevelOne : {}", levelOne);
        return levelOneRepository.save(levelOne);
    }

    @Override
    public LevelOne update(LevelOne levelOne) {
        log.debug("Request to update LevelOne : {}", levelOne);
        return levelOneRepository.save(levelOne);
    }

    @Override
    public Optional<LevelOne> partialUpdate(LevelOne levelOne) {
        log.debug("Request to partially update LevelOne : {}", levelOne);

        return levelOneRepository
            .findById(levelOne.getId())
            .map(existingLevelOne -> {
                if (levelOne.getIdentifier() != null) {
                    existingLevelOne.setIdentifier(levelOne.getIdentifier());
                }
                if (levelOne.getCompanyName() != null) {
                    existingLevelOne.setCompanyName(levelOne.getCompanyName());
                }
                if (levelOne.getCompanyLogo() != null) {
                    existingLevelOne.setCompanyLogo(levelOne.getCompanyLogo());
                }
                if (levelOne.getCompanyLogoContentType() != null) {
                    existingLevelOne.setCompanyLogoContentType(levelOne.getCompanyLogoContentType());
                }
                if (levelOne.getBrandName() != null) {
                    existingLevelOne.setBrandName(levelOne.getBrandName());
                }
                if (levelOne.getProductLogo() != null) {
                    existingLevelOne.setProductLogo(levelOne.getProductLogo());
                }
                if (levelOne.getProductLogoContentType() != null) {
                    existingLevelOne.setProductLogoContentType(levelOne.getProductLogoContentType());
                }
                if (levelOne.getIndustry() != null) {
                    existingLevelOne.setIndustry(levelOne.getIndustry());
                }
                if (levelOne.getOrganizationType() != null) {
                    existingLevelOne.setOrganizationType(levelOne.getOrganizationType());
                }
                if (levelOne.getProductsServices() != null) {
                    existingLevelOne.setProductsServices(levelOne.getProductsServices());
                }
                if (levelOne.getTerritory() != null) {
                    existingLevelOne.setTerritory(levelOne.getTerritory());
                }
                if (levelOne.getNoEmployees() != null) {
                    existingLevelOne.setNoEmployees(levelOne.getNoEmployees());
                }
                if (levelOne.getRevenues() != null) {
                    existingLevelOne.setRevenues(levelOne.getRevenues());
                }
                if (levelOne.getMission() != null) {
                    existingLevelOne.setMission(levelOne.getMission());
                }
                if (levelOne.getVision() != null) {
                    existingLevelOne.setVision(levelOne.getVision());
                }
                if (levelOne.getCompanyValues() != null) {
                    existingLevelOne.setCompanyValues(levelOne.getCompanyValues());
                }
                if (levelOne.getStrategicFocus() != null) {
                    existingLevelOne.setStrategicFocus(levelOne.getStrategicFocus());
                }
                if (levelOne.getMarketingBudget() != null) {
                    existingLevelOne.setMarketingBudget(levelOne.getMarketingBudget());
                }
                if (levelOne.getProductDescription() != null) {
                    existingLevelOne.setProductDescription(levelOne.getProductDescription());
                }
                if (levelOne.getMaturityPhase() != null) {
                    existingLevelOne.setMaturityPhase(levelOne.getMaturityPhase());
                }
                if (levelOne.getCompetitivePosition() != null) {
                    existingLevelOne.setCompetitivePosition(levelOne.getCompetitivePosition());
                }
                if (levelOne.getTargetAudienceDescription() != null) {
                    existingLevelOne.setTargetAudienceDescription(levelOne.getTargetAudienceDescription());
                }
                if (levelOne.getPotentialCustomersGroups() != null) {
                    existingLevelOne.setPotentialCustomersGroups(levelOne.getPotentialCustomersGroups());
                }
                if (levelOne.getStrengths() != null) {
                    existingLevelOne.setStrengths(levelOne.getStrengths());
                }
                if (levelOne.getWeaknesses() != null) {
                    existingLevelOne.setWeaknesses(levelOne.getWeaknesses());
                }
                if (levelOne.getOpportunities() != null) {
                    existingLevelOne.setOpportunities(levelOne.getOpportunities());
                }
                if (levelOne.getThreats() != null) {
                    existingLevelOne.setThreats(levelOne.getThreats());
                }

                return existingLevelOne;
            })
            .map(levelOneRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelOne> findAll(Pageable pageable) {
        log.debug("Request to get all LevelOnes");
        return levelOneRepository.findAll(pageable);
    }

    public Page<LevelOne> findAllWithEagerRelationships(Pageable pageable) {
        return levelOneRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelOne> findOne(Long id) {
        log.debug("Request to get LevelOne : {}", id);
        return levelOneRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelOne : {}", id);
        levelOneRepository.deleteById(id);
    }
}
