package io.valius.app.web.rest;

import io.valius.app.domain.BuyingCriteria;
import io.valius.app.repository.BuyingCriteriaRepository;
import io.valius.app.service.BuyingCriteriaService;
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
 * REST controller for managing {@link io.valius.app.domain.BuyingCriteria}.
 */
@RestController
@RequestMapping("/api")
public class BuyingCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(BuyingCriteriaResource.class);

    private static final String ENTITY_NAME = "buyingCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyingCriteriaService buyingCriteriaService;

    private final BuyingCriteriaRepository buyingCriteriaRepository;

    public BuyingCriteriaResource(BuyingCriteriaService buyingCriteriaService, BuyingCriteriaRepository buyingCriteriaRepository) {
        this.buyingCriteriaService = buyingCriteriaService;
        this.buyingCriteriaRepository = buyingCriteriaRepository;
    }

    /**
     * {@code POST  /buying-criteria} : Create a new buyingCriteria.
     *
     * @param buyingCriteria the buyingCriteria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyingCriteria, or with status {@code 400 (Bad Request)} if the buyingCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buying-criteria")
    public ResponseEntity<BuyingCriteria> createBuyingCriteria(@Valid @RequestBody BuyingCriteria buyingCriteria)
        throws URISyntaxException {
        log.debug("REST request to save BuyingCriteria : {}", buyingCriteria);
        if (buyingCriteria.getId() != null) {
            throw new BadRequestAlertException("A new buyingCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyingCriteria result = buyingCriteriaService.save(buyingCriteria);
        return ResponseEntity
            .created(new URI("/api/buying-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buying-criteria/:id} : Updates an existing buyingCriteria.
     *
     * @param id the id of the buyingCriteria to save.
     * @param buyingCriteria the buyingCriteria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyingCriteria,
     * or with status {@code 400 (Bad Request)} if the buyingCriteria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyingCriteria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buying-criteria/{id}")
    public ResponseEntity<BuyingCriteria> updateBuyingCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BuyingCriteria buyingCriteria
    ) throws URISyntaxException {
        log.debug("REST request to update BuyingCriteria : {}, {}", id, buyingCriteria);
        if (buyingCriteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyingCriteria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyingCriteriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuyingCriteria result = buyingCriteriaService.update(buyingCriteria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyingCriteria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buying-criteria/:id} : Partial updates given fields of an existing buyingCriteria, field will ignore if it is null
     *
     * @param id the id of the buyingCriteria to save.
     * @param buyingCriteria the buyingCriteria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyingCriteria,
     * or with status {@code 400 (Bad Request)} if the buyingCriteria is not valid,
     * or with status {@code 404 (Not Found)} if the buyingCriteria is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyingCriteria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buying-criteria/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuyingCriteria> partialUpdateBuyingCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BuyingCriteria buyingCriteria
    ) throws URISyntaxException {
        log.debug("REST request to partial update BuyingCriteria partially : {}, {}", id, buyingCriteria);
        if (buyingCriteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyingCriteria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyingCriteriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyingCriteria> result = buyingCriteriaService.partialUpdate(buyingCriteria);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyingCriteria.getId().toString())
        );
    }

    /**
     * {@code GET  /buying-criteria} : get all the buyingCriteria.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyingCriteria in body.
     */
    @GetMapping("/buying-criteria")
    public ResponseEntity<List<BuyingCriteria>> getAllBuyingCriteria(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BuyingCriteria");
        Page<BuyingCriteria> page = buyingCriteriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buying-criteria/:id} : get the "id" buyingCriteria.
     *
     * @param id the id of the buyingCriteria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyingCriteria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buying-criteria/{id}")
    public ResponseEntity<BuyingCriteria> getBuyingCriteria(@PathVariable Long id) {
        log.debug("REST request to get BuyingCriteria : {}", id);
        Optional<BuyingCriteria> buyingCriteria = buyingCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyingCriteria);
    }

    /**
     * {@code DELETE  /buying-criteria/:id} : delete the "id" buyingCriteria.
     *
     * @param id the id of the buyingCriteria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buying-criteria/{id}")
    public ResponseEntity<Void> deleteBuyingCriteria(@PathVariable Long id) {
        log.debug("REST request to delete BuyingCriteria : {}", id);
        buyingCriteriaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
