package io.valius.app.web.rest;

import io.valius.app.domain.PreferredCommunicationChannel;
import io.valius.app.repository.PreferredCommunicationChannelRepository;
import io.valius.app.service.PreferredCommunicationChannelService;
import io.valius.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.valius.app.domain.PreferredCommunicationChannel}.
 */
@RestController
@RequestMapping("/api")
public class PreferredCommunicationChannelResource {

    private final Logger log = LoggerFactory.getLogger(PreferredCommunicationChannelResource.class);

    private static final String ENTITY_NAME = "preferredCommunicationChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferredCommunicationChannelService preferredCommunicationChannelService;

    private final PreferredCommunicationChannelRepository preferredCommunicationChannelRepository;

    public PreferredCommunicationChannelResource(
        PreferredCommunicationChannelService preferredCommunicationChannelService,
        PreferredCommunicationChannelRepository preferredCommunicationChannelRepository
    ) {
        this.preferredCommunicationChannelService = preferredCommunicationChannelService;
        this.preferredCommunicationChannelRepository = preferredCommunicationChannelRepository;
    }

    /**
     * {@code POST  /preferred-communication-channels} : Create a new preferredCommunicationChannel.
     *
     * @param preferredCommunicationChannel the preferredCommunicationChannel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferredCommunicationChannel, or with status {@code 400 (Bad Request)} if the preferredCommunicationChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferred-communication-channels")
    public ResponseEntity<PreferredCommunicationChannel> createPreferredCommunicationChannel(
        @Valid @RequestBody PreferredCommunicationChannel preferredCommunicationChannel
    ) throws URISyntaxException {
        log.debug("REST request to save PreferredCommunicationChannel : {}", preferredCommunicationChannel);
        if (preferredCommunicationChannel.getId() != null) {
            throw new BadRequestAlertException("A new preferredCommunicationChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferredCommunicationChannel result = preferredCommunicationChannelService.save(preferredCommunicationChannel);
        return ResponseEntity
            .created(new URI("/api/preferred-communication-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /preferred-communication-channels/:id} : Updates an existing preferredCommunicationChannel.
     *
     * @param id the id of the preferredCommunicationChannel to save.
     * @param preferredCommunicationChannel the preferredCommunicationChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredCommunicationChannel,
     * or with status {@code 400 (Bad Request)} if the preferredCommunicationChannel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferredCommunicationChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferred-communication-channels/{id}")
    public ResponseEntity<PreferredCommunicationChannel> updatePreferredCommunicationChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PreferredCommunicationChannel preferredCommunicationChannel
    ) throws URISyntaxException {
        log.debug("REST request to update PreferredCommunicationChannel : {}, {}", id, preferredCommunicationChannel);
        if (preferredCommunicationChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredCommunicationChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredCommunicationChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferredCommunicationChannel result = preferredCommunicationChannelService.update(preferredCommunicationChannel);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredCommunicationChannel.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /preferred-communication-channels/:id} : Partial updates given fields of an existing preferredCommunicationChannel, field will ignore if it is null
     *
     * @param id the id of the preferredCommunicationChannel to save.
     * @param preferredCommunicationChannel the preferredCommunicationChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredCommunicationChannel,
     * or with status {@code 400 (Bad Request)} if the preferredCommunicationChannel is not valid,
     * or with status {@code 404 (Not Found)} if the preferredCommunicationChannel is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferredCommunicationChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferred-communication-channels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreferredCommunicationChannel> partialUpdatePreferredCommunicationChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PreferredCommunicationChannel preferredCommunicationChannel
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreferredCommunicationChannel partially : {}, {}", id, preferredCommunicationChannel);
        if (preferredCommunicationChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredCommunicationChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredCommunicationChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferredCommunicationChannel> result = preferredCommunicationChannelService.partialUpdate(preferredCommunicationChannel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredCommunicationChannel.getId().toString())
        );
    }

    /**
     * {@code GET  /preferred-communication-channels} : get all the preferredCommunicationChannels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferredCommunicationChannels in body.
     */
    @GetMapping("/preferred-communication-channels")
    public ResponseEntity<List<PreferredCommunicationChannel>> getAllPreferredCommunicationChannels(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PreferredCommunicationChannels");
        Page<PreferredCommunicationChannel> page = preferredCommunicationChannelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /preferred-communication-channels/:id} : get the "id" preferredCommunicationChannel.
     *
     * @param id the id of the preferredCommunicationChannel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferredCommunicationChannel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferred-communication-channels/{id}")
    public ResponseEntity<PreferredCommunicationChannel> getPreferredCommunicationChannel(@PathVariable Long id) {
        log.debug("REST request to get PreferredCommunicationChannel : {}", id);
        Optional<PreferredCommunicationChannel> preferredCommunicationChannel = preferredCommunicationChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferredCommunicationChannel);
    }

    /**
     * {@code DELETE  /preferred-communication-channels/:id} : delete the "id" preferredCommunicationChannel.
     *
     * @param id the id of the preferredCommunicationChannel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferred-communication-channels/{id}")
    public ResponseEntity<Void> deletePreferredCommunicationChannel(@PathVariable Long id) {
        log.debug("REST request to delete PreferredCommunicationChannel : {}", id);
        preferredCommunicationChannelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
