package io.valius.app.web.rest;

import io.valius.app.domain.OrganisationType;
import io.valius.app.repository.OrganisationTypeRepository;
import io.valius.app.service.OrganisationTypeService;
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
 * REST controller for managing {@link io.valius.app.domain.OrganisationType}.
 */
@RestController
@RequestMapping("/api")
public class OrganisationTypeResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationTypeResource.class);

    private static final String ENTITY_NAME = "organisationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisationTypeService organisationTypeService;

    private final OrganisationTypeRepository organisationTypeRepository;

    public OrganisationTypeResource(
        OrganisationTypeService organisationTypeService,
        OrganisationTypeRepository organisationTypeRepository
    ) {
        this.organisationTypeService = organisationTypeService;
        this.organisationTypeRepository = organisationTypeRepository;
    }

    /**
     * {@code POST  /organisation-types} : Create a new organisationType.
     *
     * @param organisationType the organisationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisationType, or with status {@code 400 (Bad Request)} if the organisationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisation-types")
    public ResponseEntity<OrganisationType> createOrganisationType(@Valid @RequestBody OrganisationType organisationType)
        throws URISyntaxException {
        log.debug("REST request to save OrganisationType : {}", organisationType);
        if (organisationType.getId() != null) {
            throw new BadRequestAlertException("A new organisationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganisationType result = organisationTypeService.save(organisationType);
        return ResponseEntity
            .created(new URI("/api/organisation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisation-types/:id} : Updates an existing organisationType.
     *
     * @param id the id of the organisationType to save.
     * @param organisationType the organisationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisationType,
     * or with status {@code 400 (Bad Request)} if the organisationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisation-types/{id}")
    public ResponseEntity<OrganisationType> updateOrganisationType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganisationType organisationType
    ) throws URISyntaxException {
        log.debug("REST request to update OrganisationType : {}, {}", id, organisationType);
        if (organisationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganisationType result = organisationTypeService.update(organisationType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organisationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organisation-types/:id} : Partial updates given fields of an existing organisationType, field will ignore if it is null
     *
     * @param id the id of the organisationType to save.
     * @param organisationType the organisationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisationType,
     * or with status {@code 400 (Bad Request)} if the organisationType is not valid,
     * or with status {@code 404 (Not Found)} if the organisationType is not found,
     * or with status {@code 500 (Internal Server Error)} if the organisationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organisation-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganisationType> partialUpdateOrganisationType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganisationType organisationType
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganisationType partially : {}, {}", id, organisationType);
        if (organisationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganisationType> result = organisationTypeService.partialUpdate(organisationType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organisationType.getId().toString())
        );
    }

    /**
     * {@code GET  /organisation-types} : get all the organisationTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisationTypes in body.
     */
    @GetMapping("/organisation-types")
    public ResponseEntity<List<OrganisationType>> getAllOrganisationTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OrganisationTypes");
        Page<OrganisationType> page = organisationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organisation-types/:id} : get the "id" organisationType.
     *
     * @param id the id of the organisationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisation-types/{id}")
    public ResponseEntity<OrganisationType> getOrganisationType(@PathVariable Long id) {
        log.debug("REST request to get OrganisationType : {}", id);
        Optional<OrganisationType> organisationType = organisationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organisationType);
    }

    /**
     * {@code DELETE  /organisation-types/:id} : delete the "id" organisationType.
     *
     * @param id the id of the organisationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisation-types/{id}")
    public ResponseEntity<Void> deleteOrganisationType(@PathVariable Long id) {
        log.debug("REST request to delete OrganisationType : {}", id);
        organisationTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
