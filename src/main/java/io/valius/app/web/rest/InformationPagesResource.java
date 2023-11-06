package io.valius.app.web.rest;

import io.valius.app.domain.InformationPages;
import io.valius.app.repository.InformationPagesRepository;
import io.valius.app.service.InformationPagesService;
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
 * REST controller for managing {@link io.valius.app.domain.InformationPages}.
 */
@RestController
@RequestMapping("/api")
public class InformationPagesResource {

    private final Logger log = LoggerFactory.getLogger(InformationPagesResource.class);

    private static final String ENTITY_NAME = "informationPages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformationPagesService informationPagesService;

    private final InformationPagesRepository informationPagesRepository;

    public InformationPagesResource(
        InformationPagesService informationPagesService,
        InformationPagesRepository informationPagesRepository
    ) {
        this.informationPagesService = informationPagesService;
        this.informationPagesRepository = informationPagesRepository;
    }

    /**
     * {@code POST  /information-pages} : Create a new informationPages.
     *
     * @param informationPages the informationPages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informationPages, or with status {@code 400 (Bad Request)} if the informationPages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/information-pages")
    public ResponseEntity<InformationPages> createInformationPages(@Valid @RequestBody InformationPages informationPages)
        throws URISyntaxException {
        log.debug("REST request to save InformationPages : {}", informationPages);
        if (informationPages.getId() != null) {
            throw new BadRequestAlertException("A new informationPages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformationPages result = informationPagesService.save(informationPages);
        return ResponseEntity
            .created(new URI("/api/information-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /information-pages/:id} : Updates an existing informationPages.
     *
     * @param id the id of the informationPages to save.
     * @param informationPages the informationPages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationPages,
     * or with status {@code 400 (Bad Request)} if the informationPages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informationPages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/information-pages/{id}")
    public ResponseEntity<InformationPages> updateInformationPages(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InformationPages informationPages
    ) throws URISyntaxException {
        log.debug("REST request to update InformationPages : {}, {}", id, informationPages);
        if (informationPages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationPages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informationPagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InformationPages result = informationPagesService.update(informationPages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informationPages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /information-pages/:id} : Partial updates given fields of an existing informationPages, field will ignore if it is null
     *
     * @param id the id of the informationPages to save.
     * @param informationPages the informationPages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationPages,
     * or with status {@code 400 (Bad Request)} if the informationPages is not valid,
     * or with status {@code 404 (Not Found)} if the informationPages is not found,
     * or with status {@code 500 (Internal Server Error)} if the informationPages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/information-pages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InformationPages> partialUpdateInformationPages(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InformationPages informationPages
    ) throws URISyntaxException {
        log.debug("REST request to partial update InformationPages partially : {}, {}", id, informationPages);
        if (informationPages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationPages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informationPagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InformationPages> result = informationPagesService.partialUpdate(informationPages);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informationPages.getId().toString())
        );
    }

    /**
     * {@code GET  /information-pages} : get all the informationPages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informationPages in body.
     */
    @GetMapping("/information-pages")
    public ResponseEntity<List<InformationPages>> getAllInformationPages(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of InformationPages");
        Page<InformationPages> page = informationPagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /information-pages/:id} : get the "id" informationPages.
     *
     * @param id the id of the informationPages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informationPages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/information-pages/{id}")
    public ResponseEntity<InformationPages> getInformationPages(@PathVariable Long id) {
        log.debug("REST request to get InformationPages : {}", id);
        Optional<InformationPages> informationPages = informationPagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(informationPages);
    }

    /**
     * {@code DELETE  /information-pages/:id} : delete the "id" informationPages.
     *
     * @param id the id of the informationPages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/information-pages/{id}")
    public ResponseEntity<Void> deleteInformationPages(@PathVariable Long id) {
        log.debug("REST request to delete InformationPages : {}", id);
        informationPagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
