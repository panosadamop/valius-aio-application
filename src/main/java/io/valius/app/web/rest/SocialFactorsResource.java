package io.valius.app.web.rest;

import io.valius.app.domain.SocialFactors;
import io.valius.app.repository.SocialFactorsRepository;
import io.valius.app.service.SocialFactorsService;
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
 * REST controller for managing {@link io.valius.app.domain.SocialFactors}.
 */
@RestController
@RequestMapping("/api")
public class SocialFactorsResource {

    private final Logger log = LoggerFactory.getLogger(SocialFactorsResource.class);

    private static final String ENTITY_NAME = "socialFactors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocialFactorsService socialFactorsService;

    private final SocialFactorsRepository socialFactorsRepository;

    public SocialFactorsResource(SocialFactorsService socialFactorsService, SocialFactorsRepository socialFactorsRepository) {
        this.socialFactorsService = socialFactorsService;
        this.socialFactorsRepository = socialFactorsRepository;
    }

    /**
     * {@code POST  /social-factors} : Create a new socialFactors.
     *
     * @param socialFactors the socialFactors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socialFactors, or with status {@code 400 (Bad Request)} if the socialFactors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/social-factors")
    public ResponseEntity<SocialFactors> createSocialFactors(@Valid @RequestBody SocialFactors socialFactors) throws URISyntaxException {
        log.debug("REST request to save SocialFactors : {}", socialFactors);
        if (socialFactors.getId() != null) {
            throw new BadRequestAlertException("A new socialFactors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialFactors result = socialFactorsService.save(socialFactors);
        return ResponseEntity
            .created(new URI("/api/social-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /social-factors/:id} : Updates an existing socialFactors.
     *
     * @param id the id of the socialFactors to save.
     * @param socialFactors the socialFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialFactors,
     * or with status {@code 400 (Bad Request)} if the socialFactors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socialFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/social-factors/{id}")
    public ResponseEntity<SocialFactors> updateSocialFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocialFactors socialFactors
    ) throws URISyntaxException {
        log.debug("REST request to update SocialFactors : {}, {}", id, socialFactors);
        if (socialFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocialFactors result = socialFactorsService.update(socialFactors);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialFactors.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /social-factors/:id} : Partial updates given fields of an existing socialFactors, field will ignore if it is null
     *
     * @param id the id of the socialFactors to save.
     * @param socialFactors the socialFactors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialFactors,
     * or with status {@code 400 (Bad Request)} if the socialFactors is not valid,
     * or with status {@code 404 (Not Found)} if the socialFactors is not found,
     * or with status {@code 500 (Internal Server Error)} if the socialFactors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/social-factors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocialFactors> partialUpdateSocialFactors(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocialFactors socialFactors
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocialFactors partially : {}, {}", id, socialFactors);
        if (socialFactors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialFactors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialFactorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocialFactors> result = socialFactorsService.partialUpdate(socialFactors);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialFactors.getId().toString())
        );
    }

    /**
     * {@code GET  /social-factors} : get all the socialFactors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socialFactors in body.
     */
    @GetMapping("/social-factors")
    public ResponseEntity<List<SocialFactors>> getAllSocialFactors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SocialFactors");
        Page<SocialFactors> page = socialFactorsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /social-factors/:id} : get the "id" socialFactors.
     *
     * @param id the id of the socialFactors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socialFactors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/social-factors/{id}")
    public ResponseEntity<SocialFactors> getSocialFactors(@PathVariable Long id) {
        log.debug("REST request to get SocialFactors : {}", id);
        Optional<SocialFactors> socialFactors = socialFactorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialFactors);
    }

    /**
     * {@code DELETE  /social-factors/:id} : delete the "id" socialFactors.
     *
     * @param id the id of the socialFactors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/social-factors/{id}")
    public ResponseEntity<Void> deleteSocialFactors(@PathVariable Long id) {
        log.debug("REST request to delete SocialFactors : {}", id);
        socialFactorsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
