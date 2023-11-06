package io.valius.app.web.rest;

import io.valius.app.domain.TargetMarket;
import io.valius.app.repository.TargetMarketRepository;
import io.valius.app.service.TargetMarketService;
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
 * REST controller for managing {@link io.valius.app.domain.TargetMarket}.
 */
@RestController
@RequestMapping("/api")
public class TargetMarketResource {

    private final Logger log = LoggerFactory.getLogger(TargetMarketResource.class);

    private static final String ENTITY_NAME = "targetMarket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TargetMarketService targetMarketService;

    private final TargetMarketRepository targetMarketRepository;

    public TargetMarketResource(TargetMarketService targetMarketService, TargetMarketRepository targetMarketRepository) {
        this.targetMarketService = targetMarketService;
        this.targetMarketRepository = targetMarketRepository;
    }

    /**
     * {@code POST  /target-markets} : Create a new targetMarket.
     *
     * @param targetMarket the targetMarket to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new targetMarket, or with status {@code 400 (Bad Request)} if the targetMarket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/target-markets")
    public ResponseEntity<TargetMarket> createTargetMarket(@Valid @RequestBody TargetMarket targetMarket) throws URISyntaxException {
        log.debug("REST request to save TargetMarket : {}", targetMarket);
        if (targetMarket.getId() != null) {
            throw new BadRequestAlertException("A new targetMarket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TargetMarket result = targetMarketService.save(targetMarket);
        return ResponseEntity
            .created(new URI("/api/target-markets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /target-markets/:id} : Updates an existing targetMarket.
     *
     * @param id the id of the targetMarket to save.
     * @param targetMarket the targetMarket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetMarket,
     * or with status {@code 400 (Bad Request)} if the targetMarket is not valid,
     * or with status {@code 500 (Internal Server Error)} if the targetMarket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/target-markets/{id}")
    public ResponseEntity<TargetMarket> updateTargetMarket(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TargetMarket targetMarket
    ) throws URISyntaxException {
        log.debug("REST request to update TargetMarket : {}, {}", id, targetMarket);
        if (targetMarket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetMarket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!targetMarketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TargetMarket result = targetMarketService.update(targetMarket);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, targetMarket.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /target-markets/:id} : Partial updates given fields of an existing targetMarket, field will ignore if it is null
     *
     * @param id the id of the targetMarket to save.
     * @param targetMarket the targetMarket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetMarket,
     * or with status {@code 400 (Bad Request)} if the targetMarket is not valid,
     * or with status {@code 404 (Not Found)} if the targetMarket is not found,
     * or with status {@code 500 (Internal Server Error)} if the targetMarket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/target-markets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TargetMarket> partialUpdateTargetMarket(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TargetMarket targetMarket
    ) throws URISyntaxException {
        log.debug("REST request to partial update TargetMarket partially : {}, {}", id, targetMarket);
        if (targetMarket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetMarket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!targetMarketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TargetMarket> result = targetMarketService.partialUpdate(targetMarket);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, targetMarket.getId().toString())
        );
    }

    /**
     * {@code GET  /target-markets} : get all the targetMarkets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of targetMarkets in body.
     */
    @GetMapping("/target-markets")
    public ResponseEntity<List<TargetMarket>> getAllTargetMarkets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TargetMarkets");
        Page<TargetMarket> page = targetMarketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /target-markets/:id} : get the "id" targetMarket.
     *
     * @param id the id of the targetMarket to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the targetMarket, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/target-markets/{id}")
    public ResponseEntity<TargetMarket> getTargetMarket(@PathVariable Long id) {
        log.debug("REST request to get TargetMarket : {}", id);
        Optional<TargetMarket> targetMarket = targetMarketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(targetMarket);
    }

    /**
     * {@code DELETE  /target-markets/:id} : delete the "id" targetMarket.
     *
     * @param id the id of the targetMarket to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/target-markets/{id}")
    public ResponseEntity<Void> deleteTargetMarket(@PathVariable Long id) {
        log.debug("REST request to delete TargetMarket : {}", id);
        targetMarketService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
