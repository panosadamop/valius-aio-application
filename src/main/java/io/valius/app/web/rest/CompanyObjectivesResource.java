package io.valius.app.web.rest;

import io.valius.app.domain.CompanyObjectives;
import io.valius.app.repository.CompanyObjectivesRepository;
import io.valius.app.service.CompanyObjectivesService;
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
 * REST controller for managing {@link io.valius.app.domain.CompanyObjectives}.
 */
@RestController
@RequestMapping("/api")
public class CompanyObjectivesResource {

    private final Logger log = LoggerFactory.getLogger(CompanyObjectivesResource.class);

    private static final String ENTITY_NAME = "companyObjectives";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyObjectivesService companyObjectivesService;

    private final CompanyObjectivesRepository companyObjectivesRepository;

    public CompanyObjectivesResource(
        CompanyObjectivesService companyObjectivesService,
        CompanyObjectivesRepository companyObjectivesRepository
    ) {
        this.companyObjectivesService = companyObjectivesService;
        this.companyObjectivesRepository = companyObjectivesRepository;
    }

    /**
     * {@code POST  /company-objectives} : Create a new companyObjectives.
     *
     * @param companyObjectives the companyObjectives to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyObjectives, or with status {@code 400 (Bad Request)} if the companyObjectives has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-objectives")
    public ResponseEntity<CompanyObjectives> createCompanyObjectives(@Valid @RequestBody CompanyObjectives companyObjectives)
        throws URISyntaxException {
        log.debug("REST request to save CompanyObjectives : {}", companyObjectives);
        if (companyObjectives.getId() != null) {
            throw new BadRequestAlertException("A new companyObjectives cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyObjectives result = companyObjectivesService.save(companyObjectives);
        return ResponseEntity
            .created(new URI("/api/company-objectives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-objectives/:id} : Updates an existing companyObjectives.
     *
     * @param id the id of the companyObjectives to save.
     * @param companyObjectives the companyObjectives to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyObjectives,
     * or with status {@code 400 (Bad Request)} if the companyObjectives is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyObjectives couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-objectives/{id}")
    public ResponseEntity<CompanyObjectives> updateCompanyObjectives(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompanyObjectives companyObjectives
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyObjectives : {}, {}", id, companyObjectives);
        if (companyObjectives.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyObjectives.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyObjectivesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyObjectives result = companyObjectivesService.update(companyObjectives);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyObjectives.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-objectives/:id} : Partial updates given fields of an existing companyObjectives, field will ignore if it is null
     *
     * @param id the id of the companyObjectives to save.
     * @param companyObjectives the companyObjectives to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyObjectives,
     * or with status {@code 400 (Bad Request)} if the companyObjectives is not valid,
     * or with status {@code 404 (Not Found)} if the companyObjectives is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyObjectives couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-objectives/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyObjectives> partialUpdateCompanyObjectives(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompanyObjectives companyObjectives
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyObjectives partially : {}, {}", id, companyObjectives);
        if (companyObjectives.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyObjectives.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyObjectivesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyObjectives> result = companyObjectivesService.partialUpdate(companyObjectives);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyObjectives.getId().toString())
        );
    }

    /**
     * {@code GET  /company-objectives} : get all the companyObjectives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyObjectives in body.
     */
    @GetMapping("/company-objectives")
    public ResponseEntity<List<CompanyObjectives>> getAllCompanyObjectives(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompanyObjectives");
        Page<CompanyObjectives> page = companyObjectivesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-objectives/:id} : get the "id" companyObjectives.
     *
     * @param id the id of the companyObjectives to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyObjectives, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-objectives/{id}")
    public ResponseEntity<CompanyObjectives> getCompanyObjectives(@PathVariable Long id) {
        log.debug("REST request to get CompanyObjectives : {}", id);
        Optional<CompanyObjectives> companyObjectives = companyObjectivesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyObjectives);
    }

    /**
     * {@code DELETE  /company-objectives/:id} : delete the "id" companyObjectives.
     *
     * @param id the id of the companyObjectives to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-objectives/{id}")
    public ResponseEntity<Void> deleteCompanyObjectives(@PathVariable Long id) {
        log.debug("REST request to delete CompanyObjectives : {}", id);
        companyObjectivesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
