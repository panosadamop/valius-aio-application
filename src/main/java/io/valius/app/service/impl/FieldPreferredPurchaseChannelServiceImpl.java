package io.valius.app.service.impl;

import io.valius.app.domain.FieldPreferredPurchaseChannel;
import io.valius.app.repository.FieldPreferredPurchaseChannelRepository;
import io.valius.app.service.FieldPreferredPurchaseChannelService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldPreferredPurchaseChannel}.
 */
@Service
@Transactional
public class FieldPreferredPurchaseChannelServiceImpl implements FieldPreferredPurchaseChannelService {

    private final Logger log = LoggerFactory.getLogger(FieldPreferredPurchaseChannelServiceImpl.class);

    private final FieldPreferredPurchaseChannelRepository fieldPreferredPurchaseChannelRepository;

    public FieldPreferredPurchaseChannelServiceImpl(FieldPreferredPurchaseChannelRepository fieldPreferredPurchaseChannelRepository) {
        this.fieldPreferredPurchaseChannelRepository = fieldPreferredPurchaseChannelRepository;
    }

    @Override
    public FieldPreferredPurchaseChannel save(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel) {
        log.debug("Request to save FieldPreferredPurchaseChannel : {}", fieldPreferredPurchaseChannel);
        return fieldPreferredPurchaseChannelRepository.save(fieldPreferredPurchaseChannel);
    }

    @Override
    public FieldPreferredPurchaseChannel update(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel) {
        log.debug("Request to update FieldPreferredPurchaseChannel : {}", fieldPreferredPurchaseChannel);
        return fieldPreferredPurchaseChannelRepository.save(fieldPreferredPurchaseChannel);
    }

    @Override
    public Optional<FieldPreferredPurchaseChannel> partialUpdate(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel) {
        log.debug("Request to partially update FieldPreferredPurchaseChannel : {}", fieldPreferredPurchaseChannel);

        return fieldPreferredPurchaseChannelRepository
            .findById(fieldPreferredPurchaseChannel.getId())
            .map(existingFieldPreferredPurchaseChannel -> {
                if (fieldPreferredPurchaseChannel.getPreferredPurchaseChannel() != null) {
                    existingFieldPreferredPurchaseChannel.setPreferredPurchaseChannel(
                        fieldPreferredPurchaseChannel.getPreferredPurchaseChannel()
                    );
                }

                return existingFieldPreferredPurchaseChannel;
            })
            .map(fieldPreferredPurchaseChannelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldPreferredPurchaseChannel> findAll(Pageable pageable) {
        log.debug("Request to get all FieldPreferredPurchaseChannels");
        return fieldPreferredPurchaseChannelRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldPreferredPurchaseChannel> findOne(Long id) {
        log.debug("Request to get FieldPreferredPurchaseChannel : {}", id);
        return fieldPreferredPurchaseChannelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldPreferredPurchaseChannel : {}", id);
        fieldPreferredPurchaseChannelRepository.deleteById(id);
    }
}
