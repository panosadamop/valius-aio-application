package io.valius.app.web.rest;

import io.valius.app.domain.InternalReports;
import io.valius.app.repository.InternalReportsRepository;
import io.valius.app.service.InternalReportsService;
import io.valius.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link io.valius.app.domain.InternalReports}.
 */
@RestController
@RequestMapping("/api")
public class InternalReportsResource {

    private final Logger log = LoggerFactory.getLogger(InternalReportsResource.class);

    private static final String ENTITY_NAME = "internalReports";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternalReportsService internalReportsService;

    private final InternalReportsRepository internalReportsRepository;

    public InternalReportsResource(InternalReportsService internalReportsService, InternalReportsRepository internalReportsRepository) {
        this.internalReportsService = internalReportsService;
        this.internalReportsRepository = internalReportsRepository;
    }

    /**
     * {@code POST  /internal-reports} : Create a new internalReports.
     *
     * @param internalReports the internalReports to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new internalReports, or with status {@code 400 (Bad Request)} if the internalReports has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/internal-reports")
    public ResponseEntity<InternalReports> createInternalReports(@RequestBody InternalReports internalReports) throws URISyntaxException {
        log.debug("REST request to save InternalReports : {}", internalReports);
        if (internalReports.getId() != null) {
            throw new BadRequestAlertException("A new internalReports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternalReports result = internalReportsService.save(internalReports);
        return ResponseEntity
            .created(new URI("/api/internal-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /internal-reports/:id} : Updates an existing internalReports.
     *
     * @param id the id of the internalReports to save.
     * @param internalReports the internalReports to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internalReports,
     * or with status {@code 400 (Bad Request)} if the internalReports is not valid,
     * or with status {@code 500 (Internal Server Error)} if the internalReports couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/internal-reports/{id}")
    public ResponseEntity<InternalReports> updateInternalReports(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InternalReports internalReports
    ) throws URISyntaxException {
        log.debug("REST request to update InternalReports : {}, {}", id, internalReports);
        if (internalReports.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, internalReports.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!internalReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InternalReports result = internalReportsService.update(internalReports);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internalReports.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /internal-reports/:id} : Partial updates given fields of an existing internalReports, field will ignore if it is null
     *
     * @param id the id of the internalReports to save.
     * @param internalReports the internalReports to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internalReports,
     * or with status {@code 400 (Bad Request)} if the internalReports is not valid,
     * or with status {@code 404 (Not Found)} if the internalReports is not found,
     * or with status {@code 500 (Internal Server Error)} if the internalReports couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/internal-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InternalReports> partialUpdateInternalReports(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InternalReports internalReports
    ) throws URISyntaxException {
        log.debug("REST request to partial update InternalReports partially : {}, {}", id, internalReports);
        if (internalReports.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, internalReports.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!internalReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InternalReports> result = internalReportsService.partialUpdate(internalReports);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internalReports.getId().toString())
        );
    }

    /**
     * {@code GET  /internal-reports} : get all the internalReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of internalReports in body.
     */
    @GetMapping("/internal-reports")
    public ResponseEntity<List<InternalReports>> getAllInternalReports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of InternalReports");
        Page<InternalReports> page = internalReportsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /internal-reports/:id} : get the "id" internalReports.
     *
     * @param id the id of the internalReports to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the internalReports, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/internal-reports/{id}")
    public ResponseEntity<InternalReports> getInternalReports(@PathVariable Long id) {
        log.debug("REST request to get InternalReports : {}", id);
        Optional<InternalReports> internalReports = internalReportsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internalReports);
    }

    /**
     * {@code DELETE  /internal-reports/:id} : delete the "id" internalReports.
     *
     * @param id the id of the internalReports to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/internal-reports/{id}")
    public ResponseEntity<Void> deleteInternalReports(@PathVariable Long id) {
        log.debug("REST request to delete InternalReports : {}", id);
        internalReportsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
