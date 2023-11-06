package io.valius.app.web.rest;

import io.valius.app.domain.AttractivenessFactors;
import io.valius.app.repository.AttractivenessFactorsRepository;
import io.valius.app.service.AttractivenessFactorsService;
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
 * REST controller for managing {@link io.valius.app.domain.AttractivenessFactors}.
 */
@RestController
@RequestMapping("/api")
public class AttractivenessFactorsResource {

    private final Logger log = LoggerFactory.getLogger(AttractivenessFactorsResource.class);

    private static final String ENTITY_NAME = "attractivenessFactors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttractivenessFactorsService attractivenessFactorsService;

    private final AttractivenessFactorsRepository attractivenessFactorsRepository;

    public AttractivenessFactorsResource(
        AttractivenessFactorsService attractivenessFactorsService,
        AttractivenessFactorsRepository attractivenessFactorsRepository
    ) {
        this.attractivenessFactorsService = attractivenessFactorsService;
        this.attractivenessFactorsRepository = attractivenessFactorsRepository;
    }

    /**
     * {@code POST  /attractiveness-factors} : Create a new attractivenessFactors.
     *
     * @param attractivenessFactors the attractivenessFactors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attractivenessFactors, or with status {@code 400 (Bad Request)} if the attractivenessFactors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attractiveness-factors")
    public ResponseEntity<AttractivenessFactors> createAttractivenessFactors(
        @Valid @RequestBody AttractivenessFactors attractivenessFactors
    ) throws URISyntaxException {
        log.debug("REST request to save AttractivenessFactors : {}", attractivenessFactors);
        if (attractivenessFactors.getId() != null) {
            throw new BadRequestAlertException("A new attractivenessFactors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttractivenessFactors result = attractivenessFactorsService.save(attractivenessFactors);
        return ResponseEntity
            .created(new URI("/api/attractiveness-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attractiveness-factors/:id} : Updates an existing attractivenessFactors.
     *
     * @param id the id of the attractivenessFactors to save.
     * @param attractivenessFactors the attractivenessFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attractivenessFactors,
     * or with status {@code 400 (Bad Request)} if the attractivenessFactors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attractivenessFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attractiveness-factors/{id}")
    public ResponseEntity<AttractivenessFactors> updateAttractivenessFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttractivenessFactors attractivenessFactors
    ) throws URISyntaxException {
        log.debug("REST request to update AttractivenessFactors : {}, {}", id, attractivenessFactors);
        if (attractivenessFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attractivenessFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attractivenessFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttractivenessFactors result = attractivenessFactorsService.update(attractivenessFactors);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attractivenessFactors.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attractiveness-factors/:id} : Partial updates given fields of an existing attractivenessFactors, field will ignore if it is null
     *
     * @param id the id of the attractivenessFactors to save.
     * @param attractivenessFactors the attractivenessFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attractivenessFactors,
     * or with status {@code 400 (Bad Request)} if the attractivenessFactors is not valid,
     * or with status {@code 404 (Not Found)} if the attractivenessFactors is not found,
     * or with status {@code 500 (Internal Server Error)} if the attractivenessFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attractiveness-factors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttractivenessFactors> partialUpdateAttractivenessFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttractivenessFactors attractivenessFactors
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttractivenessFactors partially : {}, {}", id, attractivenessFactors);
        if (attractivenessFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attractivenessFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attractivenessFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttractivenessFactors> result = attractivenessFactorsService.partialUpdate(attractivenessFactors);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attractivenessFactors.getId().toString())
        );
    }

    /**
     * {@code GET  /attractiveness-factors} : get all the attractivenessFactors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attractivenessFactors in body.
     */
    @GetMapping("/attractiveness-factors")
    public ResponseEntity<List<AttractivenessFactors>> getAllAttractivenessFactors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AttractivenessFactors");
        Page<AttractivenessFactors> page = attractivenessFactorsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attractiveness-factors/:id} : get the "id" attractivenessFactors.
     *
     * @param id the id of the attractivenessFactors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attractivenessFactors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attractiveness-factors/{id}")
    public ResponseEntity<AttractivenessFactors> getAttractivenessFactors(@PathVariable Long id) {
        log.debug("REST request to get AttractivenessFactors : {}", id);
        Optional<AttractivenessFactors> attractivenessFactors = attractivenessFactorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attractivenessFactors);
    }

    /**
     * {@code DELETE  /attractiveness-factors/:id} : delete the "id" attractivenessFactors.
     *
     * @param id the id of the attractivenessFactors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attractiveness-factors/{id}")
    public ResponseEntity<Void> deleteAttractivenessFactors(@PathVariable Long id) {
        log.debug("REST request to delete AttractivenessFactors : {}", id);
        attractivenessFactorsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
