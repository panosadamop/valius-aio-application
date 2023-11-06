package io.valius.app.web.rest;

import io.valius.app.domain.InfoPageCategory;
import io.valius.app.repository.InfoPageCategoryRepository;
import io.valius.app.service.InfoPageCategoryService;
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
 * REST controller for managing {@link io.valius.app.domain.InfoPageCategory}.
 */
@RestController
@RequestMapping("/api")
public class InfoPageCategoryResource {

    private final Logger log = LoggerFactory.getLogger(InfoPageCategoryResource.class);

    private static final String ENTITY_NAME = "infoPageCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoPageCategoryService infoPageCategoryService;

    private final InfoPageCategoryRepository infoPageCategoryRepository;

    public InfoPageCategoryResource(
        InfoPageCategoryService infoPageCategoryService,
        InfoPageCategoryRepository infoPageCategoryRepository
    ) {
        this.infoPageCategoryService = infoPageCategoryService;
        this.infoPageCategoryRepository = infoPageCategoryRepository;
    }

    /**
     * {@code POST  /info-page-categories} : Create a new infoPageCategory.
     *
     * @param infoPageCategory the infoPageCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoPageCategory, or with status {@code 400 (Bad Request)} if the infoPageCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-page-categories")
    public ResponseEntity<InfoPageCategory> createInfoPageCategory(@Valid @RequestBody InfoPageCategory infoPageCategory)
        throws URISyntaxException {
        log.debug("REST request to save InfoPageCategory : {}", infoPageCategory);
        if (infoPageCategory.getId() != null) {
            throw new BadRequestAlertException("A new infoPageCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoPageCategory result = infoPageCategoryService.save(infoPageCategory);
        return ResponseEntity
            .created(new URI("/api/info-page-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-page-categories/:id} : Updates an existing infoPageCategory.
     *
     * @param id the id of the infoPageCategory to save.
     * @param infoPageCategory the infoPageCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoPageCategory,
     * or with status {@code 400 (Bad Request)} if the infoPageCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoPageCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-page-categories/{id}")
    public ResponseEntity<InfoPageCategory> updateInfoPageCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InfoPageCategory infoPageCategory
    ) throws URISyntaxException {
        log.debug("REST request to update InfoPageCategory : {}, {}", id, infoPageCategory);
        if (infoPageCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoPageCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoPageCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InfoPageCategory result = infoPageCategoryService.update(infoPageCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoPageCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /info-page-categories/:id} : Partial updates given fields of an existing infoPageCategory, field will ignore if it is null
     *
     * @param id the id of the infoPageCategory to save.
     * @param infoPageCategory the infoPageCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoPageCategory,
     * or with status {@code 400 (Bad Request)} if the infoPageCategory is not valid,
     * or with status {@code 404 (Not Found)} if the infoPageCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoPageCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-page-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InfoPageCategory> partialUpdateInfoPageCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InfoPageCategory infoPageCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoPageCategory partially : {}, {}", id, infoPageCategory);
        if (infoPageCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoPageCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoPageCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InfoPageCategory> result = infoPageCategoryService.partialUpdate(infoPageCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoPageCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /info-page-categories} : get all the infoPageCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoPageCategories in body.
     */
    @GetMapping("/info-page-categories")
    public ResponseEntity<List<InfoPageCategory>> getAllInfoPageCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of InfoPageCategories");
        Page<InfoPageCategory> page = infoPageCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /info-page-categories/:id} : get the "id" infoPageCategory.
     *
     * @param id the id of the infoPageCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoPageCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-page-categories/{id}")
    public ResponseEntity<InfoPageCategory> getInfoPageCategory(@PathVariable Long id) {
        log.debug("REST request to get InfoPageCategory : {}", id);
        Optional<InfoPageCategory> infoPageCategory = infoPageCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infoPageCategory);
    }

    /**
     * {@code DELETE  /info-page-categories/:id} : delete the "id" infoPageCategory.
     *
     * @param id the id of the infoPageCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-page-categories/{id}")
    public ResponseEntity<Void> deleteInfoPageCategory(@PathVariable Long id) {
        log.debug("REST request to delete InfoPageCategory : {}", id);
        infoPageCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
