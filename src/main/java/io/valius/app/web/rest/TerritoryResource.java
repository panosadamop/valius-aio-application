package io.valius.app.web.rest;

import io.valius.app.domain.Territory;
import io.valius.app.repository.TerritoryRepository;
import io.valius.app.service.TerritoryService;
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
 * REST controller for managing {@link io.valius.app.domain.Territory}.
 */
@RestController
@RequestMapping("/api")
public class TerritoryResource {

    private final Logger log = LoggerFactory.getLogger(TerritoryResource.class);

    private static final String ENTITY_NAME = "territory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerritoryService territoryService;

    private final TerritoryRepository territoryRepository;

    public TerritoryResource(TerritoryService territoryService, TerritoryRepository territoryRepository) {
        this.territoryService = territoryService;
        this.territoryRepository = territoryRepository;
    }

    /**
     * {@code POST  /territories} : Create a new territory.
     *
     * @param territory the territory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new territory, or with status {@code 400 (Bad Request)} if the territory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/territories")
    public ResponseEntity<Territory> createTerritory(@Valid @RequestBody Territory territory) throws URISyntaxException {
        log.debug("REST request to save Territory : {}", territory);
        if (territory.getId() != null) {
            throw new BadRequestAlertException("A new territory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Territory result = territoryService.save(territory);
        return ResponseEntity
            .created(new URI("/api/territories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /territories/:id} : Updates an existing territory.
     *
     * @param id the id of the territory to save.
     * @param territory the territory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated territory,
     * or with status {@code 400 (Bad Request)} if the territory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the territory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/territories/{id}")
    public ResponseEntity<Territory> updateTerritory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Territory territory
    ) throws URISyntaxException {
        log.debug("REST request to update Territory : {}, {}", id, territory);
        if (territory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, territory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!territoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Territory result = territoryService.update(territory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, territory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /territories/:id} : Partial updates given fields of an existing territory, field will ignore if it is null
     *
     * @param id the id of the territory to save.
     * @param territory the territory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated territory,
     * or with status {@code 400 (Bad Request)} if the territory is not valid,
     * or with status {@code 404 (Not Found)} if the territory is not found,
     * or with status {@code 500 (Internal Server Error)} if the territory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/territories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Territory> partialUpdateTerritory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Territory territory
    ) throws URISyntaxException {
        log.debug("REST request to partial update Territory partially : {}, {}", id, territory);
        if (territory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, territory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!territoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Territory> result = territoryService.partialUpdate(territory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, territory.getId().toString())
        );
    }

    /**
     * {@code GET  /territories} : get all the territories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of territories in body.
     */
    @GetMapping("/territories")
    public ResponseEntity<List<Territory>> getAllTerritories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Territories");
        Page<Territory> page = territoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /territories/:id} : get the "id" territory.
     *
     * @param id the id of the territory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the territory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/territories/{id}")
    public ResponseEntity<Territory> getTerritory(@PathVariable Long id) {
        log.debug("REST request to get Territory : {}", id);
        Optional<Territory> territory = territoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(territory);
    }

    /**
     * {@code DELETE  /territories/:id} : delete the "id" territory.
     *
     * @param id the id of the territory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/territories/{id}")
    public ResponseEntity<Void> deleteTerritory(@PathVariable Long id) {
        log.debug("REST request to delete Territory : {}", id);
        territoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
