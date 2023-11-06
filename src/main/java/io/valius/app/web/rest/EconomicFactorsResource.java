package io.valius.app.web.rest;

import io.valius.app.domain.EconomicFactors;
import io.valius.app.repository.EconomicFactorsRepository;
import io.valius.app.service.EconomicFactorsService;
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
 * REST controller for managing {@link io.valius.app.domain.EconomicFactors}.
 */
@RestController
@RequestMapping("/api")
public class EconomicFactorsResource {

    private final Logger log = LoggerFactory.getLogger(EconomicFactorsResource.class);

    private static final String ENTITY_NAME = "economicFactors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EconomicFactorsService economicFactorsService;

    private final EconomicFactorsRepository economicFactorsRepository;

    public EconomicFactorsResource(EconomicFactorsService economicFactorsService, EconomicFactorsRepository economicFactorsRepository) {
        this.economicFactorsService = economicFactorsService;
        this.economicFactorsRepository = economicFactorsRepository;
    }

    /**
     * {@code POST  /economic-factors} : Create a new economicFactors.
     *
     * @param economicFactors the economicFactors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new economicFactors, or with status {@code 400 (Bad Request)} if the economicFactors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/economic-factors")
    public ResponseEntity<EconomicFactors> createEconomicFactors(@Valid @RequestBody EconomicFactors economicFactors)
        throws URISyntaxException {
        log.debug("REST request to save EconomicFactors : {}", economicFactors);
        if (economicFactors.getId() != null) {
            throw new BadRequestAlertException("A new economicFactors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EconomicFactors result = economicFactorsService.save(economicFactors);
        return ResponseEntity
            .created(new URI("/api/economic-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /economic-factors/:id} : Updates an existing economicFactors.
     *
     * @param id the id of the economicFactors to save.
     * @param economicFactors the economicFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated economicFactors,
     * or with status {@code 400 (Bad Request)} if the economicFactors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the economicFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/economic-factors/{id}")
    public ResponseEntity<EconomicFactors> updateEconomicFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EconomicFactors economicFactors
    ) throws URISyntaxException {
        log.debug("REST request to update EconomicFactors : {}, {}", id, economicFactors);
        if (economicFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, economicFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!economicFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EconomicFactors result = economicFactorsService.update(economicFactors);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, economicFactors.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /economic-factors/:id} : Partial updates given fields of an existing economicFactors, field will ignore if it is null
     *
     * @param id the id of the economicFactors to save.
     * @param economicFactors the economicFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated economicFactors,
     * or with status {@code 400 (Bad Request)} if the economicFactors is not valid,
     * or with status {@code 404 (Not Found)} if the economicFactors is not found,
     * or with status {@code 500 (Internal Server Error)} if the economicFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/economic-factors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EconomicFactors> partialUpdateEconomicFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EconomicFactors economicFactors
    ) throws URISyntaxException {
        log.debug("REST request to partial update EconomicFactors partially : {}, {}", id, economicFactors);
        if (economicFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, economicFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!economicFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EconomicFactors> result = economicFactorsService.partialUpdate(economicFactors);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, economicFactors.getId().toString())
        );
    }

    /**
     * {@code GET  /economic-factors} : get all the economicFactors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of economicFactors in body.
     */
    @GetMapping("/economic-factors")
    public ResponseEntity<List<EconomicFactors>> getAllEconomicFactors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EconomicFactors");
        Page<EconomicFactors> page = economicFactorsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /economic-factors/:id} : get the "id" economicFactors.
     *
     * @param id the id of the economicFactors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the economicFactors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/economic-factors/{id}")
    public ResponseEntity<EconomicFactors> getEconomicFactors(@PathVariable Long id) {
        log.debug("REST request to get EconomicFactors : {}", id);
        Optional<EconomicFactors> economicFactors = economicFactorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(economicFactors);
    }

    /**
     * {@code DELETE  /economic-factors/:id} : delete the "id" economicFactors.
     *
     * @param id the id of the economicFactors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/economic-factors/{id}")
    public ResponseEntity<Void> deleteEconomicFactors(@PathVariable Long id) {
        log.debug("REST request to delete EconomicFactors : {}", id);
        economicFactorsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
