package io.valius.app.web.rest;

import io.valius.app.domain.MarketSegmentationTypeB2bCategories;
import io.valius.app.repository.MarketSegmentationTypeB2bCategoriesRepository;
import io.valius.app.service.MarketSegmentationTypeB2bCategoriesService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketSegmentationTypeB2bCategories}.
 */
@RestController
@RequestMapping("/api")
public class MarketSegmentationTypeB2bCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2bCategoriesResource.class);

    private static final String ENTITY_NAME = "marketSegmentationTypeB2bCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketSegmentationTypeB2bCategoriesService marketSegmentationTypeB2bCategoriesService;

    private final MarketSegmentationTypeB2bCategoriesRepository marketSegmentationTypeB2bCategoriesRepository;

    public MarketSegmentationTypeB2bCategoriesResource(
        MarketSegmentationTypeB2bCategoriesService marketSegmentationTypeB2bCategoriesService,
        MarketSegmentationTypeB2bCategoriesRepository marketSegmentationTypeB2bCategoriesRepository
    ) {
        this.marketSegmentationTypeB2bCategoriesService = marketSegmentationTypeB2bCategoriesService;
        this.marketSegmentationTypeB2bCategoriesRepository = marketSegmentationTypeB2bCategoriesRepository;
    }

    /**
     * {@code POST  /market-segmentation-type-b-2-b-categories} : Create a new marketSegmentationTypeB2bCategories.
     *
     * @param marketSegmentationTypeB2bCategories the marketSegmentationTypeB2bCategories to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketSegmentationTypeB2bCategories, or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2bCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-segmentation-type-b-2-b-categories")
    public ResponseEntity<MarketSegmentationTypeB2bCategories> createMarketSegmentationTypeB2bCategories(
        @Valid @RequestBody MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories
    ) throws URISyntaxException {
        log.debug("REST request to save MarketSegmentationTypeB2bCategories : {}", marketSegmentationTypeB2bCategories);
        if (marketSegmentationTypeB2bCategories.getId() != null) {
            throw new BadRequestAlertException(
                "A new marketSegmentationTypeB2bCategories cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        MarketSegmentationTypeB2bCategories result = marketSegmentationTypeB2bCategoriesService.save(marketSegmentationTypeB2bCategories);
        return ResponseEntity
            .created(new URI("/api/market-segmentation-type-b-2-b-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-segmentation-type-b-2-b-categories/:id} : Updates an existing marketSegmentationTypeB2bCategories.
     *
     * @param id the id of the marketSegmentationTypeB2bCategories to save.
     * @param marketSegmentationTypeB2bCategories the marketSegmentationTypeB2bCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2bCategories,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2bCategories is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2bCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-segmentation-type-b-2-b-categories/{id}")
    public ResponseEntity<MarketSegmentationTypeB2bCategories> updateMarketSegmentationTypeB2bCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories
    ) throws URISyntaxException {
        log.debug("REST request to update MarketSegmentationTypeB2bCategories : {}, {}", id, marketSegmentationTypeB2bCategories);
        if (marketSegmentationTypeB2bCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2bCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2bCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketSegmentationTypeB2bCategories result = marketSegmentationTypeB2bCategoriesService.update(marketSegmentationTypeB2bCategories);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    marketSegmentationTypeB2bCategories.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /market-segmentation-type-b-2-b-categories/:id} : Partial updates given fields of an existing marketSegmentationTypeB2bCategories, field will ignore if it is null
     *
     * @param id the id of the marketSegmentationTypeB2bCategories to save.
     * @param marketSegmentationTypeB2bCategories the marketSegmentationTypeB2bCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2bCategories,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2bCategories is not valid,
     * or with status {@code 404 (Not Found)} if the marketSegmentationTypeB2bCategories is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2bCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/market-segmentation-type-b-2-b-categories/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<MarketSegmentationTypeB2bCategories> partialUpdateMarketSegmentationTypeB2bCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update MarketSegmentationTypeB2bCategories partially : {}, {}",
            id,
            marketSegmentationTypeB2bCategories
        );
        if (marketSegmentationTypeB2bCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2bCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2bCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketSegmentationTypeB2bCategories> result = marketSegmentationTypeB2bCategoriesService.partialUpdate(
            marketSegmentationTypeB2bCategories
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2bCategories.getId().toString())
        );
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-b-categories} : get all the marketSegmentationTypeB2bCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketSegmentationTypeB2bCategories in body.
     */
    @GetMapping("/market-segmentation-type-b-2-b-categories")
    public ResponseEntity<List<MarketSegmentationTypeB2bCategories>> getAllMarketSegmentationTypeB2bCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketSegmentationTypeB2bCategories");
        Page<MarketSegmentationTypeB2bCategories> page = marketSegmentationTypeB2bCategoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-b-categories/:id} : get the "id" marketSegmentationTypeB2bCategories.
     *
     * @param id the id of the marketSegmentationTypeB2bCategories to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketSegmentationTypeB2bCategories, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-segmentation-type-b-2-b-categories/{id}")
    public ResponseEntity<MarketSegmentationTypeB2bCategories> getMarketSegmentationTypeB2bCategories(@PathVariable Long id) {
        log.debug("REST request to get MarketSegmentationTypeB2bCategories : {}", id);
        Optional<MarketSegmentationTypeB2bCategories> marketSegmentationTypeB2bCategories =
            marketSegmentationTypeB2bCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketSegmentationTypeB2bCategories);
    }

    /**
     * {@code DELETE  /market-segmentation-type-b-2-b-categories/:id} : delete the "id" marketSegmentationTypeB2bCategories.
     *
     * @param id the id of the marketSegmentationTypeB2bCategories to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-segmentation-type-b-2-b-categories/{id}")
    public ResponseEntity<Void> deleteMarketSegmentationTypeB2bCategories(@PathVariable Long id) {
        log.debug("REST request to delete MarketSegmentationTypeB2bCategories : {}", id);
        marketSegmentationTypeB2bCategoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
