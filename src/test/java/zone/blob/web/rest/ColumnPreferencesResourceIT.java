package zone.blob.web.rest;

import zone.blob.BzoneApp;
import zone.blob.domain.ColumnPreferences;
import zone.blob.repository.ColumnPreferencesRepository;
import zone.blob.service.ColumnPreferencesService;
import zone.blob.service.dto.ColumnPreferencesDTO;
import zone.blob.service.mapper.ColumnPreferencesMapper;
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
 * Integration tests for the {@Link ColumnPreferencesResource} REST controller.
 */
@SpringBootTest(classes = BzoneApp.class)
public class ColumnPreferencesResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ColumnPreferencesRepository columnPreferencesRepository;

    @Autowired
    private ColumnPreferencesMapper columnPreferencesMapper;

    @Autowired
    private ColumnPreferencesService columnPreferencesService;

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

    private MockMvc restColumnPreferencesMockMvc;

    private ColumnPreferences columnPreferences;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColumnPreferencesResource columnPreferencesResource = new ColumnPreferencesResource(columnPreferencesService);
        this.restColumnPreferencesMockMvc = MockMvcBuilders.standaloneSetup(columnPreferencesResource)
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
    public static ColumnPreferences createEntity(EntityManager em) {
        ColumnPreferences columnPreferences = new ColumnPreferences()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return columnPreferences;
    }

    @BeforeEach
    public void initTest() {
        columnPreferences = createEntity(em);
    }

    @Test
    @Transactional
    public void createColumnPreferences() throws Exception {
        int databaseSizeBeforeCreate = columnPreferencesRepository.findAll().size();

        // Create the ColumnPreferences
        ColumnPreferencesDTO columnPreferencesDTO = columnPreferencesMapper.toDto(columnPreferences);
        restColumnPreferencesMockMvc.perform(post("/api/column-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnPreferencesDTO)))
            .andExpect(status().isCreated());

        // Validate the ColumnPreferences in the database
        List<ColumnPreferences> columnPreferencesList = columnPreferencesRepository.findAll();
        assertThat(columnPreferencesList).hasSize(databaseSizeBeforeCreate + 1);
        ColumnPreferences testColumnPreferences = columnPreferencesList.get(columnPreferencesList.size() - 1);
        assertThat(testColumnPreferences.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testColumnPreferences.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createColumnPreferencesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = columnPreferencesRepository.findAll().size();

        // Create the ColumnPreferences with an existing ID
        columnPreferences.setId(1L);
        ColumnPreferencesDTO columnPreferencesDTO = columnPreferencesMapper.toDto(columnPreferences);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColumnPreferencesMockMvc.perform(post("/api/column-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnPreferencesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColumnPreferences in the database
        List<ColumnPreferences> columnPreferencesList = columnPreferencesRepository.findAll();
        assertThat(columnPreferencesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = columnPreferencesRepository.findAll().size();
        // set the field null
        columnPreferences.setKey(null);

        // Create the ColumnPreferences, which fails.
        ColumnPreferencesDTO columnPreferencesDTO = columnPreferencesMapper.toDto(columnPreferences);

        restColumnPreferencesMockMvc.perform(post("/api/column-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnPreferencesDTO)))
            .andExpect(status().isBadRequest());

        List<ColumnPreferences> columnPreferencesList = columnPreferencesRepository.findAll();
        assertThat(columnPreferencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = columnPreferencesRepository.findAll().size();
        // set the field null
        columnPreferences.setValue(null);

        // Create the ColumnPreferences, which fails.
        ColumnPreferencesDTO columnPreferencesDTO = columnPreferencesMapper.toDto(columnPreferences);

        restColumnPreferencesMockMvc.perform(post("/api/column-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnPreferencesDTO)))
            .andExpect(status().isBadRequest());

        List<ColumnPreferences> columnPreferencesList = columnPreferencesRepository.findAll();
        assertThat(columnPreferencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColumnPreferences() throws Exception {
        // Initialize the database
        columnPreferencesRepository.saveAndFlush(columnPreferences);

        // Get all the columnPreferencesList
        restColumnPreferencesMockMvc.perform(get("/api/column-preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(columnPreferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getColumnPreferences() throws Exception {
        // Initialize the database
        columnPreferencesRepository.saveAndFlush(columnPreferences);

        // Get the columnPreferences
        restColumnPreferencesMockMvc.perform(get("/api/column-preferences/{id}", columnPreferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(columnPreferences.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingColumnPreferences() throws Exception {
        // Get the columnPreferences
        restColumnPreferencesMockMvc.perform(get("/api/column-preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColumnPreferences() throws Exception {
        // Initialize the database
        columnPreferencesRepository.saveAndFlush(columnPreferences);

        int databaseSizeBeforeUpdate = columnPreferencesRepository.findAll().size();

        // Update the columnPreferences
        ColumnPreferences updatedColumnPreferences = columnPreferencesRepository.findById(columnPreferences.getId()).get();
        // Disconnect from session so that the updates on updatedColumnPreferences are not directly saved in db
        em.detach(updatedColumnPreferences);
        updatedColumnPreferences
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        ColumnPreferencesDTO columnPreferencesDTO = columnPreferencesMapper.toDto(updatedColumnPreferences);

        restColumnPreferencesMockMvc.perform(put("/api/column-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnPreferencesDTO)))
            .andExpect(status().isOk());

        // Validate the ColumnPreferences in the database
        List<ColumnPreferences> columnPreferencesList = columnPreferencesRepository.findAll();
        assertThat(columnPreferencesList).hasSize(databaseSizeBeforeUpdate);
        ColumnPreferences testColumnPreferences = columnPreferencesList.get(columnPreferencesList.size() - 1);
        assertThat(testColumnPreferences.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testColumnPreferences.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingColumnPreferences() throws Exception {
        int databaseSizeBeforeUpdate = columnPreferencesRepository.findAll().size();

        // Create the ColumnPreferences
        ColumnPreferencesDTO columnPreferencesDTO = columnPreferencesMapper.toDto(columnPreferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColumnPreferencesMockMvc.perform(put("/api/column-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnPreferencesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColumnPreferences in the database
        List<ColumnPreferences> columnPreferencesList = columnPreferencesRepository.findAll();
        assertThat(columnPreferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColumnPreferences() throws Exception {
        // Initialize the database
        columnPreferencesRepository.saveAndFlush(columnPreferences);

        int databaseSizeBeforeDelete = columnPreferencesRepository.findAll().size();

        // Delete the columnPreferences
        restColumnPreferencesMockMvc.perform(delete("/api/column-preferences/{id}", columnPreferences.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ColumnPreferences> columnPreferencesList = columnPreferencesRepository.findAll();
        assertThat(columnPreferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColumnPreferences.class);
        ColumnPreferences columnPreferences1 = new ColumnPreferences();
        columnPreferences1.setId(1L);
        ColumnPreferences columnPreferences2 = new ColumnPreferences();
        columnPreferences2.setId(columnPreferences1.getId());
        assertThat(columnPreferences1).isEqualTo(columnPreferences2);
        columnPreferences2.setId(2L);
        assertThat(columnPreferences1).isNotEqualTo(columnPreferences2);
        columnPreferences1.setId(null);
        assertThat(columnPreferences1).isNotEqualTo(columnPreferences2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColumnPreferencesDTO.class);
        ColumnPreferencesDTO columnPreferencesDTO1 = new ColumnPreferencesDTO();
        columnPreferencesDTO1.setId(1L);
        ColumnPreferencesDTO columnPreferencesDTO2 = new ColumnPreferencesDTO();
        assertThat(columnPreferencesDTO1).isNotEqualTo(columnPreferencesDTO2);
        columnPreferencesDTO2.setId(columnPreferencesDTO1.getId());
        assertThat(columnPreferencesDTO1).isEqualTo(columnPreferencesDTO2);
        columnPreferencesDTO2.setId(2L);
        assertThat(columnPreferencesDTO1).isNotEqualTo(columnPreferencesDTO2);
        columnPreferencesDTO1.setId(null);
        assertThat(columnPreferencesDTO1).isNotEqualTo(columnPreferencesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(columnPreferencesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(columnPreferencesMapper.fromId(null)).isNull();
    }
}
