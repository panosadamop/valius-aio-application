package io.valius.app.web.rest;

import io.valius.app.domain.PyramidData;
import io.valius.app.repository.PyramidDataRepository;
import io.valius.app.service.PyramidDataService;
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
 * REST controller for managing {@link io.valius.app.domain.PyramidData}.
 */
@RestController
@RequestMapping("/api")
public class PyramidDataResource {

    private final Logger log = LoggerFactory.getLogger(PyramidDataResource.class);

    private static final String ENTITY_NAME = "pyramidData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PyramidDataService pyramidDataService;

    private final PyramidDataRepository pyramidDataRepository;

    public PyramidDataResource(PyramidDataService pyramidDataService, PyramidDataRepository pyramidDataRepository) {
        this.pyramidDataService = pyramidDataService;
        this.pyramidDataRepository = pyramidDataRepository;
    }

    /**
     * {@code POST  /pyramid-data} : Create a new pyramidData.
     *
     * @param pyramidData the pyramidData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pyramidData, or with status {@code 400 (Bad Request)} if the pyramidData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pyramid-data")
    public ResponseEntity<PyramidData> createPyramidData(@Valid @RequestBody PyramidData pyramidData) throws URISyntaxException {
        log.debug("REST request to save PyramidData : {}", pyramidData);
        if (pyramidData.getId() != null) {
            throw new BadRequestAlertException("A new pyramidData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PyramidData result = pyramidDataService.save(pyramidData);
        return ResponseEntity
            .created(new URI("/api/pyramid-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pyramid-data/:id} : Updates an existing pyramidData.
     *
     * @param id the id of the pyramidData to save.
     * @param pyramidData the pyramidData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pyramidData,
     * or with status {@code 400 (Bad Request)} if the pyramidData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pyramidData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pyramid-data/{id}")
    public ResponseEntity<PyramidData> updatePyramidData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PyramidData pyramidData
    ) throws URISyntaxException {
        log.debug("REST request to update PyramidData : {}, {}", id, pyramidData);
        if (pyramidData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pyramidData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pyramidDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PyramidData result = pyramidDataService.update(pyramidData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pyramidData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pyramid-data/:id} : Partial updates given fields of an existing pyramidData, field will ignore if it is null
     *
     * @param id the id of the pyramidData to save.
     * @param pyramidData the pyramidData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pyramidData,
     * or with status {@code 400 (Bad Request)} if the pyramidData is not valid,
     * or with status {@code 404 (Not Found)} if the pyramidData is not found,
     * or with status {@code 500 (Internal Server Error)} if the pyramidData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pyramid-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PyramidData> partialUpdatePyramidData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PyramidData pyramidData
    ) throws URISyntaxException {
        log.debug("REST request to partial update PyramidData partially : {}, {}", id, pyramidData);
        if (pyramidData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pyramidData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pyramidDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PyramidData> result = pyramidDataService.partialUpdate(pyramidData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pyramidData.getId().toString())
        );
    }

    /**
     * {@code GET  /pyramid-data} : get all the pyramidData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pyramidData in body.
     */
    @GetMapping("/pyramid-data")
    public ResponseEntity<List<PyramidData>> getAllPyramidData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PyramidData");
        Page<PyramidData> page = pyramidDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pyramid-data/:id} : get the "id" pyramidData.
     *
     * @param id the id of the pyramidData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pyramidData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pyramid-data/{id}")
    public ResponseEntity<PyramidData> getPyramidData(@PathVariable Long id) {
        log.debug("REST request to get PyramidData : {}", id);
        Optional<PyramidData> pyramidData = pyramidDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pyramidData);
    }

    /**
     * {@code DELETE  /pyramid-data/:id} : delete the "id" pyramidData.
     *
     * @param id the id of the pyramidData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pyramid-data/{id}")
    public ResponseEntity<Void> deletePyramidData(@PathVariable Long id) {
        log.debug("REST request to delete PyramidData : {}", id);
        pyramidDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
