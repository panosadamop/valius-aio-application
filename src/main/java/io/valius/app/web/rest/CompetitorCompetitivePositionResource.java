package io.valius.app.web.rest;

import io.valius.app.domain.CompetitorCompetitivePosition;
import io.valius.app.repository.CompetitorCompetitivePositionRepository;
import io.valius.app.service.CompetitorCompetitivePositionService;
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
 * REST controller for managing {@link io.valius.app.domain.CompetitorCompetitivePosition}.
 */
@RestController
@RequestMapping("/api")
public class CompetitorCompetitivePositionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitorCompetitivePositionResource.class);

    private static final String ENTITY_NAME = "competitorCompetitivePosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitorCompetitivePositionService competitorCompetitivePositionService;

    private final CompetitorCompetitivePositionRepository competitorCompetitivePositionRepository;

    public CompetitorCompetitivePositionResource(
        CompetitorCompetitivePositionService competitorCompetitivePositionService,
        CompetitorCompetitivePositionRepository competitorCompetitivePositionRepository
    ) {
        this.competitorCompetitivePositionService = competitorCompetitivePositionService;
        this.competitorCompetitivePositionRepository = competitorCompetitivePositionRepository;
    }

    /**
     * {@code POST  /competitor-competitive-positions} : Create a new competitorCompetitivePosition.
     *
     * @param competitorCompetitivePosition the competitorCompetitivePosition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitorCompetitivePosition, or with status {@code 400 (Bad Request)} if the competitorCompetitivePosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitor-competitive-positions")
    public ResponseEntity<CompetitorCompetitivePosition> createCompetitorCompetitivePosition(
        @Valid @RequestBody CompetitorCompetitivePosition competitorCompetitivePosition
    ) throws URISyntaxException {
        log.debug("REST request to save CompetitorCompetitivePosition : {}", competitorCompetitivePosition);
        if (competitorCompetitivePosition.getId() != null) {
            throw new BadRequestAlertException("A new competitorCompetitivePosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitorCompetitivePosition result = competitorCompetitivePositionService.save(competitorCompetitivePosition);
        return ResponseEntity
            .created(new URI("/api/competitor-competitive-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitor-competitive-positions/:id} : Updates an existing competitorCompetitivePosition.
     *
     * @param id the id of the competitorCompetitivePosition to save.
     * @param competitorCompetitivePosition the competitorCompetitivePosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitorCompetitivePosition,
     * or with status {@code 400 (Bad Request)} if the competitorCompetitivePosition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitorCompetitivePosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitor-competitive-positions/{id}")
    public ResponseEntity<CompetitorCompetitivePosition> updateCompetitorCompetitivePosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompetitorCompetitivePosition competitorCompetitivePosition
    ) throws URISyntaxException {
        log.debug("REST request to update CompetitorCompetitivePosition : {}, {}", id, competitorCompetitivePosition);
        if (competitorCompetitivePosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitorCompetitivePosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitorCompetitivePositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompetitorCompetitivePosition result = competitorCompetitivePositionService.update(competitorCompetitivePosition);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitorCompetitivePosition.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /competitor-competitive-positions/:id} : Partial updates given fields of an existing competitorCompetitivePosition, field will ignore if it is null
     *
     * @param id the id of the competitorCompetitivePosition to save.
     * @param competitorCompetitivePosition the competitorCompetitivePosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitorCompetitivePosition,
     * or with status {@code 400 (Bad Request)} if the competitorCompetitivePosition is not valid,
     * or with status {@code 404 (Not Found)} if the competitorCompetitivePosition is not found,
     * or with status {@code 500 (Internal Server Error)} if the competitorCompetitivePosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competitor-competitive-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompetitorCompetitivePosition> partialUpdateCompetitorCompetitivePosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompetitorCompetitivePosition competitorCompetitivePosition
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompetitorCompetitivePosition partially : {}, {}", id, competitorCompetitivePosition);
        if (competitorCompetitivePosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitorCompetitivePosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitorCompetitivePositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompetitorCompetitivePosition> result = competitorCompetitivePositionService.partialUpdate(competitorCompetitivePosition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitorCompetitivePosition.getId().toString())
        );
    }

    /**
     * {@code GET  /competitor-competitive-positions} : get all the competitorCompetitivePositions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitorCompetitivePositions in body.
     */
    @GetMapping("/competitor-competitive-positions")
    public ResponseEntity<List<CompetitorCompetitivePosition>> getAllCompetitorCompetitivePositions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompetitorCompetitivePositions");
        Page<CompetitorCompetitivePosition> page = competitorCompetitivePositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competitor-competitive-positions/:id} : get the "id" competitorCompetitivePosition.
     *
     * @param id the id of the competitorCompetitivePosition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitorCompetitivePosition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitor-competitive-positions/{id}")
    public ResponseEntity<CompetitorCompetitivePosition> getCompetitorCompetitivePosition(@PathVariable Long id) {
        log.debug("REST request to get CompetitorCompetitivePosition : {}", id);
        Optional<CompetitorCompetitivePosition> competitorCompetitivePosition = competitorCompetitivePositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitorCompetitivePosition);
    }

    /**
     * {@code DELETE  /competitor-competitive-positions/:id} : delete the "id" competitorCompetitivePosition.
     *
     * @param id the id of the competitorCompetitivePosition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitor-competitive-positions/{id}")
    public ResponseEntity<Void> deleteCompetitorCompetitivePosition(@PathVariable Long id) {
        log.debug("REST request to delete CompetitorCompetitivePosition : {}", id);
        competitorCompetitivePositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
