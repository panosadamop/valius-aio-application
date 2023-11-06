package io.valius.app.web.rest;

import io.valius.app.domain.MarketSegmentationTypeB2cCategories;
import io.valius.app.repository.MarketSegmentationTypeB2cCategoriesRepository;
import io.valius.app.service.MarketSegmentationTypeB2cCategoriesService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketSegmentationTypeB2cCategories}.
 */
@RestController
@RequestMapping("/api")
public class MarketSegmentationTypeB2cCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2cCategoriesResource.class);

    private static final String ENTITY_NAME = "marketSegmentationTypeB2cCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketSegmentationTypeB2cCategoriesService marketSegmentationTypeB2cCategoriesService;

    private final MarketSegmentationTypeB2cCategoriesRepository marketSegmentationTypeB2cCategoriesRepository;

    public MarketSegmentationTypeB2cCategoriesResource(
        MarketSegmentationTypeB2cCategoriesService marketSegmentationTypeB2cCategoriesService,
        MarketSegmentationTypeB2cCategoriesRepository marketSegmentationTypeB2cCategoriesRepository
    ) {
        this.marketSegmentationTypeB2cCategoriesService = marketSegmentationTypeB2cCategoriesService;
        this.marketSegmentationTypeB2cCategoriesRepository = marketSegmentationTypeB2cCategoriesRepository;
    }

    /**
     * {@code POST  /market-segmentation-type-b-2-c-categories} : Create a new marketSegmentationTypeB2cCategories.
     *
     * @param marketSegmentationTypeB2cCategories the marketSegmentationTypeB2cCategories to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketSegmentationTypeB2cCategories, or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2cCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-segmentation-type-b-2-c-categories")
    public ResponseEntity<MarketSegmentationTypeB2cCategories> createMarketSegmentationTypeB2cCategories(
        @Valid @RequestBody MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories
    ) throws URISyntaxException {
        log.debug("REST request to save MarketSegmentationTypeB2cCategories : {}", marketSegmentationTypeB2cCategories);
        if (marketSegmentationTypeB2cCategories.getId() != null) {
            throw new BadRequestAlertException(
                "A new marketSegmentationTypeB2cCategories cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        MarketSegmentationTypeB2cCategories result = marketSegmentationTypeB2cCategoriesService.save(marketSegmentationTypeB2cCategories);
        return ResponseEntity
            .created(new URI("/api/market-segmentation-type-b-2-c-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-segmentation-type-b-2-c-categories/:id} : Updates an existing marketSegmentationTypeB2cCategories.
     *
     * @param id the id of the marketSegmentationTypeB2cCategories to save.
     * @param marketSegmentationTypeB2cCategories the marketSegmentationTypeB2cCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2cCategories,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2cCategories is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2cCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-segmentation-type-b-2-c-categories/{id}")
    public ResponseEntity<MarketSegmentationTypeB2cCategories> updateMarketSegmentationTypeB2cCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories
    ) throws URISyntaxException {
        log.debug("REST request to update MarketSegmentationTypeB2cCategories : {}, {}", id, marketSegmentationTypeB2cCategories);
        if (marketSegmentationTypeB2cCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2cCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2cCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketSegmentationTypeB2cCategories result = marketSegmentationTypeB2cCategoriesService.update(marketSegmentationTypeB2cCategories);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    marketSegmentationTypeB2cCategories.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /market-segmentation-type-b-2-c-categories/:id} : Partial updates given fields of an existing marketSegmentationTypeB2cCategories, field will ignore if it is null
     *
     * @param id the id of the marketSegmentationTypeB2cCategories to save.
     * @param marketSegmentationTypeB2cCategories the marketSegmentationTypeB2cCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2cCategories,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2cCategories is not valid,
     * or with status {@code 404 (Not Found)} if the marketSegmentationTypeB2cCategories is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2cCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/market-segmentation-type-b-2-c-categories/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<MarketSegmentationTypeB2cCategories> partialUpdateMarketSegmentationTypeB2cCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update MarketSegmentationTypeB2cCategories partially : {}, {}",
            id,
            marketSegmentationTypeB2cCategories
        );
        if (marketSegmentationTypeB2cCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2cCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2cCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketSegmentationTypeB2cCategories> result = marketSegmentationTypeB2cCategoriesService.partialUpdate(
            marketSegmentationTypeB2cCategories
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2cCategories.getId().toString())
        );
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-c-categories} : get all the marketSegmentationTypeB2cCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketSegmentationTypeB2cCategories in body.
     */
    @GetMapping("/market-segmentation-type-b-2-c-categories")
    public ResponseEntity<List<MarketSegmentationTypeB2cCategories>> getAllMarketSegmentationTypeB2cCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketSegmentationTypeB2cCategories");
        Page<MarketSegmentationTypeB2cCategories> page = marketSegmentationTypeB2cCategoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-c-categories/:id} : get the "id" marketSegmentationTypeB2cCategories.
     *
     * @param id the id of the marketSegmentationTypeB2cCategories to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketSegmentationTypeB2cCategories, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-segmentation-type-b-2-c-categories/{id}")
    public ResponseEntity<MarketSegmentationTypeB2cCategories> getMarketSegmentationTypeB2cCategories(@PathVariable Long id) {
        log.debug("REST request to get MarketSegmentationTypeB2cCategories : {}", id);
        Optional<MarketSegmentationTypeB2cCategories> marketSegmentationTypeB2cCategories =
            marketSegmentationTypeB2cCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketSegmentationTypeB2cCategories);
    }

    /**
     * {@code DELETE  /market-segmentation-type-b-2-c-categories/:id} : delete the "id" marketSegmentationTypeB2cCategories.
     *
     * @param id the id of the marketSegmentationTypeB2cCategories to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-segmentation-type-b-2-c-categories/{id}")
    public ResponseEntity<Void> deleteMarketSegmentationTypeB2cCategories(@PathVariable Long id) {
        log.debug("REST request to delete MarketSegmentationTypeB2cCategories : {}", id);
        marketSegmentationTypeB2cCategoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
