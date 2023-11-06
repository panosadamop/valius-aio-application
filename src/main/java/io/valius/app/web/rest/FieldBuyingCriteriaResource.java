package io.valius.app.web.rest;

import io.valius.app.domain.FieldBuyingCriteria;
import io.valius.app.repository.FieldBuyingCriteriaRepository;
import io.valius.app.service.FieldBuyingCriteriaService;
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
 * REST controller for managing {@link io.valius.app.domain.FieldBuyingCriteria}.
 */
@RestController
@RequestMapping("/api")
public class FieldBuyingCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(FieldBuyingCriteriaResource.class);

    private static final String ENTITY_NAME = "fieldBuyingCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldBuyingCriteriaService fieldBuyingCriteriaService;

    private final FieldBuyingCriteriaRepository fieldBuyingCriteriaRepository;

    public FieldBuyingCriteriaResource(
        FieldBuyingCriteriaService fieldBuyingCriteriaService,
        FieldBuyingCriteriaRepository fieldBuyingCriteriaRepository
    ) {
        this.fieldBuyingCriteriaService = fieldBuyingCriteriaService;
        this.fieldBuyingCriteriaRepository = fieldBuyingCriteriaRepository;
    }

    /**
     * {@code POST  /field-buying-criteria} : Create a new fieldBuyingCriteria.
     *
     * @param fieldBuyingCriteria the fieldBuyingCriteria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldBuyingCriteria, or with status {@code 400 (Bad Request)} if the fieldBuyingCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-buying-criteria")
    public ResponseEntity<FieldBuyingCriteria> createFieldBuyingCriteria(@RequestBody FieldBuyingCriteria fieldBuyingCriteria)
        throws URISyntaxException {
        log.debug("REST request to save FieldBuyingCriteria : {}", fieldBuyingCriteria);
        if (fieldBuyingCriteria.getId() != null) {
            throw new BadRequestAlertException("A new fieldBuyingCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldBuyingCriteria result = fieldBuyingCriteriaService.save(fieldBuyingCriteria);
        return ResponseEntity
            .created(new URI("/api/field-buying-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-buying-criteria/:id} : Updates an existing fieldBuyingCriteria.
     *
     * @param id the id of the fieldBuyingCriteria to save.
     * @param fieldBuyingCriteria the fieldBuyingCriteria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldBuyingCriteria,
     * or with status {@code 400 (Bad Request)} if the fieldBuyingCriteria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldBuyingCriteria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-buying-criteria/{id}")
    public ResponseEntity<FieldBuyingCriteria> updateFieldBuyingCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldBuyingCriteria fieldBuyingCriteria
    ) throws URISyntaxException {
        log.debug("REST request to update FieldBuyingCriteria : {}, {}", id, fieldBuyingCriteria);
        if (fieldBuyingCriteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldBuyingCriteria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldBuyingCriteriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldBuyingCriteria result = fieldBuyingCriteriaService.update(fieldBuyingCriteria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldBuyingCriteria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-buying-criteria/:id} : Partial updates given fields of an existing fieldBuyingCriteria, field will ignore if it is null
     *
     * @param id the id of the fieldBuyingCriteria to save.
     * @param fieldBuyingCriteria the fieldBuyingCriteria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldBuyingCriteria,
     * or with status {@code 400 (Bad Request)} if the fieldBuyingCriteria is not valid,
     * or with status {@code 404 (Not Found)} if the fieldBuyingCriteria is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldBuyingCriteria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-buying-criteria/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldBuyingCriteria> partialUpdateFieldBuyingCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldBuyingCriteria fieldBuyingCriteria
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldBuyingCriteria partially : {}, {}", id, fieldBuyingCriteria);
        if (fieldBuyingCriteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldBuyingCriteria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldBuyingCriteriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldBuyingCriteria> result = fieldBuyingCriteriaService.partialUpdate(fieldBuyingCriteria);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldBuyingCriteria.getId().toString())
        );
    }

    /**
     * {@code GET  /field-buying-criteria} : get all the fieldBuyingCriteria.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldBuyingCriteria in body.
     */
    @GetMapping("/field-buying-criteria")
    public ResponseEntity<List<FieldBuyingCriteria>> getAllFieldBuyingCriteria(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FieldBuyingCriteria");
        Page<FieldBuyingCriteria> page = fieldBuyingCriteriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-buying-criteria/:id} : get the "id" fieldBuyingCriteria.
     *
     * @param id the id of the fieldBuyingCriteria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldBuyingCriteria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-buying-criteria/{id}")
    public ResponseEntity<FieldBuyingCriteria> getFieldBuyingCriteria(@PathVariable Long id) {
        log.debug("REST request to get FieldBuyingCriteria : {}", id);
        Optional<FieldBuyingCriteria> fieldBuyingCriteria = fieldBuyingCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldBuyingCriteria);
    }

    /**
     * {@code DELETE  /field-buying-criteria/:id} : delete the "id" fieldBuyingCriteria.
     *
     * @param id the id of the fieldBuyingCriteria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-buying-criteria/{id}")
    public ResponseEntity<Void> deleteFieldBuyingCriteria(@PathVariable Long id) {
        log.debug("REST request to delete FieldBuyingCriteria : {}", id);
        fieldBuyingCriteriaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
