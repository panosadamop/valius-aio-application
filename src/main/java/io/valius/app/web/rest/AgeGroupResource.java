package io.valius.app.web.rest;

import io.valius.app.domain.AgeGroup;
import io.valius.app.repository.AgeGroupRepository;
import io.valius.app.service.AgeGroupService;
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
 * REST controller for managing {@link io.valius.app.domain.AgeGroup}.
 */
@RestController
@RequestMapping("/api")
public class AgeGroupResource {

    private final Logger log = LoggerFactory.getLogger(AgeGroupResource.class);

    private static final String ENTITY_NAME = "ageGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgeGroupService ageGroupService;

    private final AgeGroupRepository ageGroupRepository;

    public AgeGroupResource(AgeGroupService ageGroupService, AgeGroupRepository ageGroupRepository) {
        this.ageGroupService = ageGroupService;
        this.ageGroupRepository = ageGroupRepository;
    }

    /**
     * {@code POST  /age-groups} : Create a new ageGroup.
     *
     * @param ageGroup the ageGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ageGroup, or with status {@code 400 (Bad Request)} if the ageGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/age-groups")
    public ResponseEntity<AgeGroup> createAgeGroup(@Valid @RequestBody AgeGroup ageGroup) throws URISyntaxException {
        log.debug("REST request to save AgeGroup : {}", ageGroup);
        if (ageGroup.getId() != null) {
            throw new BadRequestAlertException("A new ageGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgeGroup result = ageGroupService.save(ageGroup);
        return ResponseEntity
            .created(new URI("/api/age-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /age-groups/:id} : Updates an existing ageGroup.
     *
     * @param id the id of the ageGroup to save.
     * @param ageGroup the ageGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ageGroup,
     * or with status {@code 400 (Bad Request)} if the ageGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ageGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/age-groups/{id}")
    public ResponseEntity<AgeGroup> updateAgeGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgeGroup ageGroup
    ) throws URISyntaxException {
        log.debug("REST request to update AgeGroup : {}, {}", id, ageGroup);
        if (ageGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AgeGroup result = ageGroupService.update(ageGroup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /age-groups/:id} : Partial updates given fields of an existing ageGroup, field will ignore if it is null
     *
     * @param id the id of the ageGroup to save.
     * @param ageGroup the ageGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ageGroup,
     * or with status {@code 400 (Bad Request)} if the ageGroup is not valid,
     * or with status {@code 404 (Not Found)} if the ageGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the ageGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/age-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgeGroup> partialUpdateAgeGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgeGroup ageGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update AgeGroup partially : {}, {}", id, ageGroup);
        if (ageGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgeGroup> result = ageGroupService.partialUpdate(ageGroup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /age-groups} : get all the ageGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ageGroups in body.
     */
    @GetMapping("/age-groups")
    public ResponseEntity<List<AgeGroup>> getAllAgeGroups(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AgeGroups");
        Page<AgeGroup> page = ageGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /age-groups/:id} : get the "id" ageGroup.
     *
     * @param id the id of the ageGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ageGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/age-groups/{id}")
    public ResponseEntity<AgeGroup> getAgeGroup(@PathVariable Long id) {
        log.debug("REST request to get AgeGroup : {}", id);
        Optional<AgeGroup> ageGroup = ageGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ageGroup);
    }

    /**
     * {@code DELETE  /age-groups/:id} : delete the "id" ageGroup.
     *
     * @param id the id of the ageGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/age-groups/{id}")
    public ResponseEntity<Void> deleteAgeGroup(@PathVariable Long id) {
        log.debug("REST request to delete AgeGroup : {}", id);
        ageGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
