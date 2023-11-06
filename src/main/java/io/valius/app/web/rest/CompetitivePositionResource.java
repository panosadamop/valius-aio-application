package io.valius.app.web.rest;

import io.valius.app.domain.CompetitivePosition;
import io.valius.app.repository.CompetitivePositionRepository;
import io.valius.app.service.CompetitivePositionService;
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
 * REST controller for managing {@link io.valius.app.domain.CompetitivePosition}.
 */
@RestController
@RequestMapping("/api")
public class CompetitivePositionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitivePositionResource.class);

    private static final String ENTITY_NAME = "competitivePosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitivePositionService competitivePositionService;

    private final CompetitivePositionRepository competitivePositionRepository;

    public CompetitivePositionResource(
        CompetitivePositionService competitivePositionService,
        CompetitivePositionRepository competitivePositionRepository
    ) {
        this.competitivePositionService = competitivePositionService;
        this.competitivePositionRepository = competitivePositionRepository;
    }

    /**
     * {@code POST  /competitive-positions} : Create a new competitivePosition.
     *
     * @param competitivePosition the competitivePosition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitivePosition, or with status {@code 400 (Bad Request)} if the competitivePosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitive-positions")
    public ResponseEntity<CompetitivePosition> createCompetitivePosition(@Valid @RequestBody CompetitivePosition competitivePosition)
        throws URISyntaxException {
        log.debug("REST request to save CompetitivePosition : {}", competitivePosition);
        if (competitivePosition.getId() != null) {
            throw new BadRequestAlertException("A new competitivePosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitivePosition result = competitivePositionService.save(competitivePosition);
        return ResponseEntity
            .created(new URI("/api/competitive-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitive-positions/:id} : Updates an existing competitivePosition.
     *
     * @param id the id of the competitivePosition to save.
     * @param competitivePosition the competitivePosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitivePosition,
     * or with status {@code 400 (Bad Request)} if the competitivePosition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitivePosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitive-positions/{id}")
    public ResponseEntity<CompetitivePosition> updateCompetitivePosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompetitivePosition competitivePosition
    ) throws URISyntaxException {
        log.debug("REST request to update CompetitivePosition : {}, {}", id, competitivePosition);
        if (competitivePosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitivePosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitivePositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompetitivePosition result = competitivePositionService.update(competitivePosition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitivePosition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competitive-positions/:id} : Partial updates given fields of an existing competitivePosition, field will ignore if it is null
     *
     * @param id the id of the competitivePosition to save.
     * @param competitivePosition the competitivePosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitivePosition,
     * or with status {@code 400 (Bad Request)} if the competitivePosition is not valid,
     * or with status {@code 404 (Not Found)} if the competitivePosition is not found,
     * or with status {@code 500 (Internal Server Error)} if the competitivePosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competitive-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompetitivePosition> partialUpdateCompetitivePosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompetitivePosition competitivePosition
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompetitivePosition partially : {}, {}", id, competitivePosition);
        if (competitivePosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitivePosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitivePositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompetitivePosition> result = competitivePositionService.partialUpdate(competitivePosition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitivePosition.getId().toString())
        );
    }

    /**
     * {@code GET  /competitive-positions} : get all the competitivePositions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitivePositions in body.
     */
    @GetMapping("/competitive-positions")
    public ResponseEntity<List<CompetitivePosition>> getAllCompetitivePositions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompetitivePositions");
        Page<CompetitivePosition> page = competitivePositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competitive-positions/:id} : get the "id" competitivePosition.
     *
     * @param id the id of the competitivePosition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitivePosition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitive-positions/{id}")
    public ResponseEntity<CompetitivePosition> getCompetitivePosition(@PathVariable Long id) {
        log.debug("REST request to get CompetitivePosition : {}", id);
        Optional<CompetitivePosition> competitivePosition = competitivePositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitivePosition);
    }

    /**
     * {@code DELETE  /competitive-positions/:id} : delete the "id" competitivePosition.
     *
     * @param id the id of the competitivePosition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitive-positions/{id}")
    public ResponseEntity<Void> deleteCompetitivePosition(@PathVariable Long id) {
        log.debug("REST request to delete CompetitivePosition : {}", id);
        competitivePositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
