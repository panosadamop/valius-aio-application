package io.valius.app.service.impl;

import io.valius.app.domain.IntangibleElements;
import io.valius.app.repository.IntangibleElementsRepository;
import io.valius.app.service.IntangibleElementsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IntangibleElements}.
 */
@Service
@Transactional
public class IntangibleElementsServiceImpl implements IntangibleElementsService {

    private final Logger log = LoggerFactory.getLogger(IntangibleElementsServiceImpl.class);

    private final IntangibleElementsRepository intangibleElementsRepository;

    public IntangibleElementsServiceImpl(IntangibleElementsRepository intangibleElementsRepository) {
        this.intangibleElementsRepository = intangibleElementsRepository;
    }

    @Override
    public IntangibleElements save(IntangibleElements intangibleElements) {
        log.debug("Request to save IntangibleElements : {}", intangibleElements);
        return intangibleElementsRepository.save(intangibleElements);
    }

    @Override
    public IntangibleElements update(IntangibleElements intangibleElements) {
        log.debug("Request to update IntangibleElements : {}", intangibleElements);
        return intangibleElementsRepository.save(intangibleElements);
    }

    @Override
    public Optional<IntangibleElements> partialUpdate(IntangibleElements intangibleElements) {
        log.debug("Request to partially update IntangibleElements : {}", intangibleElements);

        return intangibleElementsRepository
            .findById(intangibleElements.getId())
            .map(existingIntangibleElements -> {
                if (intangibleElements.getValue() != null) {
                    existingIntangibleElements.setValue(intangibleElements.getValue());
                }
                if (intangibleElements.getDescription() != null) {
                    existingIntangibleElements.setDescription(intangibleElements.getDescription());
                }
                if (intangibleElements.getLanguage() != null) {
                    existingIntangibleElements.setLanguage(intangibleElements.getLanguage());
                }

                return existingIntangibleElements;
            })
            .map(intangibleElementsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntangibleElements> findAll(Pageable pageable) {
        log.debug("Request to get all IntangibleElements");
        return intangibleElementsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntangibleElements> findOne(Long id) {
        log.debug("Request to get IntangibleElements : {}", id);
        return intangibleElementsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntangibleElements : {}", id);
        intangibleElementsRepository.deleteById(id);
    }
}
