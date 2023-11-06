package io.valius.app.web.rest;

import io.valius.app.domain.MarketSegmentationTypeB2c;
import io.valius.app.repository.MarketSegmentationTypeB2cRepository;
import io.valius.app.service.MarketSegmentationTypeB2cService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketSegmentationTypeB2c}.
 */
@RestController
@RequestMapping("/api")
public class MarketSegmentationTypeB2cResource {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2cResource.class);

    private static final String ENTITY_NAME = "marketSegmentationTypeB2c";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketSegmentationTypeB2cService marketSegmentationTypeB2cService;

    private final MarketSegmentationTypeB2cRepository marketSegmentationTypeB2cRepository;

    public MarketSegmentationTypeB2cResource(
        MarketSegmentationTypeB2cService marketSegmentationTypeB2cService,
        MarketSegmentationTypeB2cRepository marketSegmentationTypeB2cRepository
    ) {
        this.marketSegmentationTypeB2cService = marketSegmentationTypeB2cService;
        this.marketSegmentationTypeB2cRepository = marketSegmentationTypeB2cRepository;
    }

    /**
     * {@code POST  /market-segmentation-type-b-2-cs} : Create a new marketSegmentationTypeB2c.
     *
     * @param marketSegmentationTypeB2c the marketSegmentationTypeB2c to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketSegmentationTypeB2c, or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2c has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-segmentation-type-b-2-cs")
    public ResponseEntity<MarketSegmentationTypeB2c> createMarketSegmentationTypeB2c(
        @Valid @RequestBody MarketSegmentationTypeB2c marketSegmentationTypeB2c
    ) throws URISyntaxException {
        log.debug("REST request to save MarketSegmentationTypeB2c : {}", marketSegmentationTypeB2c);
        if (marketSegmentationTypeB2c.getId() != null) {
            throw new BadRequestAlertException("A new marketSegmentationTypeB2c cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketSegmentationTypeB2c result = marketSegmentationTypeB2cService.save(marketSegmentationTypeB2c);
        return ResponseEntity
            .created(new URI("/api/market-segmentation-type-b-2-cs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-segmentation-type-b-2-cs/:id} : Updates an existing marketSegmentationTypeB2c.
     *
     * @param id the id of the marketSegmentationTypeB2c to save.
     * @param marketSegmentationTypeB2c the marketSegmentationTypeB2c to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2c,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2c is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2c couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-segmentation-type-b-2-cs/{id}")
    public ResponseEntity<MarketSegmentationTypeB2c> updateMarketSegmentationTypeB2c(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketSegmentationTypeB2c marketSegmentationTypeB2c
    ) throws URISyntaxException {
        log.debug("REST request to update MarketSegmentationTypeB2c : {}, {}", id, marketSegmentationTypeB2c);
        if (marketSegmentationTypeB2c.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2c.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2cRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketSegmentationTypeB2c result = marketSegmentationTypeB2cService.update(marketSegmentationTypeB2c);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2c.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /market-segmentation-type-b-2-cs/:id} : Partial updates given fields of an existing marketSegmentationTypeB2c, field will ignore if it is null
     *
     * @param id the id of the marketSegmentationTypeB2c to save.
     * @param marketSegmentationTypeB2c the marketSegmentationTypeB2c to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2c,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2c is not valid,
     * or with status {@code 404 (Not Found)} if the marketSegmentationTypeB2c is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2c couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/market-segmentation-type-b-2-cs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketSegmentationTypeB2c> partialUpdateMarketSegmentationTypeB2c(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketSegmentationTypeB2c marketSegmentationTypeB2c
    ) throws URISyntaxException {
        log.debug("REST request to partial update MarketSegmentationTypeB2c partially : {}, {}", id, marketSegmentationTypeB2c);
        if (marketSegmentationTypeB2c.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2c.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2cRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketSegmentationTypeB2c> result = marketSegmentationTypeB2cService.partialUpdate(marketSegmentationTypeB2c);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2c.getId().toString())
        );
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-cs} : get all the marketSegmentationTypeB2cs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketSegmentationTypeB2cs in body.
     */
    @GetMapping("/market-segmentation-type-b-2-cs")
    public ResponseEntity<List<MarketSegmentationTypeB2c>> getAllMarketSegmentationTypeB2cs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketSegmentationTypeB2cs");
        Page<MarketSegmentationTypeB2c> page = marketSegmentationTypeB2cService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-cs/:id} : get the "id" marketSegmentationTypeB2c.
     *
     * @param id the id of the marketSegmentationTypeB2c to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketSegmentationTypeB2c, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-segmentation-type-b-2-cs/{id}")
    public ResponseEntity<MarketSegmentationTypeB2c> getMarketSegmentationTypeB2c(@PathVariable Long id) {
        log.debug("REST request to get MarketSegmentationTypeB2c : {}", id);
        Optional<MarketSegmentationTypeB2c> marketSegmentationTypeB2c = marketSegmentationTypeB2cService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketSegmentationTypeB2c);
    }

    /**
     * {@code DELETE  /market-segmentation-type-b-2-cs/:id} : delete the "id" marketSegmentationTypeB2c.
     *
     * @param id the id of the marketSegmentationTypeB2c to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-segmentation-type-b-2-cs/{id}")
    public ResponseEntity<Void> deleteMarketSegmentationTypeB2c(@PathVariable Long id) {
        log.debug("REST request to delete MarketSegmentationTypeB2c : {}", id);
        marketSegmentationTypeB2cService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
