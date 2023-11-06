package io.valius.app.web.rest;

import io.valius.app.domain.IntangibleElements;
import io.valius.app.repository.IntangibleElementsRepository;
import io.valius.app.service.IntangibleElementsService;
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
 * REST controller for managing {@link io.valius.app.domain.IntangibleElements}.
 */
@RestController
@RequestMapping("/api")
public class IntangibleElementsResource {

    private final Logger log = LoggerFactory.getLogger(IntangibleElementsResource.class);

    private static final String ENTITY_NAME = "intangibleElements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntangibleElementsService intangibleElementsService;

    private final IntangibleElementsRepository intangibleElementsRepository;

    public IntangibleElementsResource(
        IntangibleElementsService intangibleElementsService,
        IntangibleElementsRepository intangibleElementsRepository
    ) {
        this.intangibleElementsService = intangibleElementsService;
        this.intangibleElementsRepository = intangibleElementsRepository;
    }

    /**
     * {@code POST  /intangible-elements} : Create a new intangibleElements.
     *
     * @param intangibleElements the intangibleElements to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intangibleElements, or with status {@code 400 (Bad Request)} if the intangibleElements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intangible-elements")
    public ResponseEntity<IntangibleElements> createIntangibleElements(@Valid @RequestBody IntangibleElements intangibleElements)
        throws URISyntaxException {
        log.debug("REST request to save IntangibleElements : {}", intangibleElements);
        if (intangibleElements.getId() != null) {
            throw new BadRequestAlertException("A new intangibleElements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntangibleElements result = intangibleElementsService.save(intangibleElements);
        return ResponseEntity
            .created(new URI("/api/intangible-elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intangible-elements/:id} : Updates an existing intangibleElements.
     *
     * @param id the id of the intangibleElements to save.
     * @param intangibleElements the intangibleElements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intangibleElements,
     * or with status {@code 400 (Bad Request)} if the intangibleElements is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intangibleElements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intangible-elements/{id}")
    public ResponseEntity<IntangibleElements> updateIntangibleElements(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntangibleElements intangibleElements
    ) throws URISyntaxException {
        log.debug("REST request to update IntangibleElements : {}, {}", id, intangibleElements);
        if (intangibleElements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intangibleElements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intangibleElementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntangibleElements result = intangibleElementsService.update(intangibleElements);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intangibleElements.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /intangible-elements/:id} : Partial updates given fields of an existing intangibleElements, field will ignore if it is null
     *
     * @param id the id of the intangibleElements to save.
     * @param intangibleElements the intangibleElements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intangibleElements,
     * or with status {@code 400 (Bad Request)} if the intangibleElements is not valid,
     * or with status {@code 404 (Not Found)} if the intangibleElements is not found,
     * or with status {@code 500 (Internal Server Error)} if the intangibleElements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/intangible-elements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntangibleElements> partialUpdateIntangibleElements(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntangibleElements intangibleElements
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntangibleElements partially : {}, {}", id, intangibleElements);
        if (intangibleElements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intangibleElements.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intangibleElementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntangibleElements> result = intangibleElementsService.partialUpdate(intangibleElements);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intangibleElements.getId().toString())
        );
    }

    /**
     * {@code GET  /intangible-elements} : get all the intangibleElements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intangibleElements in body.
     */
    @GetMapping("/intangible-elements")
    public ResponseEntity<List<IntangibleElements>> getAllIntangibleElements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of IntangibleElements");
        Page<IntangibleElements> page = intangibleElementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intangible-elements/:id} : get the "id" intangibleElements.
     *
     * @param id the id of the intangibleElements to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intangibleElements, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intangible-elements/{id}")
    public ResponseEntity<IntangibleElements> getIntangibleElements(@PathVariable Long id) {
        log.debug("REST request to get IntangibleElements : {}", id);
        Optional<IntangibleElements> intangibleElements = intangibleElementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intangibleElements);
    }

    /**
     * {@code DELETE  /intangible-elements/:id} : delete the "id" intangibleElements.
     *
     * @param id the id of the intangibleElements to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intangible-elements/{id}")
    public ResponseEntity<Void> deleteIntangibleElements(@PathVariable Long id) {
        log.debug("REST request to delete IntangibleElements : {}", id);
        intangibleElementsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
