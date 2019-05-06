package zone.blob.web.rest;

import zone.blob.BzoneApp;
import zone.blob.domain.DataCardColumn;
import zone.blob.domain.DataCard;
import zone.blob.domain.DataSourceColumn;
import zone.blob.repository.DataCardColumnRepository;
import zone.blob.service.DataCardColumnService;
import zone.blob.service.dto.DataCardColumnDTO;
import zone.blob.service.mapper.DataCardColumnMapper;
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

import zone.blob.domain.enumeration.ColumnDataType;
/**
 * Integration tests for the {@Link DataCardColumnResource} REST controller.
 */
@SpringBootTest(classes = BzoneApp.class)
public class DataCardColumnResourceIT {

    private static final ColumnDataType DEFAULT_DATA_TYPE = ColumnDataType.STRING;
    private static final ColumnDataType UPDATED_DATA_TYPE = ColumnDataType.NUMERIC;

    @Autowired
    private DataCardColumnRepository dataCardColumnRepository;

    @Autowired
    private DataCardColumnMapper dataCardColumnMapper;

    @Autowired
    private DataCardColumnService dataCardColumnService;

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

    private MockMvc restDataCardColumnMockMvc;

    private DataCardColumn dataCardColumn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataCardColumnResource dataCardColumnResource = new DataCardColumnResource(dataCardColumnService);
        this.restDataCardColumnMockMvc = MockMvcBuilders.standaloneSetup(dataCardColumnResource)
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
    public static DataCardColumn createEntity(EntityManager em) {
        DataCardColumn dataCardColumn = new DataCardColumn()
            .dataType(DEFAULT_DATA_TYPE);
        // Add required entity
        DataCard dataCard = DataCardResourceIT.createEntity(em);
        em.persist(dataCard);
        em.flush();
        dataCardColumn.setDataCard(dataCard);
        // Add required entity
        DataSourceColumn dataSourceColumn = DataSourceColumnResourceIT.createEntity(em);
        em.persist(dataSourceColumn);
        em.flush();
        dataCardColumn.setDataSourceColumn(dataSourceColumn);
        return dataCardColumn;
    }

