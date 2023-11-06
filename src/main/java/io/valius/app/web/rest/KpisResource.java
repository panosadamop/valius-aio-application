package io.valius.app.web.rest;

import io.valius.app.domain.Kpis;
import io.valius.app.repository.KpisRepository;
import io.valius.app.service.KpisService;
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
 * REST controller for managing {@link io.valius.app.domain.Kpis}.
 */
@RestController
@RequestMapping("/api")
public class KpisResource {

    private final Logger log = LoggerFactory.getLogger(KpisResource.class);

    private static final String ENTITY_NAME = "kpis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpisService kpisService;

    private final KpisRepository kpisRepository;

    public KpisResource(KpisService kpisService, KpisRepository kpisRepository) {
        this.kpisService = kpisService;
        this.kpisRepository = kpisRepository;
    }

    /**
     * {@code POST  /kpis} : Create a new kpis.
     *
     * @param kpis the kpis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kpis, or with status {@code 400 (Bad Request)} if the kpis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kpis")
    public ResponseEntity<Kpis> createKpis(@Valid @RequestBody Kpis kpis) throws URISyntaxException {
        log.debug("REST request to save Kpis : {}", kpis);
        if (kpis.getId() != null) {
            throw new BadRequestAlertException("A new kpis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kpis result = kpisService.save(kpis);
        return ResponseEntity
            .created(new URI("/api/kpis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kpis/:id} : Updates an existing kpis.
     *
     * @param id the id of the kpis to save.
     * @param kpis the kpis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpis,
     * or with status {@code 400 (Bad Request)} if the kpis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kpis/{id}")
    public ResponseEntity<Kpis> updateKpis(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Kpis kpis)
        throws URISyntaxException {
        log.debug("REST request to update Kpis : {}, {}", id, kpis);
        if (kpis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Kpis result = kpisService.update(kpis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kpis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kpis/:id} : Partial updates given fields of an existing kpis, field will ignore if it is null
     *
     * @param id the id of the kpis to save.
     * @param kpis the kpis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpis,
     * or with status {@code 400 (Bad Request)} if the kpis is not valid,
     * or with status {@code 404 (Not Found)} if the kpis is not found,
     * or with status {@code 500 (Internal Server Error)} if the kpis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kpis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kpis> partialUpdateKpis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kpis kpis
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kpis partially : {}, {}", id, kpis);
        if (kpis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kpis> result = kpisService.partialUpdate(kpis);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kpis.getId().toString())
        );
    }

    /**
     * {@code GET  /kpis} : get all the kpis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpis in body.
     */
    @GetMapping("/kpis")
    public ResponseEntity<List<Kpis>> getAllKpis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Kpis");
        Page<Kpis> page = kpisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kpis/:id} : get the "id" kpis.
     *
     * @param id the id of the kpis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpis/{id}")
    public ResponseEntity<Kpis> getKpis(@PathVariable Long id) {
        log.debug("REST request to get Kpis : {}", id);
        Optional<Kpis> kpis = kpisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kpis);
    }

    /**
     * {@code DELETE  /kpis/:id} : delete the "id" kpis.
     *
     * @param id the id of the kpis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kpis/{id}")
    public ResponseEntity<Void> deleteKpis(@PathVariable Long id) {
        log.debug("REST request to delete Kpis : {}", id);
        kpisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
