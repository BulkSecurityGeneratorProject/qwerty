package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.DemoApp;

import com.mycompany.myapp.domain.Policy_details;
import com.mycompany.myapp.repository.Policy_detailsRepository;
import com.mycompany.myapp.service.Policy_detailsService;
import com.mycompany.myapp.service.dto.Policy_detailsDTO;
import com.mycompany.myapp.service.mapper.Policy_detailsMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Policy_detailsResource REST controller.
 *
 * @see Policy_detailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class Policy_detailsResourceIntTest extends AbstractCassandraTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final UUID DEFAULT_SCHEME_ID = UUID.randomUUID();
    private static final UUID UPDATED_SCHEME_ID = UUID.randomUUID();

    private static final UUID DEFAULT_AGENT_ID = UUID.randomUUID();
    private static final UUID UPDATED_AGENT_ID = UUID.randomUUID();

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final BigDecimal DEFAULT_COMMISION = new BigDecimal(1);
    private static final BigDecimal UPDATED_COMMISION = new BigDecimal(2);

    @Autowired
    private Policy_detailsRepository policy_detailsRepository;

    @Autowired
    private Policy_detailsMapper policy_detailsMapper;

    @Autowired
    private Policy_detailsService policy_detailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPolicy_detailsMockMvc;

    private Policy_details policy_details;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Policy_detailsResource policy_detailsResource = new Policy_detailsResource(policy_detailsService);
        this.restPolicy_detailsMockMvc = MockMvcBuilders.standaloneSetup(policy_detailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy_details createEntity() {
        Policy_details policy_details = new Policy_details()
            .date(DEFAULT_DATE)
            .scheme_id(DEFAULT_SCHEME_ID)
            .agent_id(DEFAULT_AGENT_ID)
            .customer_name(DEFAULT_CUSTOMER_NAME)
            .amount(DEFAULT_AMOUNT)
            .commision(DEFAULT_COMMISION);
        return policy_details;
    }

    @Before
    public void initTest() {
        policy_detailsRepository.deleteAll();
        policy_details = createEntity();
    }

    @Test
    public void createPolicy_details() throws Exception {
        int databaseSizeBeforeCreate = policy_detailsRepository.findAll().size();

        // Create the Policy_details
        Policy_detailsDTO policy_detailsDTO = policy_detailsMapper.toDto(policy_details);
        restPolicy_detailsMockMvc.perform(post("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policy_detailsDTO)))
            .andExpect(status().isCreated());

        // Validate the Policy_details in the database
        List<Policy_details> policy_detailsList = policy_detailsRepository.findAll();
        assertThat(policy_detailsList).hasSize(databaseSizeBeforeCreate + 1);
        Policy_details testPolicy_details = policy_detailsList.get(policy_detailsList.size() - 1);
        assertThat(testPolicy_details.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPolicy_details.getScheme_id()).isEqualTo(DEFAULT_SCHEME_ID);
        assertThat(testPolicy_details.getAgent_id()).isEqualTo(DEFAULT_AGENT_ID);
        assertThat(testPolicy_details.getCustomer_name()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testPolicy_details.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPolicy_details.getCommision()).isEqualTo(DEFAULT_COMMISION);
    }

    @Test
    public void createPolicy_detailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policy_detailsRepository.findAll().size();

        // Create the Policy_details with an existing ID
        policy_details.setId(UUID.randomUUID());
        Policy_detailsDTO policy_detailsDTO = policy_detailsMapper.toDto(policy_details);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicy_detailsMockMvc.perform(post("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policy_detailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Policy_details in the database
        List<Policy_details> policy_detailsList = policy_detailsRepository.findAll();
        assertThat(policy_detailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPolicy_details() throws Exception {
        // Initialize the database
        policy_detailsRepository.save(policy_details);

        // Get all the policy_detailsList
        restPolicy_detailsMockMvc.perform(get("/api/policy-details"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy_details.getId().toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].scheme_id").value(hasItem(DEFAULT_SCHEME_ID.toString())))
            .andExpect(jsonPath("$.[*].agent_id").value(hasItem(DEFAULT_AGENT_ID.toString())))
            .andExpect(jsonPath("$.[*].customer_name").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].commision").value(hasItem(DEFAULT_COMMISION.intValue())));
    }

    @Test
    public void getPolicy_details() throws Exception {
        // Initialize the database
        policy_detailsRepository.save(policy_details);

        // Get the policy_details
        restPolicy_detailsMockMvc.perform(get("/api/policy-details/{id}", policy_details.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policy_details.getId().toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.scheme_id").value(DEFAULT_SCHEME_ID.toString()))
            .andExpect(jsonPath("$.agent_id").value(DEFAULT_AGENT_ID.toString()))
            .andExpect(jsonPath("$.customer_name").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.commision").value(DEFAULT_COMMISION.intValue()));
    }

    @Test
    public void getNonExistingPolicy_details() throws Exception {
        // Get the policy_details
        restPolicy_detailsMockMvc.perform(get("/api/policy-details/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePolicy_details() throws Exception {
        // Initialize the database
        policy_detailsRepository.save(policy_details);
        int databaseSizeBeforeUpdate = policy_detailsRepository.findAll().size();

        // Update the policy_details
        Policy_details updatedPolicy_details = policy_detailsRepository.findOne(policy_details.getId());
        updatedPolicy_details
            .date(UPDATED_DATE)
            .scheme_id(UPDATED_SCHEME_ID)
            .agent_id(UPDATED_AGENT_ID)
            .customer_name(UPDATED_CUSTOMER_NAME)
            .amount(UPDATED_AMOUNT)
            .commision(UPDATED_COMMISION);
        Policy_detailsDTO policy_detailsDTO = policy_detailsMapper.toDto(updatedPolicy_details);

        restPolicy_detailsMockMvc.perform(put("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policy_detailsDTO)))
            .andExpect(status().isOk());

        // Validate the Policy_details in the database
        List<Policy_details> policy_detailsList = policy_detailsRepository.findAll();
        assertThat(policy_detailsList).hasSize(databaseSizeBeforeUpdate);
        Policy_details testPolicy_details = policy_detailsList.get(policy_detailsList.size() - 1);
        assertThat(testPolicy_details.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPolicy_details.getScheme_id()).isEqualTo(UPDATED_SCHEME_ID);
        assertThat(testPolicy_details.getAgent_id()).isEqualTo(UPDATED_AGENT_ID);
        assertThat(testPolicy_details.getCustomer_name()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testPolicy_details.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPolicy_details.getCommision()).isEqualTo(UPDATED_COMMISION);
    }

    @Test
    public void updateNonExistingPolicy_details() throws Exception {
        int databaseSizeBeforeUpdate = policy_detailsRepository.findAll().size();

        // Create the Policy_details
        Policy_detailsDTO policy_detailsDTO = policy_detailsMapper.toDto(policy_details);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicy_detailsMockMvc.perform(put("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policy_detailsDTO)))
            .andExpect(status().isCreated());

        // Validate the Policy_details in the database
        List<Policy_details> policy_detailsList = policy_detailsRepository.findAll();
        assertThat(policy_detailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePolicy_details() throws Exception {
        // Initialize the database
        policy_detailsRepository.save(policy_details);
        int databaseSizeBeforeDelete = policy_detailsRepository.findAll().size();

        // Get the policy_details
        restPolicy_detailsMockMvc.perform(delete("/api/policy-details/{id}", policy_details.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Policy_details> policy_detailsList = policy_detailsRepository.findAll();
        assertThat(policy_detailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Policy_details.class);
        Policy_details policy_details1 = new Policy_details();
        policy_details1.setId(UUID.randomUUID());
        Policy_details policy_details2 = new Policy_details();
        policy_details2.setId(policy_details1.getId());
        assertThat(policy_details1).isEqualTo(policy_details2);
        policy_details2.setId(UUID.randomUUID());
        assertThat(policy_details1).isNotEqualTo(policy_details2);
        policy_details1.setId(null);
        assertThat(policy_details1).isNotEqualTo(policy_details2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Policy_detailsDTO.class);
        Policy_detailsDTO policy_detailsDTO1 = new Policy_detailsDTO();
        policy_detailsDTO1.setId(UUID.randomUUID());
        Policy_detailsDTO policy_detailsDTO2 = new Policy_detailsDTO();
        assertThat(policy_detailsDTO1).isNotEqualTo(policy_detailsDTO2);
        policy_detailsDTO2.setId(policy_detailsDTO1.getId());
        assertThat(policy_detailsDTO1).isEqualTo(policy_detailsDTO2);
        policy_detailsDTO2.setId(UUID.randomUUID());
        assertThat(policy_detailsDTO1).isNotEqualTo(policy_detailsDTO2);
        policy_detailsDTO1.setId(null);
        assertThat(policy_detailsDTO1).isNotEqualTo(policy_detailsDTO2);
    }
}
