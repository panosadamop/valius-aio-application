package io.valius.app.service.impl;

import io.valius.app.domain.SegmentScoreMAF;
import io.valius.app.repository.SegmentScoreMAFRepository;
import io.valius.app.service.SegmentScoreMAFService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SegmentScoreMAF}.
 */
@Service
@Transactional
public class SegmentScoreMAFServiceImpl implements SegmentScoreMAFService {

    private final Logger log = LoggerFactory.getLogger(SegmentScoreMAFServiceImpl.class);

    private final SegmentScoreMAFRepository segmentScoreMAFRepository;

    public SegmentScoreMAFServiceImpl(SegmentScoreMAFRepository segmentScoreMAFRepository) {
        this.segmentScoreMAFRepository = segmentScoreMAFRepository;
    }

    @Override
    public SegmentScoreMAF save(SegmentScoreMAF segmentScoreMAF) {
        log.debug("Request to save SegmentScoreMAF : {}", segmentScoreMAF);
        return segmentScoreMAFRepository.save(segmentScoreMAF);
    }

    @Override
    public SegmentScoreMAF update(SegmentScoreMAF segmentScoreMAF) {
        log.debug("Request to update SegmentScoreMAF : {}", segmentScoreMAF);
        return segmentScoreMAFRepository.save(segmentScoreMAF);
    }

    @Override
    public Optional<SegmentScoreMAF> partialUpdate(SegmentScoreMAF segmentScoreMAF) {
        log.debug("Request to partially update SegmentScoreMAF : {}", segmentScoreMAF);

        return segmentScoreMAFRepository
            .findById(segmentScoreMAF.getId())
            .map(existingSegmentScoreMAF -> {
                if (segmentScoreMAF.getValue() != null) {
                    existingSegmentScoreMAF.setValue(segmentScoreMAF.getValue());
                }
                if (segmentScoreMAF.getDescription() != null) {
                    existingSegmentScoreMAF.setDescription(segmentScoreMAF.getDescription());
                }
                if (segmentScoreMAF.getLanguage() != null) {
                    existingSegmentScoreMAF.setLanguage(segmentScoreMAF.getLanguage());
                }

                return existingSegmentScoreMAF;
            })
            .map(segmentScoreMAFRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SegmentScoreMAF> findAll(Pageable pageable) {
        log.debug("Request to get all SegmentScoreMAFS");
        return segmentScoreMAFRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SegmentScoreMAF> findOne(Long id) {
        log.debug("Request to get SegmentScoreMAF : {}", id);
        return segmentScoreMAFRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SegmentScoreMAF : {}", id);
        segmentScoreMAFRepository.deleteById(id);
    }
}
