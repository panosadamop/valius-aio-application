package io.valius.app.service.impl;

import io.valius.app.domain.SocialFactors;
import io.valius.app.repository.SocialFactorsRepository;
import io.valius.app.service.SocialFactorsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocialFactors}.
 */
@Service
@Transactional
public class SocialFactorsServiceImpl implements SocialFactorsService {

    private final Logger log = LoggerFactory.getLogger(SocialFactorsServiceImpl.class);

    private final SocialFactorsRepository socialFactorsRepository;

    public SocialFactorsServiceImpl(SocialFactorsRepository socialFactorsRepository) {
        this.socialFactorsRepository = socialFactorsRepository;
    }

    @Override
    public SocialFactors save(SocialFactors socialFactors) {
        log.debug("Request to save SocialFactors : {}", socialFactors);
        return socialFactorsRepository.save(socialFactors);
    }

    @Override
    public SocialFactors update(SocialFactors socialFactors) {
        log.debug("Request to update SocialFactors : {}", socialFactors);
        return socialFactorsRepository.save(socialFactors);
    }

    @Override
    public Optional<SocialFactors> partialUpdate(SocialFactors socialFactors) {
        log.debug("Request to partially update SocialFactors : {}", socialFactors);

        return socialFactorsRepository
            .findById(socialFactors.getId())
            .map(existingSocialFactors -> {
                if (socialFactors.getValue() != null) {
                    existingSocialFactors.setValue(socialFactors.getValue());
                }
                if (socialFactors.getDescription() != null) {
                    existingSocialFactors.setDescription(socialFactors.getDescription());
                }
                if (socialFactors.getLanguage() != null) {
                    existingSocialFactors.setLanguage(socialFactors.getLanguage());
                }

                return existingSocialFactors;
            })
            .map(socialFactorsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialFactors> findAll(Pageable pageable) {
        log.debug("Request to get all SocialFactors");
        return socialFactorsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocialFactors> findOne(Long id) {
        log.debug("Request to get SocialFactors : {}", id);
        return socialFactorsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialFactors : {}", id);
        socialFactorsRepository.deleteById(id);
    }
}
