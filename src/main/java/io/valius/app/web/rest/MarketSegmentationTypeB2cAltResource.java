package io.valius.app.web.rest;

import io.valius.app.domain.MarketSegmentationTypeB2cAlt;
import io.valius.app.repository.MarketSegmentationTypeB2cAltRepository;
import io.valius.app.service.MarketSegmentationTypeB2cAltService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketSegmentationTypeB2cAlt}.
 */
@RestController
@RequestMapping("/api")
public class MarketSegmentationTypeB2cAltResource {

    private final Logger log = LoggerFactory.getLogger(MarketSegmentationTypeB2cAltResource.class);

    private static final String ENTITY_NAME = "marketSegmentationTypeB2cAlt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketSegmentationTypeB2cAltService marketSegmentationTypeB2cAltService;

    private final MarketSegmentationTypeB2cAltRepository marketSegmentationTypeB2cAltRepository;

    public MarketSegmentationTypeB2cAltResource(
        MarketSegmentationTypeB2cAltService marketSegmentationTypeB2cAltService,
        MarketSegmentationTypeB2cAltRepository marketSegmentationTypeB2cAltRepository
    ) {
        this.marketSegmentationTypeB2cAltService = marketSegmentationTypeB2cAltService;
        this.marketSegmentationTypeB2cAltRepository = marketSegmentationTypeB2cAltRepository;
    }

    /**
     * {@code POST  /market-segmentation-type-b-2-c-alts} : Create a new marketSegmentationTypeB2cAlt.
     *
     * @param marketSegmentationTypeB2cAlt the marketSegmentationTypeB2cAlt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketSegmentationTypeB2cAlt, or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2cAlt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-segmentation-type-b-2-c-alts")
    public ResponseEntity<MarketSegmentationTypeB2cAlt> createMarketSegmentationTypeB2cAlt(
        @Valid @RequestBody MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt
    ) throws URISyntaxException {
        log.debug("REST request to save MarketSegmentationTypeB2cAlt : {}", marketSegmentationTypeB2cAlt);
        if (marketSegmentationTypeB2cAlt.getId() != null) {
            throw new BadRequestAlertException("A new marketSegmentationTypeB2cAlt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketSegmentationTypeB2cAlt result = marketSegmentationTypeB2cAltService.save(marketSegmentationTypeB2cAlt);
        return ResponseEntity
            .created(new URI("/api/market-segmentation-type-b-2-c-alts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-segmentation-type-b-2-c-alts/:id} : Updates an existing marketSegmentationTypeB2cAlt.
     *
     * @param id the id of the marketSegmentationTypeB2cAlt to save.
     * @param marketSegmentationTypeB2cAlt the marketSegmentationTypeB2cAlt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2cAlt,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2cAlt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2cAlt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-segmentation-type-b-2-c-alts/{id}")
    public ResponseEntity<MarketSegmentationTypeB2cAlt> updateMarketSegmentationTypeB2cAlt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt
    ) throws URISyntaxException {
        log.debug("REST request to update MarketSegmentationTypeB2cAlt : {}, {}", id, marketSegmentationTypeB2cAlt);
        if (marketSegmentationTypeB2cAlt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2cAlt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2cAltRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketSegmentationTypeB2cAlt result = marketSegmentationTypeB2cAltService.update(marketSegmentationTypeB2cAlt);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2cAlt.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /market-segmentation-type-b-2-c-alts/:id} : Partial updates given fields of an existing marketSegmentationTypeB2cAlt, field will ignore if it is null
     *
     * @param id the id of the marketSegmentationTypeB2cAlt to save.
     * @param marketSegmentationTypeB2cAlt the marketSegmentationTypeB2cAlt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketSegmentationTypeB2cAlt,
     * or with status {@code 400 (Bad Request)} if the marketSegmentationTypeB2cAlt is not valid,
     * or with status {@code 404 (Not Found)} if the marketSegmentationTypeB2cAlt is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketSegmentationTypeB2cAlt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/market-segmentation-type-b-2-c-alts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketSegmentationTypeB2cAlt> partialUpdateMarketSegmentationTypeB2cAlt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt
    ) throws URISyntaxException {
        log.debug("REST request to partial update MarketSegmentationTypeB2cAlt partially : {}, {}", id, marketSegmentationTypeB2cAlt);
        if (marketSegmentationTypeB2cAlt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketSegmentationTypeB2cAlt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketSegmentationTypeB2cAltRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketSegmentationTypeB2cAlt> result = marketSegmentationTypeB2cAltService.partialUpdate(marketSegmentationTypeB2cAlt);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketSegmentationTypeB2cAlt.getId().toString())
        );
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-c-alts} : get all the marketSegmentationTypeB2cAlts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketSegmentationTypeB2cAlts in body.
     */
    @GetMapping("/market-segmentation-type-b-2-c-alts")
    public ResponseEntity<List<MarketSegmentationTypeB2cAlt>> getAllMarketSegmentationTypeB2cAlts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MarketSegmentationTypeB2cAlts");
        Page<MarketSegmentationTypeB2cAlt> page = marketSegmentationTypeB2cAltService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /market-segmentation-type-b-2-c-alts/:id} : get the "id" marketSegmentationTypeB2cAlt.
     *
     * @param id the id of the marketSegmentationTypeB2cAlt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketSegmentationTypeB2cAlt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-segmentation-type-b-2-c-alts/{id}")
    public ResponseEntity<MarketSegmentationTypeB2cAlt> getMarketSegmentationTypeB2cAlt(@PathVariable Long id) {
        log.debug("REST request to get MarketSegmentationTypeB2cAlt : {}", id);
        Optional<MarketSegmentationTypeB2cAlt> marketSegmentationTypeB2cAlt = marketSegmentationTypeB2cAltService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketSegmentationTypeB2cAlt);
    }

    /**
     * {@code DELETE  /market-segmentation-type-b-2-c-alts/:id} : delete the "id" marketSegmentationTypeB2cAlt.
     *
     * @param id the id of the marketSegmentationTypeB2cAlt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-segmentation-type-b-2-c-alts/{id}")
    public ResponseEntity<Void> deleteMarketSegmentationTypeB2cAlt(@PathVariable Long id) {
        log.debug("REST request to delete MarketSegmentationTypeB2cAlt : {}", id);
        marketSegmentationTypeB2cAltService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
