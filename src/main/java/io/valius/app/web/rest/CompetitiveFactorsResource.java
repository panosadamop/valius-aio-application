package io.valius.app.web.rest;

import io.valius.app.domain.CompetitiveFactors;
import io.valius.app.repository.CompetitiveFactorsRepository;
import io.valius.app.service.CompetitiveFactorsService;
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
 * REST controller for managing {@link io.valius.app.domain.CompetitiveFactors}.
 */
@RestController
@RequestMapping("/api")
public class CompetitiveFactorsResource {

    private final Logger log = LoggerFactory.getLogger(CompetitiveFactorsResource.class);

    private static final String ENTITY_NAME = "competitiveFactors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitiveFactorsService competitiveFactorsService;

    private final CompetitiveFactorsRepository competitiveFactorsRepository;

    public CompetitiveFactorsResource(
        CompetitiveFactorsService competitiveFactorsService,
        CompetitiveFactorsRepository competitiveFactorsRepository
    ) {
        this.competitiveFactorsService = competitiveFactorsService;
        this.competitiveFactorsRepository = competitiveFactorsRepository;
    }

    /**
     * {@code POST  /competitive-factors} : Create a new competitiveFactors.
     *
     * @param competitiveFactors the competitiveFactors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitiveFactors, or with status {@code 400 (Bad Request)} if the competitiveFactors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitive-factors")
    public ResponseEntity<CompetitiveFactors> createCompetitiveFactors(@Valid @RequestBody CompetitiveFactors competitiveFactors)
        throws URISyntaxException {
        log.debug("REST request to save CompetitiveFactors : {}", competitiveFactors);
        if (competitiveFactors.getId() != null) {
            throw new BadRequestAlertException("A new competitiveFactors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitiveFactors result = competitiveFactorsService.save(competitiveFactors);
        return ResponseEntity
            .created(new URI("/api/competitive-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitive-factors/:id} : Updates an existing competitiveFactors.
     *
     * @param id the id of the competitiveFactors to save.
     * @param competitiveFactors the competitiveFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitiveFactors,
     * or with status {@code 400 (Bad Request)} if the competitiveFactors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitiveFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitive-factors/{id}")
    public ResponseEntity<CompetitiveFactors> updateCompetitiveFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompetitiveFactors competitiveFactors
    ) throws URISyntaxException {
        log.debug("REST request to update CompetitiveFactors : {}, {}", id, competitiveFactors);
        if (competitiveFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitiveFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitiveFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompetitiveFactors result = competitiveFactorsService.update(competitiveFactors);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitiveFactors.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competitive-factors/:id} : Partial updates given fields of an existing competitiveFactors, field will ignore if it is null
     *
     * @param id the id of the competitiveFactors to save.
     * @param competitiveFactors the competitiveFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitiveFactors,
     * or with status {@code 400 (Bad Request)} if the competitiveFactors is not valid,
     * or with status {@code 404 (Not Found)} if the competitiveFactors is not found,
     * or with status {@code 500 (Internal Server Error)} if the competitiveFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competitive-factors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompetitiveFactors> partialUpdateCompetitiveFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompetitiveFactors competitiveFactors
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompetitiveFactors partially : {}, {}", id, competitiveFactors);
        if (competitiveFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competitiveFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitiveFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompetitiveFactors> result = competitiveFactorsService.partialUpdate(competitiveFactors);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitiveFactors.getId().toString())
        );
    }

    /**
     * {@code GET  /competitive-factors} : get all the competitiveFactors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitiveFactors in body.
     */
    @GetMapping("/competitive-factors")
    public ResponseEntity<List<CompetitiveFactors>> getAllCompetitiveFactors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompetitiveFactors");
        Page<CompetitiveFactors> page = competitiveFactorsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competitive-factors/:id} : get the "id" competitiveFactors.
     *
     * @param id the id of the competitiveFactors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitiveFactors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitive-factors/{id}")
    public ResponseEntity<CompetitiveFactors> getCompetitiveFactors(@PathVariable Long id) {
        log.debug("REST request to get CompetitiveFactors : {}", id);
        Optional<CompetitiveFactors> competitiveFactors = competitiveFactorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitiveFactors);
    }

    /**
     * {@code DELETE  /competitive-factors/:id} : delete the "id" competitiveFactors.
     *
     * @param id the id of the competitiveFactors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitive-factors/{id}")
    public ResponseEntity<Void> deleteCompetitiveFactors(@PathVariable Long id) {
        log.debug("REST request to delete CompetitiveFactors : {}", id);
        competitiveFactorsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
