package io.valius.app.web.rest;

import io.valius.app.domain.ExternalReports;
import io.valius.app.repository.ExternalReportsRepository;
import io.valius.app.service.ExternalReportsService;
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
 * REST controller for managing {@link io.valius.app.domain.ExternalReports}.
 */
@RestController
@RequestMapping("/api")
public class ExternalReportsResource {

    private final Logger log = LoggerFactory.getLogger(ExternalReportsResource.class);

    private static final String ENTITY_NAME = "externalReports";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExternalReportsService externalReportsService;

    private final ExternalReportsRepository externalReportsRepository;

    public ExternalReportsResource(ExternalReportsService externalReportsService, ExternalReportsRepository externalReportsRepository) {
        this.externalReportsService = externalReportsService;
        this.externalReportsRepository = externalReportsRepository;
    }

    /**
     * {@code POST  /external-reports} : Create a new externalReports.
     *
     * @param externalReports the externalReports to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new externalReports, or with status {@code 400 (Bad Request)} if the externalReports has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/external-reports")
    public ResponseEntity<ExternalReports> createExternalReports(@Valid @RequestBody ExternalReports externalReports)
        throws URISyntaxException {
        log.debug("REST request to save ExternalReports : {}", externalReports);
        if (externalReports.getId() != null) {
            throw new BadRequestAlertException("A new externalReports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalReports result = externalReportsService.save(externalReports);
        return ResponseEntity
            .created(new URI("/api/external-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /external-reports/:id} : Updates an existing externalReports.
     *
     * @param id the id of the externalReports to save.
     * @param externalReports the externalReports to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externalReports,
     * or with status {@code 400 (Bad Request)} if the externalReports is not valid,
     * or with status {@code 500 (Internal Server Error)} if the externalReports couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/external-reports/{id}")
    public ResponseEntity<ExternalReports> updateExternalReports(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExternalReports externalReports
    ) throws URISyntaxException {
        log.debug("REST request to update ExternalReports : {}, {}", id, externalReports);
        if (externalReports.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, externalReports.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!externalReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExternalReports result = externalReportsService.update(externalReports);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, externalReports.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /external-reports/:id} : Partial updates given fields of an existing externalReports, field will ignore if it is null
     *
     * @param id the id of the externalReports to save.
     * @param externalReports the externalReports to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externalReports,
     * or with status {@code 400 (Bad Request)} if the externalReports is not valid,
     * or with status {@code 404 (Not Found)} if the externalReports is not found,
     * or with status {@code 500 (Internal Server Error)} if the externalReports couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/external-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExternalReports> partialUpdateExternalReports(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExternalReports externalReports
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExternalReports partially : {}, {}", id, externalReports);
        if (externalReports.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, externalReports.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!externalReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExternalReports> result = externalReportsService.partialUpdate(externalReports);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, externalReports.getId().toString())
        );
    }

    /**
     * {@code GET  /external-reports} : get all the externalReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of externalReports in body.
     */
    @GetMapping("/external-reports")
    public ResponseEntity<List<ExternalReports>> getAllExternalReports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ExternalReports");
        Page<ExternalReports> page = externalReportsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /external-reports/:id} : get the "id" externalReports.
     *
     * @param id the id of the externalReports to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the externalReports, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/external-reports/{id}")
    public ResponseEntity<ExternalReports> getExternalReports(@PathVariable Long id) {
        log.debug("REST request to get ExternalReports : {}", id);
        Optional<ExternalReports> externalReports = externalReportsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(externalReports);
    }

    /**
     * {@code DELETE  /external-reports/:id} : delete the "id" externalReports.
     *
     * @param id the id of the externalReports to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/external-reports/{id}")
    public ResponseEntity<Void> deleteExternalReports(@PathVariable Long id) {
        log.debug("REST request to delete ExternalReports : {}", id);
        externalReportsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
