package io.valius.app.web.rest;

import io.valius.app.domain.FieldPreferredCommunicationChannel;
import io.valius.app.repository.FieldPreferredCommunicationChannelRepository;
import io.valius.app.service.FieldPreferredCommunicationChannelService;
import io.valius.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link io.valius.app.domain.FieldPreferredCommunicationChannel}.
 */
@RestController
@RequestMapping("/api")
public class FieldPreferredCommunicationChannelResource {

    private final Logger log = LoggerFactory.getLogger(FieldPreferredCommunicationChannelResource.class);

    private static final String ENTITY_NAME = "fieldPreferredCommunicationChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldPreferredCommunicationChannelService fieldPreferredCommunicationChannelService;

    private final FieldPreferredCommunicationChannelRepository fieldPreferredCommunicationChannelRepository;

    public FieldPreferredCommunicationChannelResource(
        FieldPreferredCommunicationChannelService fieldPreferredCommunicationChannelService,
        FieldPreferredCommunicationChannelRepository fieldPreferredCommunicationChannelRepository
    ) {
        this.fieldPreferredCommunicationChannelService = fieldPreferredCommunicationChannelService;
        this.fieldPreferredCommunicationChannelRepository = fieldPreferredCommunicationChannelRepository;
    }

    /**
     * {@code POST  /field-preferred-communication-channels} : Create a new fieldPreferredCommunicationChannel.
     *
     * @param fieldPreferredCommunicationChannel the fieldPreferredCommunicationChannel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldPreferredCommunicationChannel, or with status {@code 400 (Bad Request)} if the fieldPreferredCommunicationChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-preferred-communication-channels")
    public ResponseEntity<FieldPreferredCommunicationChannel> createFieldPreferredCommunicationChannel(
        @RequestBody FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel
    ) throws URISyntaxException {
        log.debug("REST request to save FieldPreferredCommunicationChannel : {}", fieldPreferredCommunicationChannel);
        if (fieldPreferredCommunicationChannel.getId() != null) {
            throw new BadRequestAlertException(
                "A new fieldPreferredCommunicationChannel cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        FieldPreferredCommunicationChannel result = fieldPreferredCommunicationChannelService.save(fieldPreferredCommunicationChannel);
        return ResponseEntity
            .created(new URI("/api/field-preferred-communication-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-preferred-communication-channels/:id} : Updates an existing fieldPreferredCommunicationChannel.
     *
     * @param id the id of the fieldPreferredCommunicationChannel to save.
     * @param fieldPreferredCommunicationChannel the fieldPreferredCommunicationChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldPreferredCommunicationChannel,
     * or with status {@code 400 (Bad Request)} if the fieldPreferredCommunicationChannel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldPreferredCommunicationChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-preferred-communication-channels/{id}")
    public ResponseEntity<FieldPreferredCommunicationChannel> updateFieldPreferredCommunicationChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel
    ) throws URISyntaxException {
        log.debug("REST request to update FieldPreferredCommunicationChannel : {}, {}", id, fieldPreferredCommunicationChannel);
        if (fieldPreferredCommunicationChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldPreferredCommunicationChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldPreferredCommunicationChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldPreferredCommunicationChannel result = fieldPreferredCommunicationChannelService.update(fieldPreferredCommunicationChannel);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    fieldPreferredCommunicationChannel.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /field-preferred-communication-channels/:id} : Partial updates given fields of an existing fieldPreferredCommunicationChannel, field will ignore if it is null
     *
     * @param id the id of the fieldPreferredCommunicationChannel to save.
     * @param fieldPreferredCommunicationChannel the fieldPreferredCommunicationChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldPreferredCommunicationChannel,
     * or with status {@code 400 (Bad Request)} if the fieldPreferredCommunicationChannel is not valid,
     * or with status {@code 404 (Not Found)} if the fieldPreferredCommunicationChannel is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldPreferredCommunicationChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-preferred-communication-channels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldPreferredCommunicationChannel> partialUpdateFieldPreferredCommunicationChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update FieldPreferredCommunicationChannel partially : {}, {}",
            id,
            fieldPreferredCommunicationChannel
        );
        if (fieldPreferredCommunicationChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldPreferredCommunicationChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldPreferredCommunicationChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldPreferredCommunicationChannel> result = fieldPreferredCommunicationChannelService.partialUpdate(
            fieldPreferredCommunicationChannel
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldPreferredCommunicationChannel.getId().toString())
        );
    }

    /**
     * {@code GET  /field-preferred-communication-channels} : get all the fieldPreferredCommunicationChannels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldPreferredCommunicationChannels in body.
     */
    @GetMapping("/field-preferred-communication-channels")
    public ResponseEntity<List<FieldPreferredCommunicationChannel>> getAllFieldPreferredCommunicationChannels(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FieldPreferredCommunicationChannels");
        Page<FieldPreferredCommunicationChannel> page = fieldPreferredCommunicationChannelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-preferred-communication-channels/:id} : get the "id" fieldPreferredCommunicationChannel.
     *
     * @param id the id of the fieldPreferredCommunicationChannel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldPreferredCommunicationChannel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-preferred-communication-channels/{id}")
    public ResponseEntity<FieldPreferredCommunicationChannel> getFieldPreferredCommunicationChannel(@PathVariable Long id) {
        log.debug("REST request to get FieldPreferredCommunicationChannel : {}", id);
        Optional<FieldPreferredCommunicationChannel> fieldPreferredCommunicationChannel = fieldPreferredCommunicationChannelService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(fieldPreferredCommunicationChannel);
    }

    /**
     * {@code DELETE  /field-preferred-communication-channels/:id} : delete the "id" fieldPreferredCommunicationChannel.
     *
     * @param id the id of the fieldPreferredCommunicationChannel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-preferred-communication-channels/{id}")
    public ResponseEntity<Void> deleteFieldPreferredCommunicationChannel(@PathVariable Long id) {
        log.debug("REST request to delete FieldPreferredCommunicationChannel : {}", id);
        fieldPreferredCommunicationChannelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
