package io.valius.app.service.impl;

import io.valius.app.domain.SegmentUniqueCharacteristic;
import io.valius.app.repository.SegmentUniqueCharacteristicRepository;
import io.valius.app.service.SegmentUniqueCharacteristicService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SegmentUniqueCharacteristic}.
 */
@Service
@Transactional
public class SegmentUniqueCharacteristicServiceImpl implements SegmentUniqueCharacteristicService {

    private final Logger log = LoggerFactory.getLogger(SegmentUniqueCharacteristicServiceImpl.class);

    private final SegmentUniqueCharacteristicRepository segmentUniqueCharacteristicRepository;

    public SegmentUniqueCharacteristicServiceImpl(SegmentUniqueCharacteristicRepository segmentUniqueCharacteristicRepository) {
        this.segmentUniqueCharacteristicRepository = segmentUniqueCharacteristicRepository;
    }

    @Override
    public SegmentUniqueCharacteristic save(SegmentUniqueCharacteristic segmentUniqueCharacteristic) {
        log.debug("Request to save SegmentUniqueCharacteristic : {}", segmentUniqueCharacteristic);
        return segmentUniqueCharacteristicRepository.save(segmentUniqueCharacteristic);
    }

    @Override
    public SegmentUniqueCharacteristic update(SegmentUniqueCharacteristic segmentUniqueCharacteristic) {
        log.debug("Request to update SegmentUniqueCharacteristic : {}", segmentUniqueCharacteristic);
        return segmentUniqueCharacteristicRepository.save(segmentUniqueCharacteristic);
    }

    @Override
    public Optional<SegmentUniqueCharacteristic> partialUpdate(SegmentUniqueCharacteristic segmentUniqueCharacteristic) {
        log.debug("Request to partially update SegmentUniqueCharacteristic : {}", segmentUniqueCharacteristic);

        return segmentUniqueCharacteristicRepository
            .findById(segmentUniqueCharacteristic.getId())
            .map(existingSegmentUniqueCharacteristic -> {
                if (segmentUniqueCharacteristic.getValue() != null) {
                    existingSegmentUniqueCharacteristic.setValue(segmentUniqueCharacteristic.getValue());
                }
                if (segmentUniqueCharacteristic.getCategory() != null) {
                    existingSegmentUniqueCharacteristic.setCategory(segmentUniqueCharacteristic.getCategory());
                }
                if (segmentUniqueCharacteristic.getDescription() != null) {
                    existingSegmentUniqueCharacteristic.setDescription(segmentUniqueCharacteristic.getDescription());
                }
                if (segmentUniqueCharacteristic.getLanguage() != null) {
                    existingSegmentUniqueCharacteristic.setLanguage(segmentUniqueCharacteristic.getLanguage());
                }

                return existingSegmentUniqueCharacteristic;
            })
            .map(segmentUniqueCharacteristicRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SegmentUniqueCharacteristic> findAll(Pageable pageable) {
        log.debug("Request to get all SegmentUniqueCharacteristics");
        return segmentUniqueCharacteristicRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SegmentUniqueCharacteristic> findOne(Long id) {
        log.debug("Request to get SegmentUniqueCharacteristic : {}", id);
        return segmentUniqueCharacteristicRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SegmentUniqueCharacteristic : {}", id);
        segmentUniqueCharacteristicRepository.deleteById(id);
    }
}
