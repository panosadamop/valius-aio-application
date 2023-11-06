package io.valius.app.service.impl;

import io.valius.app.domain.Occupation;
import io.valius.app.repository.OccupationRepository;
import io.valius.app.service.OccupationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Occupation}.
 */
@Service
@Transactional
public class OccupationServiceImpl implements OccupationService {

    private final Logger log = LoggerFactory.getLogger(OccupationServiceImpl.class);

    private final OccupationRepository occupationRepository;

    public OccupationServiceImpl(OccupationRepository occupationRepository) {
        this.occupationRepository = occupationRepository;
    }

    @Override
    public Occupation save(Occupation occupation) {
        log.debug("Request to save Occupation : {}", occupation);
        return occupationRepository.save(occupation);
    }

    @Override
    public Occupation update(Occupation occupation) {
        log.debug("Request to update Occupation : {}", occupation);
        return occupationRepository.save(occupation);
    }

    @Override
    public Optional<Occupation> partialUpdate(Occupation occupation) {
        log.debug("Request to partially update Occupation : {}", occupation);

        return occupationRepository
            .findById(occupation.getId())
            .map(existingOccupation -> {
                if (occupation.getValue() != null) {
                    existingOccupation.setValue(occupation.getValue());
                }
                if (occupation.getDescription() != null) {
                    existingOccupation.setDescription(occupation.getDescription());
                }
                if (occupation.getLanguage() != null) {
                    existingOccupation.setLanguage(occupation.getLanguage());
                }

                return existingOccupation;
            })
            .map(occupationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Occupation> findAll(Pageable pageable) {
        log.debug("Request to get all Occupations");
        return occupationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Occupation> findOne(Long id) {
        log.debug("Request to get Occupation : {}", id);
        return occupationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Occupation : {}", id);
        occupationRepository.deleteById(id);
    }
}
