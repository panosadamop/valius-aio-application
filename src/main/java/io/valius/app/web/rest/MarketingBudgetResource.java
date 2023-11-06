package io.valius.app.web.rest;

import io.valius.app.domain.MarketingBudget;
import io.valius.app.repository.MarketingBudgetRepository;
import io.valius.app.service.MarketingBudgetService;
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
 * REST controller for managing {@link io.valius.app.domain.MarketingBudget}.
 */
@RestController
@RequestMapping("/api")
public class MarketingBudgetResource {

    private final Logger log = LoggerFactory.getLogger(MarketingBudgetResource.class);

    private static final String ENTITY_NAME = "marketingBudget";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketingBudgetService marketingBudgetService;

    private final MarketingBudgetRepository marketingBudgetRepository;

    public MarketingBudgetResource(MarketingBudgetService marketingBudgetService, MarketingBudgetRepository marketingBudgetRepository) {
        this.marketingBudgetService = marketingBudgetService;
        this.marketingBudgetRepository = marketingBudgetRepository;
    }

    /**
     * {@code POST  /marketing-budgets} : Create a new marketingBudget.
     *
     * @param marketingBudget the marketingBudget to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketingBudget, or with status {@code 400 (Bad Request)} if the marketingBudget has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marketing-budgets")
    public ResponseEntity<MarketingBudget> createMarketingBudget(@Valid @RequestBody MarketingBudget marketingBudget)
        throws URISyntaxException {
        log.debug("REST request to save MarketingBudget : {}", marketingBudget);
        if (marketingBudget.getId() != null) {
            throw new BadRequestAlertException("A new marketingBudget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketingBudget result = marketingBudgetService.save(marketingBudget);
        return ResponseEntity
            .created(new URI("/api/marketing-budgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marketing-budgets/:id} : Updates an existing marketingBudget.
     *
     * @param id the id of the marketingBudget to save.
     * @param marketingBudget the marketingBudget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketingBudget,
     * or with status {@code 400 (Bad Request)} if the marketingBudget is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketingBudget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marketing-budgets/{id}")
    public ResponseEntity<MarketingBudget> updateMarketingBudget(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketingBudget marketingBudget
    ) throws URISyntaxException {
        log.debug("REST request to update MarketingBudget : {}, {}", id, marketingBudget);
        if (marketingBudget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketingBudget.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketingBudgetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketingBudget result = marketingBudgetService.update(marketingBudget);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketingBudget.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /marketing-budgets/:id} : Partial updates given fields of an existing marketingBudget, field will ignore if it is null
     *
     * @param id the id of the marketingBudget to save.
     * @param marketingBudget the marketingBudget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketingBudget,
     * or with status {@code 400 (Bad Request)} if the marketingBudget is not valid,
     * or with status {@code 404 (Not Found)} if the marketingBudget is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketingBudget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/marketing-budgets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketingBudget> partialUpdateMarketingBudget(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketingBudget marketingBudget
    ) throws URISyntaxException {
        log.debug("REST request to partial update MarketingBudget partially : {}, {}", id, marketingBudget);
        if (marketingBudget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketingBudget.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketingBudgetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketingBudget> result = marketingBudgetService.partialUpdate(marketingBudget);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketingBudget.getId().toString())
        );
    }

    /**
     * {@code GET  /marketing-budgets} : get all the marketingBudgets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketingBudgets in body.
     */
    @GetMapping("/marketing-budgets")
    public ResponseEntity<List<MarketingBudget>> getAllMarketingBudgets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MarketingBudgets");
        Page<MarketingBudget> page = marketingBudgetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /marketing-budgets/:id} : get the "id" marketingBudget.
     *
     * @param id the id of the marketingBudget to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketingBudget, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marketing-budgets/{id}")
    public ResponseEntity<MarketingBudget> getMarketingBudget(@PathVariable Long id) {
        log.debug("REST request to get MarketingBudget : {}", id);
        Optional<MarketingBudget> marketingBudget = marketingBudgetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketingBudget);
    }

    /**
     * {@code DELETE  /marketing-budgets/:id} : delete the "id" marketingBudget.
     *
     * @param id the id of the marketingBudget to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marketing-budgets/{id}")
    public ResponseEntity<Void> deleteMarketingBudget(@PathVariable Long id) {
        log.debug("REST request to delete MarketingBudget : {}", id);
        marketingBudgetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
