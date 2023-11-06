package io.valius.app.web.rest;

import io.valius.app.domain.FieldKpi;
import io.valius.app.repository.FieldKpiRepository;
import io.valius.app.service.FieldKpiService;
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
 * REST controller for managing {@link io.valius.app.domain.FieldKpi}.
 */
@RestController
@RequestMapping("/api")
public class FieldKpiResource {

    private final Logger log = LoggerFactory.getLogger(FieldKpiResource.class);

    private static final String ENTITY_NAME = "fieldKpi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldKpiService fieldKpiService;

    private final FieldKpiRepository fieldKpiRepository;

    public FieldKpiResource(FieldKpiService fieldKpiService, FieldKpiRepository fieldKpiRepository) {
        this.fieldKpiService = fieldKpiService;
        this.fieldKpiRepository = fieldKpiRepository;
    }

    /**
     * {@code POST  /field-kpis} : Create a new fieldKpi.
     *
     * @param fieldKpi the fieldKpi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldKpi, or with status {@code 400 (Bad Request)} if the fieldKpi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-kpis")
    public ResponseEntity<FieldKpi> createFieldKpi(@RequestBody FieldKpi fieldKpi) throws URISyntaxException {
        log.debug("REST request to save FieldKpi : {}", fieldKpi);
        if (fieldKpi.getId() != null) {
            throw new BadRequestAlertException("A new fieldKpi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldKpi result = fieldKpiService.save(fieldKpi);
        return ResponseEntity
            .created(new URI("/api/field-kpis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-kpis/:id} : Updates an existing fieldKpi.
     *
     * @param id the id of the fieldKpi to save.
     * @param fieldKpi the fieldKpi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldKpi,
     * or with status {@code 400 (Bad Request)} if the fieldKpi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldKpi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-kpis/{id}")
    public ResponseEntity<FieldKpi> updateFieldKpi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldKpi fieldKpi
    ) throws URISyntaxException {
        log.debug("REST request to update FieldKpi : {}, {}", id, fieldKpi);
        if (fieldKpi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldKpi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldKpiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldKpi result = fieldKpiService.update(fieldKpi);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldKpi.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-kpis/:id} : Partial updates given fields of an existing fieldKpi, field will ignore if it is null
     *
     * @param id the id of the fieldKpi to save.
     * @param fieldKpi the fieldKpi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldKpi,
     * or with status {@code 400 (Bad Request)} if the fieldKpi is not valid,
     * or with status {@code 404 (Not Found)} if the fieldKpi is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldKpi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-kpis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldKpi> partialUpdateFieldKpi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldKpi fieldKpi
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldKpi partially : {}, {}", id, fieldKpi);
        if (fieldKpi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldKpi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldKpiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldKpi> result = fieldKpiService.partialUpdate(fieldKpi);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldKpi.getId().toString())
        );
    }

    /**
     * {@code GET  /field-kpis} : get all the fieldKpis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldKpis in body.
     */
    @GetMapping("/field-kpis")
    public ResponseEntity<List<FieldKpi>> getAllFieldKpis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FieldKpis");
        Page<FieldKpi> page = fieldKpiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-kpis/:id} : get the "id" fieldKpi.
     *
     * @param id the id of the fieldKpi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldKpi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-kpis/{id}")
    public ResponseEntity<FieldKpi> getFieldKpi(@PathVariable Long id) {
        log.debug("REST request to get FieldKpi : {}", id);
        Optional<FieldKpi> fieldKpi = fieldKpiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldKpi);
    }

    /**
     * {@code DELETE  /field-kpis/:id} : delete the "id" fieldKpi.
     *
     * @param id the id of the fieldKpi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-kpis/{id}")
    public ResponseEntity<Void> deleteFieldKpi(@PathVariable Long id) {
        log.debug("REST request to delete FieldKpi : {}", id);
        fieldKpiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
