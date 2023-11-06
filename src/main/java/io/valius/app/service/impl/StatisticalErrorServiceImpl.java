package io.valius.app.service.impl;

import io.valius.app.domain.StatisticalError;
import io.valius.app.repository.StatisticalErrorRepository;
import io.valius.app.service.StatisticalErrorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatisticalError}.
 */
@Service
@Transactional
public class StatisticalErrorServiceImpl implements StatisticalErrorService {

    private final Logger log = LoggerFactory.getLogger(StatisticalErrorServiceImpl.class);

    private final StatisticalErrorRepository statisticalErrorRepository;

    public StatisticalErrorServiceImpl(StatisticalErrorRepository statisticalErrorRepository) {
        this.statisticalErrorRepository = statisticalErrorRepository;
    }

    @Override
    public StatisticalError save(StatisticalError statisticalError) {
        log.debug("Request to save StatisticalError : {}", statisticalError);
        return statisticalErrorRepository.save(statisticalError);
    }

    @Override
    public StatisticalError update(StatisticalError statisticalError) {
        log.debug("Request to update StatisticalError : {}", statisticalError);
        return statisticalErrorRepository.save(statisticalError);
    }

    @Override
    public Optional<StatisticalError> partialUpdate(StatisticalError statisticalError) {
        log.debug("Request to partially update StatisticalError : {}", statisticalError);

        return statisticalErrorRepository
            .findById(statisticalError.getId())
            .map(existingStatisticalError -> {
                if (statisticalError.getValue() != null) {
                    existingStatisticalError.setValue(statisticalError.getValue());
                }
                if (statisticalError.getDescription() != null) {
                    existingStatisticalError.setDescription(statisticalError.getDescription());
                }
                if (statisticalError.getLanguage() != null) {
                    existingStatisticalError.setLanguage(statisticalError.getLanguage());
                }

                return existingStatisticalError;
            })
            .map(statisticalErrorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatisticalError> findAll(Pageable pageable) {
        log.debug("Request to get all StatisticalErrors");
        return statisticalErrorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatisticalError> findOne(Long id) {
        log.debug("Request to get StatisticalError : {}", id);
        return statisticalErrorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatisticalError : {}", id);
        statisticalErrorRepository.deleteById(id);
    }
}
