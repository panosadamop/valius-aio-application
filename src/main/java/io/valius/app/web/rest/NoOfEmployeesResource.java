package io.valius.app.web.rest;

import io.valius.app.domain.NoOfEmployees;
import io.valius.app.repository.NoOfEmployeesRepository;
import io.valius.app.service.NoOfEmployeesService;
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
 * REST controller for managing {@link io.valius.app.domain.NoOfEmployees}.
 */
@RestController
@RequestMapping("/api")
public class NoOfEmployeesResource {

    private final Logger log = LoggerFactory.getLogger(NoOfEmployeesResource.class);

    private static final String ENTITY_NAME = "noOfEmployees";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoOfEmployeesService noOfEmployeesService;

    private final NoOfEmployeesRepository noOfEmployeesRepository;

    public NoOfEmployeesResource(NoOfEmployeesService noOfEmployeesService, NoOfEmployeesRepository noOfEmployeesRepository) {
        this.noOfEmployeesService = noOfEmployeesService;
        this.noOfEmployeesRepository = noOfEmployeesRepository;
    }

    /**
     * {@code POST  /no-of-employees} : Create a new noOfEmployees.
     *
     * @param noOfEmployees the noOfEmployees to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noOfEmployees, or with status {@code 400 (Bad Request)} if the noOfEmployees has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/no-of-employees")
    public ResponseEntity<NoOfEmployees> createNoOfEmployees(@Valid @RequestBody NoOfEmployees noOfEmployees) throws URISyntaxException {
        log.debug("REST request to save NoOfEmployees : {}", noOfEmployees);
        if (noOfEmployees.getId() != null) {
            throw new BadRequestAlertException("A new noOfEmployees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoOfEmployees result = noOfEmployeesService.save(noOfEmployees);
        return ResponseEntity
            .created(new URI("/api/no-of-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /no-of-employees/:id} : Updates an existing noOfEmployees.
     *
     * @param id the id of the noOfEmployees to save.
     * @param noOfEmployees the noOfEmployees to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noOfEmployees,
     * or with status {@code 400 (Bad Request)} if the noOfEmployees is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noOfEmployees couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/no-of-employees/{id}")
    public ResponseEntity<NoOfEmployees> updateNoOfEmployees(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NoOfEmployees noOfEmployees
    ) throws URISyntaxException {
        log.debug("REST request to update NoOfEmployees : {}, {}", id, noOfEmployees);
        if (noOfEmployees.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noOfEmployees.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noOfEmployeesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NoOfEmployees result = noOfEmployeesService.update(noOfEmployees);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noOfEmployees.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /no-of-employees/:id} : Partial updates given fields of an existing noOfEmployees, field will ignore if it is null
     *
     * @param id the id of the noOfEmployees to save.
     * @param noOfEmployees the noOfEmployees to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noOfEmployees,
     * or with status {@code 400 (Bad Request)} if the noOfEmployees is not valid,
     * or with status {@code 404 (Not Found)} if the noOfEmployees is not found,
     * or with status {@code 500 (Internal Server Error)} if the noOfEmployees couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/no-of-employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NoOfEmployees> partialUpdateNoOfEmployees(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NoOfEmployees noOfEmployees
    ) throws URISyntaxException {
        log.debug("REST request to partial update NoOfEmployees partially : {}, {}", id, noOfEmployees);
        if (noOfEmployees.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noOfEmployees.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noOfEmployeesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NoOfEmployees> result = noOfEmployeesService.partialUpdate(noOfEmployees);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noOfEmployees.getId().toString())
        );
    }

    /**
     * {@code GET  /no-of-employees} : get all the noOfEmployees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noOfEmployees in body.
     */
    @GetMapping("/no-of-employees")
    public ResponseEntity<List<NoOfEmployees>> getAllNoOfEmployees(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NoOfEmployees");
        Page<NoOfEmployees> page = noOfEmployeesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /no-of-employees/:id} : get the "id" noOfEmployees.
     *
     * @param id the id of the noOfEmployees to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noOfEmployees, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/no-of-employees/{id}")
    public ResponseEntity<NoOfEmployees> getNoOfEmployees(@PathVariable Long id) {
        log.debug("REST request to get NoOfEmployees : {}", id);
        Optional<NoOfEmployees> noOfEmployees = noOfEmployeesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noOfEmployees);
    }

    /**
     * {@code DELETE  /no-of-employees/:id} : delete the "id" noOfEmployees.
     *
     * @param id the id of the noOfEmployees to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/no-of-employees/{id}")
    public ResponseEntity<Void> deleteNoOfEmployees(@PathVariable Long id) {
        log.debug("REST request to delete NoOfEmployees : {}", id);
        noOfEmployeesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
