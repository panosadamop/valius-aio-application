package io.valius.app.web.rest;

import io.valius.app.domain.SegmentUniqueCharacteristic;
import io.valius.app.repository.SegmentUniqueCharacteristicRepository;
import io.valius.app.service.SegmentUniqueCharacteristicService;
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
 * REST controller for managing {@link io.valius.app.domain.SegmentUniqueCharacteristic}.
 */
@RestController
@RequestMapping("/api")
public class SegmentUniqueCharacteristicResource {

    private final Logger log = LoggerFactory.getLogger(SegmentUniqueCharacteristicResource.class);

    private static final String ENTITY_NAME = "segmentUniqueCharacteristic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentUniqueCharacteristicService segmentUniqueCharacteristicService;

    private final SegmentUniqueCharacteristicRepository segmentUniqueCharacteristicRepository;

    public SegmentUniqueCharacteristicResource(
        SegmentUniqueCharacteristicService segmentUniqueCharacteristicService,
        SegmentUniqueCharacteristicRepository segmentUniqueCharacteristicRepository
    ) {
        this.segmentUniqueCharacteristicService = segmentUniqueCharacteristicService;
        this.segmentUniqueCharacteristicRepository = segmentUniqueCharacteristicRepository;
    }

    /**
     * {@code POST  /segment-unique-characteristics} : Create a new segmentUniqueCharacteristic.
     *
     * @param segmentUniqueCharacteristic the segmentUniqueCharacteristic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmentUniqueCharacteristic, or with status {@code 400 (Bad Request)} if the segmentUniqueCharacteristic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/segment-unique-characteristics")
    public ResponseEntity<SegmentUniqueCharacteristic> createSegmentUniqueCharacteristic(
        @Valid @RequestBody SegmentUniqueCharacteristic segmentUniqueCharacteristic
    ) throws URISyntaxException {
        log.debug("REST request to save SegmentUniqueCharacteristic : {}", segmentUniqueCharacteristic);
        if (segmentUniqueCharacteristic.getId() != null) {
            throw new BadRequestAlertException("A new segmentUniqueCharacteristic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SegmentUniqueCharacteristic result = segmentUniqueCharacteristicService.save(segmentUniqueCharacteristic);
        return ResponseEntity
            .created(new URI("/api/segment-unique-characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /segment-unique-characteristics/:id} : Updates an existing segmentUniqueCharacteristic.
     *
     * @param id the id of the segmentUniqueCharacteristic to save.
     * @param segmentUniqueCharacteristic the segmentUniqueCharacteristic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentUniqueCharacteristic,
     * or with status {@code 400 (Bad Request)} if the segmentUniqueCharacteristic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmentUniqueCharacteristic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/segment-unique-characteristics/{id}")
    public ResponseEntity<SegmentUniqueCharacteristic> updateSegmentUniqueCharacteristic(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SegmentUniqueCharacteristic segmentUniqueCharacteristic
    ) throws URISyntaxException {
        log.debug("REST request to update SegmentUniqueCharacteristic : {}, {}", id, segmentUniqueCharacteristic);
        if (segmentUniqueCharacteristic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentUniqueCharacteristic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentUniqueCharacteristicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SegmentUniqueCharacteristic result = segmentUniqueCharacteristicService.update(segmentUniqueCharacteristic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentUniqueCharacteristic.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /segment-unique-characteristics/:id} : Partial updates given fields of an existing segmentUniqueCharacteristic, field will ignore if it is null
     *
     * @param id the id of the segmentUniqueCharacteristic to save.
     * @param segmentUniqueCharacteristic the segmentUniqueCharacteristic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentUniqueCharacteristic,
     * or with status {@code 400 (Bad Request)} if the segmentUniqueCharacteristic is not valid,
     * or with status {@code 404 (Not Found)} if the segmentUniqueCharacteristic is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmentUniqueCharacteristic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/segment-unique-characteristics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SegmentUniqueCharacteristic> partialUpdateSegmentUniqueCharacteristic(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SegmentUniqueCharacteristic segmentUniqueCharacteristic
    ) throws URISyntaxException {
        log.debug("REST request to partial update SegmentUniqueCharacteristic partially : {}, {}", id, segmentUniqueCharacteristic);
        if (segmentUniqueCharacteristic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentUniqueCharacteristic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentUniqueCharacteristicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SegmentUniqueCharacteristic> result = segmentUniqueCharacteristicService.partialUpdate(segmentUniqueCharacteristic);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentUniqueCharacteristic.getId().toString())
        );
    }

    /**
     * {@code GET  /segment-unique-characteristics} : get all the segmentUniqueCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segmentUniqueCharacteristics in body.
     */
    @GetMapping("/segment-unique-characteristics")
    public ResponseEntity<List<SegmentUniqueCharacteristic>> getAllSegmentUniqueCharacteristics(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SegmentUniqueCharacteristics");
        Page<SegmentUniqueCharacteristic> page = segmentUniqueCharacteristicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /segment-unique-characteristics/:id} : get the "id" segmentUniqueCharacteristic.
     *
     * @param id the id of the segmentUniqueCharacteristic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmentUniqueCharacteristic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/segment-unique-characteristics/{id}")
    public ResponseEntity<SegmentUniqueCharacteristic> getSegmentUniqueCharacteristic(@PathVariable Long id) {
        log.debug("REST request to get SegmentUniqueCharacteristic : {}", id);
        Optional<SegmentUniqueCharacteristic> segmentUniqueCharacteristic = segmentUniqueCharacteristicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(segmentUniqueCharacteristic);
    }

    /**
     * {@code DELETE  /segment-unique-characteristics/:id} : delete the "id" segmentUniqueCharacteristic.
     *
     * @param id the id of the segmentUniqueCharacteristic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/segment-unique-characteristics/{id}")
    public ResponseEntity<Void> deleteSegmentUniqueCharacteristic(@PathVariable Long id) {
        log.debug("REST request to delete SegmentUniqueCharacteristic : {}", id);
        segmentUniqueCharacteristicService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
