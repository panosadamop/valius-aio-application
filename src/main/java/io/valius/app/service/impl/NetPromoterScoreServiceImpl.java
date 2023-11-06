package io.valius.app.service.impl;

import io.valius.app.domain.NetPromoterScore;
import io.valius.app.repository.NetPromoterScoreRepository;
import io.valius.app.service.NetPromoterScoreService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NetPromoterScore}.
 */
@Service
@Transactional
public class NetPromoterScoreServiceImpl implements NetPromoterScoreService {

    private final Logger log = LoggerFactory.getLogger(NetPromoterScoreServiceImpl.class);

    private final NetPromoterScoreRepository netPromoterScoreRepository;

    public NetPromoterScoreServiceImpl(NetPromoterScoreRepository netPromoterScoreRepository) {
        this.netPromoterScoreRepository = netPromoterScoreRepository;
    }

    @Override
    public NetPromoterScore save(NetPromoterScore netPromoterScore) {
        log.debug("Request to save NetPromoterScore : {}", netPromoterScore);
        return netPromoterScoreRepository.save(netPromoterScore);
    }

    @Override
    public NetPromoterScore update(NetPromoterScore netPromoterScore) {
        log.debug("Request to update NetPromoterScore : {}", netPromoterScore);
        return netPromoterScoreRepository.save(netPromoterScore);
    }

    @Override
    public Optional<NetPromoterScore> partialUpdate(NetPromoterScore netPromoterScore) {
        log.debug("Request to partially update NetPromoterScore : {}", netPromoterScore);

        return netPromoterScoreRepository
            .findById(netPromoterScore.getId())
            .map(existingNetPromoterScore -> {
                if (netPromoterScore.getValue() != null) {
                    existingNetPromoterScore.setValue(netPromoterScore.getValue());
                }
                if (netPromoterScore.getDescription() != null) {
                    existingNetPromoterScore.setDescription(netPromoterScore.getDescription());
                }
                if (netPromoterScore.getLanguage() != null) {
                    existingNetPromoterScore.setLanguage(netPromoterScore.getLanguage());
                }

                return existingNetPromoterScore;
            })
            .map(netPromoterScoreRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NetPromoterScore> findAll(Pageable pageable) {
        log.debug("Request to get all NetPromoterScores");
        return netPromoterScoreRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NetPromoterScore> findOne(Long id) {
        log.debug("Request to get NetPromoterScore : {}", id);
        return netPromoterScoreRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NetPromoterScore : {}", id);
        netPromoterScoreRepository.deleteById(id);
    }
}
