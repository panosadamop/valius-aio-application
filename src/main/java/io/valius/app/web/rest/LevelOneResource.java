package io.valius.app.web.rest;

import io.valius.app.domain.LevelOne;
import io.valius.app.repository.LevelOneRepository;
import io.valius.app.service.LevelOneService;
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
 * REST controller for managing {@link io.valius.app.domain.LevelOne}.
 */
@RestController
@RequestMapping("/api")
public class LevelOneResource {

    private final Logger log = LoggerFactory.getLogger(LevelOneResource.class);

    private static final String ENTITY_NAME = "levelOne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelOneService levelOneService;

    private final LevelOneRepository levelOneRepository;

    public LevelOneResource(LevelOneService levelOneService, LevelOneRepository levelOneRepository) {
        this.levelOneService = levelOneService;
        this.levelOneRepository = levelOneRepository;
    }

    /**
     * {@code POST  /level-ones} : Create a new levelOne.
     *
     * @param levelOne the levelOne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelOne, or with status {@code 400 (Bad Request)} if the levelOne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/level-ones")
    public ResponseEntity<LevelOne> createLevelOne(@Valid @RequestBody LevelOne levelOne) throws URISyntaxException {
        log.debug("REST request to save LevelOne : {}", levelOne);
        if (levelOne.getId() != null) {
            throw new BadRequestAlertException("A new levelOne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelOne result = levelOneService.save(levelOne);
        return ResponseEntity
            .created(new URI("/api/level-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-ones/:id} : Updates an existing levelOne.
     *
     * @param id the id of the levelOne to save.
     * @param levelOne the levelOne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelOne,
     * or with status {@code 400 (Bad Request)} if the levelOne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelOne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/level-ones/{id}")
    public ResponseEntity<LevelOne> updateLevelOne(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelOne levelOne
    ) throws URISyntaxException {
        log.debug("REST request to update LevelOne : {}, {}", id, levelOne);
        if (levelOne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelOne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelOneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelOne result = levelOneService.update(levelOne);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelOne.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /level-ones/:id} : Partial updates given fields of an existing levelOne, field will ignore if it is null
     *
     * @param id the id of the levelOne to save.
     * @param levelOne the levelOne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelOne,
     * or with status {@code 400 (Bad Request)} if the levelOne is not valid,
     * or with status {@code 404 (Not Found)} if the levelOne is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelOne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/level-ones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LevelOne> partialUpdateLevelOne(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelOne levelOne
    ) throws URISyntaxException {
        log.debug("REST request to partial update LevelOne partially : {}, {}", id, levelOne);
        if (levelOne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelOne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelOneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelOne> result = levelOneService.partialUpdate(levelOne);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelOne.getId().toString())
        );
    }

    /**
     * {@code GET  /level-ones} : get all the levelOnes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelOnes in body.
     */
    @GetMapping("/level-ones")
    public ResponseEntity<List<LevelOne>> getAllLevelOnes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of LevelOnes");
        Page<LevelOne> page;
        if (eagerload) {
            page = levelOneService.findAllWithEagerRelationships(pageable);
        } else {
            page = levelOneService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /level-ones/:id} : get the "id" levelOne.
     *
     * @param id the id of the levelOne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelOne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/level-ones/{id}")
    public ResponseEntity<LevelOne> getLevelOne(@PathVariable Long id) {
        log.debug("REST request to get LevelOne : {}", id);
        Optional<LevelOne> levelOne = levelOneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelOne);
    }

    /**
     * {@code DELETE  /level-ones/:id} : delete the "id" levelOne.
     *
     * @param id the id of the levelOne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/level-ones/{id}")
    public ResponseEntity<Void> deleteLevelOne(@PathVariable Long id) {
        log.debug("REST request to delete LevelOne : {}", id);
        levelOneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
