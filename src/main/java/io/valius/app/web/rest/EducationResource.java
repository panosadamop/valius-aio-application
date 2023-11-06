package io.valius.app.web.rest;

import io.valius.app.domain.Education;
import io.valius.app.repository.EducationRepository;
import io.valius.app.service.EducationService;
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
 * REST controller for managing {@link io.valius.app.domain.Education}.
 */
@RestController
@RequestMapping("/api")
public class EducationResource {

    private final Logger log = LoggerFactory.getLogger(EducationResource.class);

    private static final String ENTITY_NAME = "education";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationService educationService;

    private final EducationRepository educationRepository;

    public EducationResource(EducationService educationService, EducationRepository educationRepository) {
        this.educationService = educationService;
        this.educationRepository = educationRepository;
    }

    /**
     * {@code POST  /educations} : Create a new education.
     *
     * @param education the education to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new education, or with status {@code 400 (Bad Request)} if the education has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educations")
    public ResponseEntity<Education> createEducation(@Valid @RequestBody Education education) throws URISyntaxException {
        log.debug("REST request to save Education : {}", education);
        if (education.getId() != null) {
            throw new BadRequestAlertException("A new education cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Education result = educationService.save(education);
        return ResponseEntity
            .created(new URI("/api/educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /educations/:id} : Updates an existing education.
     *
     * @param id the id of the education to save.
     * @param education the education to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated education,
     * or with status {@code 400 (Bad Request)} if the education is not valid,
     * or with status {@code 500 (Internal Server Error)} if the education couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educations/{id}")
    public ResponseEntity<Education> updateEducation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Education education
    ) throws URISyntaxException {
        log.debug("REST request to update Education : {}, {}", id, education);
        if (education.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, education.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Education result = educationService.update(education);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, education.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /educations/:id} : Partial updates given fields of an existing education, field will ignore if it is null
     *
     * @param id the id of the education to save.
     * @param education the education to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated education,
     * or with status {@code 400 (Bad Request)} if the education is not valid,
     * or with status {@code 404 (Not Found)} if the education is not found,
     * or with status {@code 500 (Internal Server Error)} if the education couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/educations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Education> partialUpdateEducation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Education education
    ) throws URISyntaxException {
        log.debug("REST request to partial update Education partially : {}, {}", id, education);
        if (education.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, education.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Education> result = educationService.partialUpdate(education);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, education.getId().toString())
        );
    }

    /**
     * {@code GET  /educations} : get all the educations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educations in body.
     */
    @GetMapping("/educations")
    public ResponseEntity<List<Education>> getAllEducations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Educations");
        Page<Education> page = educationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /educations/:id} : get the "id" education.
     *
     * @param id the id of the education to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the education, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educations/{id}")
    public ResponseEntity<Education> getEducation(@PathVariable Long id) {
        log.debug("REST request to get Education : {}", id);
        Optional<Education> education = educationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(education);
    }

    /**
     * {@code DELETE  /educations/:id} : delete the "id" education.
     *
     * @param id the id of the education to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educations/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.debug("REST request to delete Education : {}", id);
        educationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
