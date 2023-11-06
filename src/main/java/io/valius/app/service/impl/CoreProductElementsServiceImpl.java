package io.valius.app.service.impl;

import io.valius.app.domain.CoreProductElements;
import io.valius.app.repository.CoreProductElementsRepository;
import io.valius.app.service.CoreProductElementsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CoreProductElements}.
 */
@Service
@Transactional
public class CoreProductElementsServiceImpl implements CoreProductElementsService {

    private final Logger log = LoggerFactory.getLogger(CoreProductElementsServiceImpl.class);

    private final CoreProductElementsRepository coreProductElementsRepository;

    public CoreProductElementsServiceImpl(CoreProductElementsRepository coreProductElementsRepository) {
        this.coreProductElementsRepository = coreProductElementsRepository;
    }

    @Override
    public CoreProductElements save(CoreProductElements coreProductElements) {
        log.debug("Request to save CoreProductElements : {}", coreProductElements);
        return coreProductElementsRepository.save(coreProductElements);
    }

    @Override
    public CoreProductElements update(CoreProductElements coreProductElements) {
        log.debug("Request to update CoreProductElements : {}", coreProductElements);
        return coreProductElementsRepository.save(coreProductElements);
    }

    @Override
    public Optional<CoreProductElements> partialUpdate(CoreProductElements coreProductElements) {
        log.debug("Request to partially update CoreProductElements : {}", coreProductElements);

        return coreProductElementsRepository
            .findById(coreProductElements.getId())
            .map(existingCoreProductElements -> {
                if (coreProductElements.getValue() != null) {
                    existingCoreProductElements.setValue(coreProductElements.getValue());
                }
                if (coreProductElements.getDescription() != null) {
                    existingCoreProductElements.setDescription(coreProductElements.getDescription());
                }
                if (coreProductElements.getLanguage() != null) {
                    existingCoreProductElements.setLanguage(coreProductElements.getLanguage());
                }

                return existingCoreProductElements;
            })
            .map(coreProductElementsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CoreProductElements> findAll(Pageable pageable) {
        log.debug("Request to get all CoreProductElements");
        return coreProductElementsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CoreProductElements> findOne(Long id) {
        log.debug("Request to get CoreProductElements : {}", id);
        return coreProductElementsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoreProductElements : {}", id);
        coreProductElementsRepository.deleteById(id);
    }
}
