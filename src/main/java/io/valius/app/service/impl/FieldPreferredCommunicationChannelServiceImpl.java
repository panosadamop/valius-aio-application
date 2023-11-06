package io.valius.app.service.impl;

import io.valius.app.domain.FieldPreferredCommunicationChannel;
import io.valius.app.repository.FieldPreferredCommunicationChannelRepository;
import io.valius.app.service.FieldPreferredCommunicationChannelService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldPreferredCommunicationChannel}.
 */
@Service
@Transactional
public class FieldPreferredCommunicationChannelServiceImpl implements FieldPreferredCommunicationChannelService {

    private final Logger log = LoggerFactory.getLogger(FieldPreferredCommunicationChannelServiceImpl.class);

    private final FieldPreferredCommunicationChannelRepository fieldPreferredCommunicationChannelRepository;

    public FieldPreferredCommunicationChannelServiceImpl(
        FieldPreferredCommunicationChannelRepository fieldPreferredCommunicationChannelRepository
    ) {
        this.fieldPreferredCommunicationChannelRepository = fieldPreferredCommunicationChannelRepository;
    }

    @Override
    public FieldPreferredCommunicationChannel save(FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel) {
        log.debug("Request to save FieldPreferredCommunicationChannel : {}", fieldPreferredCommunicationChannel);
        return fieldPreferredCommunicationChannelRepository.save(fieldPreferredCommunicationChannel);
    }

    @Override
    public FieldPreferredCommunicationChannel update(FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel) {
        log.debug("Request to update FieldPreferredCommunicationChannel : {}", fieldPreferredCommunicationChannel);
        return fieldPreferredCommunicationChannelRepository.save(fieldPreferredCommunicationChannel);
    }

    @Override
    public Optional<FieldPreferredCommunicationChannel> partialUpdate(
        FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel
    ) {
        log.debug("Request to partially update FieldPreferredCommunicationChannel : {}", fieldPreferredCommunicationChannel);

        return fieldPreferredCommunicationChannelRepository
            .findById(fieldPreferredCommunicationChannel.getId())
            .map(existingFieldPreferredCommunicationChannel -> {
                if (fieldPreferredCommunicationChannel.getPreferredCommunicationChannel() != null) {
                    existingFieldPreferredCommunicationChannel.setPreferredCommunicationChannel(
                        fieldPreferredCommunicationChannel.getPreferredCommunicationChannel()
                    );
                }

                return existingFieldPreferredCommunicationChannel;
            })
            .map(fieldPreferredCommunicationChannelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldPreferredCommunicationChannel> findAll(Pageable pageable) {
        log.debug("Request to get all FieldPreferredCommunicationChannels");
        return fieldPreferredCommunicationChannelRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldPreferredCommunicationChannel> findOne(Long id) {
        log.debug("Request to get FieldPreferredCommunicationChannel : {}", id);
        return fieldPreferredCommunicationChannelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldPreferredCommunicationChannel : {}", id);
        fieldPreferredCommunicationChannelRepository.deleteById(id);
    }
}
