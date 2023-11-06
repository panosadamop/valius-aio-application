package io.valius.app.web.rest;

import io.valius.app.domain.FieldBuyingCriteriaWeighting;
import io.valius.app.repository.FieldBuyingCriteriaWeightingRepository;
import io.valius.app.service.FieldBuyingCriteriaWeightingService;
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
 * REST controller for managing {@link io.valius.app.domain.FieldBuyingCriteriaWeighting}.
 */
@RestController
@RequestMapping("/api")
public class FieldBuyingCriteriaWeightingResource {

    private final Logger log = LoggerFactory.getLogger(FieldBuyingCriteriaWeightingResource.class);

    private static final String ENTITY_NAME = "fieldBuyingCriteriaWeighting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldBuyingCriteriaWeightingService fieldBuyingCriteriaWeightingService;

    private final FieldBuyingCriteriaWeightingRepository fieldBuyingCriteriaWeightingRepository;

    public FieldBuyingCriteriaWeightingResource(
        FieldBuyingCriteriaWeightingService fieldBuyingCriteriaWeightingService,
        FieldBuyingCriteriaWeightingRepository fieldBuyingCriteriaWeightingRepository
    ) {
        this.fieldBuyingCriteriaWeightingService = fieldBuyingCriteriaWeightingService;
        this.fieldBuyingCriteriaWeightingRepository = fieldBuyingCriteriaWeightingRepository;
    }

    /**
     * {@code POST  /field-buying-criteria-weightings} : Create a new fieldBuyingCriteriaWeighting.
     *
     * @param fieldBuyingCriteriaWeighting the fieldBuyingCriteriaWeighting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldBuyingCriteriaWeighting, or with status {@code 400 (Bad Request)} if the fieldBuyingCriteriaWeighting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-buying-criteria-weightings")
    public ResponseEntity<FieldBuyingCriteriaWeighting> createFieldBuyingCriteriaWeighting(
        @RequestBody FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting
    ) throws URISyntaxException {
        log.debug("REST request to save FieldBuyingCriteriaWeighting : {}", fieldBuyingCriteriaWeighting);
        if (fieldBuyingCriteriaWeighting.getId() != null) {
            throw new BadRequestAlertException("A new fieldBuyingCriteriaWeighting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldBuyingCriteriaWeighting result = fieldBuyingCriteriaWeightingService.save(fieldBuyingCriteriaWeighting);
        return ResponseEntity
            .created(new URI("/api/field-buying-criteria-weightings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-buying-criteria-weightings/:id} : Updates an existing fieldBuyingCriteriaWeighting.
     *
     * @param id the id of the fieldBuyingCriteriaWeighting to save.
     * @param fieldBuyingCriteriaWeighting the fieldBuyingCriteriaWeighting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldBuyingCriteriaWeighting,
     * or with status {@code 400 (Bad Request)} if the fieldBuyingCriteriaWeighting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldBuyingCriteriaWeighting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-buying-criteria-weightings/{id}")
    public ResponseEntity<FieldBuyingCriteriaWeighting> updateFieldBuyingCriteriaWeighting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting
    ) throws URISyntaxException {
        log.debug("REST request to update FieldBuyingCriteriaWeighting : {}, {}", id, fieldBuyingCriteriaWeighting);
        if (fieldBuyingCriteriaWeighting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldBuyingCriteriaWeighting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldBuyingCriteriaWeightingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldBuyingCriteriaWeighting result = fieldBuyingCriteriaWeightingService.update(fieldBuyingCriteriaWeighting);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldBuyingCriteriaWeighting.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /field-buying-criteria-weightings/:id} : Partial updates given fields of an existing fieldBuyingCriteriaWeighting, field will ignore if it is null
     *
     * @param id the id of the fieldBuyingCriteriaWeighting to save.
     * @param fieldBuyingCriteriaWeighting the fieldBuyingCriteriaWeighting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldBuyingCriteriaWeighting,
     * or with status {@code 400 (Bad Request)} if the fieldBuyingCriteriaWeighting is not valid,
     * or with status {@code 404 (Not Found)} if the fieldBuyingCriteriaWeighting is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldBuyingCriteriaWeighting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-buying-criteria-weightings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldBuyingCriteriaWeighting> partialUpdateFieldBuyingCriteriaWeighting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldBuyingCriteriaWeighting partially : {}, {}", id, fieldBuyingCriteriaWeighting);
        if (fieldBuyingCriteriaWeighting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldBuyingCriteriaWeighting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldBuyingCriteriaWeightingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldBuyingCriteriaWeighting> result = fieldBuyingCriteriaWeightingService.partialUpdate(fieldBuyingCriteriaWeighting);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldBuyingCriteriaWeighting.getId().toString())
        );
    }

    /**
     * {@code GET  /field-buying-criteria-weightings} : get all the fieldBuyingCriteriaWeightings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldBuyingCriteriaWeightings in body.
     */
    @GetMapping("/field-buying-criteria-weightings")
    public ResponseEntity<List<FieldBuyingCriteriaWeighting>> getAllFieldBuyingCriteriaWeightings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FieldBuyingCriteriaWeightings");
        Page<FieldBuyingCriteriaWeighting> page = fieldBuyingCriteriaWeightingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-buying-criteria-weightings/:id} : get the "id" fieldBuyingCriteriaWeighting.
     *
     * @param id the id of the fieldBuyingCriteriaWeighting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldBuyingCriteriaWeighting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-buying-criteria-weightings/{id}")
    public ResponseEntity<FieldBuyingCriteriaWeighting> getFieldBuyingCriteriaWeighting(@PathVariable Long id) {
        log.debug("REST request to get FieldBuyingCriteriaWeighting : {}", id);
        Optional<FieldBuyingCriteriaWeighting> fieldBuyingCriteriaWeighting = fieldBuyingCriteriaWeightingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldBuyingCriteriaWeighting);
    }

    /**
     * {@code DELETE  /field-buying-criteria-weightings/:id} : delete the "id" fieldBuyingCriteriaWeighting.
     *
     * @param id the id of the fieldBuyingCriteriaWeighting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-buying-criteria-weightings/{id}")
    public ResponseEntity<Void> deleteFieldBuyingCriteriaWeighting(@PathVariable Long id) {
        log.debug("REST request to delete FieldBuyingCriteriaWeighting : {}", id);
        fieldBuyingCriteriaWeightingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
