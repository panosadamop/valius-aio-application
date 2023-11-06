package io.valius.app.web.rest;

import io.valius.app.domain.Revenues;
import io.valius.app.repository.RevenuesRepository;
import io.valius.app.service.RevenuesService;
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
 * REST controller for managing {@link io.valius.app.domain.Revenues}.
 */
@RestController
@RequestMapping("/api")
public class RevenuesResource {

    private final Logger log = LoggerFactory.getLogger(RevenuesResource.class);

    private static final String ENTITY_NAME = "revenues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RevenuesService revenuesService;

    private final RevenuesRepository revenuesRepository;

    public RevenuesResource(RevenuesService revenuesService, RevenuesRepository revenuesRepository) {
        this.revenuesService = revenuesService;
        this.revenuesRepository = revenuesRepository;
    }

    /**
     * {@code POST  /revenues} : Create a new revenues.
     *
     * @param revenues the revenues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new revenues, or with status {@code 400 (Bad Request)} if the revenues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/revenues")
    public ResponseEntity<Revenues> createRevenues(@Valid @RequestBody Revenues revenues) throws URISyntaxException {
        log.debug("REST request to save Revenues : {}", revenues);
        if (revenues.getId() != null) {
            throw new BadRequestAlertException("A new revenues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Revenues result = revenuesService.save(revenues);
        return ResponseEntity
            .created(new URI("/api/revenues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /revenues/:id} : Updates an existing revenues.
     *
     * @param id the id of the revenues to save.
     * @param revenues the revenues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revenues,
     * or with status {@code 400 (Bad Request)} if the revenues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the revenues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/revenues/{id}")
    public ResponseEntity<Revenues> updateRevenues(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Revenues revenues
    ) throws URISyntaxException {
        log.debug("REST request to update Revenues : {}, {}", id, revenues);
        if (revenues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revenues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revenuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Revenues result = revenuesService.update(revenues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revenues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /revenues/:id} : Partial updates given fields of an existing revenues, field will ignore if it is null
     *
     * @param id the id of the revenues to save.
     * @param revenues the revenues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revenues,
     * or with status {@code 400 (Bad Request)} if the revenues is not valid,
     * or with status {@code 404 (Not Found)} if the revenues is not found,
     * or with status {@code 500 (Internal Server Error)} if the revenues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/revenues/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Revenues> partialUpdateRevenues(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Revenues revenues
    ) throws URISyntaxException {
        log.debug("REST request to partial update Revenues partially : {}, {}", id, revenues);
        if (revenues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revenues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revenuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Revenues> result = revenuesService.partialUpdate(revenues);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revenues.getId().toString())
        );
    }

    /**
     * {@code GET  /revenues} : get all the revenues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of revenues in body.
     */
    @GetMapping("/revenues")
    public ResponseEntity<List<Revenues>> getAllRevenues(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Revenues");
        Page<Revenues> page = revenuesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /revenues/:id} : get the "id" revenues.
     *
     * @param id the id of the revenues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the revenues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/revenues/{id}")
    public ResponseEntity<Revenues> getRevenues(@PathVariable Long id) {
        log.debug("REST request to get Revenues : {}", id);
        Optional<Revenues> revenues = revenuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(revenues);
    }

    /**
     * {@code DELETE  /revenues/:id} : delete the "id" revenues.
     *
     * @param id the id of the revenues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/revenues/{id}")
    public ResponseEntity<Void> deleteRevenues(@PathVariable Long id) {
        log.debug("REST request to delete Revenues : {}", id);
        revenuesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
