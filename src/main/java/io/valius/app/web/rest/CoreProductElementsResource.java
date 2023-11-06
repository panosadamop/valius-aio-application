package io.valius.app.web.rest;

import io.valius.app.domain.CoreProductElements;
import io.valius.app.repository.CoreProductElementsRepository;
import io.valius.app.service.CoreProductElementsService;
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
 * REST controller for managing {@link io.valius.app.domain.CoreProductElements}.
 */
@RestController
@RequestMapping("/api")
public class CoreProductElementsResource {

    private final Logger log = LoggerFactory.getLogger(CoreProductElementsResource.class);

    private static final String ENTITY_NAME = "coreProductElements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoreProductElementsService coreProductElementsService;

    private final CoreProductElementsRepository coreProductElementsRepository;

    public CoreProductElementsResource(
        CoreProductElementsService coreProductElementsService,
        CoreProductElementsRepository coreProductElementsRepository
    ) {
        this.coreProductElementsService = coreProductElementsService;
        this.coreProductElementsRepository = coreProductElementsRepository;
    }

    /**
     * {@code POST  /core-product-elements} : Create a new coreProductElements.
     *
     * @param coreProductElements the coreProductElements to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coreProductElements, or with status {@code 400 (Bad Request)} if the coreProductElements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/core-product-elements")
    public ResponseEntity<CoreProductElements> createCoreProductElements(@Valid @RequestBody CoreProductElements coreProductElements)
        throws URISyntaxException {
        log.debug("REST request to save CoreProductElements : {}", coreProductElements);
        if (coreProductElements.getId() != null) {
            throw new BadRequestAlertException("A new coreProductElements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoreProductElements result = coreProductElementsService.save(coreProductElements);
        return ResponseEntity
            .created(new URI("/api/core-product-elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /core-product-elements/:id} : Updates an existing coreProductElements.
     *
     * @param id the id of the coreProductElements to save.
     * @param coreProductElements the coreProductElements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coreProductElements,
     * or with status {@code 400 (Bad Request)} if the coreProductElements is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coreProductElements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/core-product-elements/{id}")
    public ResponseEntity<CoreProductElements> updateCoreProductElements(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CoreProductElements coreProductElements
    ) throws URISyntaxException {
        log.debug("REST request to update CoreProductElements : {}, {}", id, coreProductElements);
        if (coreProductElements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coreProductElements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coreProductElementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CoreProductElements result = coreProductElementsService.update(coreProductElements);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coreProductElements.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /core-product-elements/:id} : Partial updates given fields of an existing coreProductElements, field will ignore if it is null
     *
     * @param id the id of the coreProductElements to save.
     * @param coreProductElements the coreProductElements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coreProductElements,
     * or with status {@code 400 (Bad Request)} if the coreProductElements is not valid,
     * or with status {@code 404 (Not Found)} if the coreProductElements is not found,
     * or with status {@code 500 (Internal Server Error)} if the coreProductElements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/core-product-elements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CoreProductElements> partialUpdateCoreProductElements(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CoreProductElements coreProductElements
    ) throws URISyntaxException {
        log.debug("REST request to partial update CoreProductElements partially : {}, {}", id, coreProductElements);
        if (coreProductElements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coreProductElements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coreProductElementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CoreProductElements> result = coreProductElementsService.partialUpdate(coreProductElements);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coreProductElements.getId().toString())
        );
    }

    /**
     * {@code GET  /core-product-elements} : get all the coreProductElements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coreProductElements in body.
     */
    @GetMapping("/core-product-elements")
    public ResponseEntity<List<CoreProductElements>> getAllCoreProductElements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CoreProductElements");
        Page<CoreProductElements> page = coreProductElementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /core-product-elements/:id} : get the "id" coreProductElements.
     *
     * @param id the id of the coreProductElements to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coreProductElements, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/core-product-elements/{id}")
    public ResponseEntity<CoreProductElements> getCoreProductElements(@PathVariable Long id) {
        log.debug("REST request to get CoreProductElements : {}", id);
        Optional<CoreProductElements> coreProductElements = coreProductElementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coreProductElements);
    }

    /**
     * {@code DELETE  /core-product-elements/:id} : delete the "id" coreProductElements.
     *
     * @param id the id of the coreProductElements to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/core-product-elements/{id}")
    public ResponseEntity<Void> deleteCoreProductElements(@PathVariable Long id) {
        log.debug("REST request to delete CoreProductElements : {}", id);
        coreProductElementsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
