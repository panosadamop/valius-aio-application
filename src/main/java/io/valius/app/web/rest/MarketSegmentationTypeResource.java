package io.valius.app.web.rest;

import io.valius.app.domain.MarketSegmentationType;
import io.valius.app.repository.MarketSegmentationTypeRepository;
import io.valius.app.service.MarketSegmentationTypeService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketSegmentationType}.
 */
@RestController
@RequestMapping("/api")
public class MarketSegmentationTypeResource {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeResource.class);

    private static final String ENTITY_NAME = "marketSegmentationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketSegmentationTypeService marketSegmentationTypeService;

    private final MarketSegmentationTypeRepository marketSegmentationTypeRepository;

    public MarketSegmentationTypeResource(
        MarketSegmentationTypeService marketSegmentationTypeService,
        MarketSegmentationTypeRepository marketSegmentationTypeRepository
    ) {
        this.marketSegmentationTypeService = marketSegmentationTypeService;
        this.marketSegmentationTypeRepository = marketSegmentationTypeRepository;
    }

    /**
     * {@code POST  /market-segmentation-types} : Create a new marketSegmentationType.
     *
     * @param marketSegmentationType the marketSegmentationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketSegmentationType, or with status {@code 400 (Bad Request)} if the marketSegmentationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-segmentation-types")
    public ResponseEntity<MarketSegmentationType> createMarketSegmentationType(
        @Valid @RequestBody MarketSegmentationType marketSegmentationType
    ) throws URISyntaxException {
        log.debug("REST request to save MarketSegmentationType : {}", marketSegmentationType);
        if (marketSegmentationType.getId() != null) {
            throw new BadRequestAlertException("A new marketSegmentationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketSegmentationType result = marketSegmentationTypeService.save(marketSegmentationType);
        return ResponseEntity
            .created(new URI("/api/market-segmentation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-segmentation-types/:id} : Updates an existing marketSegmentationType.
     *
     * @param id the id of the marketSegmentationType to save.
     * @param marketSegmentationType the marketSegmentationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationType,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-segmentation-types/{id}")
    public ResponseEntity<MarketSegmentationType> updateMarketSegmentationType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketSegmentationType marketSegmentationType
    ) throws URISyntaxException {
        log.debug("REST request to update MarketSegmentationType : {}, {}", id, marketSegmentationType);
        if (marketSegmentationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketSegmentationType result = marketSegmentationTypeService.update(marketSegmentationType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /market-segmentation-types/:id} : Partial updates given fields of an existing marketSegmentationType, field will ignore if it is null
     *
     * @param id the id of the marketSegmentationType to save.
     * @param marketSegmentationType the marketSegmentationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationType,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationType is not valid,
     * or with status {@code 404 (Not Found)} if the marketSegmentationType is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/market-segmentation-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketSegmentationType> partialUpdateMarketSegmentationType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketSegmentationType marketSegmentationType
    ) throws URISyntaxException {
        log.debug("REST request to partial update MarketSegmentationType partially : {}, {}", id, marketSegmentationType);
        if (marketSegmentationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketSegmentationType> result = marketSegmentationTypeService.partialUpdate(marketSegmentationType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationType.getId().toString())
        );
    }

    /**
     * {@code GET  /market-segmentation-types} : get all the marketSegmentationTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketSegmentationTypes in body.
     */
    @GetMapping("/market-segmentation-types")
    public ResponseEntity<List<MarketSegmentationType>> getAllMarketSegmentationTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketSegmentationTypes");
        Page<MarketSegmentationType> page = marketSegmentationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-segmentation-types/:id} : get the "id" marketSegmentationType.
     *
     * @param id the id of the marketSegmentationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketSegmentationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-segmentation-types/{id}")
    public ResponseEntity<MarketSegmentationType> getMarketSegmentationType(@PathVariable Long id) {
        log.debug("REST request to get MarketSegmentationType : {}", id);
        Optional<MarketSegmentationType> marketSegmentationType = marketSegmentationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketSegmentationType);
    }

    /**
     * {@code DELETE  /market-segmentation-types/:id} : delete the "id" marketSegmentationType.
     *
     * @param id the id of the marketSegmentationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-segmentation-types/{id}")
    public ResponseEntity<Void> deleteMarketSegmentationType(@PathVariable Long id) {
        log.debug("REST request to delete MarketSegmentationType : {}", id);
        marketSegmentationTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
