package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.InformationPages;
import io.valius.app.repository.InformationPagesRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link InformationPagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InformationPagesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/information-pages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InformationPagesRepository informationPagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformationPagesMockMvc;

    private InformationPages informationPages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformationPages createEntity(EntityManager em) {
        InformationPages informationPages = new InformationPages()
            .title(DEFAULT_TITLE)
            .subTitle(DEFAULT_SUB_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return informationPages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformationPages createUpdatedEntity(EntityManager em) {
        InformationPages informationPages = new InformationPages()
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .description(UPDATED_DESCRIPTION);
        return informationPages;
    }

    @BeforeEach
    public void initTest() {
        informationPages = createEntity(em);
    }

    @Test
    @Transactional
    void createInformationPages() throws Exception {
        int databaseSizeBeforeCreate = informationPagesRepository.findAll().size();
        // Create the InformationPages
        restInformationPagesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isCreated());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeCreate + 1);
        InformationPages testInformationPages = informationPagesList.get(informationPagesList.size() - 1);
        assertThat(testInformationPages.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testInformationPages.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testInformationPages.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createInformationPagesWithExistingId() throws Exception {
        // Create the InformationPages with an existing ID
        informationPages.setId(1L);

        int databaseSizeBeforeCreate = informationPagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformationPagesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = informationPagesRepository.findAll().size();
        // set the field null
        informationPages.setTitle(null);

        // Create the InformationPages, which fails.

        restInformationPagesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isBadRequest());

        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInformationPages() throws Exception {
        // Initialize the database
        informationPagesRepository.saveAndFlush(informationPages);

        // Get all the informationPagesList
        restInformationPagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informationPages.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getInformationPages() throws Exception {
        // Initialize the database
        informationPagesRepository.saveAndFlush(informationPages);

        // Get the informationPages
        restInformationPagesMockMvc
            .perform(get(ENTITY_API_URL_ID, informationPages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informationPages.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInformationPages() throws Exception {
        // Get the informationPages
        restInformationPagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInformationPages() throws Exception {
        // Initialize the database
        informationPagesRepository.saveAndFlush(informationPages);

        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();

        // Update the informationPages
        InformationPages updatedInformationPages = informationPagesRepository.findById(informationPages.getId()).get();
        // Disconnect from session so that the updates on updatedInformationPages are not directly saved in db
        em.detach(updatedInformationPages);
        updatedInformationPages.title(UPDATED_TITLE).subTitle(UPDATED_SUB_TITLE).description(UPDATED_DESCRIPTION);

        restInformationPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInformationPages.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInformationPages))
            )
            .andExpect(status().isOk());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
        InformationPages testInformationPages = informationPagesList.get(informationPagesList.size() - 1);
        assertThat(testInformationPages.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testInformationPages.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testInformationPages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingInformationPages() throws Exception {
        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();
        informationPages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformationPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, informationPages.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInformationPages() throws Exception {
        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();
        informationPages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInformationPages() throws Exception {
        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();
        informationPages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationPagesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInformationPagesWithPatch() throws Exception {
        // Initialize the database
        informationPagesRepository.saveAndFlush(informationPages);

        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();

        // Update the informationPages using partial update
        InformationPages partialUpdatedInformationPages = new InformationPages();
        partialUpdatedInformationPages.setId(informationPages.getId());

        partialUpdatedInformationPages.title(UPDATED_TITLE).subTitle(UPDATED_SUB_TITLE).description(UPDATED_DESCRIPTION);

        restInformationPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInformationPages.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInformationPages))
            )
            .andExpect(status().isOk());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
        InformationPages testInformationPages = informationPagesList.get(informationPagesList.size() - 1);
        assertThat(testInformationPages.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testInformationPages.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testInformationPages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateInformationPagesWithPatch() throws Exception {
        // Initialize the database
        informationPagesRepository.saveAndFlush(informationPages);

        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();

        // Update the informationPages using partial update
        InformationPages partialUpdatedInformationPages = new InformationPages();
        partialUpdatedInformationPages.setId(informationPages.getId());

        partialUpdatedInformationPages.title(UPDATED_TITLE).subTitle(UPDATED_SUB_TITLE).description(UPDATED_DESCRIPTION);

        restInformationPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInformationPages.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInformationPages))
            )
            .andExpect(status().isOk());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
        InformationPages testInformationPages = informationPagesList.get(informationPagesList.size() - 1);
        assertThat(testInformationPages.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testInformationPages.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testInformationPages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingInformationPages() throws Exception {
        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();
        informationPages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformationPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, informationPages.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInformationPages() throws Exception {
        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();
        informationPages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInformationPages() throws Exception {
        int databaseSizeBeforeUpdate = informationPagesRepository.findAll().size();
        informationPages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationPagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(informationPages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InformationPages in the database
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInformationPages() throws Exception {
        // Initialize the database
        informationPagesRepository.saveAndFlush(informationPages);

        int databaseSizeBeforeDelete = informationPagesRepository.findAll().size();

        // Delete the informationPages
        restInformationPagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, informationPages.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InformationPages> informationPagesList = informationPagesRepository.findAll();
        assertThat(informationPagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
