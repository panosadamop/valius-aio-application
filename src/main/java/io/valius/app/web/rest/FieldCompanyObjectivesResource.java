package io.valius.app.web.rest;

import io.valius.app.domain.FieldCompanyObjectives;
import io.valius.app.repository.FieldCompanyObjectivesRepository;
import io.valius.app.service.FieldCompanyObjectivesService;
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
 * REST controller for managing {@link io.valius.app.domain.FieldCompanyObjectives}.
 */
@RestController
@RequestMapping("/api")
public class FieldCompanyObjectivesResource {

    private final Logger log = LoggerFactory.getLogger(FieldCompanyObjectivesResource.class);

    private static final String ENTITY_NAME = "fieldCompanyObjectives";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldCompanyObjectivesService fieldCompanyObjectivesService;

    private final FieldCompanyObjectivesRepository fieldCompanyObjectivesRepository;

    public FieldCompanyObjectivesResource(
        FieldCompanyObjectivesService fieldCompanyObjectivesService,
        FieldCompanyObjectivesRepository fieldCompanyObjectivesRepository
    ) {
        this.fieldCompanyObjectivesService = fieldCompanyObjectivesService;
        this.fieldCompanyObjectivesRepository = fieldCompanyObjectivesRepository;
    }

    /**
     * {@code POST  /field-company-objectives} : Create a new fieldCompanyObjectives.
     *
     * @param fieldCompanyObjectives the fieldCompanyObjectives to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldCompanyObjectives, or with status {@code 400 (Bad Request)} if the fieldCompanyObjectives has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-company-objectives")
    public ResponseEntity<FieldCompanyObjectives> createFieldCompanyObjectives(@RequestBody FieldCompanyObjectives fieldCompanyObjectives)
        throws URISyntaxException {
        log.debug("REST request to save FieldCompanyObjectives : {}", fieldCompanyObjectives);
        if (fieldCompanyObjectives.getId() != null) {
            throw new BadRequestAlertException("A new fieldCompanyObjectives cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldCompanyObjectives result = fieldCompanyObjectivesService.save(fieldCompanyObjectives);
        return ResponseEntity
            .created(new URI("/api/field-company-objectives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-company-objectives/:id} : Updates an existing fieldCompanyObjectives.
     *
     * @param id the id of the fieldCompanyObjectives to save.
     * @param fieldCompanyObjectives the fieldCompanyObjectives to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldCompanyObjectives,
     * or with status {@code 400 (Bad Request)} if the fieldCompanyObjectives is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldCompanyObjectives couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-company-objectives/{id}")
    public ResponseEntity<FieldCompanyObjectives> updateFieldCompanyObjectives(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldCompanyObjectives fieldCompanyObjectives
    ) throws URISyntaxException {
        log.debug("REST request to update FieldCompanyObjectives : {}, {}", id, fieldCompanyObjectives);
        if (fieldCompanyObjectives.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldCompanyObjectives.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldCompanyObjectivesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldCompanyObjectives result = fieldCompanyObjectivesService.update(fieldCompanyObjectives);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldCompanyObjectives.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-company-objectives/:id} : Partial updates given fields of an existing fieldCompanyObjectives, field will ignore if it is null
     *
     * @param id the id of the fieldCompanyObjectives to save.
     * @param fieldCompanyObjectives the fieldCompanyObjectives to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldCompanyObjectives,
     * or with status {@code 400 (Bad Request)} if the fieldCompanyObjectives is not valid,
     * or with status {@code 404 (Not Found)} if the fieldCompanyObjectives is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldCompanyObjectives couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-company-objectives/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldCompanyObjectives> partialUpdateFieldCompanyObjectives(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldCompanyObjectives fieldCompanyObjectives
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldCompanyObjectives partially : {}, {}", id, fieldCompanyObjectives);
        if (fieldCompanyObjectives.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldCompanyObjectives.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldCompanyObjectivesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldCompanyObjectives> result = fieldCompanyObjectivesService.partialUpdate(fieldCompanyObjectives);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldCompanyObjectives.getId().toString())
        );
    }

    /**
     * {@code GET  /field-company-objectives} : get all the fieldCompanyObjectives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldCompanyObjectives in body.
     */
    @GetMapping("/field-company-objectives")
    public ResponseEntity<List<FieldCompanyObjectives>> getAllFieldCompanyObjectives(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FieldCompanyObjectives");
        Page<FieldCompanyObjectives> page = fieldCompanyObjectivesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-company-objectives/:id} : get the "id" fieldCompanyObjectives.
     *
     * @param id the id of the fieldCompanyObjectives to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldCompanyObjectives, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-company-objectives/{id}")
    public ResponseEntity<FieldCompanyObjectives> getFieldCompanyObjectives(@PathVariable Long id) {
        log.debug("REST request to get FieldCompanyObjectives : {}", id);
        Optional<FieldCompanyObjectives> fieldCompanyObjectives = fieldCompanyObjectivesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldCompanyObjectives);
    }

    /**
     * {@code DELETE  /field-company-objectives/:id} : delete the "id" fieldCompanyObjectives.
     *
     * @param id the id of the fieldCompanyObjectives to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-company-objectives/{id}")
    public ResponseEntity<Void> deleteFieldCompanyObjectives(@PathVariable Long id) {
        log.debug("REST request to delete FieldCompanyObjectives : {}", id);
        fieldCompanyObjectivesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
