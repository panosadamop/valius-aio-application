package io.valius.app.web.rest;

import io.valius.app.domain.StrategicFocus;
import io.valius.app.repository.StrategicFocusRepository;
import io.valius.app.service.StrategicFocusService;
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
 * REST controller for managing {@link io.valius.app.domain.StrategicFocus}.
 */
@RestController
@RequestMapping("/api")
public class StrategicFocusResource {

    private final Logger log = LoggerFactory.getLogger(StrategicFocusResource.class);

    private static final String ENTITY_NAME = "strategicFocus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrategicFocusService strategicFocusService;

    private final StrategicFocusRepository strategicFocusRepository;

    public StrategicFocusResource(StrategicFocusService strategicFocusService, StrategicFocusRepository strategicFocusRepository) {
        this.strategicFocusService = strategicFocusService;
        this.strategicFocusRepository = strategicFocusRepository;
    }

    /**
     * {@code POST  /strategic-foci} : Create a new strategicFocus.
     *
     * @param strategicFocus the strategicFocus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strategicFocus, or with status {@code 400 (Bad Request)} if the strategicFocus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strategic-foci")
    public ResponseEntity<StrategicFocus> createStrategicFocus(@Valid @RequestBody StrategicFocus strategicFocus)
        throws URISyntaxException {
        log.debug("REST request to save StrategicFocus : {}", strategicFocus);
        if (strategicFocus.getId() != null) {
            throw new BadRequestAlertException("A new strategicFocus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StrategicFocus result = strategicFocusService.save(strategicFocus);
        return ResponseEntity
            .created(new URI("/api/strategic-foci/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strategic-foci/:id} : Updates an existing strategicFocus.
     *
     * @param id the id of the strategicFocus to save.
     * @param strategicFocus the strategicFocus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strategicFocus,
     * or with status {@code 400 (Bad Request)} if the strategicFocus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strategicFocus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strategic-foci/{id}")
    public ResponseEntity<StrategicFocus> updateStrategicFocus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StrategicFocus strategicFocus
    ) throws URISyntaxException {
        log.debug("REST request to update StrategicFocus : {}, {}", id, strategicFocus);
        if (strategicFocus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strategicFocus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strategicFocusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StrategicFocus result = strategicFocusService.update(strategicFocus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strategicFocus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /strategic-foci/:id} : Partial updates given fields of an existing strategicFocus, field will ignore if it is null
     *
     * @param id the id of the strategicFocus to save.
     * @param strategicFocus the strategicFocus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strategicFocus,
     * or with status {@code 400 (Bad Request)} if the strategicFocus is not valid,
     * or with status {@code 404 (Not Found)} if the strategicFocus is not found,
     * or with status {@code 500 (Internal Server Error)} if the strategicFocus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/strategic-foci/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StrategicFocus> partialUpdateStrategicFocus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StrategicFocus strategicFocus
    ) throws URISyntaxException {
        log.debug("REST request to partial update StrategicFocus partially : {}, {}", id, strategicFocus);
        if (strategicFocus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strategicFocus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strategicFocusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StrategicFocus> result = strategicFocusService.partialUpdate(strategicFocus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strategicFocus.getId().toString())
        );
    }

    /**
     * {@code GET  /strategic-foci} : get all the strategicFoci.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strategicFoci in body.
     */
    @GetMapping("/strategic-foci")
    public ResponseEntity<List<StrategicFocus>> getAllStrategicFoci(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StrategicFoci");
        Page<StrategicFocus> page = strategicFocusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /strategic-foci/:id} : get the "id" strategicFocus.
     *
     * @param id the id of the strategicFocus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strategicFocus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strategic-foci/{id}")
    public ResponseEntity<StrategicFocus> getStrategicFocus(@PathVariable Long id) {
        log.debug("REST request to get StrategicFocus : {}", id);
        Optional<StrategicFocus> strategicFocus = strategicFocusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strategicFocus);
    }

    /**
     * {@code DELETE  /strategic-foci/:id} : delete the "id" strategicFocus.
     *
     * @param id the id of the strategicFocus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strategic-foci/{id}")
    public ResponseEntity<Void> deleteStrategicFocus(@PathVariable Long id) {
        log.debug("REST request to delete StrategicFocus : {}", id);
        strategicFocusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
