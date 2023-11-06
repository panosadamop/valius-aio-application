package io.valius.app.web.rest;

import io.valius.app.domain.StatisticalError;
import io.valius.app.repository.StatisticalErrorRepository;
import io.valius.app.service.StatisticalErrorService;
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
 * REST controller for managing {@link io.valius.app.domain.StatisticalError}.
 */
@RestController
@RequestMapping("/api")
public class StatisticalErrorResource {

    private final Logger log = LoggerFactory.getLogger(StatisticalErrorResource.class);

    private static final String ENTITY_NAME = "statisticalError";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticalErrorService statisticalErrorService;

    private final StatisticalErrorRepository statisticalErrorRepository;

    public StatisticalErrorResource(
        StatisticalErrorService statisticalErrorService,
        StatisticalErrorRepository statisticalErrorRepository
    ) {
        this.statisticalErrorService = statisticalErrorService;
        this.statisticalErrorRepository = statisticalErrorRepository;
    }

    /**
     * {@code POST  /statistical-errors} : Create a new statisticalError.
     *
     * @param statisticalError the statisticalError to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statisticalError, or with status {@code 400 (Bad Request)} if the statisticalError has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statistical-errors")
    public ResponseEntity<StatisticalError> createStatisticalError(@Valid @RequestBody StatisticalError statisticalError)
        throws URISyntaxException {
        log.debug("REST request to save StatisticalError : {}", statisticalError);
        if (statisticalError.getId() != null) {
            throw new BadRequestAlertException("A new statisticalError cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatisticalError result = statisticalErrorService.save(statisticalError);
        return ResponseEntity
            .created(new URI("/api/statistical-errors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statistical-errors/:id} : Updates an existing statisticalError.
     *
     * @param id the id of the statisticalError to save.
     * @param statisticalError the statisticalError to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticalError,
     * or with status {@code 400 (Bad Request)} if the statisticalError is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statisticalError couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statistical-errors/{id}")
    public ResponseEntity<StatisticalError> updateStatisticalError(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StatisticalError statisticalError
    ) throws URISyntaxException {
        log.debug("REST request to update StatisticalError : {}, {}", id, statisticalError);
        if (statisticalError.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticalError.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticalErrorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatisticalError result = statisticalErrorService.update(statisticalError);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statisticalError.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /statistical-errors/:id} : Partial updates given fields of an existing statisticalError, field will ignore if it is null
     *
     * @param id the id of the statisticalError to save.
     * @param statisticalError the statisticalError to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticalError,
     * or with status {@code 400 (Bad Request)} if the statisticalError is not valid,
     * or with status {@code 404 (Not Found)} if the statisticalError is not found,
     * or with status {@code 500 (Internal Server Error)} if the statisticalError couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/statistical-errors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatisticalError> partialUpdateStatisticalError(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StatisticalError statisticalError
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatisticalError partially : {}, {}", id, statisticalError);
        if (statisticalError.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticalError.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticalErrorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatisticalError> result = statisticalErrorService.partialUpdate(statisticalError);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statisticalError.getId().toString())
        );
    }

    /**
     * {@code GET  /statistical-errors} : get all the statisticalErrors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statisticalErrors in body.
     */
    @GetMapping("/statistical-errors")
    public ResponseEntity<List<StatisticalError>> getAllStatisticalErrors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of StatisticalErrors");
        Page<StatisticalError> page = statisticalErrorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statistical-errors/:id} : get the "id" statisticalError.
     *
     * @param id the id of the statisticalError to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statisticalError, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statistical-errors/{id}")
    public ResponseEntity<StatisticalError> getStatisticalError(@PathVariable Long id) {
        log.debug("REST request to get StatisticalError : {}", id);
        Optional<StatisticalError> statisticalError = statisticalErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statisticalError);
    }

    /**
     * {@code DELETE  /statistical-errors/:id} : delete the "id" statisticalError.
     *
     * @param id the id of the statisticalError to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statistical-errors/{id}")
    public ResponseEntity<Void> deleteStatisticalError(@PathVariable Long id) {
        log.debug("REST request to delete StatisticalError : {}", id);
        statisticalErrorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
