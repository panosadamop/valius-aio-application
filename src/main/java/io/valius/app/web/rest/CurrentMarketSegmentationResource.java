package io.valius.app.web.rest;

import io.valius.app.domain.CurrentMarketSegmentation;
import io.valius.app.repository.CurrentMarketSegmentationRepository;
import io.valius.app.service.CurrentMarketSegmentationService;
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
 * REST controller for managing {@link io.valius.app.domain.CurrentMarketSegmentation}.
 */
@RestController
@RequestMapping("/api")
public class CurrentMarketSegmentationResource {

    private final Logger log = LoggerFactory.getLogger(CurrentMarketSegmentationResource.class);

    private static final String ENTITY_NAME = "currentMarketSegmentation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrentMarketSegmentationService currentMarketSegmentationService;

    private final CurrentMarketSegmentationRepository currentMarketSegmentationRepository;

    public CurrentMarketSegmentationResource(
        CurrentMarketSegmentationService currentMarketSegmentationService,
        CurrentMarketSegmentationRepository currentMarketSegmentationRepository
    ) {
        this.currentMarketSegmentationService = currentMarketSegmentationService;
        this.currentMarketSegmentationRepository = currentMarketSegmentationRepository;
    }

    /**
     * {@code POST  /current-market-segmentations} : Create a new currentMarketSegmentation.
     *
     * @param currentMarketSegmentation the currentMarketSegmentation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currentMarketSegmentation, or with status {@code 400 (Bad Request)} if the currentMarketSegmentation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/current-market-segmentations")
    public ResponseEntity<CurrentMarketSegmentation> createCurrentMarketSegmentation(
        @Valid @RequestBody CurrentMarketSegmentation currentMarketSegmentation
    ) throws URISyntaxException {
        log.debug("REST request to save CurrentMarketSegmentation : {}", currentMarketSegmentation);
        if (currentMarketSegmentation.getId() != null) {
            throw new BadRequestAlertException("A new currentMarketSegmentation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrentMarketSegmentation result = currentMarketSegmentationService.save(currentMarketSegmentation);
        return ResponseEntity
            .created(new URI("/api/current-market-segmentations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /current-market-segmentations/:id} : Updates an existing currentMarketSegmentation.
     *
     * @param id the id of the currentMarketSegmentation to save.
     * @param currentMarketSegmentation the currentMarketSegmentation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentMarketSegmentation,
     * or with status {@code 400 (Bad Request)} if the currentMarketSegmentation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currentMarketSegmentation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/current-market-segmentations/{id}")
    public ResponseEntity<CurrentMarketSegmentation> updateCurrentMarketSegmentation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CurrentMarketSegmentation currentMarketSegmentation
    ) throws URISyntaxException {
        log.debug("REST request to update CurrentMarketSegmentation : {}, {}", id, currentMarketSegmentation);
        if (currentMarketSegmentation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentMarketSegmentation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentMarketSegmentationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CurrentMarketSegmentation result = currentMarketSegmentationService.update(currentMarketSegmentation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentMarketSegmentation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /current-market-segmentations/:id} : Partial updates given fields of an existing currentMarketSegmentation, field will ignore if it is null
     *
     * @param id the id of the currentMarketSegmentation to save.
     * @param currentMarketSegmentation the currentMarketSegmentation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentMarketSegmentation,
     * or with status {@code 400 (Bad Request)} if the currentMarketSegmentation is not valid,
     * or with status {@code 404 (Not Found)} if the currentMarketSegmentation is not found,
     * or with status {@code 500 (Internal Server Error)} if the currentMarketSegmentation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/current-market-segmentations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CurrentMarketSegmentation> partialUpdateCurrentMarketSegmentation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CurrentMarketSegmentation currentMarketSegmentation
    ) throws URISyntaxException {
        log.debug("REST request to partial update CurrentMarketSegmentation partially : {}, {}", id, currentMarketSegmentation);
        if (currentMarketSegmentation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentMarketSegmentation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentMarketSegmentationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CurrentMarketSegmentation> result = currentMarketSegmentationService.partialUpdate(currentMarketSegmentation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentMarketSegmentation.getId().toString())
        );
    }

    /**
     * {@code GET  /current-market-segmentations} : get all the currentMarketSegmentations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentMarketSegmentations in body.
     */
    @GetMapping("/current-market-segmentations")
    public ResponseEntity<List<CurrentMarketSegmentation>> getAllCurrentMarketSegmentations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CurrentMarketSegmentations");
        Page<CurrentMarketSegmentation> page = currentMarketSegmentationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /current-market-segmentations/:id} : get the "id" currentMarketSegmentation.
     *
     * @param id the id of the currentMarketSegmentation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currentMarketSegmentation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/current-market-segmentations/{id}")
    public ResponseEntity<CurrentMarketSegmentation> getCurrentMarketSegmentation(@PathVariable Long id) {
        log.debug("REST request to get CurrentMarketSegmentation : {}", id);
        Optional<CurrentMarketSegmentation> currentMarketSegmentation = currentMarketSegmentationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currentMarketSegmentation);
    }

    /**
     * {@code DELETE  /current-market-segmentations/:id} : delete the "id" currentMarketSegmentation.
     *
     * @param id the id of the currentMarketSegmentation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/current-market-segmentations/{id}")
    public ResponseEntity<Void> deleteCurrentMarketSegmentation(@PathVariable Long id) {
        log.debug("REST request to delete CurrentMarketSegmentation : {}", id);
        currentMarketSegmentationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
