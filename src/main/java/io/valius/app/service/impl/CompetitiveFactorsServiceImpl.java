package io.valius.app.service.impl;

import io.valius.app.domain.CompetitiveFactors;
import io.valius.app.repository.CompetitiveFactorsRepository;
import io.valius.app.service.CompetitiveFactorsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompetitiveFactors}.
 */
@Service
@Transactional
public class CompetitiveFactorsServiceImpl implements CompetitiveFactorsService {

    private final Logger log = LoggerFactory.getLogger(CompetitiveFactorsServiceImpl.class);

    private final CompetitiveFactorsRepository competitiveFactorsRepository;

    public CompetitiveFactorsServiceImpl(CompetitiveFactorsRepository competitiveFactorsRepository) {
        this.competitiveFactorsRepository = competitiveFactorsRepository;
    }

    @Override
    public CompetitiveFactors save(CompetitiveFactors competitiveFactors) {
        log.debug("Request to save CompetitiveFactors : {}", competitiveFactors);
        return competitiveFactorsRepository.save(competitiveFactors);
    }

    @Override
    public CompetitiveFactors update(CompetitiveFactors competitiveFactors) {
        log.debug("Request to update CompetitiveFactors : {}", competitiveFactors);
        return competitiveFactorsRepository.save(competitiveFactors);
    }

    @Override
    public Optional<CompetitiveFactors> partialUpdate(CompetitiveFactors competitiveFactors) {
        log.debug("Request to partially update CompetitiveFactors : {}", competitiveFactors);

        return competitiveFactorsRepository
            .findById(competitiveFactors.getId())
            .map(existingCompetitiveFactors -> {
                if (competitiveFactors.getValue() != null) {
                    existingCompetitiveFactors.setValue(competitiveFactors.getValue());
                }
                if (competitiveFactors.getDescription() != null) {
                    existingCompetitiveFactors.setDescription(competitiveFactors.getDescription());
                }
                if (competitiveFactors.getLanguage() != null) {
                    existingCompetitiveFactors.setLanguage(competitiveFactors.getLanguage());
                }

                return existingCompetitiveFactors;
            })
            .map(competitiveFactorsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetitiveFactors> findAll(Pageable pageable) {
        log.debug("Request to get all CompetitiveFactors");
        return competitiveFactorsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitiveFactors> findOne(Long id) {
        log.debug("Request to get CompetitiveFactors : {}", id);
        return competitiveFactorsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompetitiveFactors : {}", id);
        competitiveFactorsRepository.deleteById(id);
    }
}
