package io.valius.app.web.rest;

import io.valius.app.domain.FieldProductype;
import io.valius.app.repository.FieldProductypeRepository;
import io.valius.app.service.FieldProductypeService;
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
 * REST controller for managing {@link io.valius.app.domain.FieldProductype}.
 */
@RestController
@RequestMapping("/api")
public class FieldProductypeResource {

    private final Logger log = LoggerFactory.getLogger(FieldProductypeResource.class);

    private static final String ENTITY_NAME = "fieldProductype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldProductypeService fieldProductypeService;

    private final FieldProductypeRepository fieldProductypeRepository;

    public FieldProductypeResource(FieldProductypeService fieldProductypeService, FieldProductypeRepository fieldProductypeRepository) {
        this.fieldProductypeService = fieldProductypeService;
        this.fieldProductypeRepository = fieldProductypeRepository;
    }

    /**
     * {@code POST  /field-productypes} : Create a new fieldProductype.
     *
     * @param fieldProductype the fieldProductype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldProductype, or with status {@code 400 (Bad Request)} if the fieldProductype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-productypes")
    public ResponseEntity<FieldProductype> createFieldProductype(@RequestBody FieldProductype fieldProductype) throws URISyntaxException {
        log.debug("REST request to save FieldProductype : {}", fieldProductype);
        if (fieldProductype.getId() != null) {
            throw new BadRequestAlertException("A new fieldProductype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldProductype result = fieldProductypeService.save(fieldProductype);
        return ResponseEntity
            .created(new URI("/api/field-productypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-productypes/:id} : Updates an existing fieldProductype.
     *
     * @param id the id of the fieldProductype to save.
     * @param fieldProductype the fieldProductype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldProductype,
     * or with status {@code 400 (Bad Request)} if the fieldProductype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldProductype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-productypes/{id}")
    public ResponseEntity<FieldProductype> updateFieldProductype(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldProductype fieldProductype
    ) throws URISyntaxException {
        log.debug("REST request to update FieldProductype : {}, {}", id, fieldProductype);
        if (fieldProductype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldProductype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldProductypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldProductype result = fieldProductypeService.update(fieldProductype);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldProductype.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-productypes/:id} : Partial updates given fields of an existing fieldProductype, field will ignore if it is null
     *
     * @param id the id of the fieldProductype to save.
     * @param fieldProductype the fieldProductype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldProductype,
     * or with status {@code 400 (Bad Request)} if the fieldProductype is not valid,
     * or with status {@code 404 (Not Found)} if the fieldProductype is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldProductype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-productypes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldProductype> partialUpdateFieldProductype(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldProductype fieldProductype
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldProductype partially : {}, {}", id, fieldProductype);
        if (fieldProductype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldProductype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldProductypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldProductype> result = fieldProductypeService.partialUpdate(fieldProductype);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldProductype.getId().toString())
        );
    }

    /**
     * {@code GET  /field-productypes} : get all the fieldProductypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldProductypes in body.
     */
    @GetMapping("/field-productypes")
    public ResponseEntity<List<FieldProductype>> getAllFieldProductypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FieldProductypes");
        Page<FieldProductype> page = fieldProductypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-productypes/:id} : get the "id" fieldProductype.
     *
     * @param id the id of the fieldProductype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldProductype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-productypes/{id}")
    public ResponseEntity<FieldProductype> getFieldProductype(@PathVariable Long id) {
        log.debug("REST request to get FieldProductype : {}", id);
        Optional<FieldProductype> fieldProductype = fieldProductypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldProductype);
    }

    /**
     * {@code DELETE  /field-productypes/:id} : delete the "id" fieldProductype.
     *
     * @param id the id of the fieldProductype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-productypes/{id}")
    public ResponseEntity<Void> deleteFieldProductype(@PathVariable Long id) {
        log.debug("REST request to delete FieldProductype : {}", id);
        fieldProductypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
