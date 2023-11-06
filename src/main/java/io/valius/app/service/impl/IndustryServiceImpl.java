package io.valius.app.service.impl;

import io.valius.app.domain.Industry;
import io.valius.app.repository.IndustryRepository;
import io.valius.app.service.IndustryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Industry}.
 */
@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    private final Logger log = LoggerFactory.getLogger(IndustryServiceImpl.class);

    private final IndustryRepository industryRepository;

    public IndustryServiceImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    public Industry save(Industry industry) {
        log.debug("Request to save Industry : {}", industry);
        return industryRepository.save(industry);
    }

    @Override
    public Industry update(Industry industry) {
        log.debug("Request to update Industry : {}", industry);
        return industryRepository.save(industry);
    }

    @Override
    public Optional<Industry> partialUpdate(Industry industry) {
        log.debug("Request to partially update Industry : {}", industry);

        return industryRepository
            .findById(industry.getId())
            .map(existingIndustry -> {
                if (industry.getValue() != null) {
                    existingIndustry.setValue(industry.getValue());
                }
                if (industry.getDescription() != null) {
                    existingIndustry.setDescription(industry.getDescription());
                }
                if (industry.getLanguage() != null) {
                    existingIndustry.setLanguage(industry.getLanguage());
                }

                return existingIndustry;
            })
            .map(industryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Industry> findAll(Pageable pageable) {
        log.debug("Request to get all Industries");
        return industryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Industry> findOne(Long id) {
        log.debug("Request to get Industry : {}", id);
        return industryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Industry : {}", id);
        industryRepository.deleteById(id);
    }
}
