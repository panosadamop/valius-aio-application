package io.valius.app.web.rest;

import io.valius.app.domain.MarketAttractivenessFactorsCategory;
import io.valius.app.repository.MarketAttractivenessFactorsCategoryRepository;
import io.valius.app.service.MarketAttractivenessFactorsCategoryService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketAttractivenessFactorsCategory}.
 */
@RestController
@RequestMapping("/api")
public class MarketAttractivenessFactorsCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MarketAttractivenessFactorsCategoryResource.class);

    private static final String ENTITY_NAME = "marketAttractivenessFactorsCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketAttractivenessFactorsCategoryService marketAttractivenessFactorsCategoryService;

    private final MarketAttractivenessFactorsCategoryRepository marketAttractivenessFactorsCategoryRepository;

    public MarketAttractivenessFactorsCategoryResource(
        MarketAttractivenessFactorsCategoryService marketAttractivenessFactorsCategoryService,
        MarketAttractivenessFactorsCategoryRepository marketAttractivenessFactorsCategoryRepository
    ) {
        this.marketAttractivenessFactorsCategoryService = marketAttractivenessFactorsCategoryService;
        this.marketAttractivenessFactorsCategoryRepository = marketAttractivenessFactorsCategoryRepository;
    }

    /**
     * {@code POST  /market-attractiveness-factors-categories} : Create a new marketAttractivenessFactorsCategory.
     *
     * @param marketAttractivenessFactorsCategory the marketAttractivenessFactorsCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketAttractivenessFactorsCategory, or with status {@code 400 (Bad Request)} if the marketAttractivenessFactorsCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-attractiveness-factors-categories")
    public ResponseEntity<MarketAttractivenessFactorsCategory> createMarketAttractivenessFactorsCategory(
        @Valid @RequestBody MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory
    ) throws URISyntaxException {
        log.debug("REST request to save MarketAttractivenessFactorsCategory : {}", marketAttractivenessFactorsCategory);
        if (marketAttractivenessFactorsCategory.getId() != null) {
            throw new BadRequestAlertException(
                "A new marketAttractivenessFactorsCategory cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        MarketAttractivenessFactorsCategory result = marketAttractivenessFactorsCategoryService.save(marketAttractivenessFactorsCategory);
        return ResponseEntity
            .created(new URI("/api/market-attractiveness-factors-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-attractiveness-factors-categories/:id} : Updates an existing marketAttractivenessFactorsCategory.
     *
     * @param id the id of the marketAttractivenessFactorsCategory to save.
     * @param marketAttractivenessFactorsCategory the marketAttractivenessFactorsCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketAttractivenessFactorsCategory,
     * or with status {@code 400 (Bad Request)} if the marketAttractivenessFactorsCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketAttractivenessFactorsCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-attractiveness-factors-categories/{id}")
    public ResponseEntity<MarketAttractivenessFactorsCategory> updateMarketAttractivenessFactorsCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory
    ) throws URISyntaxException {
        log.debug("REST request to update MarketAttractivenessFactorsCategory : {}, {}", id, marketAttractivenessFactorsCategory);
        if (marketAttractivenessFactorsCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketAttractivenessFactorsCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketAttractivenessFactorsCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketAttractivenessFactorsCategory result = marketAttractivenessFactorsCategoryService.update(marketAttractivenessFactorsCategory);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    marketAttractivenessFactorsCategory.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /market-attractiveness-factors-categories/:id} : Partial updates given fields of an existing marketAttractivenessFactorsCategory, field will ignore if it is null
     *
     * @param id the id of the marketAttractivenessFactorsCategory to save.
     * @param marketAttractivenessFactorsCategory the marketAttractivenessFactorsCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketAttractivenessFactorsCategory,
     * or with status {@code 400 (Bad Request)} if the marketAttractivenessFactorsCategory is not valid,
     * or with status {@code 404 (Not Found)} if the marketAttractivenessFactorsCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketAttractivenessFactorsCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/market-attractiveness-factors-categories/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<MarketAttractivenessFactorsCategory> partialUpdateMarketAttractivenessFactorsCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update MarketAttractivenessFactorsCategory partially : {}, {}",
            id,
            marketAttractivenessFactorsCategory
        );
        if (marketAttractivenessFactorsCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketAttractivenessFactorsCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketAttractivenessFactorsCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketAttractivenessFactorsCategory> result = marketAttractivenessFactorsCategoryService.partialUpdate(
            marketAttractivenessFactorsCategory
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketAttractivenessFactorsCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /market-attractiveness-factors-categories} : get all the marketAttractivenessFactorsCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketAttractivenessFactorsCategories in body.
     */
    @GetMapping("/market-attractiveness-factors-categories")
    public ResponseEntity<List<MarketAttractivenessFactorsCategory>> getAllMarketAttractivenessFactorsCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketAttractivenessFactorsCategories");
        Page<MarketAttractivenessFactorsCategory> page = marketAttractivenessFactorsCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-attractiveness-factors-categories/:id} : get the "id" marketAttractivenessFactorsCategory.
     *
     * @param id the id of the marketAttractivenessFactorsCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketAttractivenessFactorsCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-attractiveness-factors-categories/{id}")
    public ResponseEntity<MarketAttractivenessFactorsCategory> getMarketAttractivenessFactorsCategory(@PathVariable Long id) {
        log.debug("REST request to get MarketAttractivenessFactorsCategory : {}", id);
        Optional<MarketAttractivenessFactorsCategory> marketAttractivenessFactorsCategory =
            marketAttractivenessFactorsCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketAttractivenessFactorsCategory);
    }

    /**
     * {@code DELETE  /market-attractiveness-factors-categories/:id} : delete the "id" marketAttractivenessFactorsCategory.
     *
     * @param id the id of the marketAttractivenessFactorsCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-attractiveness-factors-categories/{id}")
    public ResponseEntity<Void> deleteMarketAttractivenessFactorsCategory(@PathVariable Long id) {
        log.debug("REST request to delete MarketAttractivenessFactorsCategory : {}", id);
        marketAttractivenessFactorsCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
