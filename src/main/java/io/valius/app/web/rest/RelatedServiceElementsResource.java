package io.valius.app.web.rest;

import io.valius.app.domain.RelatedServiceElements;
import io.valius.app.repository.RelatedServiceElementsRepository;
import io.valius.app.service.RelatedServiceElementsService;
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
 * REST controller for managing {@link io.valius.app.domain.RelatedServiceElements}.
 */
@RestController
@RequestMapping("/api")
public class RelatedServiceElementsResource {

    private final Logger log = LoggerFactory.getLogger(RelatedServiceElementsResource.class);

    private static final String ENTITY_NAME = "relatedServiceElements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatedServiceElementsService relatedServiceElementsService;

    private final RelatedServiceElementsRepository relatedServiceElementsRepository;

    public RelatedServiceElementsResource(
        RelatedServiceElementsService relatedServiceElementsService,
        RelatedServiceElementsRepository relatedServiceElementsRepository
    ) {
        this.relatedServiceElementsService = relatedServiceElementsService;
        this.relatedServiceElementsRepository = relatedServiceElementsRepository;
    }

    /**
     * {@code POST  /related-service-elements} : Create a new relatedServiceElements.
     *
     * @param relatedServiceElements the relatedServiceElements to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relatedServiceElements, or with status {@code 400 (Bad Request)} if the relatedServiceElements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/related-service-elements")
    public ResponseEntity<RelatedServiceElements> createRelatedServiceElements(
        @Valid @RequestBody RelatedServiceElements relatedServiceElements
    ) throws URISyntaxException {
        log.debug("REST request to save RelatedServiceElements : {}", relatedServiceElements);
        if (relatedServiceElements.getId() != null) {
            throw new BadRequestAlertException("A new relatedServiceElements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelatedServiceElements result = relatedServiceElementsService.save(relatedServiceElements);
        return ResponseEntity
            .created(new URI("/api/related-service-elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /related-service-elements/:id} : Updates an existing relatedServiceElements.
     *
     * @param id the id of the relatedServiceElements to save.
     * @param relatedServiceElements the relatedServiceElements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedServiceElements,
     * or with status {@code 400 (Bad Request)} if the relatedServiceElements is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relatedServiceElements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/related-service-elements/{id}")
    public ResponseEntity<RelatedServiceElements> updateRelatedServiceElements(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RelatedServiceElements relatedServiceElements
    ) throws URISyntaxException {
        log.debug("REST request to update RelatedServiceElements : {}, {}", id, relatedServiceElements);
        if (relatedServiceElements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedServiceElements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatedServiceElementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RelatedServiceElements result = relatedServiceElementsService.update(relatedServiceElements);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relatedServiceElements.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /related-service-elements/:id} : Partial updates given fields of an existing relatedServiceElements, field will ignore if it is null
     *
     * @param id the id of the relatedServiceElements to save.
     * @param relatedServiceElements the relatedServiceElements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedServiceElements,
     * or with status {@code 400 (Bad Request)} if the relatedServiceElements is not valid,
     * or with status {@code 404 (Not Found)} if the relatedServiceElements is not found,
     * or with status {@code 500 (Internal Server Error)} if the relatedServiceElements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/related-service-elements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RelatedServiceElements> partialUpdateRelatedServiceElements(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RelatedServiceElements relatedServiceElements
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelatedServiceElements partially : {}, {}", id, relatedServiceElements);
        if (relatedServiceElements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedServiceElements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatedServiceElementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RelatedServiceElements> result = relatedServiceElementsService.partialUpdate(relatedServiceElements);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relatedServiceElements.getId().toString())
        );
    }

    /**
     * {@code GET  /related-service-elements} : get all the relatedServiceElements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relatedServiceElements in body.
     */
    @GetMapping("/related-service-elements")
    public ResponseEntity<List<RelatedServiceElements>> getAllRelatedServiceElements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RelatedServiceElements");
        Page<RelatedServiceElements> page = relatedServiceElementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /related-service-elements/:id} : get the "id" relatedServiceElements.
     *
     * @param id the id of the relatedServiceElements to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relatedServiceElements, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/related-service-elements/{id}")
    public ResponseEntity<RelatedServiceElements> getRelatedServiceElements(@PathVariable Long id) {
        log.debug("REST request to get RelatedServiceElements : {}", id);
        Optional<RelatedServiceElements> relatedServiceElements = relatedServiceElementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relatedServiceElements);
    }

    /**
     * {@code DELETE  /related-service-elements/:id} : delete the "id" relatedServiceElements.
     *
     * @param id the id of the relatedServiceElements to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/related-service-elements/{id}")
    public ResponseEntity<Void> deleteRelatedServiceElements(@PathVariable Long id) {
        log.debug("REST request to delete RelatedServiceElements : {}", id);
        relatedServiceElementsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
