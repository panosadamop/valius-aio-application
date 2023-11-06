package io.valius.app.web.rest;

import io.valius.app.domain.PopulationSize;
import io.valius.app.repository.PopulationSizeRepository;
import io.valius.app.service.PopulationSizeService;
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
 * REST controller for managing {@link io.valius.app.domain.PopulationSize}.
 */
@RestController
@RequestMapping("/api")
public class PopulationSizeResource {

    private final Logger log = LoggerFactory.getLogger(PopulationSizeResource.class);

    private static final String ENTITY_NAME = "populationSize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PopulationSizeService populationSizeService;

    private final PopulationSizeRepository populationSizeRepository;

    public PopulationSizeResource(PopulationSizeService populationSizeService, PopulationSizeRepository populationSizeRepository) {
        this.populationSizeService = populationSizeService;
        this.populationSizeRepository = populationSizeRepository;
    }

    /**
     * {@code POST  /population-sizes} : Create a new populationSize.
     *
     * @param populationSize the populationSize to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new populationSize, or with status {@code 400 (Bad Request)} if the populationSize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/population-sizes")
    public ResponseEntity<PopulationSize> createPopulationSize(@Valid @RequestBody PopulationSize populationSize)
        throws URISyntaxException {
        log.debug("REST request to save PopulationSize : {}", populationSize);
        if (populationSize.getId() != null) {
            throw new BadRequestAlertException("A new populationSize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PopulationSize result = populationSizeService.save(populationSize);
        return ResponseEntity
            .created(new URI("/api/population-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /population-sizes/:id} : Updates an existing populationSize.
     *
     * @param id the id of the populationSize to save.
     * @param populationSize the populationSize to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated populationSize,
     * or with status {@code 400 (Bad Request)} if the populationSize is not valid,
     * or with status {@code 500 (Internal Server Error)} if the populationSize couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/population-sizes/{id}")
    public ResponseEntity<PopulationSize> updatePopulationSize(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PopulationSize populationSize
    ) throws URISyntaxException {
        log.debug("REST request to update PopulationSize : {}, {}", id, populationSize);
        if (populationSize.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, populationSize.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!populationSizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PopulationSize result = populationSizeService.update(populationSize);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, populationSize.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /population-sizes/:id} : Partial updates given fields of an existing populationSize, field will ignore if it is null
     *
     * @param id the id of the populationSize to save.
     * @param populationSize the populationSize to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated populationSize,
     * or with status {@code 400 (Bad Request)} if the populationSize is not valid,
     * or with status {@code 404 (Not Found)} if the populationSize is not found,
     * or with status {@code 500 (Internal Server Error)} if the populationSize couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/population-sizes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PopulationSize> partialUpdatePopulationSize(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PopulationSize populationSize
    ) throws URISyntaxException {
        log.debug("REST request to partial update PopulationSize partially : {}, {}", id, populationSize);
        if (populationSize.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, populationSize.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!populationSizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PopulationSize> result = populationSizeService.partialUpdate(populationSize);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, populationSize.getId().toString())
        );
    }

    /**
     * {@code GET  /population-sizes} : get all the populationSizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of populationSizes in body.
     */
    @GetMapping("/population-sizes")
    public ResponseEntity<List<PopulationSize>> getAllPopulationSizes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PopulationSizes");
        Page<PopulationSize> page = populationSizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /population-sizes/:id} : get the "id" populationSize.
     *
     * @param id the id of the populationSize to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the populationSize, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/population-sizes/{id}")
    public ResponseEntity<PopulationSize> getPopulationSize(@PathVariable Long id) {
        log.debug("REST request to get PopulationSize : {}", id);
        Optional<PopulationSize> populationSize = populationSizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(populationSize);
    }

    /**
     * {@code DELETE  /population-sizes/:id} : delete the "id" populationSize.
     *
     * @param id the id of the populationSize to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/population-sizes/{id}")
    public ResponseEntity<Void> deletePopulationSize(@PathVariable Long id) {
        log.debug("REST request to delete PopulationSize : {}", id);
        populationSizeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
