package io.valius.app.web.rest;

import io.valius.app.domain.RequiredSampleSize;
import io.valius.app.repository.RequiredSampleSizeRepository;
import io.valius.app.service.RequiredSampleSizeService;
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
 * REST controller for managing {@link io.valius.app.domain.RequiredSampleSize}.
 */
@RestController
@RequestMapping("/api")
public class RequiredSampleSizeResource {

    private final Logger log = LoggerFactory.getLogger(RequiredSampleSizeResource.class);

    private static final String ENTITY_NAME = "requiredSampleSize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequiredSampleSizeService requiredSampleSizeService;

    private final RequiredSampleSizeRepository requiredSampleSizeRepository;

    public RequiredSampleSizeResource(
        RequiredSampleSizeService requiredSampleSizeService,
        RequiredSampleSizeRepository requiredSampleSizeRepository
    ) {
        this.requiredSampleSizeService = requiredSampleSizeService;
        this.requiredSampleSizeRepository = requiredSampleSizeRepository;
    }

    /**
     * {@code POST  /required-sample-sizes} : Create a new requiredSampleSize.
     *
     * @param requiredSampleSize the requiredSampleSize to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requiredSampleSize, or with status {@code 400 (Bad Request)} if the requiredSampleSize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/required-sample-sizes")
    public ResponseEntity<RequiredSampleSize> createRequiredSampleSize(@Valid @RequestBody RequiredSampleSize requiredSampleSize)
        throws URISyntaxException {
        log.debug("REST request to save RequiredSampleSize : {}", requiredSampleSize);
        if (requiredSampleSize.getId() != null) {
            throw new BadRequestAlertException("A new requiredSampleSize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequiredSampleSize result = requiredSampleSizeService.save(requiredSampleSize);
        return ResponseEntity
            .created(new URI("/api/required-sample-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /required-sample-sizes/:id} : Updates an existing requiredSampleSize.
     *
     * @param id the id of the requiredSampleSize to save.
     * @param requiredSampleSize the requiredSampleSize to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requiredSampleSize,
     * or with status {@code 400 (Bad Request)} if the requiredSampleSize is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requiredSampleSize couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/required-sample-sizes/{id}")
    public ResponseEntity<RequiredSampleSize> updateRequiredSampleSize(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequiredSampleSize requiredSampleSize
    ) throws URISyntaxException {
        log.debug("REST request to update RequiredSampleSize : {}, {}", id, requiredSampleSize);
        if (requiredSampleSize.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requiredSampleSize.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requiredSampleSizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequiredSampleSize result = requiredSampleSizeService.update(requiredSampleSize);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requiredSampleSize.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /required-sample-sizes/:id} : Partial updates given fields of an existing requiredSampleSize, field will ignore if it is null
     *
     * @param id the id of the requiredSampleSize to save.
     * @param requiredSampleSize the requiredSampleSize to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requiredSampleSize,
     * or with status {@code 400 (Bad Request)} if the requiredSampleSize is not valid,
     * or with status {@code 404 (Not Found)} if the requiredSampleSize is not found,
     * or with status {@code 500 (Internal Server Error)} if the requiredSampleSize couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/required-sample-sizes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequiredSampleSize> partialUpdateRequiredSampleSize(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequiredSampleSize requiredSampleSize
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequiredSampleSize partially : {}, {}", id, requiredSampleSize);
        if (requiredSampleSize.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requiredSampleSize.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requiredSampleSizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequiredSampleSize> result = requiredSampleSizeService.partialUpdate(requiredSampleSize);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requiredSampleSize.getId().toString())
        );
    }

    /**
     * {@code GET  /required-sample-sizes} : get all the requiredSampleSizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requiredSampleSizes in body.
     */
    @GetMapping("/required-sample-sizes")
    public ResponseEntity<List<RequiredSampleSize>> getAllRequiredSampleSizes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RequiredSampleSizes");
        Page<RequiredSampleSize> page = requiredSampleSizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /required-sample-sizes/:id} : get the "id" requiredSampleSize.
     *
     * @param id the id of the requiredSampleSize to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requiredSampleSize, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/required-sample-sizes/{id}")
    public ResponseEntity<RequiredSampleSize> getRequiredSampleSize(@PathVariable Long id) {
        log.debug("REST request to get RequiredSampleSize : {}", id);
        Optional<RequiredSampleSize> requiredSampleSize = requiredSampleSizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requiredSampleSize);
    }

    /**
     * {@code DELETE  /required-sample-sizes/:id} : delete the "id" requiredSampleSize.
     *
     * @param id the id of the requiredSampleSize to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/required-sample-sizes/{id}")
    public ResponseEntity<Void> deleteRequiredSampleSize(@PathVariable Long id) {
        log.debug("REST request to delete RequiredSampleSize : {}", id);
        requiredSampleSizeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
