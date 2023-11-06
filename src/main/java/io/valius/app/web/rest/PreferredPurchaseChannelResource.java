package io.valius.app.web.rest;

import io.valius.app.domain.PreferredPurchaseChannel;
import io.valius.app.repository.PreferredPurchaseChannelRepository;
import io.valius.app.service.PreferredPurchaseChannelService;
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
 * REST controller for managing {@link io.valius.app.domain.PreferredPurchaseChannel}.
 */
@RestController
@RequestMapping("/api")
public class PreferredPurchaseChannelResource {

    private final Logger log = LoggerFactory.getLogger(PreferredPurchaseChannelResource.class);

    private static final String ENTITY_NAME = "preferredPurchaseChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferredPurchaseChannelService preferredPurchaseChannelService;

    private final PreferredPurchaseChannelRepository preferredPurchaseChannelRepository;

    public PreferredPurchaseChannelResource(
        PreferredPurchaseChannelService preferredPurchaseChannelService,
        PreferredPurchaseChannelRepository preferredPurchaseChannelRepository
    ) {
        this.preferredPurchaseChannelService = preferredPurchaseChannelService;
        this.preferredPurchaseChannelRepository = preferredPurchaseChannelRepository;
    }

    /**
     * {@code POST  /preferred-purchase-channels} : Create a new preferredPurchaseChannel.
     *
     * @param preferredPurchaseChannel the preferredPurchaseChannel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferredPurchaseChannel, or with status {@code 400 (Bad Request)} if the preferredPurchaseChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferred-purchase-channels")
    public ResponseEntity<PreferredPurchaseChannel> createPreferredPurchaseChannel(
        @Valid @RequestBody PreferredPurchaseChannel preferredPurchaseChannel
    ) throws URISyntaxException {
        log.debug("REST request to save PreferredPurchaseChannel : {}", preferredPurchaseChannel);
        if (preferredPurchaseChannel.getId() != null) {
            throw new BadRequestAlertException("A new preferredPurchaseChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferredPurchaseChannel result = preferredPurchaseChannelService.save(preferredPurchaseChannel);
        return ResponseEntity
            .created(new URI("/api/preferred-purchase-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /preferred-purchase-channels/:id} : Updates an existing preferredPurchaseChannel.
     *
     * @param id the id of the preferredPurchaseChannel to save.
     * @param preferredPurchaseChannel the preferredPurchaseChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredPurchaseChannel,
     * or with status {@code 400 (Bad Request)} if the preferredPurchaseChannel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferredPurchaseChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferred-purchase-channels/{id}")
    public ResponseEntity<PreferredPurchaseChannel> updatePreferredPurchaseChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PreferredPurchaseChannel preferredPurchaseChannel
    ) throws URISyntaxException {
        log.debug("REST request to update PreferredPurchaseChannel : {}, {}", id, preferredPurchaseChannel);
        if (preferredPurchaseChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredPurchaseChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredPurchaseChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferredPurchaseChannel result = preferredPurchaseChannelService.update(preferredPurchaseChannel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredPurchaseChannel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferred-purchase-channels/:id} : Partial updates given fields of an existing preferredPurchaseChannel, field will ignore if it is null
     *
     * @param id the id of the preferredPurchaseChannel to save.
     * @param preferredPurchaseChannel the preferredPurchaseChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredPurchaseChannel,
     * or with status {@code 400 (Bad Request)} if the preferredPurchaseChannel is not valid,
     * or with status {@code 404 (Not Found)} if the preferredPurchaseChannel is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferredPurchaseChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferred-purchase-channels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreferredPurchaseChannel> partialUpdatePreferredPurchaseChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PreferredPurchaseChannel preferredPurchaseChannel
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreferredPurchaseChannel partially : {}, {}", id, preferredPurchaseChannel);
        if (preferredPurchaseChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredPurchaseChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredPurchaseChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferredPurchaseChannel> result = preferredPurchaseChannelService.partialUpdate(preferredPurchaseChannel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredPurchaseChannel.getId().toString())
        );
    }

    /**
     * {@code GET  /preferred-purchase-channels} : get all the preferredPurchaseChannels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferredPurchaseChannels in body.
     */
    @GetMapping("/preferred-purchase-channels")
    public ResponseEntity<List<PreferredPurchaseChannel>> getAllPreferredPurchaseChannels(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PreferredPurchaseChannels");
        Page<PreferredPurchaseChannel> page = preferredPurchaseChannelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /preferred-purchase-channels/:id} : get the "id" preferredPurchaseChannel.
     *
     * @param id the id of the preferredPurchaseChannel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferredPurchaseChannel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferred-purchase-channels/{id}")
    public ResponseEntity<PreferredPurchaseChannel> getPreferredPurchaseChannel(@PathVariable Long id) {
        log.debug("REST request to get PreferredPurchaseChannel : {}", id);
        Optional<PreferredPurchaseChannel> preferredPurchaseChannel = preferredPurchaseChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferredPurchaseChannel);
    }

    /**
     * {@code DELETE  /preferred-purchase-channels/:id} : delete the "id" preferredPurchaseChannel.
     *
     * @param id the id of the preferredPurchaseChannel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferred-purchase-channels/{id}")
    public ResponseEntity<Void> deletePreferredPurchaseChannel(@PathVariable Long id) {
        log.debug("REST request to delete PreferredPurchaseChannel : {}", id);
        preferredPurchaseChannelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
