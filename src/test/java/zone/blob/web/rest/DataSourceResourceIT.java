package zone.blob.web.rest;

import zone.blob.BzoneApp;
import zone.blob.domain.DataSource;
import zone.blob.repository.DataSourceRepository;
import zone.blob.service.DataSourceService;
import zone.blob.service.dto.DataSourceDTO;
import zone.blob.service.mapper.DataSourceMapper;
import zone.blob.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static zone.blob.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DataSourceResource} REST controller.
 */
@SpringBootTest(classes = BzoneApp.class)
public class DataSourceResourceIT {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDataSourceMockMvc;

    private DataSource dataSource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataSourceResource dataSourceResource = new DataSourceResource(dataSourceService);
        this.restDataSourceMockMvc = MockMvcBuilders.standaloneSetup(dataSourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSource createEntity(EntityManager em) {
        DataSource dataSource = new DataSource();
        return dataSource;
    }

    @BeforeEach
    public void initTest() {
        dataSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataSource() throws Exception {
        int databaseSizeBeforeCreate = dataSourceRepository.findAll().size();

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);
        restDataSourceMockMvc.perform(post("/api/data-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeCreate + 1);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
    }

    @Test
    @Transactional
    public void createDataSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataSourceRepository.findAll().size();

        // Create the DataSource with an existing ID
        dataSource.setId(1L);
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataSourceMockMvc.perform(post("/api/data-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataSources() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        // Get all the dataSourceList
        restDataSourceMockMvc.perform(get("/api/data-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSource.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDataSource() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        // Get the dataSource
        restDataSourceMockMvc.perform(get("/api/data-sources/{id}", dataSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataSource.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDataSource() throws Exception {
        // Get the dataSource
        restDataSourceMockMvc.perform(get("/api/data-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataSource() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();

        // Update the dataSource
        DataSource updatedDataSource = dataSourceRepository.findById(dataSource.getId()).get();
        // Disconnect from session so that the updates on updatedDataSource are not directly saved in db
        em.detach(updatedDataSource);
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(updatedDataSource);

        restDataSourceMockMvc.perform(put("/api/data-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isOk());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSourceMockMvc.perform(put("/api/data-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataSource() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        int databaseSizeBeforeDelete = dataSourceRepository.findAll().size();

        // Delete the dataSource
        restDataSourceMockMvc.perform(delete("/api/data-sources/{id}", dataSource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSource.class);
        DataSource dataSource1 = new DataSource();
        dataSource1.setId(1L);
        DataSource dataSource2 = new DataSource();
        dataSource2.setId(dataSource1.getId());
        assertThat(dataSource1).isEqualTo(dataSource2);
        dataSource2.setId(2L);
        assertThat(dataSource1).isNotEqualTo(dataSource2);
        dataSource1.setId(null);
        assertThat(dataSource1).isNotEqualTo(dataSource2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSourceDTO.class);
        DataSourceDTO dataSourceDTO1 = new DataSourceDTO();
        dataSourceDTO1.setId(1L);
        DataSourceDTO dataSourceDTO2 = new DataSourceDTO();
        assertThat(dataSourceDTO1).isNotEqualTo(dataSourceDTO2);
        dataSourceDTO2.setId(dataSourceDTO1.getId());
        assertThat(dataSourceDTO1).isEqualTo(dataSourceDTO2);
        dataSourceDTO2.setId(2L);
        assertThat(dataSourceDTO1).isNotEqualTo(dataSourceDTO2);
        dataSourceDTO1.setId(null);
        assertThat(dataSourceDTO1).isNotEqualTo(dataSourceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataSourceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataSourceMapper.fromId(null)).isNull();
    }
}
