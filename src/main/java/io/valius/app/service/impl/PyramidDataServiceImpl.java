package io.valius.app.service.impl;

import io.valius.app.domain.PyramidData;
import io.valius.app.repository.PyramidDataRepository;
import io.valius.app.service.PyramidDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PyramidData}.
 */
@Service
@Transactional
public class PyramidDataServiceImpl implements PyramidDataService {

    private final Logger log = LoggerFactory.getLogger(PyramidDataServiceImpl.class);

    private final PyramidDataRepository pyramidDataRepository;

    public PyramidDataServiceImpl(PyramidDataRepository pyramidDataRepository) {
        this.pyramidDataRepository = pyramidDataRepository;
    }

    @Override
    public PyramidData save(PyramidData pyramidData) {
        log.debug("Request to save PyramidData : {}", pyramidData);
        return pyramidDataRepository.save(pyramidData);
    }

    @Override
    public PyramidData update(PyramidData pyramidData) {
        log.debug("Request to update PyramidData : {}", pyramidData);
        return pyramidDataRepository.save(pyramidData);
    }

    @Override
    public Optional<PyramidData> partialUpdate(PyramidData pyramidData) {
        log.debug("Request to partially update PyramidData : {}", pyramidData);

        return pyramidDataRepository
            .findById(pyramidData.getId())
            .map(existingPyramidData -> {
                if (pyramidData.getIdentifier() != null) {
                    existingPyramidData.setIdentifier(pyramidData.getIdentifier());
                }
                if (pyramidData.getCategory() != null) {
                    existingPyramidData.setCategory(pyramidData.getCategory());
                }
                if (pyramidData.getValue() != null) {
                    existingPyramidData.setValue(pyramidData.getValue());
                }
                if (pyramidData.getOrder() != null) {
                    existingPyramidData.setOrder(pyramidData.getOrder());
                }
                if (pyramidData.getImg() != null) {
                    existingPyramidData.setImg(pyramidData.getImg());
                }
                if (pyramidData.getImgContentType() != null) {
                    existingPyramidData.setImgContentType(pyramidData.getImgContentType());
                }

                return existingPyramidData;
            })
            .map(pyramidDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PyramidData> findAll(Pageable pageable) {
        log.debug("Request to get all PyramidData");
        return pyramidDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PyramidData> findOne(Long id) {
        log.debug("Request to get PyramidData : {}", id);
        return pyramidDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PyramidData : {}", id);
        pyramidDataRepository.deleteById(id);
    }
}
