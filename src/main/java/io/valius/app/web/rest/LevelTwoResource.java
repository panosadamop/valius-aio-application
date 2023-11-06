package io.valius.app.web.rest;

import io.valius.app.domain.LevelTwo;
import io.valius.app.repository.LevelTwoRepository;
import io.valius.app.service.LevelTwoService;
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
 * REST controller for managing {@link io.valius.app.domain.LevelTwo}.
 */
@RestController
@RequestMapping("/api")
public class LevelTwoResource {

    private final Logger log = LoggerFactory.getLogger(LevelTwoResource.class);

    private static final String ENTITY_NAME = "levelTwo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelTwoService levelTwoService;

    private final LevelTwoRepository levelTwoRepository;

    public LevelTwoResource(LevelTwoService levelTwoService, LevelTwoRepository levelTwoRepository) {
        this.levelTwoService = levelTwoService;
        this.levelTwoRepository = levelTwoRepository;
    }

    /**
     * {@code POST  /level-twos} : Create a new levelTwo.
     *
     * @param levelTwo the levelTwo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelTwo, or with status {@code 400 (Bad Request)} if the levelTwo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/level-twos")
    public ResponseEntity<LevelTwo> createLevelTwo(@Valid @RequestBody LevelTwo levelTwo) throws URISyntaxException {
        log.debug("REST request to save LevelTwo : {}", levelTwo);
        if (levelTwo.getId() != null) {
            throw new BadRequestAlertException("A new levelTwo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelTwo result = levelTwoService.save(levelTwo);
        return ResponseEntity
            .created(new URI("/api/level-twos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-twos/:id} : Updates an existing levelTwo.
     *
     * @param id the id of the levelTwo to save.
     * @param levelTwo the levelTwo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelTwo,
     * or with status {@code 400 (Bad Request)} if the levelTwo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelTwo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/level-twos/{id}")
    public ResponseEntity<LevelTwo> updateLevelTwo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelTwo levelTwo
    ) throws URISyntaxException {
        log.debug("REST request to update LevelTwo : {}, {}", id, levelTwo);
        if (levelTwo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelTwo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelTwoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelTwo result = levelTwoService.update(levelTwo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelTwo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /level-twos/:id} : Partial updates given fields of an existing levelTwo, field will ignore if it is null
     *
     * @param id the id of the levelTwo to save.
     * @param levelTwo the levelTwo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelTwo,
     * or with status {@code 400 (Bad Request)} if the levelTwo is not valid,
     * or with status {@code 404 (Not Found)} if the levelTwo is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelTwo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/level-twos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LevelTwo> partialUpdateLevelTwo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelTwo levelTwo
    ) throws URISyntaxException {
        log.debug("REST request to partial update LevelTwo partially : {}, {}", id, levelTwo);
        if (levelTwo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelTwo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelTwoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelTwo> result = levelTwoService.partialUpdate(levelTwo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelTwo.getId().toString())
        );
    }

    /**
     * {@code GET  /level-twos} : get all the levelTwos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelTwos in body.
     */
    @GetMapping("/level-twos")
    public ResponseEntity<List<LevelTwo>> getAllLevelTwos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of LevelTwos");
        Page<LevelTwo> page;
        if (eagerload) {
            page = levelTwoService.findAllWithEagerRelationships(pageable);
        } else {
            page = levelTwoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /level-twos/:id} : get the "id" levelTwo.
     *
     * @param id the id of the levelTwo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelTwo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/level-twos/{id}")
    public ResponseEntity<LevelTwo> getLevelTwo(@PathVariable Long id) {
        log.debug("REST request to get LevelTwo : {}", id);
        Optional<LevelTwo> levelTwo = levelTwoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelTwo);
    }

    /**
     * {@code DELETE  /level-twos/:id} : delete the "id" levelTwo.
     *
     * @param id the id of the levelTwo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/level-twos/{id}")
    public ResponseEntity<Void> deleteLevelTwo(@PathVariable Long id) {
        log.debug("REST request to delete LevelTwo : {}", id);
        levelTwoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
