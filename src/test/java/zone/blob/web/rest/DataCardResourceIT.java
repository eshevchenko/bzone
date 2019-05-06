package zone.blob.web.rest;

import zone.blob.BzoneApp;
import zone.blob.domain.DataCard;
import zone.blob.domain.DataSource;
import zone.blob.repository.DataCardRepository;
import zone.blob.service.DataCardService;
import zone.blob.service.dto.DataCardDTO;
import zone.blob.service.mapper.DataCardMapper;
import zone.blob.web.rest.errors.ExceptionTranslator;
import zone.blob.service.dto.DataCardCriteria;
import zone.blob.service.DataCardQueryService;

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

import zone.blob.domain.enumeration.DataCardType;
import zone.blob.domain.enumeration.DataCardStatus;
/**
 * Integration tests for the {@Link DataCardResource} REST controller.
 */
@SpringBootTest(classes = BzoneApp.class)
public class DataCardResourceIT {

    private static final DataCardType DEFAULT_TYPE = DataCardType.CLUSTERING;
    private static final DataCardType UPDATED_TYPE = DataCardType.DEDUPLICATION;

    private static final DataCardStatus DEFAULT_STATUS = DataCardStatus.DRAFT;
    private static final DataCardStatus UPDATED_STATUS = DataCardStatus.RUNNING;

    @Autowired
    private DataCardRepository dataCardRepository;

    @Autowired
    private DataCardMapper dataCardMapper;

    @Autowired
    private DataCardService dataCardService;

    @Autowired
    private DataCardQueryService dataCardQueryService;

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

    private MockMvc restDataCardMockMvc;