    @BeforeEach
    public void initTest() {
        dataCardColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataCardColumn() throws Exception {
        int databaseSizeBeforeCreate = dataCardColumnRepository.findAll().size();

        // Create the DataCardColumn
        DataCardColumnDTO dataCardColumnDTO = dataCardColumnMapper.toDto(dataCardColumn);
        restDataCardColumnMockMvc.perform(post("/api/data-card-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardColumnDTO)))
            .andExpect(status().isCreated());

        // Validate the DataCardColumn in the database
        List<DataCardColumn> dataCardColumnList = dataCardColumnRepository.findAll();
        assertThat(dataCardColumnList).hasSize(databaseSizeBeforeCreate + 1);
        DataCardColumn testDataCardColumn = dataCardColumnList.get(dataCardColumnList.size() - 1);
        assertThat(testDataCardColumn.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
    }

    @Test
    @Transactional
    public void createDataCardColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataCardColumnRepository.findAll().size();

        // Create the DataCardColumn with an existing ID
        dataCardColumn.setId(1L);
        DataCardColumnDTO dataCardColumnDTO = dataCardColumnMapper.toDto(dataCardColumn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataCardColumnMockMvc.perform(post("/api/data-card-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataCardColumn in the database
        List<DataCardColumn> dataCardColumnList = dataCardColumnRepository.findAll();
        assertThat(dataCardColumnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataCardColumnRepository.findAll().size();
        // set the field null
        dataCardColumn.setDataType(null);

        // Create the DataCardColumn, which fails.
        DataCardColumnDTO dataCardColumnDTO = dataCardColumnMapper.toDto(dataCardColumn);

        restDataCardColumnMockMvc.perform(post("/api/data-card-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardColumnDTO)))
            .andExpect(status().isBadRequest());

        List<DataCardColumn> dataCardColumnList = dataCardColumnRepository.findAll();
        assertThat(dataCardColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataCardColumns() throws Exception {
        // Initialize the database
        dataCardColumnRepository.saveAndFlush(dataCardColumn);

        // Get all the dataCardColumnList
        restDataCardColumnMockMvc.perform(get("/api/data-card-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataCardColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDataCardColumn() throws Exception {
        // Initialize the database
        dataCardColumnRepository.saveAndFlush(dataCardColumn);

        // Get the dataCardColumn
        restDataCardColumnMockMvc.perform(get("/api/data-card-columns/{id}", dataCardColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataCardColumn.getId().intValue()))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataCardColumn() throws Exception {
        // Get the dataCardColumn
        restDataCardColumnMockMvc.perform(get("/api/data-card-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataCardColumn() throws Exception {
        // Initialize the database
        dataCardColumnRepository.saveAndFlush(dataCardColumn);

        int databaseSizeBeforeUpdate = dataCardColumnRepository.findAll().size();

        // Update the dataCardColumn
        DataCardColumn updatedDataCardColumn = dataCardColumnRepository.findById(dataCardColumn.getId()).get();
        // Disconnect from session so that the updates on updatedDataCardColumn are not directly saved in db
        em.detach(updatedDataCardColumn);
        updatedDataCardColumn
            .dataType(UPDATED_DATA_TYPE);
        DataCardColumnDTO dataCardColumnDTO = dataCardColumnMapper.toDto(updatedDataCardColumn);

        restDataCardColumnMockMvc.perform(put("/api/data-card-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardColumnDTO)))
            .andExpect(status().isOk());

        // Validate the DataCardColumn in the database
        List<DataCardColumn> dataCardColumnList = dataCardColumnRepository.findAll();
        assertThat(dataCardColumnList).hasSize(databaseSizeBeforeUpdate);
        DataCardColumn testDataCardColumn = dataCardColumnList.get(dataCardColumnList.size() - 1);
        assertThat(testDataCardColumn.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataCardColumn() throws Exception {
        int databaseSizeBeforeUpdate = dataCardColumnRepository.findAll().size();

        // Create the DataCardColumn
        DataCardColumnDTO dataCardColumnDTO = dataCardColumnMapper.toDto(dataCardColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataCardColumnMockMvc.perform(put("/api/data-card-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataCardColumn in the database
        List<DataCardColumn> dataCardColumnList = dataCardColumnRepository.findAll();
        assertThat(dataCardColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataCardColumn() throws Exception {
        // Initialize the database
        dataCardColumnRepository.saveAndFlush(dataCardColumn);

        int databaseSizeBeforeDelete = dataCardColumnRepository.findAll().size();

        // Delete the dataCardColumn
        restDataCardColumnMockMvc.perform(delete("/api/data-card-columns/{id}", dataCardColumn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DataCardColumn> dataCardColumnList = dataCardColumnRepository.findAll();
        assertThat(dataCardColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCardColumn.class);
        DataCardColumn dataCardColumn1 = new DataCardColumn();
        dataCardColumn1.setId(1L);
        DataCardColumn dataCardColumn2 = new DataCardColumn();
        dataCardColumn2.setId(dataCardColumn1.getId());
        assertThat(dataCardColumn1).isEqualTo(dataCardColumn2);
        dataCardColumn2.setId(2L);
        assertThat(dataCardColumn1).isNotEqualTo(dataCardColumn2);
        dataCardColumn1.setId(null);
        assertThat(dataCardColumn1).isNotEqualTo(dataCardColumn2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCardColumnDTO.class);
        DataCardColumnDTO dataCardColumnDTO1 = new DataCardColumnDTO();
        dataCardColumnDTO1.setId(1L);
        DataCardColumnDTO dataCardColumnDTO2 = new DataCardColumnDTO();
        assertThat(dataCardColumnDTO1).isNotEqualTo(dataCardColumnDTO2);
        dataCardColumnDTO2.setId(dataCardColumnDTO1.getId());
        assertThat(dataCardColumnDTO1).isEqualTo(dataCardColumnDTO2);
        dataCardColumnDTO2.setId(2L);
        assertThat(dataCardColumnDTO1).isNotEqualTo(dataCardColumnDTO2);
        dataCardColumnDTO1.setId(null);
        assertThat(dataCardColumnDTO1).isNotEqualTo(dataCardColumnDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataCardColumnMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataCardColumnMapper.fromId(null)).isNull();
    }
}
