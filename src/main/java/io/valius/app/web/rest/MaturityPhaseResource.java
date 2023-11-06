package io.valius.app.web.rest;

import io.valius.app.domain.MaturityPhase;
import io.valius.app.repository.MaturityPhaseRepository;
import io.valius.app.service.MaturityPhaseService;
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
 * REST controller for managing {@link io.valius.app.domain.MaturityPhase}.
 */
@RestController
@RequestMapping("/api")
public class MaturityPhaseResource {

    private final Logger log = LoggerFactory.getLogger(MaturityPhaseResource.class);

    private static final String ENTITY_NAME = "maturityPhase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaturityPhaseService maturityPhaseService;

    private final MaturityPhaseRepository maturityPhaseRepository;

    public MaturityPhaseResource(MaturityPhaseService maturityPhaseService, MaturityPhaseRepository maturityPhaseRepository) {
        this.maturityPhaseService = maturityPhaseService;
        this.maturityPhaseRepository = maturityPhaseRepository;
    }

    /**
     * {@code POST  /maturity-phases} : Create a new maturityPhase.
     *
     * @param maturityPhase the maturityPhase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maturityPhase, or with status {@code 400 (Bad Request)} if the maturityPhase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/maturity-phases")
    public ResponseEntity<MaturityPhase> createMaturityPhase(@Valid @RequestBody MaturityPhase maturityPhase) throws URISyntaxException {
        log.debug("REST request to save MaturityPhase : {}", maturityPhase);
        if (maturityPhase.getId() != null) {
            throw new BadRequestAlertException("A new maturityPhase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaturityPhase result = maturityPhaseService.save(maturityPhase);
        return ResponseEntity
            .created(new URI("/api/maturity-phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /maturity-phases/:id} : Updates an existing maturityPhase.
     *
     * @param id the id of the maturityPhase to save.
     * @param maturityPhase the maturityPhase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maturityPhase,
     * or with status {@code 400 (Bad Request)} if the maturityPhase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maturityPhase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/maturity-phases/{id}")
    public ResponseEntity<MaturityPhase> updateMaturityPhase(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MaturityPhase maturityPhase
    ) throws URISyntaxException {
        log.debug("REST request to update MaturityPhase : {}, {}", id, maturityPhase);
        if (maturityPhase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maturityPhase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maturityPhaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaturityPhase result = maturityPhaseService.update(maturityPhase);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maturityPhase.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /maturity-phases/:id} : Partial updates given fields of an existing maturityPhase, field will ignore if it is null
     *
     * @param id the id of the maturityPhase to save.
     * @param maturityPhase the maturityPhase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maturityPhase,
     * or with status {@code 400 (Bad Request)} if the maturityPhase is not valid,
     * or with status {@code 404 (Not Found)} if the maturityPhase is not found,
     * or with status {@code 500 (Internal Server Error)} if the maturityPhase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/maturity-phases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaturityPhase> partialUpdateMaturityPhase(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MaturityPhase maturityPhase
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaturityPhase partially : {}, {}", id, maturityPhase);
        if (maturityPhase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maturityPhase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maturityPhaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaturityPhase> result = maturityPhaseService.partialUpdate(maturityPhase);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maturityPhase.getId().toString())
        );
    }

    /**
     * {@code GET  /maturity-phases} : get all the maturityPhases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maturityPhases in body.
     */
    @GetMapping("/maturity-phases")
    public ResponseEntity<List<MaturityPhase>> getAllMaturityPhases(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MaturityPhases");
        Page<MaturityPhase> page = maturityPhaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /maturity-phases/:id} : get the "id" maturityPhase.
     *
     * @param id the id of the maturityPhase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maturityPhase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/maturity-phases/{id}")
    public ResponseEntity<MaturityPhase> getMaturityPhase(@PathVariable Long id) {
        log.debug("REST request to get MaturityPhase : {}", id);
        Optional<MaturityPhase> maturityPhase = maturityPhaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(maturityPhase);
    }

    /**
     * {@code DELETE  /maturity-phases/:id} : delete the "id" maturityPhase.
     *
     * @param id the id of the maturityPhase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/maturity-phases/{id}")
    public ResponseEntity<Void> deleteMaturityPhase(@PathVariable Long id) {
        log.debug("REST request to delete MaturityPhase : {}", id);
        maturityPhaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
