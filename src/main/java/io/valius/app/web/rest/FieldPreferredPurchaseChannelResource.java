package io.valius.app.web.rest;

import io.valius.app.domain.FieldPreferredPurchaseChannel;
import io.valius.app.repository.FieldPreferredPurchaseChannelRepository;
import io.valius.app.service.FieldPreferredPurchaseChannelService;
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
 * REST controller for managing {@link io.valius.app.domain.FieldPreferredPurchaseChannel}.
 */
@RestController
@RequestMapping("/api")
public class FieldPreferredPurchaseChannelResource {

    private final Logger log = LoggerFactory.getLogger(FieldPreferredPurchaseChannelResource.class);

    private static final String ENTITY_NAME = "fieldPreferredPurchaseChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldPreferredPurchaseChannelService fieldPreferredPurchaseChannelService;

    private final FieldPreferredPurchaseChannelRepository fieldPreferredPurchaseChannelRepository;

    public FieldPreferredPurchaseChannelResource(
        FieldPreferredPurchaseChannelService fieldPreferredPurchaseChannelService,
        FieldPreferredPurchaseChannelRepository fieldPreferredPurchaseChannelRepository
    ) {
        this.fieldPreferredPurchaseChannelService = fieldPreferredPurchaseChannelService;
        this.fieldPreferredPurchaseChannelRepository = fieldPreferredPurchaseChannelRepository;
    }

    /**
     * {@code POST  /field-preferred-purchase-channels} : Create a new fieldPreferredPurchaseChannel.
     *
     * @param fieldPreferredPurchaseChannel the fieldPreferredPurchaseChannel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldPreferredPurchaseChannel, or with status {@code 400 (Bad Request)} if the fieldPreferredPurchaseChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-preferred-purchase-channels")
    public ResponseEntity<FieldPreferredPurchaseChannel> createFieldPreferredPurchaseChannel(
        @RequestBody FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel
    ) throws URISyntaxException {
        log.debug("REST request to save FieldPreferredPurchaseChannel : {}", fieldPreferredPurchaseChannel);
        if (fieldPreferredPurchaseChannel.getId() != null) {
            throw new BadRequestAlertException("A new fieldPreferredPurchaseChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldPreferredPurchaseChannel result = fieldPreferredPurchaseChannelService.save(fieldPreferredPurchaseChannel);
        return ResponseEntity
            .created(new URI("/api/field-preferred-purchase-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-preferred-purchase-channels/:id} : Updates an existing fieldPreferredPurchaseChannel.
     *
     * @param id the id of the fieldPreferredPurchaseChannel to save.
     * @param fieldPreferredPurchaseChannel the fieldPreferredPurchaseChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldPreferredPurchaseChannel,
     * or with status {@code 400 (Bad Request)} if the fieldPreferredPurchaseChannel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldPreferredPurchaseChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-preferred-purchase-channels/{id}")
    public ResponseEntity<FieldPreferredPurchaseChannel> updateFieldPreferredPurchaseChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel
    ) throws URISyntaxException {
        log.debug("REST request to update FieldPreferredPurchaseChannel : {}, {}", id, fieldPreferredPurchaseChannel);
        if (fieldPreferredPurchaseChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldPreferredPurchaseChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldPreferredPurchaseChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldPreferredPurchaseChannel result = fieldPreferredPurchaseChannelService.update(fieldPreferredPurchaseChannel);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldPreferredPurchaseChannel.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /field-preferred-purchase-channels/:id} : Partial updates given fields of an existing fieldPreferredPurchaseChannel, field will ignore if it is null
     *
     * @param id the id of the fieldPreferredPurchaseChannel to save.
     * @param fieldPreferredPurchaseChannel the fieldPreferredPurchaseChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldPreferredPurchaseChannel,
     * or with status {@code 400 (Bad Request)} if the fieldPreferredPurchaseChannel is not valid,
     * or with status {@code 404 (Not Found)} if the fieldPreferredPurchaseChannel is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldPreferredPurchaseChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-preferred-purchase-channels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldPreferredPurchaseChannel> partialUpdateFieldPreferredPurchaseChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldPreferredPurchaseChannel partially : {}, {}", id, fieldPreferredPurchaseChannel);
        if (fieldPreferredPurchaseChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldPreferredPurchaseChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldPreferredPurchaseChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldPreferredPurchaseChannel> result = fieldPreferredPurchaseChannelService.partialUpdate(fieldPreferredPurchaseChannel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldPreferredPurchaseChannel.getId().toString())
        );
    }

    /**
     * {@code GET  /field-preferred-purchase-channels} : get all the fieldPreferredPurchaseChannels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldPreferredPurchaseChannels in body.
     */
    @GetMapping("/field-preferred-purchase-channels")
    public ResponseEntity<List<FieldPreferredPurchaseChannel>> getAllFieldPreferredPurchaseChannels(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FieldPreferredPurchaseChannels");
        Page<FieldPreferredPurchaseChannel> page = fieldPreferredPurchaseChannelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-preferred-purchase-channels/:id} : get the "id" fieldPreferredPurchaseChannel.
     *
     * @param id the id of the fieldPreferredPurchaseChannel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldPreferredPurchaseChannel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-preferred-purchase-channels/{id}")
    public ResponseEntity<FieldPreferredPurchaseChannel> getFieldPreferredPurchaseChannel(@PathVariable Long id) {
        log.debug("REST request to get FieldPreferredPurchaseChannel : {}", id);
        Optional<FieldPreferredPurchaseChannel> fieldPreferredPurchaseChannel = fieldPreferredPurchaseChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldPreferredPurchaseChannel);
    }

    /**
     * {@code DELETE  /field-preferred-purchase-channels/:id} : delete the "id" fieldPreferredPurchaseChannel.
     *
     * @param id the id of the fieldPreferredPurchaseChannel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-preferred-purchase-channels/{id}")
    public ResponseEntity<Void> deleteFieldPreferredPurchaseChannel(@PathVariable Long id) {
        log.debug("REST request to delete FieldPreferredPurchaseChannel : {}", id);
        fieldPreferredPurchaseChannelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