    private DataCard dataCard;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataCardResource dataCardResource = new DataCardResource(dataCardService, dataCardQueryService);
        this.restDataCardMockMvc = MockMvcBuilders.standaloneSetup(dataCardResource)
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
    public static DataCard createEntity(EntityManager em) {
        DataCard dataCard = new DataCard()
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS);
        // Add required entity
        DataSource dataSource = DataSourceResourceIT.createEntity(em);
        em.persist(dataSource);
        em.flush();
        dataCard.setDataSource(dataSource);
        return dataCard;
    }

    @BeforeEach
    public void initTest() {
        dataCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataCard() throws Exception {
        int databaseSizeBeforeCreate = dataCardRepository.findAll().size();

        // Create the DataCard
        DataCardDTO dataCardDTO = dataCardMapper.toDto(dataCard);
        restDataCardMockMvc.perform(post("/api/data-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardDTO)))
            .andExpect(status().isCreated());

        // Validate the DataCard in the database
        List<DataCard> dataCardList = dataCardRepository.findAll();
        assertThat(dataCardList).hasSize(databaseSizeBeforeCreate + 1);
        DataCard testDataCard = dataCardList.get(dataCardList.size() - 1);
        assertThat(testDataCard.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDataCard.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDataCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataCardRepository.findAll().size();

        // Create the DataCard with an existing ID
        dataCard.setId(1L);
        DataCardDTO dataCardDTO = dataCardMapper.toDto(dataCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataCardMockMvc.perform(post("/api/data-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataCard in the database
        List<DataCard> dataCardList = dataCardRepository.findAll();
        assertThat(dataCardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataCardRepository.findAll().size();
        // set the field null
        dataCard.setType(null);

        // Create the DataCard, which fails.
        DataCardDTO dataCardDTO = dataCardMapper.toDto(dataCard);

        restDataCardMockMvc.perform(post("/api/data-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardDTO)))
            .andExpect(status().isBadRequest());

        List<DataCard> dataCardList = dataCardRepository.findAll();
        assertThat(dataCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataCardRepository.findAll().size();
        // set the field null
        dataCard.setStatus(null);

        // Create the DataCard, which fails.
        DataCardDTO dataCardDTO = dataCardMapper.toDto(dataCard);

        restDataCardMockMvc.perform(post("/api/data-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardDTO)))
            .andExpect(status().isBadRequest());

        List<DataCard> dataCardList = dataCardRepository.findAll();
        assertThat(dataCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataCards() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get all the dataCardList
        restDataCardMockMvc.perform(get("/api/data-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getDataCard() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get the dataCard
        restDataCardMockMvc.perform(get("/api/data-cards/{id}", dataCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataCard.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllDataCardsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get all the dataCardList where type equals to DEFAULT_TYPE
        defaultDataCardShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dataCardList where type equals to UPDATED_TYPE
        defaultDataCardShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataCardsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get all the dataCardList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDataCardShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dataCardList where type equals to UPDATED_TYPE
        defaultDataCardShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataCardsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get all the dataCardList where type is not null
        defaultDataCardShouldBeFound("type.specified=true");

        // Get all the dataCardList where type is null
        defaultDataCardShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCardsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get all the dataCardList where status equals to DEFAULT_STATUS
        defaultDataCardShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the dataCardList where status equals to UPDATED_STATUS
        defaultDataCardShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDataCardsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get all the dataCardList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDataCardShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the dataCardList where status equals to UPDATED_STATUS
        defaultDataCardShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDataCardsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        // Get all the dataCardList where status is not null
        defaultDataCardShouldBeFound("status.specified=true");

        // Get all the dataCardList where status is null
        defaultDataCardShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCardsByDataSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        DataSource dataSource = DataSourceResourceIT.createEntity(em);
        em.persist(dataSource);
        em.flush();
        dataCard.setDataSource(dataSource);
        dataCardRepository.saveAndFlush(dataCard);
        Long dataSourceId = dataSource.getId();

        // Get all the dataCardList where dataSource equals to dataSourceId
        defaultDataCardShouldBeFound("dataSourceId.equals=" + dataSourceId);

        // Get all the dataCardList where dataSource equals to dataSourceId + 1
        defaultDataCardShouldNotBeFound("dataSourceId.equals=" + (dataSourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataCardShouldBeFound(String filter) throws Exception {
        restDataCardMockMvc.perform(get("/api/data-cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restDataCardMockMvc.perform(get("/api/data-cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataCardShouldNotBeFound(String filter) throws Exception {
        restDataCardMockMvc.perform(get("/api/data-cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataCardMockMvc.perform(get("/api/data-cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataCard() throws Exception {
        // Get the dataCard
        restDataCardMockMvc.perform(get("/api/data-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataCard() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        int databaseSizeBeforeUpdate = dataCardRepository.findAll().size();

        // Update the dataCard
        DataCard updatedDataCard = dataCardRepository.findById(dataCard.getId()).get();
        // Disconnect from session so that the updates on updatedDataCard are not directly saved in db
        em.detach(updatedDataCard);
        updatedDataCard
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);
        DataCardDTO dataCardDTO = dataCardMapper.toDto(updatedDataCard);

        restDataCardMockMvc.perform(put("/api/data-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardDTO)))
            .andExpect(status().isOk());

        // Validate the DataCard in the database
        List<DataCard> dataCardList = dataCardRepository.findAll();
        assertThat(dataCardList).hasSize(databaseSizeBeforeUpdate);
        DataCard testDataCard = dataCardList.get(dataCardList.size() - 1);
        assertThat(testDataCard.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDataCard.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDataCard() throws Exception {
        int databaseSizeBeforeUpdate = dataCardRepository.findAll().size();

        // Create the DataCard
        DataCardDTO dataCardDTO = dataCardMapper.toDto(dataCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataCardMockMvc.perform(put("/api/data-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataCard in the database
        List<DataCard> dataCardList = dataCardRepository.findAll();
        assertThat(dataCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataCard() throws Exception {
        // Initialize the database
        dataCardRepository.saveAndFlush(dataCard);

        int databaseSizeBeforeDelete = dataCardRepository.findAll().size();

        // Delete the dataCard
        restDataCardMockMvc.perform(delete("/api/data-cards/{id}", dataCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DataCard> dataCardList = dataCardRepository.findAll();
        assertThat(dataCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCard.class);
        DataCard dataCard1 = new DataCard();
        dataCard1.setId(1L);
        DataCard dataCard2 = new DataCard();
        dataCard2.setId(dataCard1.getId());
        assertThat(dataCard1).isEqualTo(dataCard2);
        dataCard2.setId(2L);
        assertThat(dataCard1).isNotEqualTo(dataCard2);
        dataCard1.setId(null);
        assertThat(dataCard1).isNotEqualTo(dataCard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCardDTO.class);
        DataCardDTO dataCardDTO1 = new DataCardDTO();
        dataCardDTO1.setId(1L);
        DataCardDTO dataCardDTO2 = new DataCardDTO();
        assertThat(dataCardDTO1).isNotEqualTo(dataCardDTO2);
        dataCardDTO2.setId(dataCardDTO1.getId());
        assertThat(dataCardDTO1).isEqualTo(dataCardDTO2);
        dataCardDTO2.setId(2L);
        assertThat(dataCardDTO1).isNotEqualTo(dataCardDTO2);
        dataCardDTO1.setId(null);
        assertThat(dataCardDTO1).isNotEqualTo(dataCardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataCardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataCardMapper.fromId(null)).isNull();
    }
}
