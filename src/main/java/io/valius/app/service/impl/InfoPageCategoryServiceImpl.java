package io.valius.app.service.impl;

import io.valius.app.domain.InfoPageCategory;
import io.valius.app.repository.InfoPageCategoryRepository;
import io.valius.app.service.InfoPageCategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InfoPageCategory}.
 */
@Service
@Transactional
public class InfoPageCategoryServiceImpl implements InfoPageCategoryService {

    private final Logger log = LoggerFactory.getLogger(InfoPageCategoryServiceImpl.class);

    private final InfoPageCategoryRepository infoPageCategoryRepository;

    public InfoPageCategoryServiceImpl(InfoPageCategoryRepository infoPageCategoryRepository) {
        this.infoPageCategoryRepository = infoPageCategoryRepository;
    }

    @Override
    public InfoPageCategory save(InfoPageCategory infoPageCategory) {
        log.debug("Request to save InfoPageCategory : {}", infoPageCategory);
        return infoPageCategoryRepository.save(infoPageCategory);
    }

    @Override
    public InfoPageCategory update(InfoPageCategory infoPageCategory) {
        log.debug("Request to update InfoPageCategory : {}", infoPageCategory);
        return infoPageCategoryRepository.save(infoPageCategory);
    }

    @Override
    public Optional<InfoPageCategory> partialUpdate(InfoPageCategory infoPageCategory) {
        log.debug("Request to partially update InfoPageCategory : {}", infoPageCategory);

        return infoPageCategoryRepository
            .findById(infoPageCategory.getId())
            .map(existingInfoPageCategory -> {
                if (infoPageCategory.getValue() != null) {
                    existingInfoPageCategory.setValue(infoPageCategory.getValue());
                }
                if (infoPageCategory.getLanguage() != null) {
                    existingInfoPageCategory.setLanguage(infoPageCategory.getLanguage());
                }

                return existingInfoPageCategory;
            })
            .map(infoPageCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InfoPageCategory> findAll(Pageable pageable) {
        log.debug("Request to get all InfoPageCategories");
        return infoPageCategoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InfoPageCategory> findOne(Long id) {
        log.debug("Request to get InfoPageCategory : {}", id);
        return infoPageCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InfoPageCategory : {}", id);
        infoPageCategoryRepository.deleteById(id);
    }
}
