package io.valius.app.web.rest;

import io.valius.app.domain.BuyingCriteriaWeighting;
import io.valius.app.repository.BuyingCriteriaWeightingRepository;
import io.valius.app.service.BuyingCriteriaWeightingService;
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
 * REST controller for managing {@link io.valius.app.domain.BuyingCriteriaWeighting}.
 */
@RestController
@RequestMapping("/api")
public class BuyingCriteriaWeightingResource {

    private final Logger log = LoggerFactory.getLogger(BuyingCriteriaWeightingResource.class);

    private static final String ENTITY_NAME = "buyingCriteriaWeighting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyingCriteriaWeightingService buyingCriteriaWeightingService;

    private final BuyingCriteriaWeightingRepository buyingCriteriaWeightingRepository;

    public BuyingCriteriaWeightingResource(
        BuyingCriteriaWeightingService buyingCriteriaWeightingService,
        BuyingCriteriaWeightingRepository buyingCriteriaWeightingRepository
    ) {
        this.buyingCriteriaWeightingService = buyingCriteriaWeightingService;
        this.buyingCriteriaWeightingRepository = buyingCriteriaWeightingRepository;
    }

    /**
     * {@code POST  /buying-criteria-weightings} : Create a new buyingCriteriaWeighting.
     *
     * @param buyingCriteriaWeighting the buyingCriteriaWeighting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyingCriteriaWeighting, or with status {@code 400 (Bad Request)} if the buyingCriteriaWeighting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buying-criteria-weightings")
    public ResponseEntity<BuyingCriteriaWeighting> createBuyingCriteriaWeighting(
        @Valid @RequestBody BuyingCriteriaWeighting buyingCriteriaWeighting
    ) throws URISyntaxException {
        log.debug("REST request to save BuyingCriteriaWeighting : {}", buyingCriteriaWeighting);
        if (buyingCriteriaWeighting.getId() != null) {
            throw new BadRequestAlertException("A new buyingCriteriaWeighting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyingCriteriaWeighting result = buyingCriteriaWeightingService.save(buyingCriteriaWeighting);
        return ResponseEntity
            .created(new URI("/api/buying-criteria-weightings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buying-criteria-weightings/:id} : Updates an existing buyingCriteriaWeighting.
     *
     * @param id the id of the buyingCriteriaWeighting to save.
     * @param buyingCriteriaWeighting the buyingCriteriaWeighting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyingCriteriaWeighting,
     * or with status {@code 400 (Bad Request)} if the buyingCriteriaWeighting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyingCriteriaWeighting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buying-criteria-weightings/{id}")
    public ResponseEntity<BuyingCriteriaWeighting> updateBuyingCriteriaWeighting(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BuyingCriteriaWeighting buyingCriteriaWeighting
    ) throws URISyntaxException {
        log.debug("REST request to update BuyingCriteriaWeighting : {}, {}", id, buyingCriteriaWeighting);
        if (buyingCriteriaWeighting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyingCriteriaWeighting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyingCriteriaWeightingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuyingCriteriaWeighting result = buyingCriteriaWeightingService.update(buyingCriteriaWeighting);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyingCriteriaWeighting.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buying-criteria-weightings/:id} : Partial updates given fields of an existing buyingCriteriaWeighting, field will ignore if it is null
     *
     * @param id the id of the buyingCriteriaWeighting to save.
     * @param buyingCriteriaWeighting the buyingCriteriaWeighting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyingCriteriaWeighting,
     * or with status {@code 400 (Bad Request)} if the buyingCriteriaWeighting is not valid,
     * or with status {@code 404 (Not Found)} if the buyingCriteriaWeighting is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyingCriteriaWeighting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buying-criteria-weightings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuyingCriteriaWeighting> partialUpdateBuyingCriteriaWeighting(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BuyingCriteriaWeighting buyingCriteriaWeighting
    ) throws URISyntaxException {
        log.debug("REST request to partial update BuyingCriteriaWeighting partially : {}, {}", id, buyingCriteriaWeighting);
        if (buyingCriteriaWeighting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyingCriteriaWeighting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyingCriteriaWeightingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyingCriteriaWeighting> result = buyingCriteriaWeightingService.partialUpdate(buyingCriteriaWeighting);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyingCriteriaWeighting.getId().toString())
        );
    }

    /**
     * {@code GET  /buying-criteria-weightings} : get all the buyingCriteriaWeightings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyingCriteriaWeightings in body.
     */
    @GetMapping("/buying-criteria-weightings")
    public ResponseEntity<List<BuyingCriteriaWeighting>> getAllBuyingCriteriaWeightings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BuyingCriteriaWeightings");
        Page<BuyingCriteriaWeighting> page = buyingCriteriaWeightingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buying-criteria-weightings/:id} : get the "id" buyingCriteriaWeighting.
     *
     * @param id the id of the buyingCriteriaWeighting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyingCriteriaWeighting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buying-criteria-weightings/{id}")
    public ResponseEntity<BuyingCriteriaWeighting> getBuyingCriteriaWeighting(@PathVariable Long id) {
        log.debug("REST request to get BuyingCriteriaWeighting : {}", id);
        Optional<BuyingCriteriaWeighting> buyingCriteriaWeighting = buyingCriteriaWeightingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyingCriteriaWeighting);
    }

    /**
     * {@code DELETE  /buying-criteria-weightings/:id} : delete the "id" buyingCriteriaWeighting.
     *
     * @param id the id of the buyingCriteriaWeighting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buying-criteria-weightings/{id}")
    public ResponseEntity<Void> deleteBuyingCriteriaWeighting(@PathVariable Long id) {
        log.debug("REST request to delete BuyingCriteriaWeighting : {}", id);
        buyingCriteriaWeightingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
