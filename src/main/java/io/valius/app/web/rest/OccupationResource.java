package io.valius.app.web.rest;

import io.valius.app.domain.Occupation;
import io.valius.app.repository.OccupationRepository;
import io.valius.app.service.OccupationService;
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
 * REST controller for managing {@link io.valius.app.domain.Occupation}.
 */
@RestController
@RequestMapping("/api")
public class OccupationResource {

    private final Logger log = LoggerFactory.getLogger(OccupationResource.class);

    private static final String ENTITY_NAME = "occupation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OccupationService occupationService;

    private final OccupationRepository occupationRepository;

    public OccupationResource(OccupationService occupationService, OccupationRepository occupationRepository) {
        this.occupationService = occupationService;
        this.occupationRepository = occupationRepository;
    }

    /**
     * {@code POST  /occupations} : Create a new occupation.
     *
     * @param occupation the occupation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new occupation, or with status {@code 400 (Bad Request)} if the occupation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/occupations")
    public ResponseEntity<Occupation> createOccupation(@Valid @RequestBody Occupation occupation) throws URISyntaxException {
        log.debug("REST request to save Occupation : {}", occupation);
        if (occupation.getId() != null) {
            throw new BadRequestAlertException("A new occupation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Occupation result = occupationService.save(occupation);
        return ResponseEntity
            .created(new URI("/api/occupations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /occupations/:id} : Updates an existing occupation.
     *
     * @param id the id of the occupation to save.
     * @param occupation the occupation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occupation,
     * or with status {@code 400 (Bad Request)} if the occupation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the occupation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/occupations/{id}")
    public ResponseEntity<Occupation> updateOccupation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Occupation occupation
    ) throws URISyntaxException {
        log.debug("REST request to update Occupation : {}, {}", id, occupation);
        if (occupation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, occupation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!occupationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Occupation result = occupationService.update(occupation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, occupation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /occupations/:id} : Partial updates given fields of an existing occupation, field will ignore if it is null
     *
     * @param id the id of the occupation to save.
     * @param occupation the occupation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occupation,
     * or with status {@code 400 (Bad Request)} if the occupation is not valid,
     * or with status {@code 404 (Not Found)} if the occupation is not found,
     * or with status {@code 500 (Internal Server Error)} if the occupation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/occupations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Occupation> partialUpdateOccupation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Occupation occupation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Occupation partially : {}, {}", id, occupation);
        if (occupation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, occupation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!occupationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Occupation> result = occupationService.partialUpdate(occupation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, occupation.getId().toString())
        );
    }

    /**
     * {@code GET  /occupations} : get all the occupations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of occupations in body.
     */
    @GetMapping("/occupations")
    public ResponseEntity<List<Occupation>> getAllOccupations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Occupations");
        Page<Occupation> page = occupationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /occupations/:id} : get the "id" occupation.
     *
     * @param id the id of the occupation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the occupation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/occupations/{id}")
    public ResponseEntity<Occupation> getOccupation(@PathVariable Long id) {
        log.debug("REST request to get Occupation : {}", id);
        Optional<Occupation> occupation = occupationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(occupation);
    }

    /**
     * {@code DELETE  /occupations/:id} : delete the "id" occupation.
     *
     * @param id the id of the occupation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/occupations/{id}")
    public ResponseEntity<Void> deleteOccupation(@PathVariable Long id) {
        log.debug("REST request to delete Occupation : {}", id);
        occupationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
