package io.valius.app.web.rest;

import io.valius.app.domain.MarketSegmentationTypeB2bAlt;
import io.valius.app.repository.MarketSegmentationTypeB2bAltRepository;
import io.valius.app.service.MarketSegmentationTypeB2bAltService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketSegmentationTypeB2bAlt}.
 */
@RestController
@RequestMapping("/api")
public class MarketSegmentationTypeB2bAltResource {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2bAltResource.class);

    private static final String ENTITY_NAME = "marketSegmentationTypeB2bAlt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketSegmentationTypeB2bAltService marketSegmentationTypeB2bAltService;

    private final MarketSegmentationTypeB2bAltRepository marketSegmentationTypeB2bAltRepository;

    public MarketSegmentationTypeB2bAltResource(
        MarketSegmentationTypeB2bAltService marketSegmentationTypeB2bAltService,
        MarketSegmentationTypeB2bAltRepository marketSegmentationTypeB2bAltRepository
    ) {
        this.marketSegmentationTypeB2bAltService = marketSegmentationTypeB2bAltService;
        this.marketSegmentationTypeB2bAltRepository = marketSegmentationTypeB2bAltRepository;
    }

    /**
     * {@code POST  /market-segmentation-type-b-2-b-alts} : Create a new marketSegmentationTypeB2bAlt.
     *
     * @param marketSegmentationTypeB2bAlt the marketSegmentationTypeB2bAlt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketSegmentationTypeB2bAlt, or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2bAlt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-segmentation-type-b-2-b-alts")
    public ResponseEntity<MarketSegmentationTypeB2bAlt> createMarketSegmentationTypeB2bAlt(
        @Valid @RequestBody MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt
    ) throws URISyntaxException {
        log.debug("REST request to save MarketSegmentationTypeB2bAlt : {}", marketSegmentationTypeB2bAlt);
        if (marketSegmentationTypeB2bAlt.getId() != null) {
            throw new BadRequestAlertException("A new marketSegmentationTypeB2bAlt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketSegmentationTypeB2bAlt result = marketSegmentationTypeB2bAltService.save(marketSegmentationTypeB2bAlt);
        return ResponseEntity
            .created(new URI("/api/market-segmentation-type-b-2-b-alts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-segmentation-type-b-2-b-alts/:id} : Updates an existing marketSegmentationTypeB2bAlt.
     *
     * @param id the id of the marketSegmentationTypeB2bAlt to save.
     * @param marketSegmentationTypeB2bAlt the marketSegmentationTypeB2bAlt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2bAlt,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2bAlt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2bAlt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-segmentation-type-b-2-b-alts/{id}")
    public ResponseEntity<MarketSegmentationTypeB2bAlt> updateMarketSegmentationTypeB2bAlt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt
    ) throws URISyntaxException {
        log.debug("REST request to update MarketSegmentationTypeB2bAlt : {}, {}", id, marketSegmentationTypeB2bAlt);
        if (marketSegmentationTypeB2bAlt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2bAlt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2bAltRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketSegmentationTypeB2bAlt result = marketSegmentationTypeB2bAltService.update(marketSegmentationTypeB2bAlt);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2bAlt.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /market-segmentation-type-b-2-b-alts/:id} : Partial updates given fields of an existing marketSegmentationTypeB2bAlt, field will ignore if it is null
     *
     * @param id the id of the marketSegmentationTypeB2bAlt to save.
     * @param marketSegmentationTypeB2bAlt the marketSegmentationTypeB2bAlt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2bAlt,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2bAlt is not valid,
     * or with status {@code 404 (Not Found)} if the marketSegmentationTypeB2bAlt is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2bAlt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/market-segmentation-type-b-2-b-alts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketSegmentationTypeB2bAlt> partialUpdateMarketSegmentationTypeB2bAlt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt
    ) throws URISyntaxException {
        log.debug("REST request to partial update MarketSegmentationTypeB2bAlt partially : {}, {}", id, marketSegmentationTypeB2bAlt);
        if (marketSegmentationTypeB2bAlt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2bAlt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2bAltRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketSegmentationTypeB2bAlt> result = marketSegmentationTypeB2bAltService.partialUpdate(marketSegmentationTypeB2bAlt);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2bAlt.getId().toString())
        );
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-b-alts} : get all the marketSegmentationTypeB2bAlts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketSegmentationTypeB2bAlts in body.
     */
    @GetMapping("/market-segmentation-type-b-2-b-alts")
    public ResponseEntity<List<MarketSegmentationTypeB2bAlt>> getAllMarketSegmentationTypeB2bAlts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketSegmentationTypeB2bAlts");
        Page<MarketSegmentationTypeB2bAlt> page = marketSegmentationTypeB2bAltService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-b-alts/:id} : get the "id" marketSegmentationTypeB2bAlt.
     *
     * @param id the id of the marketSegmentationTypeB2bAlt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketSegmentationTypeB2bAlt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-segmentation-type-b-2-b-alts/{id}")
    public ResponseEntity<MarketSegmentationTypeB2bAlt> getMarketSegmentationTypeB2bAlt(@PathVariable Long id) {
        log.debug("REST request to get MarketSegmentationTypeB2bAlt : {}", id);
        Optional<MarketSegmentationTypeB2bAlt> marketSegmentationTypeB2bAlt = marketSegmentationTypeB2bAltService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketSegmentationTypeB2bAlt);
    }

    /**
     * {@code DELETE  /market-segmentation-type-b-2-b-alts/:id} : delete the "id" marketSegmentationTypeB2bAlt.
     *
     * @param id the id of the marketSegmentationTypeB2bAlt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-segmentation-type-b-2-b-alts/{id}")
    public ResponseEntity<Void> deleteMarketSegmentationTypeB2bAlt(@PathVariable Long id) {
        log.debug("REST request to delete MarketSegmentationTypeB2bAlt : {}", id);
        marketSegmentationTypeB2bAltService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
