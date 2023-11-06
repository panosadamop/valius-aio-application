package io.valius.app.service.impl;

import io.valius.app.domain.PreferredPurchaseChannel;
import io.valius.app.repository.PreferredPurchaseChannelRepository;
import io.valius.app.service.PreferredPurchaseChannelService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PreferredPurchaseChannel}.
 */
@Service
@Transactional
public class PreferredPurchaseChannelServiceImpl implements PreferredPurchaseChannelService {

    private final Logger log = LoggerFactory.getLogger(PreferredPurchaseChannelServiceImpl.class);

    private final PreferredPurchaseChannelRepository preferredPurchaseChannelRepository;

    public PreferredPurchaseChannelServiceImpl(PreferredPurchaseChannelRepository preferredPurchaseChannelRepository) {
        this.preferredPurchaseChannelRepository = preferredPurchaseChannelRepository;
    }

    @Override
    public PreferredPurchaseChannel save(PreferredPurchaseChannel preferredPurchaseChannel) {
        log.debug("Request to save PreferredPurchaseChannel : {}", preferredPurchaseChannel);
        return preferredPurchaseChannelRepository.save(preferredPurchaseChannel);
    }

    @Override
    public PreferredPurchaseChannel update(PreferredPurchaseChannel preferredPurchaseChannel) {
        log.debug("Request to update PreferredPurchaseChannel : {}", preferredPurchaseChannel);
        return preferredPurchaseChannelRepository.save(preferredPurchaseChannel);
    }

    @Override
    public Optional<PreferredPurchaseChannel> partialUpdate(PreferredPurchaseChannel preferredPurchaseChannel) {
        log.debug("Request to partially update PreferredPurchaseChannel : {}", preferredPurchaseChannel);

        return preferredPurchaseChannelRepository
            .findById(preferredPurchaseChannel.getId())
            .map(existingPreferredPurchaseChannel -> {
                if (preferredPurchaseChannel.getValue() != null) {
                    existingPreferredPurchaseChannel.setValue(preferredPurchaseChannel.getValue());
                }
                if (preferredPurchaseChannel.getDescription() != null) {
                    existingPreferredPurchaseChannel.setDescription(preferredPurchaseChannel.getDescription());
                }
                if (preferredPurchaseChannel.getLanguage() != null) {
                    existingPreferredPurchaseChannel.setLanguage(preferredPurchaseChannel.getLanguage());
                }

                return existingPreferredPurchaseChannel;
            })
            .map(preferredPurchaseChannelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreferredPurchaseChannel> findAll(Pageable pageable) {
        log.debug("Request to get all PreferredPurchaseChannels");
        return preferredPurchaseChannelRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PreferredPurchaseChannel> findOne(Long id) {
        log.debug("Request to get PreferredPurchaseChannel : {}", id);
        return preferredPurchaseChannelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreferredPurchaseChannel : {}", id);
        preferredPurchaseChannelRepository.deleteById(id);
    }
}
