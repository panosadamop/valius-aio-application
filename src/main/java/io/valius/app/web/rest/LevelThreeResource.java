package io.valius.app.web.rest;

import io.valius.app.domain.LevelThree;
import io.valius.app.repository.LevelThreeRepository;
import io.valius.app.service.LevelThreeService;
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
 * REST controller for managing {@link io.valius.app.domain.LevelThree}.
 */
@RestController
@RequestMapping("/api")
public class LevelThreeResource {

    private final Logger log = LoggerFactory.getLogger(LevelThreeResource.class);

    private static final String ENTITY_NAME = "levelThree";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelThreeService levelThreeService;

    private final LevelThreeRepository levelThreeRepository;

    public LevelThreeResource(LevelThreeService levelThreeService, LevelThreeRepository levelThreeRepository) {
        this.levelThreeService = levelThreeService;
        this.levelThreeRepository = levelThreeRepository;
    }

    /**
     * {@code POST  /level-threes} : Create a new levelThree.
     *
     * @param levelThree the levelThree to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelThree, or with status {@code 400 (Bad Request)} if the levelThree has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/level-threes")
    public ResponseEntity<LevelThree> createLevelThree(@Valid @RequestBody LevelThree levelThree) throws URISyntaxException {
        log.debug("REST request to save LevelThree : {}", levelThree);
        if (levelThree.getId() != null) {
            throw new BadRequestAlertException("A new levelThree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelThree result = levelThreeService.save(levelThree);
        return ResponseEntity
            .created(new URI("/api/level-threes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-threes/:id} : Updates an existing levelThree.
     *
     * @param id the id of the levelThree to save.
     * @param levelThree the levelThree to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelThree,
     * or with status {@code 400 (Bad Request)} if the levelThree is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelThree couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/level-threes/{id}")
    public ResponseEntity<LevelThree> updateLevelThree(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelThree levelThree
    ) throws URISyntaxException {
        log.debug("REST request to update LevelThree : {}, {}", id, levelThree);
        if (levelThree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelThree.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelThreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelThree result = levelThreeService.update(levelThree);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelThree.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /level-threes/:id} : Partial updates given fields of an existing levelThree, field will ignore if it is null
     *
     * @param id the id of the levelThree to save.
     * @param levelThree the levelThree to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelThree,
     * or with status {@code 400 (Bad Request)} if the levelThree is not valid,
     * or with status {@code 404 (Not Found)} if the levelThree is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelThree couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/level-threes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LevelThree> partialUpdateLevelThree(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelThree levelThree
    ) throws URISyntaxException {
        log.debug("REST request to partial update LevelThree partially : {}, {}", id, levelThree);
        if (levelThree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelThree.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelThreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelThree> result = levelThreeService.partialUpdate(levelThree);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelThree.getId().toString())
        );
    }

    /**
     * {@code GET  /level-threes} : get all the levelThrees.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelThrees in body.
     */
    @GetMapping("/level-threes")
    public ResponseEntity<List<LevelThree>> getAllLevelThrees(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of LevelThrees");
        Page<LevelThree> page;
        if (eagerload) {
            page = levelThreeService.findAllWithEagerRelationships(pageable);
        } else {
            page = levelThreeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /level-threes/:id} : get the "id" levelThree.
     *
     * @param id the id of the levelThree to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelThree, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/level-threes/{id}")
    public ResponseEntity<LevelThree> getLevelThree(@PathVariable Long id) {
        log.debug("REST request to get LevelThree : {}", id);
        Optional<LevelThree> levelThree = levelThreeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelThree);
    }

    /**
     * {@code DELETE  /level-threes/:id} : delete the "id" levelThree.
     *
     * @param id the id of the levelThree to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/level-threes/{id}")
    public ResponseEntity<Void> deleteLevelThree(@PathVariable Long id) {
        log.debug("REST request to delete LevelThree : {}", id);
        levelThreeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
