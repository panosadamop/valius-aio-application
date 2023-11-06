package io.valius.app.service.impl;

import io.valius.app.domain.PreferredCommunicationChannel;
import io.valius.app.repository.PreferredCommunicationChannelRepository;
import io.valius.app.service.PreferredCommunicationChannelService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PreferredCommunicationChannel}.
 */
@Service
@Transactional
public class PreferredCommunicationChannelServiceImpl implements PreferredCommunicationChannelService {

    private final Logger log = LoggerFactory.getLogger(PreferredCommunicationChannelServiceImpl.class);

    private final PreferredCommunicationChannelRepository preferredCommunicationChannelRepository;

    public PreferredCommunicationChannelServiceImpl(PreferredCommunicationChannelRepository preferredCommunicationChannelRepository) {
        this.preferredCommunicationChannelRepository = preferredCommunicationChannelRepository;
    }

    @Override
    public PreferredCommunicationChannel save(PreferredCommunicationChannel preferredCommunicationChannel) {
        log.debug("Request to save PreferredCommunicationChannel : {}", preferredCommunicationChannel);
        return preferredCommunicationChannelRepository.save(preferredCommunicationChannel);
    }

    @Override
    public PreferredCommunicationChannel update(PreferredCommunicationChannel preferredCommunicationChannel) {
        log.debug("Request to update PreferredCommunicationChannel : {}", preferredCommunicationChannel);
        return preferredCommunicationChannelRepository.save(preferredCommunicationChannel);
    }

    @Override
    public Optional<PreferredCommunicationChannel> partialUpdate(PreferredCommunicationChannel preferredCommunicationChannel) {
        log.debug("Request to partially update PreferredCommunicationChannel : {}", preferredCommunicationChannel);

        return preferredCommunicationChannelRepository
            .findById(preferredCommunicationChannel.getId())
            .map(existingPreferredCommunicationChannel -> {
                if (preferredCommunicationChannel.getValue() != null) {
                    existingPreferredCommunicationChannel.setValue(preferredCommunicationChannel.getValue());
                }
                if (preferredCommunicationChannel.getDescription() != null) {
                    existingPreferredCommunicationChannel.setDescription(preferredCommunicationChannel.getDescription());
                }
                if (preferredCommunicationChannel.getLanguage() != null) {
                    existingPreferredCommunicationChannel.setLanguage(preferredCommunicationChannel.getLanguage());
                }

                return existingPreferredCommunicationChannel;
            })
            .map(preferredCommunicationChannelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreferredCommunicationChannel> findAll(Pageable pageable) {
        log.debug("Request to get all PreferredCommunicationChannels");
        return preferredCommunicationChannelRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PreferredCommunicationChannel> findOne(Long id) {
        log.debug("Request to get PreferredCommunicationChannel : {}", id);
        return preferredCommunicationChannelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreferredCommunicationChannel : {}", id);
        preferredCommunicationChannelRepository.deleteById(id);
    }
}
