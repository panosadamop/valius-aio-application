package io.valius.app.service.impl;

import io.valius.app.domain.InformationPages;
import io.valius.app.repository.InformationPagesRepository;
import io.valius.app.service.InformationPagesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InformationPages}.
 */
@Service
@Transactional
public class InformationPagesServiceImpl implements InformationPagesService {

    private final Logger log = LoggerFactory.getLogger(InformationPagesServiceImpl.class);

    private final InformationPagesRepository informationPagesRepository;

    public InformationPagesServiceImpl(InformationPagesRepository informationPagesRepository) {
        this.informationPagesRepository = informationPagesRepository;
    }

    @Override
    public InformationPages save(InformationPages informationPages) {
        log.debug("Request to save InformationPages : {}", informationPages);
        return informationPagesRepository.save(informationPages);
    }

    @Override
    public InformationPages update(InformationPages informationPages) {
        log.debug("Request to update InformationPages : {}", informationPages);
        return informationPagesRepository.save(informationPages);
    }

    @Override
    public Optional<InformationPages> partialUpdate(InformationPages informationPages) {
        log.debug("Request to partially update InformationPages : {}", informationPages);

        return informationPagesRepository
            .findById(informationPages.getId())
            .map(existingInformationPages -> {
                if (informationPages.getTitle() != null) {
                    existingInformationPages.setTitle(informationPages.getTitle());
                }
                if (informationPages.getSubTitle() != null) {
                    existingInformationPages.setSubTitle(informationPages.getSubTitle());
                }
                if (informationPages.getDescription() != null) {
                    existingInformationPages.setDescription(informationPages.getDescription());
                }

                return existingInformationPages;
            })
            .map(informationPagesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformationPages> findAll(Pageable pageable) {
        log.debug("Request to get all InformationPages");
        return informationPagesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InformationPages> findOne(Long id) {
        log.debug("Request to get InformationPages : {}", id);
        return informationPagesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InformationPages : {}", id);
        informationPagesRepository.deleteById(id);
    }
}
