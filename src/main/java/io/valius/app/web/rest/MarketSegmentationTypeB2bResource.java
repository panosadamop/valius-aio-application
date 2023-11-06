package io.valius.app.web.rest;

import io.valius.app.domain.MarketSegmentationTypeB2b;
import io.valius.app.repository.MarketSegmentationTypeB2bRepository;
import io.valius.app.service.MarketSegmentationTypeB2bService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketSegmentationTypeB2b}.
 */
@RestController
@RequestMapping("/api")
public class MarketSegmentationTypeB2bResource {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2bResource.class);

    private static final String ENTITY_NAME = "marketSegmentationTypeB2b";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketSegmentationTypeB2bService marketSegmentationTypeB2bService;

    private final MarketSegmentationTypeB2bRepository marketSegmentationTypeB2bRepository;

    public MarketSegmentationTypeB2bResource(
        MarketSegmentationTypeB2bService marketSegmentationTypeB2bService,
        MarketSegmentationTypeB2bRepository marketSegmentationTypeB2bRepository
    ) {
        this.marketSegmentationTypeB2bService = marketSegmentationTypeB2bService;
        this.marketSegmentationTypeB2bRepository = marketSegmentationTypeB2bRepository;
    }

    /**
     * {@code POST  /market-segmentation-type-b-2-bs} : Create a new marketSegmentationTypeB2b.
     *
     * @param marketSegmentationTypeB2b the marketSegmentationTypeB2b to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketSegmentationTypeB2b, or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2b has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-segmentation-type-b-2-bs")
    public ResponseEntity<MarketSegmentationTypeB2b> createMarketSegmentationTypeB2b(
        @Valid @RequestBody MarketSegmentationTypeB2b marketSegmentationTypeB2b
    ) throws URISyntaxException {
        log.debug("REST request to save MarketSegmentationTypeB2b : {}", marketSegmentationTypeB2b);
        if (marketSegmentationTypeB2b.getId() != null) {
            throw new BadRequestAlertException("A new marketSegmentationTypeB2b cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketSegmentationTypeB2b result = marketSegmentationTypeB2bService.save(marketSegmentationTypeB2b);
        return ResponseEntity
            .created(new URI("/api/market-segmentation-type-b-2-bs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-segmentation-type-b-2-bs/:id} : Updates an existing marketSegmentationTypeB2b.
     *
     * @param id the id of the marketSegmentationTypeB2b to save.
     * @param marketSegmentationTypeB2b the marketSegmentationTypeB2b to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2b,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2b is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2b couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-segmentation-type-b-2-bs/{id}")
    public ResponseEntity<MarketSegmentationTypeB2b> updateMarketSegmentationTypeB2b(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketSegmentationTypeB2b marketSegmentationTypeB2b
    ) throws URISyntaxException {
        log.debug("REST request to update MarketSegmentationTypeB2b : {}, {}", id, marketSegmentationTypeB2b);
        if (marketSegmentationTypeB2b.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2b.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2bRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketSegmentationTypeB2b result = marketSegmentationTypeB2bService.update(marketSegmentationTypeB2b);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2b.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /market-segmentation-type-b-2-bs/:id} : Partial updates given fields of an existing marketSegmentationTypeB2b, field will ignore if it is null
     *
     * @param id the id of the marketSegmentationTypeB2b to save.
     * @param marketSegmentationTypeB2b the marketSegmentationTypeB2b to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2b,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2b is not valid,
     * or with status {@code 404 (Not Found)} if the marketSegmentationTypeB2b is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2b couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/market-segmentation-type-b-2-bs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketSegmentationTypeB2b> partialUpdateMarketSegmentationTypeB2b(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketSegmentationTypeB2b marketSegmentationTypeB2b
    ) throws URISyntaxException {
        log.debug("REST request to partial update MarketSegmentationTypeB2b partially : {}, {}", id, marketSegmentationTypeB2b);
        if (marketSegmentationTypeB2b.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2b.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2bRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketSegmentationTypeB2b> result = marketSegmentationTypeB2bService.partialUpdate(marketSegmentationTypeB2b);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2b.getId().toString())
        );
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-bs} : get all the marketSegmentationTypeB2bs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketSegmentationTypeB2bs in body.
     */
    @GetMapping("/market-segmentation-type-b-2-bs")
    public ResponseEntity<List<MarketSegmentationTypeB2b>> getAllMarketSegmentationTypeB2bs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketSegmentationTypeB2bs");
        Page<MarketSegmentationTypeB2b> page = marketSegmentationTypeB2bService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-bs/:id} : get the "id" marketSegmentationTypeB2b.
     *
     * @param id the id of the marketSegmentationTypeB2b to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketSegmentationTypeB2b, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-segmentation-type-b-2-bs/{id}")
    public ResponseEntity<MarketSegmentationTypeB2b> getMarketSegmentationTypeB2b(@PathVariable Long id) {
        log.debug("REST request to get MarketSegmentationTypeB2b : {}", id);
        Optional<MarketSegmentationTypeB2b> marketSegmentationTypeB2b = marketSegmentationTypeB2bService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketSegmentationTypeB2b);
    }

    /**
     * {@code DELETE  /market-segmentation-type-b-2-bs/:id} : delete the "id" marketSegmentationTypeB2b.
     *
     * @param id the id of the marketSegmentationTypeB2b to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-segmentation-type-b-2-bs/{id}")
    public ResponseEntity<Void> deleteMarketSegmentationTypeB2b(@PathVariable Long id) {
        log.debug("REST request to delete MarketSegmentationTypeB2b : {}", id);
        marketSegmentationTypeB2bService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
