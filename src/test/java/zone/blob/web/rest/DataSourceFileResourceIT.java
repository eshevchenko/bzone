package zone.blob.web.rest;

import zone.blob.BzoneApp;
import zone.blob.domain.DataSourceFile;
import zone.blob.domain.DataSource;
import zone.blob.repository.DataSourceFileRepository;
import zone.blob.service.DataSourceFileService;
import zone.blob.service.dto.DataSourceFileDTO;
import zone.blob.service.mapper.DataSourceFileMapper;
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
 * Integration tests for the {@Link DataSourceFileResource} REST controller.
 */
@SpringBootTest(classes = BzoneApp.class)
public class DataSourceFileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    @Autowired
    private DataSourceFileRepository dataSourceFileRepository;

    @Autowired
    private DataSourceFileMapper dataSourceFileMapper;

    @Autowired
    private DataSourceFileService dataSourceFileService;

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

    private MockMvc restDataSourceFileMockMvc;

    private DataSourceFile dataSourceFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataSourceFileResource dataSourceFileResource = new DataSourceFileResource(dataSourceFileService);
        this.restDataSourceFileMockMvc = MockMvcBuilders.standaloneSetup(dataSourceFileResource)
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
    public static DataSourceFile createEntity(EntityManager em) {
        DataSourceFile dataSourceFile = new DataSourceFile()
            .name(DEFAULT_NAME)
            .path(DEFAULT_PATH)
            .size(DEFAULT_SIZE);
        // Add required entity
        DataSource dataSource = DataSourceResourceIT.createEntity(em);
        em.persist(dataSource);
        em.flush();
        dataSourceFile.setDataSource(dataSource);
        return dataSourceFile;
    }

    @BeforeEach
    public void initTest() {
        dataSourceFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataSourceFile() throws Exception {
        int databaseSizeBeforeCreate = dataSourceFileRepository.findAll().size();

        // Create the DataSourceFile
        DataSourceFileDTO dataSourceFileDTO = dataSourceFileMapper.toDto(dataSourceFile);
        restDataSourceFileMockMvc.perform(post("/api/data-source-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceFileDTO)))
            .andExpect(status().isCreated());

        // Validate the DataSourceFile in the database
        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeCreate + 1);
        DataSourceFile testDataSourceFile = dataSourceFileList.get(dataSourceFileList.size() - 1);
        assertThat(testDataSourceFile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataSourceFile.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testDataSourceFile.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createDataSourceFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataSourceFileRepository.findAll().size();

        // Create the DataSourceFile with an existing ID
        dataSourceFile.setId(1L);
        DataSourceFileDTO dataSourceFileDTO = dataSourceFileMapper.toDto(dataSourceFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataSourceFileMockMvc.perform(post("/api/data-source-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSourceFile in the database
        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceFileRepository.findAll().size();
        // set the field null
        dataSourceFile.setName(null);

        // Create the DataSourceFile, which fails.
        DataSourceFileDTO dataSourceFileDTO = dataSourceFileMapper.toDto(dataSourceFile);

        restDataSourceFileMockMvc.perform(post("/api/data-source-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceFileDTO)))
            .andExpect(status().isBadRequest());

        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceFileRepository.findAll().size();
        // set the field null
        dataSourceFile.setPath(null);

        // Create the DataSourceFile, which fails.
        DataSourceFileDTO dataSourceFileDTO = dataSourceFileMapper.toDto(dataSourceFile);

        restDataSourceFileMockMvc.perform(post("/api/data-source-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceFileDTO)))
            .andExpect(status().isBadRequest());

        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceFileRepository.findAll().size();
        // set the field null
        dataSourceFile.setSize(null);

        // Create the DataSourceFile, which fails.
        DataSourceFileDTO dataSourceFileDTO = dataSourceFileMapper.toDto(dataSourceFile);

        restDataSourceFileMockMvc.perform(post("/api/data-source-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceFileDTO)))
            .andExpect(status().isBadRequest());

        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataSourceFiles() throws Exception {
        // Initialize the database
        dataSourceFileRepository.saveAndFlush(dataSourceFile);

        // Get all the dataSourceFileList
        restDataSourceFileMockMvc.perform(get("/api/data-source-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSourceFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)));
    }
    
    @Test
    @Transactional
    public void getDataSourceFile() throws Exception {
        // Initialize the database
        dataSourceFileRepository.saveAndFlush(dataSourceFile);

        // Get the dataSourceFile
        restDataSourceFileMockMvc.perform(get("/api/data-source-files/{id}", dataSourceFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataSourceFile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingDataSourceFile() throws Exception {
        // Get the dataSourceFile
        restDataSourceFileMockMvc.perform(get("/api/data-source-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataSourceFile() throws Exception {
        // Initialize the database
        dataSourceFileRepository.saveAndFlush(dataSourceFile);

        int databaseSizeBeforeUpdate = dataSourceFileRepository.findAll().size();

        // Update the dataSourceFile
        DataSourceFile updatedDataSourceFile = dataSourceFileRepository.findById(dataSourceFile.getId()).get();
        // Disconnect from session so that the updates on updatedDataSourceFile are not directly saved in db
        em.detach(updatedDataSourceFile);
        updatedDataSourceFile
            .name(UPDATED_NAME)
            .path(UPDATED_PATH)
            .size(UPDATED_SIZE);
        DataSourceFileDTO dataSourceFileDTO = dataSourceFileMapper.toDto(updatedDataSourceFile);

        restDataSourceFileMockMvc.perform(put("/api/data-source-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceFileDTO)))
            .andExpect(status().isOk());

        // Validate the DataSourceFile in the database
        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeUpdate);
        DataSourceFile testDataSourceFile = dataSourceFileList.get(dataSourceFileList.size() - 1);
        assertThat(testDataSourceFile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataSourceFile.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testDataSourceFile.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataSourceFile() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceFileRepository.findAll().size();

        // Create the DataSourceFile
        DataSourceFileDTO dataSourceFileDTO = dataSourceFileMapper.toDto(dataSourceFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSourceFileMockMvc.perform(put("/api/data-source-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSourceFile in the database
        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataSourceFile() throws Exception {
        // Initialize the database
        dataSourceFileRepository.saveAndFlush(dataSourceFile);

        int databaseSizeBeforeDelete = dataSourceFileRepository.findAll().size();

        // Delete the dataSourceFile
        restDataSourceFileMockMvc.perform(delete("/api/data-source-files/{id}", dataSourceFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DataSourceFile> dataSourceFileList = dataSourceFileRepository.findAll();
        assertThat(dataSourceFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSourceFile.class);
        DataSourceFile dataSourceFile1 = new DataSourceFile();
        dataSourceFile1.setId(1L);
        DataSourceFile dataSourceFile2 = new DataSourceFile();
        dataSourceFile2.setId(dataSourceFile1.getId());
        assertThat(dataSourceFile1).isEqualTo(dataSourceFile2);
        dataSourceFile2.setId(2L);
        assertThat(dataSourceFile1).isNotEqualTo(dataSourceFile2);
        dataSourceFile1.setId(null);
        assertThat(dataSourceFile1).isNotEqualTo(dataSourceFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSourceFileDTO.class);
        DataSourceFileDTO dataSourceFileDTO1 = new DataSourceFileDTO();
        dataSourceFileDTO1.setId(1L);
        DataSourceFileDTO dataSourceFileDTO2 = new DataSourceFileDTO();
        assertThat(dataSourceFileDTO1).isNotEqualTo(dataSourceFileDTO2);
        dataSourceFileDTO2.setId(dataSourceFileDTO1.getId());
        assertThat(dataSourceFileDTO1).isEqualTo(dataSourceFileDTO2);
        dataSourceFileDTO2.setId(2L);
        assertThat(dataSourceFileDTO1).isNotEqualTo(dataSourceFileDTO2);
        dataSourceFileDTO1.setId(null);
        assertThat(dataSourceFileDTO1).isNotEqualTo(dataSourceFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataSourceFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataSourceFileMapper.fromId(null)).isNull();
    }
}
