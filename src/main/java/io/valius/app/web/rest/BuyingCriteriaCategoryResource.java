package io.valius.app.web.rest;

import io.valius.app.domain.BuyingCriteriaCategory;
import io.valius.app.repository.BuyingCriteriaCategoryRepository;
import io.valius.app.service.BuyingCriteriaCategoryService;
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
 * REST controller for managing {@link io.valius.app.domain.BuyingCriteriaCategory}.
 */
@RestController
@RequestMapping("/api")
public class BuyingCriteriaCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BuyingCriteriaCategoryResource.class);

    private static final String ENTITY_NAME = "buyingCriteriaCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyingCriteriaCategoryService buyingCriteriaCategoryService;

    private final BuyingCriteriaCategoryRepository buyingCriteriaCategoryRepository;

    public BuyingCriteriaCategoryResource(
        BuyingCriteriaCategoryService buyingCriteriaCategoryService,
        BuyingCriteriaCategoryRepository buyingCriteriaCategoryRepository
    ) {
        this.buyingCriteriaCategoryService = buyingCriteriaCategoryService;
        this.buyingCriteriaCategoryRepository = buyingCriteriaCategoryRepository;
    }

    /**
     * {@code POST  /buying-criteria-categories} : Create a new buyingCriteriaCategory.
     *
     * @param buyingCriteriaCategory the buyingCriteriaCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyingCriteriaCategory, or with status {@code 400 (Bad Request)} if the buyingCriteriaCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buying-criteria-categories")
    public ResponseEntity<BuyingCriteriaCategory> createBuyingCriteriaCategory(
        @Valid @RequestBody BuyingCriteriaCategory buyingCriteriaCategory
    ) throws URISyntaxException {
        log.debug("REST request to save BuyingCriteriaCategory : {}", buyingCriteriaCategory);
        if (buyingCriteriaCategory.getId() != null) {
            throw new BadRequestAlertException("A new buyingCriteriaCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyingCriteriaCategory result = buyingCriteriaCategoryService.save(buyingCriteriaCategory);
        return ResponseEntity
            .created(new URI("/api/buying-criteria-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buying-criteria-categories/:id} : Updates an existing buyingCriteriaCategory.
     *
     * @param id the id of the buyingCriteriaCategory to save.
     * @param buyingCriteriaCategory the buyingCriteriaCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyingCriteriaCategory,
     * or with status {@code 400 (Bad Request)} if the buyingCriteriaCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyingCriteriaCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buying-criteria-categories/{id}")
    public ResponseEntity<BuyingCriteriaCategory> updateBuyingCriteriaCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BuyingCriteriaCategory buyingCriteriaCategory
    ) throws URISyntaxException {
        log.debug("REST request to update BuyingCriteriaCategory : {}, {}", id, buyingCriteriaCategory);
        if (buyingCriteriaCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyingCriteriaCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyingCriteriaCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuyingCriteriaCategory result = buyingCriteriaCategoryService.update(buyingCriteriaCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyingCriteriaCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buying-criteria-categories/:id} : Partial updates given fields of an existing buyingCriteriaCategory, field will ignore if it is null
     *
     * @param id the id of the buyingCriteriaCategory to save.
     * @param buyingCriteriaCategory the buyingCriteriaCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyingCriteriaCategory,
     * or with status {@code 400 (Bad Request)} if the buyingCriteriaCategory is not valid,
     * or with status {@code 404 (Not Found)} if the buyingCriteriaCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyingCriteriaCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buying-criteria-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuyingCriteriaCategory> partialUpdateBuyingCriteriaCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BuyingCriteriaCategory buyingCriteriaCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update BuyingCriteriaCategory partially : {}, {}", id, buyingCriteriaCategory);
        if (buyingCriteriaCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyingCriteriaCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyingCriteriaCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyingCriteriaCategory> result = buyingCriteriaCategoryService.partialUpdate(buyingCriteriaCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyingCriteriaCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /buying-criteria-categories} : get all the buyingCriteriaCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyingCriteriaCategories in body.
     */
    @GetMapping("/buying-criteria-categories")
    public ResponseEntity<List<BuyingCriteriaCategory>> getAllBuyingCriteriaCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BuyingCriteriaCategories");
        Page<BuyingCriteriaCategory> page = buyingCriteriaCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buying-criteria-categories/:id} : get the "id" buyingCriteriaCategory.
     *
     * @param id the id of the buyingCriteriaCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyingCriteriaCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buying-criteria-categories/{id}")
    public ResponseEntity<BuyingCriteriaCategory> getBuyingCriteriaCategory(@PathVariable Long id) {
        log.debug("REST request to get BuyingCriteriaCategory : {}", id);
        Optional<BuyingCriteriaCategory> buyingCriteriaCategory = buyingCriteriaCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyingCriteriaCategory);
    }

    /**
     * {@code DELETE  /buying-criteria-categories/:id} : delete the "id" buyingCriteriaCategory.
     *
     * @param id the id of the buyingCriteriaCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buying-criteria-categories/{id}")
    public ResponseEntity<Void> deleteBuyingCriteriaCategory(@PathVariable Long id) {
        log.debug("REST request to delete BuyingCriteriaCategory : {}", id);
        buyingCriteriaCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
