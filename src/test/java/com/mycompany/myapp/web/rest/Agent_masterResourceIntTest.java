package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.DemoApp;

import com.mycompany.myapp.domain.Agent_master;
import com.mycompany.myapp.repository.Agent_masterRepository;
import com.mycompany.myapp.service.Agent_masterService;
import com.mycompany.myapp.service.dto.Agent_masterDTO;
import com.mycompany.myapp.service.mapper.Agent_masterMapper;
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

import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Agent_masterResource REST controller.
 *
 * @see Agent_masterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class Agent_masterResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Agent_masterRepository agent_masterRepository;

    @Autowired
    private Agent_masterMapper agent_masterMapper;

    @Autowired
    private Agent_masterService agent_masterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAgent_masterMockMvc;

    private Agent_master agent_master;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Agent_masterResource agent_masterResource = new Agent_masterResource(agent_masterService);
        this.restAgent_masterMockMvc = MockMvcBuilders.standaloneSetup(agent_masterResource)
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
    public static Agent_master createEntity() {
        Agent_master agent_master = new Agent_master()
            .name(DEFAULT_NAME);
        return agent_master;
    }

    @Before
    public void initTest() {
        agent_masterRepository.deleteAll();
        agent_master = createEntity();
    }

    @Test
    public void createAgent_master() throws Exception {
        int databaseSizeBeforeCreate = agent_masterRepository.findAll().size();

        // Create the Agent_master
        Agent_masterDTO agent_masterDTO = agent_masterMapper.toDto(agent_master);
        restAgent_masterMockMvc.perform(post("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent_masterDTO)))
            .andExpect(status().isCreated());

        // Validate the Agent_master in the database
        List<Agent_master> agent_masterList = agent_masterRepository.findAll();
        assertThat(agent_masterList).hasSize(databaseSizeBeforeCreate + 1);
        Agent_master testAgent_master = agent_masterList.get(agent_masterList.size() - 1);
        assertThat(testAgent_master.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createAgent_masterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agent_masterRepository.findAll().size();

        // Create the Agent_master with an existing ID
        agent_master.setId(UUID.randomUUID());
        Agent_masterDTO agent_masterDTO = agent_masterMapper.toDto(agent_master);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgent_masterMockMvc.perform(post("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent_masterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agent_master in the database
        List<Agent_master> agent_masterList = agent_masterRepository.findAll();
        assertThat(agent_masterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAgent_masters() throws Exception {
        // Initialize the database
        agent_masterRepository.save(agent_master);

        // Get all the agent_masterList
        restAgent_masterMockMvc.perform(get("/api/agent-masters"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agent_master.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getAgent_master() throws Exception {
        // Initialize the database
        agent_masterRepository.save(agent_master);

        // Get the agent_master
        restAgent_masterMockMvc.perform(get("/api/agent-masters/{id}", agent_master.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agent_master.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingAgent_master() throws Exception {
        // Get the agent_master
        restAgent_masterMockMvc.perform(get("/api/agent-masters/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAgent_master() throws Exception {
        // Initialize the database
        agent_masterRepository.save(agent_master);
        int databaseSizeBeforeUpdate = agent_masterRepository.findAll().size();

        // Update the agent_master
        Agent_master updatedAgent_master = agent_masterRepository.findOne(agent_master.getId());
        updatedAgent_master
            .name(UPDATED_NAME);
        Agent_masterDTO agent_masterDTO = agent_masterMapper.toDto(updatedAgent_master);

        restAgent_masterMockMvc.perform(put("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent_masterDTO)))
            .andExpect(status().isOk());

        // Validate the Agent_master in the database
        List<Agent_master> agent_masterList = agent_masterRepository.findAll();
        assertThat(agent_masterList).hasSize(databaseSizeBeforeUpdate);
        Agent_master testAgent_master = agent_masterList.get(agent_masterList.size() - 1);
        assertThat(testAgent_master.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingAgent_master() throws Exception {
        int databaseSizeBeforeUpdate = agent_masterRepository.findAll().size();

        // Create the Agent_master
        Agent_masterDTO agent_masterDTO = agent_masterMapper.toDto(agent_master);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgent_masterMockMvc.perform(put("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent_masterDTO)))
            .andExpect(status().isCreated());

        // Validate the Agent_master in the database
        List<Agent_master> agent_masterList = agent_masterRepository.findAll();
        assertThat(agent_masterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAgent_master() throws Exception {
        // Initialize the database
        agent_masterRepository.save(agent_master);
        int databaseSizeBeforeDelete = agent_masterRepository.findAll().size();

        // Get the agent_master
        restAgent_masterMockMvc.perform(delete("/api/agent-masters/{id}", agent_master.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agent_master> agent_masterList = agent_masterRepository.findAll();
        assertThat(agent_masterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agent_master.class);
        Agent_master agent_master1 = new Agent_master();
        agent_master1.setId(UUID.randomUUID());
        Agent_master agent_master2 = new Agent_master();
        agent_master2.setId(agent_master1.getId());
        assertThat(agent_master1).isEqualTo(agent_master2);
        agent_master2.setId(UUID.randomUUID());
        assertThat(agent_master1).isNotEqualTo(agent_master2);
        agent_master1.setId(null);
        assertThat(agent_master1).isNotEqualTo(agent_master2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agent_masterDTO.class);
        Agent_masterDTO agent_masterDTO1 = new Agent_masterDTO();
        agent_masterDTO1.setId(UUID.randomUUID());
        Agent_masterDTO agent_masterDTO2 = new Agent_masterDTO();
        assertThat(agent_masterDTO1).isNotEqualTo(agent_masterDTO2);
        agent_masterDTO2.setId(agent_masterDTO1.getId());
        assertThat(agent_masterDTO1).isEqualTo(agent_masterDTO2);
        agent_masterDTO2.setId(UUID.randomUUID());
        assertThat(agent_masterDTO1).isNotEqualTo(agent_masterDTO2);
        agent_masterDTO1.setId(null);
        assertThat(agent_masterDTO1).isNotEqualTo(agent_masterDTO2);
    }
}
