package zone.blob.web.rest;

import zone.blob.BzoneApp;
import zone.blob.domain.DataSourceColumn;
import zone.blob.domain.DataSource;
import zone.blob.repository.DataSourceColumnRepository;
import zone.blob.service.DataSourceColumnService;
import zone.blob.service.dto.DataSourceColumnDTO;
import zone.blob.service.mapper.DataSourceColumnMapper;
import zone.blob.web.rest.errors.ExceptionTranslator;
import zone.blob.service.dto.DataSourceColumnCriteria;
import zone.blob.service.DataSourceColumnQueryService;

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

import zone.blob.domain.enumeration.ColumnDataType;
/**
 * Integration tests for the {@Link DataSourceColumnResource} REST controller.
 */
@SpringBootTest(classes = BzoneApp.class)
public class DataSourceColumnResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ColumnDataType DEFAULT_DATA_TYPE = ColumnDataType.STRING;
    private static final ColumnDataType UPDATED_DATA_TYPE = ColumnDataType.NUMERIC;

    @Autowired
    private DataSourceColumnRepository dataSourceColumnRepository;

    @Autowired
    private DataSourceColumnMapper dataSourceColumnMapper;

    @Autowired
    private DataSourceColumnService dataSourceColumnService;

    @Autowired
    private DataSourceColumnQueryService dataSourceColumnQueryService;

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

    private MockMvc restDataSourceColumnMockMvc;

    private DataSourceColumn dataSourceColumn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataSourceColumnResource dataSourceColumnResource = new DataSourceColumnResource(dataSourceColumnService, dataSourceColumnQueryService);
        this.restDataSourceColumnMockMvc = MockMvcBuilders.standaloneSetup(dataSourceColumnResource)
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
    public static DataSourceColumn createEntity(EntityManager em) {
        DataSourceColumn dataSourceColumn = new DataSourceColumn()
            .name(DEFAULT_NAME)
            .dataType(DEFAULT_DATA_TYPE);
        // Add required entity
        DataSource dataSource = DataSourceResourceIT.createEntity(em);
        em.persist(dataSource);
        em.flush();
        dataSourceColumn.setDataSource(dataSource);
        return dataSourceColumn;
    }

    @BeforeEach
    public void initTest() {
        dataSourceColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataSourceColumn() throws Exception {
        int databaseSizeBeforeCreate = dataSourceColumnRepository.findAll().size();

        // Create the DataSourceColumn
        DataSourceColumnDTO dataSourceColumnDTO = dataSourceColumnMapper.toDto(dataSourceColumn);
        restDataSourceColumnMockMvc.perform(post("/api/data-source-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceColumnDTO)))
            .andExpect(status().isCreated());

        // Validate the DataSourceColumn in the database
        List<DataSourceColumn> dataSourceColumnList = dataSourceColumnRepository.findAll();
        assertThat(dataSourceColumnList).hasSize(databaseSizeBeforeCreate + 1);
        DataSourceColumn testDataSourceColumn = dataSourceColumnList.get(dataSourceColumnList.size() - 1);
        assertThat(testDataSourceColumn.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataSourceColumn.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
    }

    @Test
    @Transactional
    public void createDataSourceColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataSourceColumnRepository.findAll().size();

        // Create the DataSourceColumn with an existing ID
        dataSourceColumn.setId(1L);
        DataSourceColumnDTO dataSourceColumnDTO = dataSourceColumnMapper.toDto(dataSourceColumn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataSourceColumnMockMvc.perform(post("/api/data-source-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSourceColumn in the database
        List<DataSourceColumn> dataSourceColumnList = dataSourceColumnRepository.findAll();
        assertThat(dataSourceColumnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceColumnRepository.findAll().size();
        // set the field null
        dataSourceColumn.setName(null);

        // Create the DataSourceColumn, which fails.
        DataSourceColumnDTO dataSourceColumnDTO = dataSourceColumnMapper.toDto(dataSourceColumn);

        restDataSourceColumnMockMvc.perform(post("/api/data-source-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceColumnDTO)))
            .andExpect(status().isBadRequest());

        List<DataSourceColumn> dataSourceColumnList = dataSourceColumnRepository.findAll();
        assertThat(dataSourceColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceColumnRepository.findAll().size();
        // set the field null
        dataSourceColumn.setDataType(null);

        // Create the DataSourceColumn, which fails.
        DataSourceColumnDTO dataSourceColumnDTO = dataSourceColumnMapper.toDto(dataSourceColumn);

        restDataSourceColumnMockMvc.perform(post("/api/data-source-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceColumnDTO)))
            .andExpect(status().isBadRequest());

        List<DataSourceColumn> dataSourceColumnList = dataSourceColumnRepository.findAll();
        assertThat(dataSourceColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataSourceColumns() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get all the dataSourceColumnList
        restDataSourceColumnMockMvc.perform(get("/api/data-source-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSourceColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDataSourceColumn() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get the dataSourceColumn
        restDataSourceColumnMockMvc.perform(get("/api/data-source-columns/{id}", dataSourceColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataSourceColumn.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllDataSourceColumnsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get all the dataSourceColumnList where name equals to DEFAULT_NAME
        defaultDataSourceColumnShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataSourceColumnList where name equals to UPDATED_NAME
        defaultDataSourceColumnShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataSourceColumnsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get all the dataSourceColumnList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataSourceColumnShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataSourceColumnList where name equals to UPDATED_NAME
        defaultDataSourceColumnShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataSourceColumnsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get all the dataSourceColumnList where name is not null
        defaultDataSourceColumnShouldBeFound("name.specified=true");

        // Get all the dataSourceColumnList where name is null
        defaultDataSourceColumnShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSourceColumnsByDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get all the dataSourceColumnList where dataType equals to DEFAULT_DATA_TYPE
        defaultDataSourceColumnShouldBeFound("dataType.equals=" + DEFAULT_DATA_TYPE);

        // Get all the dataSourceColumnList where dataType equals to UPDATED_DATA_TYPE
        defaultDataSourceColumnShouldNotBeFound("dataType.equals=" + UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataSourceColumnsByDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get all the dataSourceColumnList where dataType in DEFAULT_DATA_TYPE or UPDATED_DATA_TYPE
        defaultDataSourceColumnShouldBeFound("dataType.in=" + DEFAULT_DATA_TYPE + "," + UPDATED_DATA_TYPE);

        // Get all the dataSourceColumnList where dataType equals to UPDATED_DATA_TYPE
        defaultDataSourceColumnShouldNotBeFound("dataType.in=" + UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataSourceColumnsByDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        // Get all the dataSourceColumnList where dataType is not null
        defaultDataSourceColumnShouldBeFound("dataType.specified=true");

        // Get all the dataSourceColumnList where dataType is null
        defaultDataSourceColumnShouldNotBeFound("dataType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSourceColumnsByDataSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        DataSource dataSource = DataSourceResourceIT.createEntity(em);
        em.persist(dataSource);
        em.flush();
        dataSourceColumn.setDataSource(dataSource);
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);
        Long dataSourceId = dataSource.getId();

        // Get all the dataSourceColumnList where dataSource equals to dataSourceId
        defaultDataSourceColumnShouldBeFound("dataSourceId.equals=" + dataSourceId);

        // Get all the dataSourceColumnList where dataSource equals to dataSourceId + 1
        defaultDataSourceColumnShouldNotBeFound("dataSourceId.equals=" + (dataSourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataSourceColumnShouldBeFound(String filter) throws Exception {
        restDataSourceColumnMockMvc.perform(get("/api/data-source-columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSourceColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())));

        // Check, that the count call also returns 1
        restDataSourceColumnMockMvc.perform(get("/api/data-source-columns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataSourceColumnShouldNotBeFound(String filter) throws Exception {
        restDataSourceColumnMockMvc.perform(get("/api/data-source-columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataSourceColumnMockMvc.perform(get("/api/data-source-columns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataSourceColumn() throws Exception {
        // Get the dataSourceColumn
        restDataSourceColumnMockMvc.perform(get("/api/data-source-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataSourceColumn() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        int databaseSizeBeforeUpdate = dataSourceColumnRepository.findAll().size();

        // Update the dataSourceColumn
        DataSourceColumn updatedDataSourceColumn = dataSourceColumnRepository.findById(dataSourceColumn.getId()).get();
        // Disconnect from session so that the updates on updatedDataSourceColumn are not directly saved in db
        em.detach(updatedDataSourceColumn);
        updatedDataSourceColumn
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE);
        DataSourceColumnDTO dataSourceColumnDTO = dataSourceColumnMapper.toDto(updatedDataSourceColumn);

        restDataSourceColumnMockMvc.perform(put("/api/data-source-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceColumnDTO)))
            .andExpect(status().isOk());

        // Validate the DataSourceColumn in the database
        List<DataSourceColumn> dataSourceColumnList = dataSourceColumnRepository.findAll();
        assertThat(dataSourceColumnList).hasSize(databaseSizeBeforeUpdate);
        DataSourceColumn testDataSourceColumn = dataSourceColumnList.get(dataSourceColumnList.size() - 1);
        assertThat(testDataSourceColumn.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataSourceColumn.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataSourceColumn() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceColumnRepository.findAll().size();

        // Create the DataSourceColumn
        DataSourceColumnDTO dataSourceColumnDTO = dataSourceColumnMapper.toDto(dataSourceColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSourceColumnMockMvc.perform(put("/api/data-source-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSourceColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSourceColumn in the database
        List<DataSourceColumn> dataSourceColumnList = dataSourceColumnRepository.findAll();
        assertThat(dataSourceColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataSourceColumn() throws Exception {
        // Initialize the database
        dataSourceColumnRepository.saveAndFlush(dataSourceColumn);

        int databaseSizeBeforeDelete = dataSourceColumnRepository.findAll().size();

        // Delete the dataSourceColumn
        restDataSourceColumnMockMvc.perform(delete("/api/data-source-columns/{id}", dataSourceColumn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DataSourceColumn> dataSourceColumnList = dataSourceColumnRepository.findAll();
        assertThat(dataSourceColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSourceColumn.class);
        DataSourceColumn dataSourceColumn1 = new DataSourceColumn();
        dataSourceColumn1.setId(1L);
        DataSourceColumn dataSourceColumn2 = new DataSourceColumn();
        dataSourceColumn2.setId(dataSourceColumn1.getId());
        assertThat(dataSourceColumn1).isEqualTo(dataSourceColumn2);
        dataSourceColumn2.setId(2L);
        assertThat(dataSourceColumn1).isNotEqualTo(dataSourceColumn2);
        dataSourceColumn1.setId(null);
        assertThat(dataSourceColumn1).isNotEqualTo(dataSourceColumn2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSourceColumnDTO.class);
        DataSourceColumnDTO dataSourceColumnDTO1 = new DataSourceColumnDTO();
        dataSourceColumnDTO1.setId(1L);
        DataSourceColumnDTO dataSourceColumnDTO2 = new DataSourceColumnDTO();
        assertThat(dataSourceColumnDTO1).isNotEqualTo(dataSourceColumnDTO2);
        dataSourceColumnDTO2.setId(dataSourceColumnDTO1.getId());
        assertThat(dataSourceColumnDTO1).isEqualTo(dataSourceColumnDTO2);
        dataSourceColumnDTO2.setId(2L);
        assertThat(dataSourceColumnDTO1).isNotEqualTo(dataSourceColumnDTO2);
        dataSourceColumnDTO1.setId(null);
        assertThat(dataSourceColumnDTO1).isNotEqualTo(dataSourceColumnDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataSourceColumnMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataSourceColumnMapper.fromId(null)).isNull();
    }
}
