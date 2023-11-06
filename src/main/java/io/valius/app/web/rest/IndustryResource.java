package io.valius.app.web.rest;

import io.valius.app.domain.Industry;
import io.valius.app.repository.IndustryRepository;
import io.valius.app.service.IndustryService;
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
 * REST controller for managing {@link io.valius.app.domain.Industry}.
 */
@RestController
@RequestMapping("/api")
public class IndustryResource {

    private final Logger log = LoggerFactory.getLogger(IndustryResource.class);

    private static final String ENTITY_NAME = "industry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndustryService industryService;

    private final IndustryRepository industryRepository;

    public IndustryResource(IndustryService industryService, IndustryRepository industryRepository) {
        this.industryService = industryService;
        this.industryRepository = industryRepository;
    }

    /**
     * {@code POST  /industries} : Create a new industry.
     *
     * @param industry the industry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new industry, or with status {@code 400 (Bad Request)} if the industry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/industries")
    public ResponseEntity<Industry> createIndustry(@Valid @RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to save Industry : {}", industry);
        if (industry.getId() != null) {
            throw new BadRequestAlertException("A new industry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Industry result = industryService.save(industry);
        return ResponseEntity
            .created(new URI("/api/industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /industries/:id} : Updates an existing industry.
     *
     * @param id the id of the industry to save.
     * @param industry the industry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated industry,
     * or with status {@code 400 (Bad Request)} if the industry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the industry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/industries/{id}")
    public ResponseEntity<Industry> updateIndustry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Industry industry
    ) throws URISyntaxException {
        log.debug("REST request to update Industry : {}, {}", id, industry);
        if (industry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, industry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!industryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Industry result = industryService.update(industry);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, industry.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /industries/:id} : Partial updates given fields of an existing industry, field will ignore if it is null
     *
     * @param id the id of the industry to save.
     * @param industry the industry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated industry,
     * or with status {@code 400 (Bad Request)} if the industry is not valid,
     * or with status {@code 404 (Not Found)} if the industry is not found,
     * or with status {@code 500 (Internal Server Error)} if the industry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/industries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Industry> partialUpdateIndustry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Industry industry
    ) throws URISyntaxException {
        log.debug("REST request to partial update Industry partially : {}, {}", id, industry);
        if (industry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, industry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!industryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Industry> result = industryService.partialUpdate(industry);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, industry.getId().toString())
        );
    }

    /**
     * {@code GET  /industries} : get all the industries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of industries in body.
     */
    @GetMapping("/industries")
    public ResponseEntity<List<Industry>> getAllIndustries(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Industries");
        Page<Industry> page = industryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /industries/:id} : get the "id" industry.
     *
     * @param id the id of the industry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the industry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/industries/{id}")
    public ResponseEntity<Industry> getIndustry(@PathVariable Long id) {
        log.debug("REST request to get Industry : {}", id);
        Optional<Industry> industry = industryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(industry);
    }

    /**
     * {@code DELETE  /industries/:id} : delete the "id" industry.
     *
     * @param id the id of the industry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/industries/{id}")
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        log.debug("REST request to delete Industry : {}", id);
        industryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
