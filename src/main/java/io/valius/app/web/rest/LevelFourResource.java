package io.valius.app.web.rest;

import io.valius.app.domain.LevelFour;
import io.valius.app.repository.LevelFourRepository;
import io.valius.app.service.LevelFourService;
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
 * REST controller for managing {@link io.valius.app.domain.LevelFour}.
 */
@RestController
@RequestMapping("/api")
public class LevelFourResource {

    private final Logger log = LoggerFactory.getLogger(LevelFourResource.class);

    private static final String ENTITY_NAME = "levelFour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelFourService levelFourService;

    private final LevelFourRepository levelFourRepository;

    public LevelFourResource(LevelFourService levelFourService, LevelFourRepository levelFourRepository) {
        this.levelFourService = levelFourService;
        this.levelFourRepository = levelFourRepository;
    }

    /**
     * {@code POST  /level-fours} : Create a new levelFour.
     *
     * @param levelFour the levelFour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelFour, or with status {@code 400 (Bad Request)} if the levelFour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/level-fours")
    public ResponseEntity<LevelFour> createLevelFour(@Valid @RequestBody LevelFour levelFour) throws URISyntaxException {
        log.debug("REST request to save LevelFour : {}", levelFour);
        if (levelFour.getId() != null) {
            throw new BadRequestAlertException("A new levelFour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelFour result = levelFourService.save(levelFour);
        return ResponseEntity
            .created(new URI("/api/level-fours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-fours/:id} : Updates an existing levelFour.
     *
     * @param id the id of the levelFour to save.
     * @param levelFour the levelFour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelFour,
     * or with status {@code 400 (Bad Request)} if the levelFour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelFour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/level-fours/{id}")
    public ResponseEntity<LevelFour> updateLevelFour(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelFour levelFour
    ) throws URISyntaxException {
        log.debug("REST request to update LevelFour : {}, {}", id, levelFour);
        if (levelFour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelFour.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelFourRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelFour result = levelFourService.update(levelFour);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelFour.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /level-fours/:id} : Partial updates given fields of an existing levelFour, field will ignore if it is null
     *
     * @param id the id of the levelFour to save.
     * @param levelFour the levelFour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelFour,
     * or with status {@code 400 (Bad Request)} if the levelFour is not valid,
     * or with status {@code 404 (Not Found)} if the levelFour is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelFour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/level-fours/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LevelFour> partialUpdateLevelFour(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelFour levelFour
    ) throws URISyntaxException {
        log.debug("REST request to partial update LevelFour partially : {}, {}", id, levelFour);
        if (levelFour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelFour.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelFourRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelFour> result = levelFourService.partialUpdate(levelFour);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelFour.getId().toString())
        );
    }

    /**
     * {@code GET  /level-fours} : get all the levelFours.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelFours in body.
     */
    @GetMapping("/level-fours")
    public ResponseEntity<List<LevelFour>> getAllLevelFours(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of LevelFours");
        Page<LevelFour> page;
        if (eagerload) {
            page = levelFourService.findAllWithEagerRelationships(pageable);
        } else {
            page = levelFourService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /level-fours/:id} : get the "id" levelFour.
     *
     * @param id the id of the levelFour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelFour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/level-fours/{id}")
    public ResponseEntity<LevelFour> getLevelFour(@PathVariable Long id) {
        log.debug("REST request to get LevelFour : {}", id);
        Optional<LevelFour> levelFour = levelFourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelFour);
    }

    /**
     * {@code DELETE  /level-fours/:id} : delete the "id" levelFour.
     *
     * @param id the id of the levelFour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/level-fours/{id}")
    public ResponseEntity<Void> deleteLevelFour(@PathVariable Long id) {
        log.debug("REST request to delete LevelFour : {}", id);
        levelFourService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
