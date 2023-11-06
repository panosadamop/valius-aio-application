package io.valius.app.web.rest;

import io.valius.app.domain.ConfidenceLevel;
import io.valius.app.repository.ConfidenceLevelRepository;
import io.valius.app.service.ConfidenceLevelService;
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
 * REST controller for managing {@link io.valius.app.domain.ConfidenceLevel}.
 */
@RestController
@RequestMapping("/api")
public class ConfidenceLevelResource {

    private final Logger log = LoggerFactory.getLogger(ConfidenceLevelResource.class);

    private static final String ENTITY_NAME = "confidenceLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfidenceLevelService confidenceLevelService;

    private final ConfidenceLevelRepository confidenceLevelRepository;

    public ConfidenceLevelResource(ConfidenceLevelService confidenceLevelService, ConfidenceLevelRepository confidenceLevelRepository) {
        this.confidenceLevelService = confidenceLevelService;
        this.confidenceLevelRepository = confidenceLevelRepository;
    }

    /**
     * {@code POST  /confidence-levels} : Create a new confidenceLevel.
     *
     * @param confidenceLevel the confidenceLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new confidenceLevel, or with status {@code 400 (Bad Request)} if the confidenceLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/confidence-levels")
    public ResponseEntity<ConfidenceLevel> createConfidenceLevel(@Valid @RequestBody ConfidenceLevel confidenceLevel)
        throws URISyntaxException {
        log.debug("REST request to save ConfidenceLevel : {}", confidenceLevel);
        if (confidenceLevel.getId() != null) {
            throw new BadRequestAlertException("A new confidenceLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfidenceLevel result = confidenceLevelService.save(confidenceLevel);
        return ResponseEntity
            .created(new URI("/api/confidence-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /confidence-levels/:id} : Updates an existing confidenceLevel.
     *
     * @param id the id of the confidenceLevel to save.
     * @param confidenceLevel the confidenceLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confidenceLevel,
     * or with status {@code 400 (Bad Request)} if the confidenceLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the confidenceLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/confidence-levels/{id}")
    public ResponseEntity<ConfidenceLevel> updateConfidenceLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConfidenceLevel confidenceLevel
    ) throws URISyntaxException {
        log.debug("REST request to update ConfidenceLevel : {}, {}", id, confidenceLevel);
        if (confidenceLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confidenceLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confidenceLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfidenceLevel result = confidenceLevelService.update(confidenceLevel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, confidenceLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /confidence-levels/:id} : Partial updates given fields of an existing confidenceLevel, field will ignore if it is null
     *
     * @param id the id of the confidenceLevel to save.
     * @param confidenceLevel the confidenceLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confidenceLevel,
     * or with status {@code 400 (Bad Request)} if the confidenceLevel is not valid,
     * or with status {@code 404 (Not Found)} if the confidenceLevel is not found,
     * or with status {@code 500 (Internal Server Error)} if the confidenceLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/confidence-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConfidenceLevel> partialUpdateConfidenceLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConfidenceLevel confidenceLevel
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConfidenceLevel partially : {}, {}", id, confidenceLevel);
        if (confidenceLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confidenceLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confidenceLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfidenceLevel> result = confidenceLevelService.partialUpdate(confidenceLevel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, confidenceLevel.getId().toString())
        );
    }

    /**
     * {@code GET  /confidence-levels} : get all the confidenceLevels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of confidenceLevels in body.
     */
    @GetMapping("/confidence-levels")
    public ResponseEntity<List<ConfidenceLevel>> getAllConfidenceLevels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ConfidenceLevels");
        Page<ConfidenceLevel> page = confidenceLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /confidence-levels/:id} : get the "id" confidenceLevel.
     *
     * @param id the id of the confidenceLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the confidenceLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/confidence-levels/{id}")
    public ResponseEntity<ConfidenceLevel> getConfidenceLevel(@PathVariable Long id) {
        log.debug("REST request to get ConfidenceLevel : {}", id);
        Optional<ConfidenceLevel> confidenceLevel = confidenceLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(confidenceLevel);
    }

    /**
     * {@code DELETE  /confidence-levels/:id} : delete the "id" confidenceLevel.
     *
     * @param id the id of the confidenceLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/confidence-levels/{id}")
    public ResponseEntity<Void> deleteConfidenceLevel(@PathVariable Long id) {
        log.debug("REST request to delete ConfidenceLevel : {}", id);
        confidenceLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
