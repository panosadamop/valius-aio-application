package io.valius.app.service.impl;

import io.valius.app.domain.RelatedServiceElements;
import io.valius.app.repository.RelatedServiceElementsRepository;
import io.valius.app.service.RelatedServiceElementsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RelatedServiceElements}.
 */
@Service
@Transactional
public class RelatedServiceElementsServiceImpl implements RelatedServiceElementsService {

    private final Logger log = LoggerFactory.getLogger(RelatedServiceElementsServiceImpl.class);

    private final RelatedServiceElementsRepository relatedServiceElementsRepository;

    public RelatedServiceElementsServiceImpl(RelatedServiceElementsRepository relatedServiceElementsRepository) {
        this.relatedServiceElementsRepository = relatedServiceElementsRepository;
    }

    @Override
    public RelatedServiceElements save(RelatedServiceElements relatedServiceElements) {
        log.debug("Request to save RelatedServiceElements : {}", relatedServiceElements);
        return relatedServiceElementsRepository.save(relatedServiceElements);
    }

    @Override
    public RelatedServiceElements update(RelatedServiceElements relatedServiceElements) {
        log.debug("Request to update RelatedServiceElements : {}", relatedServiceElements);
        return relatedServiceElementsRepository.save(relatedServiceElements);
    }

    @Override
    public Optional<RelatedServiceElements> partialUpdate(RelatedServiceElements relatedServiceElements) {
        log.debug("Request to partially update RelatedServiceElements : {}", relatedServiceElements);

        return relatedServiceElementsRepository
            .findById(relatedServiceElements.getId())
            .map(existingRelatedServiceElements -> {
                if (relatedServiceElements.getValue() != null) {
                    existingRelatedServiceElements.setValue(relatedServiceElements.getValue());
                }
                if (relatedServiceElements.getDescription() != null) {
                    existingRelatedServiceElements.setDescription(relatedServiceElements.getDescription());
                }
                if (relatedServiceElements.getLanguage() != null) {
                    existingRelatedServiceElements.setLanguage(relatedServiceElements.getLanguage());
                }

                return existingRelatedServiceElements;
            })
            .map(relatedServiceElementsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RelatedServiceElements> findAll(Pageable pageable) {
        log.debug("Request to get all RelatedServiceElements");
        return relatedServiceElementsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RelatedServiceElements> findOne(Long id) {
        log.debug("Request to get RelatedServiceElements : {}", id);
        return relatedServiceElementsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RelatedServiceElements : {}", id);
        relatedServiceElementsRepository.deleteById(id);
    }
}
