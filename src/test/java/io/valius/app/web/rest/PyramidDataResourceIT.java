package io.valius.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.valius.app.IntegrationTest;
import io.valius.app.domain.PyramidData;
import io.valius.app.repository.PyramidDataRepository;
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
 * Integration tests for the {@link PyramidDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PyramidDataResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATEGORY = 1;
    private static final Integer UPDATED_CATEGORY = 2;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/pyramid-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PyramidDataRepository pyramidDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPyramidDataMockMvc;

    private PyramidData pyramidData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PyramidData createEntity(EntityManager em) {
        PyramidData pyramidData = new PyramidData()
            .identifier(DEFAULT_IDENTIFIER)
            .category(DEFAULT_CATEGORY)
            .value(DEFAULT_VALUE)
            .order(DEFAULT_ORDER)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return pyramidData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PyramidData createUpdatedEntity(EntityManager em) {
        PyramidData pyramidData = new PyramidData()
            .identifier(UPDATED_IDENTIFIER)
            .category(UPDATED_CATEGORY)
            .value(UPDATED_VALUE)
            .order(UPDATED_ORDER)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return pyramidData;
    }

    @BeforeEach
    public void initTest() {
        pyramidData = createEntity(em);
    }

    @Test
    @Transactional
    void createPyramidData() throws Exception {
        int databaseSizeBeforeCreate = pyramidDataRepository.findAll().size();
        // Create the PyramidData
        restPyramidDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isCreated());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeCreate + 1);
        PyramidData testPyramidData = pyramidDataList.get(pyramidDataList.size() - 1);
        assertThat(testPyramidData.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPyramidData.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPyramidData.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPyramidData.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testPyramidData.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testPyramidData.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPyramidDataWithExistingId() throws Exception {
        // Create the PyramidData with an existing ID
        pyramidData.setId(1L);

        int databaseSizeBeforeCreate = pyramidDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPyramidDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = pyramidDataRepository.findAll().size();
        // set the field null
        pyramidData.setIdentifier(null);

        // Create the PyramidData, which fails.

        restPyramidDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pyramidDataRepository.findAll().size();
        // set the field null
        pyramidData.setCategory(null);

        // Create the PyramidData, which fails.

        restPyramidDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = pyramidDataRepository.findAll().size();
        // set the field null
        pyramidData.setValue(null);

        // Create the PyramidData, which fails.

        restPyramidDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = pyramidDataRepository.findAll().size();
        // set the field null
        pyramidData.setOrder(null);

        // Create the PyramidData, which fails.

        restPyramidDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPyramidData() throws Exception {
        // Initialize the database
        pyramidDataRepository.saveAndFlush(pyramidData);

        // Get all the pyramidDataList
        restPyramidDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pyramidData.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }

    @Test
    @Transactional
    void getPyramidData() throws Exception {
        // Initialize the database
        pyramidDataRepository.saveAndFlush(pyramidData);

        // Get the pyramidData
        restPyramidDataMockMvc
            .perform(get(ENTITY_API_URL_ID, pyramidData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pyramidData.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)));
    }

    @Test
    @Transactional
    void getNonExistingPyramidData() throws Exception {
        // Get the pyramidData
        restPyramidDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPyramidData() throws Exception {
        // Initialize the database
        pyramidDataRepository.saveAndFlush(pyramidData);

        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();

        // Update the pyramidData
        PyramidData updatedPyramidData = pyramidDataRepository.findById(pyramidData.getId()).get();
        // Disconnect from session so that the updates on updatedPyramidData are not directly saved in db
        em.detach(updatedPyramidData);
        updatedPyramidData
            .identifier(UPDATED_IDENTIFIER)
            .category(UPDATED_CATEGORY)
            .value(UPDATED_VALUE)
            .order(UPDATED_ORDER)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restPyramidDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPyramidData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPyramidData))
            )
            .andExpect(status().isOk());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
        PyramidData testPyramidData = pyramidDataList.get(pyramidDataList.size() - 1);
        assertThat(testPyramidData.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPyramidData.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPyramidData.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPyramidData.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testPyramidData.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testPyramidData.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPyramidData() throws Exception {
        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();
        pyramidData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPyramidDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pyramidData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPyramidData() throws Exception {
        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();
        pyramidData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPyramidDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPyramidData() throws Exception {
        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();
        pyramidData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPyramidDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePyramidDataWithPatch() throws Exception {
        // Initialize the database
        pyramidDataRepository.saveAndFlush(pyramidData);

        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();

        // Update the pyramidData using partial update
        PyramidData partialUpdatedPyramidData = new PyramidData();
        partialUpdatedPyramidData.setId(pyramidData.getId());

        partialUpdatedPyramidData.order(UPDATED_ORDER);

        restPyramidDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPyramidData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPyramidData))
            )
            .andExpect(status().isOk());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
        PyramidData testPyramidData = pyramidDataList.get(pyramidDataList.size() - 1);
        assertThat(testPyramidData.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPyramidData.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPyramidData.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPyramidData.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testPyramidData.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testPyramidData.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePyramidDataWithPatch() throws Exception {
        // Initialize the database
        pyramidDataRepository.saveAndFlush(pyramidData);

        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();

        // Update the pyramidData using partial update
        PyramidData partialUpdatedPyramidData = new PyramidData();
        partialUpdatedPyramidData.setId(pyramidData.getId());

        partialUpdatedPyramidData
            .identifier(UPDATED_IDENTIFIER)
            .category(UPDATED_CATEGORY)
            .value(UPDATED_VALUE)
            .order(UPDATED_ORDER)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restPyramidDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPyramidData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPyramidData))
            )
            .andExpect(status().isOk());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
        PyramidData testPyramidData = pyramidDataList.get(pyramidDataList.size() - 1);
        assertThat(testPyramidData.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPyramidData.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPyramidData.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPyramidData.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testPyramidData.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testPyramidData.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPyramidData() throws Exception {
        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();
        pyramidData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPyramidDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pyramidData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPyramidData() throws Exception {
        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();
        pyramidData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPyramidDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPyramidData() throws Exception {
        int databaseSizeBeforeUpdate = pyramidDataRepository.findAll().size();
        pyramidData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPyramidDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pyramidData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PyramidData in the database
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePyramidData() throws Exception {
        // Initialize the database
        pyramidDataRepository.saveAndFlush(pyramidData);

        int databaseSizeBeforeDelete = pyramidDataRepository.findAll().size();

        // Delete the pyramidData
        restPyramidDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, pyramidData.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PyramidData> pyramidDataList = pyramidDataRepository.findAll();
        assertThat(pyramidDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
