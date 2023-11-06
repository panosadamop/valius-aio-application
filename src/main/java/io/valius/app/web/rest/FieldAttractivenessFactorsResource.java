package io.valius.app.web.rest;

import io.valius.app.domain.FieldAttractivenessFactors;
import io.valius.app.repository.FieldAttractivenessFactorsRepository;
import io.valius.app.service.FieldAttractivenessFactorsService;
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
 * REST controller for managing {@link io.valius.app.domain.FieldAttractivenessFactors}.
 */
@RestController
@RequestMapping("/api")
public class FieldAttractivenessFactorsResource {

    private final Logger log = LoggerFactory.getLogger(FieldAttractivenessFactorsResource.class);

    private static final String ENTITY_NAME = "fieldAttractivenessFactors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldAttractivenessFactorsService fieldAttractivenessFactorsService;

    private final FieldAttractivenessFactorsRepository fieldAttractivenessFactorsRepository;

    public FieldAttractivenessFactorsResource(
        FieldAttractivenessFactorsService fieldAttractivenessFactorsService,
        FieldAttractivenessFactorsRepository fieldAttractivenessFactorsRepository
    ) {
        this.fieldAttractivenessFactorsService = fieldAttractivenessFactorsService;
        this.fieldAttractivenessFactorsRepository = fieldAttractivenessFactorsRepository;
    }

    /**
     * {@code POST  /field-attractiveness-factors} : Create a new fieldAttractivenessFactors.
     *
     * @param fieldAttractivenessFactors the fieldAttractivenessFactors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldAttractivenessFactors, or with status {@code 400 (Bad Request)} if the fieldAttractivenessFactors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-attractiveness-factors")
    public ResponseEntity<FieldAttractivenessFactors> createFieldAttractivenessFactors(
        @RequestBody FieldAttractivenessFactors fieldAttractivenessFactors
    ) throws URISyntaxException {
        log.debug("REST request to save FieldAttractivenessFactors : {}", fieldAttractivenessFactors);
        if (fieldAttractivenessFactors.getId() != null) {
            throw new BadRequestAlertException("A new fieldAttractivenessFactors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldAttractivenessFactors result = fieldAttractivenessFactorsService.save(fieldAttractivenessFactors);
        return ResponseEntity
            .created(new URI("/api/field-attractiveness-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-attractiveness-factors/:id} : Updates an existing fieldAttractivenessFactors.
     *
     * @param id the id of the fieldAttractivenessFactors to save.
     * @param fieldAttractivenessFactors the fieldAttractivenessFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAttractivenessFactors,
     * or with status {@code 400 (Bad Request)} if the fieldAttractivenessFactors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldAttractivenessFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-attractiveness-factors/{id}")
    public ResponseEntity<FieldAttractivenessFactors> updateFieldAttractivenessFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldAttractivenessFactors fieldAttractivenessFactors
    ) throws URISyntaxException {
        log.debug("REST request to update FieldAttractivenessFactors : {}, {}", id, fieldAttractivenessFactors);
        if (fieldAttractivenessFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAttractivenessFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAttractivenessFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldAttractivenessFactors result = fieldAttractivenessFactorsService.update(fieldAttractivenessFactors);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAttractivenessFactors.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-attractiveness-factors/:id} : Partial updates given fields of an existing fieldAttractivenessFactors, field will ignore if it is null
     *
     * @param id the id of the fieldAttractivenessFactors to save.
     * @param fieldAttractivenessFactors the fieldAttractivenessFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAttractivenessFactors,
     * or with status {@code 400 (Bad Request)} if the fieldAttractivenessFactors is not valid,
     * or with status {@code 404 (Not Found)} if the fieldAttractivenessFactors is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldAttractivenessFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-attractiveness-factors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldAttractivenessFactors> partialUpdateFieldAttractivenessFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldAttractivenessFactors fieldAttractivenessFactors
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldAttractivenessFactors partially : {}, {}", id, fieldAttractivenessFactors);
        if (fieldAttractivenessFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAttractivenessFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAttractivenessFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldAttractivenessFactors> result = fieldAttractivenessFactorsService.partialUpdate(fieldAttractivenessFactors);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAttractivenessFactors.getId().toString())
        );
    }

    /**
     * {@code GET  /field-attractiveness-factors} : get all the fieldAttractivenessFactors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldAttractivenessFactors in body.
     */
    @GetMapping("/field-attractiveness-factors")
    public ResponseEntity<List<FieldAttractivenessFactors>> getAllFieldAttractivenessFactors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FieldAttractivenessFactors");
        Page<FieldAttractivenessFactors> page = fieldAttractivenessFactorsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-attractiveness-factors/:id} : get the "id" fieldAttractivenessFactors.
     *
     * @param id the id of the fieldAttractivenessFactors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldAttractivenessFactors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-attractiveness-factors/{id}")
    public ResponseEntity<FieldAttractivenessFactors> getFieldAttractivenessFactors(@PathVariable Long id) {
        log.debug("REST request to get FieldAttractivenessFactors : {}", id);
        Optional<FieldAttractivenessFactors> fieldAttractivenessFactors = fieldAttractivenessFactorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldAttractivenessFactors);
    }

    /**
     * {@code DELETE  /field-attractiveness-factors/:id} : delete the "id" fieldAttractivenessFactors.
     *
     * @param id the id of the fieldAttractivenessFactors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-attractiveness-factors/{id}")
    public ResponseEntity<Void> deleteFieldAttractivenessFactors(@PathVariable Long id) {
        log.debug("REST request to delete FieldAttractivenessFactors : {}", id);
        fieldAttractivenessFactorsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
