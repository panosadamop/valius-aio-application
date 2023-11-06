package io.valius.app.web.rest;

import io.valius.app.domain.SegmentScoreMAF;
import io.valius.app.repository.SegmentScoreMAFRepository;
import io.valius.app.service.SegmentScoreMAFService;
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
 * REST controller for managing {@link io.valius.app.domain.SegmentScoreMAF}.
 */
@RestController
@RequestMapping("/api")
public class SegmentScoreMAFResource {

    private final Logger log = LoggerFactory.getLogger(SegmentScoreMAFResource.class);

    private static final String ENTITY_NAME = "segmentScoreMAF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentScoreMAFService segmentScoreMAFService;

    private final SegmentScoreMAFRepository segmentScoreMAFRepository;

    public SegmentScoreMAFResource(SegmentScoreMAFService segmentScoreMAFService, SegmentScoreMAFRepository segmentScoreMAFRepository) {
        this.segmentScoreMAFService = segmentScoreMAFService;
        this.segmentScoreMAFRepository = segmentScoreMAFRepository;
    }

    /**
     * {@code POST  /segment-score-mafs} : Create a new segmentScoreMAF.
     *
     * @param segmentScoreMAF the segmentScoreMAF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmentScoreMAF, or with status {@code 400 (Bad Request)} if the segmentScoreMAF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/segment-score-mafs")
    public ResponseEntity<SegmentScoreMAF> createSegmentScoreMAF(@Valid @RequestBody SegmentScoreMAF segmentScoreMAF)
        throws URISyntaxException {
        log.debug("REST request to save SegmentScoreMAF : {}", segmentScoreMAF);
        if (segmentScoreMAF.getId() != null) {
            throw new BadRequestAlertException("A new segmentScoreMAF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SegmentScoreMAF result = segmentScoreMAFService.save(segmentScoreMAF);
        return ResponseEntity
            .created(new URI("/api/segment-score-mafs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /segment-score-mafs/:id} : Updates an existing segmentScoreMAF.
     *
     * @param id the id of the segmentScoreMAF to save.
     * @param segmentScoreMAF the segmentScoreMAF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentScoreMAF,
     * or with status {@code 400 (Bad Request)} if the segmentScoreMAF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmentScoreMAF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/segment-score-mafs/{id}")
    public ResponseEntity<SegmentScoreMAF> updateSegmentScoreMAF(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SegmentScoreMAF segmentScoreMAF
    ) throws URISyntaxException {
        log.debug("REST request to update SegmentScoreMAF : {}, {}", id, segmentScoreMAF);
        if (segmentScoreMAF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentScoreMAF.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentScoreMAFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SegmentScoreMAF result = segmentScoreMAFService.update(segmentScoreMAF);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentScoreMAF.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /segment-score-mafs/:id} : Partial updates given fields of an existing segmentScoreMAF, field will ignore if it is null
     *
     * @param id the id of the segmentScoreMAF to save.
     * @param segmentScoreMAF the segmentScoreMAF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentScoreMAF,
     * or with status {@code 400 (Bad Request)} if the segmentScoreMAF is not valid,
     * or with status {@code 404 (Not Found)} if the segmentScoreMAF is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmentScoreMAF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/segment-score-mafs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SegmentScoreMAF> partialUpdateSegmentScoreMAF(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SegmentScoreMAF segmentScoreMAF
    ) throws URISyntaxException {
        log.debug("REST request to partial update SegmentScoreMAF partially : {}, {}", id, segmentScoreMAF);
        if (segmentScoreMAF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentScoreMAF.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentScoreMAFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SegmentScoreMAF> result = segmentScoreMAFService.partialUpdate(segmentScoreMAF);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentScoreMAF.getId().toString())
        );
    }

    /**
     * {@code GET  /segment-score-mafs} : get all the segmentScoreMAFS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segmentScoreMAFS in body.
     */
    @GetMapping("/segment-score-mafs")
    public ResponseEntity<List<SegmentScoreMAF>> getAllSegmentScoreMAFS(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SegmentScoreMAFS");
        Page<SegmentScoreMAF> page = segmentScoreMAFService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /segment-score-mafs/:id} : get the "id" segmentScoreMAF.
     *
     * @param id the id of the segmentScoreMAF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmentScoreMAF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/segment-score-mafs/{id}")
    public ResponseEntity<SegmentScoreMAF> getSegmentScoreMAF(@PathVariable Long id) {
        log.debug("REST request to get SegmentScoreMAF : {}", id);
        Optional<SegmentScoreMAF> segmentScoreMAF = segmentScoreMAFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(segmentScoreMAF);
    }

    /**
     * {@code DELETE  /segment-score-mafs/:id} : delete the "id" segmentScoreMAF.
     *
     * @param id the id of the segmentScoreMAF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/segment-score-mafs/{id}")
    public ResponseEntity<Void> deleteSegmentScoreMAF(@PathVariable Long id) {
        log.debug("REST request to delete SegmentScoreMAF : {}", id);
        segmentScoreMAFService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
