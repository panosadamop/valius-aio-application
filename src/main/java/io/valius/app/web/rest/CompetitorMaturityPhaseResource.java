package io.valius.app.web.rest;

import io.valius.app.domain.CompetitorMaturityPhase;
import io.valius.app.repository.CompetitorMaturityPhaseRepository;
import io.valius.app.service.CompetitorMaturityPhaseService;
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
 * REST controller for managing {@link io.valius.app.domain.CompetitorMaturityPhase}.
 */
@RestController
@RequestMapping("/api")
public class CompetitorMaturityPhaseResource {

    private final Logger log = LoggerFactory.getLogger(CompetitorMaturityPhaseResource.class);

    private static final String ENTITY_NAME = "competitorMaturityPhase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitorMaturityPhaseService competitorMaturityPhaseService;

    private final CompetitorMaturityPhaseRepository competitorMaturityPhaseRepository;

    public CompetitorMaturityPhaseResource(
        CompetitorMaturityPhaseService competitorMaturityPhaseService,
        CompetitorMaturityPhaseRepository competitorMaturityPhaseRepository
    ) {
        this.competitorMaturityPhaseService = competitorMaturityPhaseService;
        this.competitorMaturityPhaseRepository = competitorMaturityPhaseRepository;
    }

    /**
     * {@code POST  /competitor-maturity-phases} : Create a new competitorMaturityPhase.
     *
     * @param competitorMaturityPhase the competitorMaturityPhase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitorMaturityPhase, or with status {@code 400 (Bad Request)} if the competitorMaturityPhase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitor-maturity-phases")
    public ResponseEntity<CompetitorMaturityPhase> createCompetitorMaturityPhase(
        @Valid @RequestBody CompetitorMaturityPhase competitorMaturityPhase
    ) throws URISyntaxException {
        log.debug("REST request to save CompetitorMaturityPhase : {}", competitorMaturityPhase);
        if (competitorMaturityPhase.getId() != null) {
            throw new BadRequestAlertException("A new competitorMaturityPhase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitorMaturityPhase result = competitorMaturityPhaseService.save(competitorMaturityPhase);
        return ResponseEntity
            .created(new URI("/api/competitor-maturity-phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitor-maturity-phases/:id} : Updates an existing competitorMaturityPhase.
     *
     * @param id the id of the competitorMaturityPhase to save.
     * @param competitorMaturityPhase the competitorMaturityPhase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitorMaturityPhase,
     * or with status {@code 400 (Bad Request)} if the competitorMaturityPhase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitorMaturityPhase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitor-maturity-phases/{id}")
    public ResponseEntity<CompetitorMaturityPhase> updateCompetitorMaturityPhase(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompetitorMaturityPhase competitorMaturityPhase
    ) throws URISyntaxException {
        log.debug("REST request to update CompetitorMaturityPhase : {}, {}", id, competitorMaturityPhase);
        if (competitorMaturityPhase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitorMaturityPhase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitorMaturityPhaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompetitorMaturityPhase result = competitorMaturityPhaseService.update(competitorMaturityPhase);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitorMaturityPhase.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competitor-maturity-phases/:id} : Partial updates given fields of an existing competitorMaturityPhase, field will ignore if it is null
     *
     * @param id the id of the competitorMaturityPhase to save.
     * @param competitorMaturityPhase the competitorMaturityPhase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitorMaturityPhase,
     * or with status {@code 400 (Bad Request)} if the competitorMaturityPhase is not valid,
     * or with status {@code 404 (Not Found)} if the competitorMaturityPhase is not found,
     * or with status {@code 500 (Internal Server Error)} if the competitorMaturityPhase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competitor-maturity-phases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompetitorMaturityPhase> partialUpdateCompetitorMaturityPhase(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompetitorMaturityPhase competitorMaturityPhase
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompetitorMaturityPhase partially : {}, {}", id, competitorMaturityPhase);
        if (competitorMaturityPhase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitorMaturityPhase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitorMaturityPhaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompetitorMaturityPhase> result = competitorMaturityPhaseService.partialUpdate(competitorMaturityPhase);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitorMaturityPhase.getId().toString())
        );
    }

    /**
     * {@code GET  /competitor-maturity-phases} : get all the competitorMaturityPhases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitorMaturityPhases in body.
     */
    @GetMapping("/competitor-maturity-phases")
    public ResponseEntity<List<CompetitorMaturityPhase>> getAllCompetitorMaturityPhases(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompetitorMaturityPhases");
        Page<CompetitorMaturityPhase> page = competitorMaturityPhaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competitor-maturity-phases/:id} : get the "id" competitorMaturityPhase.
     *
     * @param id the id of the competitorMaturityPhase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitorMaturityPhase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitor-maturity-phases/{id}")
    public ResponseEntity<CompetitorMaturityPhase> getCompetitorMaturityPhase(@PathVariable Long id) {
        log.debug("REST request to get CompetitorMaturityPhase : {}", id);
        Optional<CompetitorMaturityPhase> competitorMaturityPhase = competitorMaturityPhaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitorMaturityPhase);
    }

    /**
     * {@code DELETE  /competitor-maturity-phases/:id} : delete the "id" competitorMaturityPhase.
     *
     * @param id the id of the competitorMaturityPhase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitor-maturity-phases/{id}")
    public ResponseEntity<Void> deleteCompetitorMaturityPhase(@PathVariable Long id) {
        log.debug("REST request to delete CompetitorMaturityPhase : {}", id);
        competitorMaturityPhaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
