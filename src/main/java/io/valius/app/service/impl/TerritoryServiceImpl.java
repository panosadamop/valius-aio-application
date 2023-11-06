package io.valius.app.service.impl;

import io.valius.app.domain.Territory;
import io.valius.app.repository.TerritoryRepository;
import io.valius.app.service.TerritoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Territory}.
 */
@Service
@Transactional
public class TerritoryServiceImpl implements TerritoryService {

    private final Logger log = LoggerFactory.getLogger(TerritoryServiceImpl.class);

    private final TerritoryRepository territoryRepository;

    public TerritoryServiceImpl(TerritoryRepository territoryRepository) {
        this.territoryRepository = territoryRepository;
    }

    @Override
    public Territory save(Territory territory) {
        log.debug("Request to save Territory : {}", territory);
        return territoryRepository.save(territory);
    }

    @Override
    public Territory update(Territory territory) {
        log.debug("Request to update Territory : {}", territory);
        return territoryRepository.save(territory);
    }

    @Override
    public Optional<Territory> partialUpdate(Territory territory) {
        log.debug("Request to partially update Territory : {}", territory);

        return territoryRepository
            .findById(territory.getId())
            .map(existingTerritory -> {
                if (territory.getValue() != null) {
                    existingTerritory.setValue(territory.getValue());
                }
                if (territory.getLanguage() != null) {
                    existingTerritory.setLanguage(territory.getLanguage());
                }

                return existingTerritory;
            })
            .map(territoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Territory> findAll(Pageable pageable) {
        log.debug("Request to get all Territories");
        return territoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Territory> findOne(Long id) {
        log.debug("Request to get Territory : {}", id);
        return territoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Territory : {}", id);
        territoryRepository.deleteById(id);
    }
}
