package io.valius.app.web.rest;

import io.valius.app.domain.NetPromoterScore;
import io.valius.app.repository.NetPromoterScoreRepository;
import io.valius.app.service.NetPromoterScoreService;
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
 * REST controller for managing {@link io.valius.app.domain.NetPromoterScore}.
 */
@RestController
@RequestMapping("/api")
public class NetPromoterScoreResource {

    private final Logger log = LoggerFactory.getLogger(NetPromoterScoreResource.class);

    private static final String ENTITY_NAME = "netPromoterScore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetPromoterScoreService netPromoterScoreService;

    private final NetPromoterScoreRepository netPromoterScoreRepository;

    public NetPromoterScoreResource(
        NetPromoterScoreService netPromoterScoreService,
        NetPromoterScoreRepository netPromoterScoreRepository
    ) {
        this.netPromoterScoreService = netPromoterScoreService;
        this.netPromoterScoreRepository = netPromoterScoreRepository;
    }

    /**
     * {@code POST  /net-promoter-scores} : Create a new netPromoterScore.
     *
     * @param netPromoterScore the netPromoterScore to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new netPromoterScore, or with status {@code 400 (Bad Request)} if the netPromoterScore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/net-promoter-scores")
    public ResponseEntity<NetPromoterScore> createNetPromoterScore(@Valid @RequestBody NetPromoterScore netPromoterScore)
        throws URISyntaxException {
        log.debug("REST request to save NetPromoterScore : {}", netPromoterScore);
        if (netPromoterScore.getId() != null) {
            throw new BadRequestAlertException("A new netPromoterScore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetPromoterScore result = netPromoterScoreService.save(netPromoterScore);
        return ResponseEntity
            .created(new URI("/api/net-promoter-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /net-promoter-scores/:id} : Updates an existing netPromoterScore.
     *
     * @param id the id of the netPromoterScore to save.
     * @param netPromoterScore the netPromoterScore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated netPromoterScore,
     * or with status {@code 400 (Bad Request)} if the netPromoterScore is not valid,
     * or with status {@code 500 (Internal Server Error)} if the netPromoterScore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/net-promoter-scores/{id}")
    public ResponseEntity<NetPromoterScore> updateNetPromoterScore(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NetPromoterScore netPromoterScore
    ) throws URISyntaxException {
        log.debug("REST request to update NetPromoterScore : {}, {}", id, netPromoterScore);
        if (netPromoterScore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, netPromoterScore.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!netPromoterScoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NetPromoterScore result = netPromoterScoreService.update(netPromoterScore);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, netPromoterScore.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /net-promoter-scores/:id} : Partial updates given fields of an existing netPromoterScore, field will ignore if it is null
     *
     * @param id the id of the netPromoterScore to save.
     * @param netPromoterScore the netPromoterScore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated netPromoterScore,
     * or with status {@code 400 (Bad Request)} if the netPromoterScore is not valid,
     * or with status {@code 404 (Not Found)} if the netPromoterScore is not found,
     * or with status {@code 500 (Internal Server Error)} if the netPromoterScore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/net-promoter-scores/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NetPromoterScore> partialUpdateNetPromoterScore(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NetPromoterScore netPromoterScore
    ) throws URISyntaxException {
        log.debug("REST request to partial update NetPromoterScore partially : {}, {}", id, netPromoterScore);
        if (netPromoterScore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, netPromoterScore.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!netPromoterScoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NetPromoterScore> result = netPromoterScoreService.partialUpdate(netPromoterScore);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, netPromoterScore.getId().toString())
        );
    }

    /**
     * {@code GET  /net-promoter-scores} : get all the netPromoterScores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of netPromoterScores in body.
     */
    @GetMapping("/net-promoter-scores")
    public ResponseEntity<List<NetPromoterScore>> getAllNetPromoterScores(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of NetPromoterScores");
        Page<NetPromoterScore> page = netPromoterScoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /net-promoter-scores/:id} : get the "id" netPromoterScore.
     *
     * @param id the id of the netPromoterScore to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the netPromoterScore, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/net-promoter-scores/{id}")
    public ResponseEntity<NetPromoterScore> getNetPromoterScore(@PathVariable Long id) {
        log.debug("REST request to get NetPromoterScore : {}", id);
        Optional<NetPromoterScore> netPromoterScore = netPromoterScoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(netPromoterScore);
    }

    /**
     * {@code DELETE  /net-promoter-scores/:id} : delete the "id" netPromoterScore.
     *
     * @param id the id of the netPromoterScore to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/net-promoter-scores/{id}")
    public ResponseEntity<Void> deleteNetPromoterScore(@PathVariable Long id) {
        log.debug("REST request to delete NetPromoterScore : {}", id);
        netPromoterScoreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
