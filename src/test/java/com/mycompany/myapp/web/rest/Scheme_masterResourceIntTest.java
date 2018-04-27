package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.DemoApp;

import com.mycompany.myapp.domain.Scheme_master;
import com.mycompany.myapp.repository.Scheme_masterRepository;
import com.mycompany.myapp.service.Scheme_masterService;
import com.mycompany.myapp.service.dto.Scheme_masterDTO;
import com.mycompany.myapp.service.mapper.Scheme_masterMapper;
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
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Scheme_masterResource REST controller.
 *
 * @see Scheme_masterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class Scheme_masterResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_COMMISION = new BigDecimal(1);
    private static final BigDecimal UPDATED_COMMISION = new BigDecimal(2);

    @Autowired
    private Scheme_masterRepository scheme_masterRepository;

    @Autowired
    private Scheme_masterMapper scheme_masterMapper;

    @Autowired
    private Scheme_masterService scheme_masterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restScheme_masterMockMvc;

    private Scheme_master scheme_master;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Scheme_masterResource scheme_masterResource = new Scheme_masterResource(scheme_masterService);
        this.restScheme_masterMockMvc = MockMvcBuilders.standaloneSetup(scheme_masterResource)
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
    public static Scheme_master createEntity() {
        Scheme_master scheme_master = new Scheme_master()
            .name(DEFAULT_NAME)
            .commision(DEFAULT_COMMISION);
        return scheme_master;
    }

    @Before
    public void initTest() {
        scheme_masterRepository.deleteAll();
        scheme_master = createEntity();
    }

    @Test
    public void createScheme_master() throws Exception {
        int databaseSizeBeforeCreate = scheme_masterRepository.findAll().size();

        // Create the Scheme_master
        Scheme_masterDTO scheme_masterDTO = scheme_masterMapper.toDto(scheme_master);
        restScheme_masterMockMvc.perform(post("/api/scheme-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheme_masterDTO)))
            .andExpect(status().isCreated());

        // Validate the Scheme_master in the database
        List<Scheme_master> scheme_masterList = scheme_masterRepository.findAll();
        assertThat(scheme_masterList).hasSize(databaseSizeBeforeCreate + 1);
        Scheme_master testScheme_master = scheme_masterList.get(scheme_masterList.size() - 1);
        assertThat(testScheme_master.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScheme_master.getCommision()).isEqualTo(DEFAULT_COMMISION);
    }

    @Test
    public void createScheme_masterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheme_masterRepository.findAll().size();

        // Create the Scheme_master with an existing ID
        scheme_master.setId(UUID.randomUUID());
        Scheme_masterDTO scheme_masterDTO = scheme_masterMapper.toDto(scheme_master);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheme_masterMockMvc.perform(post("/api/scheme-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheme_masterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scheme_master in the database
        List<Scheme_master> scheme_masterList = scheme_masterRepository.findAll();
        assertThat(scheme_masterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllScheme_masters() throws Exception {
        // Initialize the database
        scheme_masterRepository.save(scheme_master);

        // Get all the scheme_masterList
        restScheme_masterMockMvc.perform(get("/api/scheme-masters"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheme_master.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].commision").value(hasItem(DEFAULT_COMMISION.intValue())));
    }

    @Test
    public void getScheme_master() throws Exception {
        // Initialize the database
        scheme_masterRepository.save(scheme_master);

        // Get the scheme_master
        restScheme_masterMockMvc.perform(get("/api/scheme-masters/{id}", scheme_master.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheme_master.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.commision").value(DEFAULT_COMMISION.intValue()));
    }

    @Test
    public void getNonExistingScheme_master() throws Exception {
        // Get the scheme_master
        restScheme_masterMockMvc.perform(get("/api/scheme-masters/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateScheme_master() throws Exception {
        // Initialize the database
        scheme_masterRepository.save(scheme_master);
        int databaseSizeBeforeUpdate = scheme_masterRepository.findAll().size();

        // Update the scheme_master
        Scheme_master updatedScheme_master = scheme_masterRepository.findOne(scheme_master.getId());
        updatedScheme_master
            .name(UPDATED_NAME)
            .commision(UPDATED_COMMISION);
        Scheme_masterDTO scheme_masterDTO = scheme_masterMapper.toDto(updatedScheme_master);

        restScheme_masterMockMvc.perform(put("/api/scheme-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheme_masterDTO)))
            .andExpect(status().isOk());

        // Validate the Scheme_master in the database
        List<Scheme_master> scheme_masterList = scheme_masterRepository.findAll();
        assertThat(scheme_masterList).hasSize(databaseSizeBeforeUpdate);
        Scheme_master testScheme_master = scheme_masterList.get(scheme_masterList.size() - 1);
        assertThat(testScheme_master.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScheme_master.getCommision()).isEqualTo(UPDATED_COMMISION);
    }

    @Test
    public void updateNonExistingScheme_master() throws Exception {
        int databaseSizeBeforeUpdate = scheme_masterRepository.findAll().size();

        // Create the Scheme_master
        Scheme_masterDTO scheme_masterDTO = scheme_masterMapper.toDto(scheme_master);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScheme_masterMockMvc.perform(put("/api/scheme-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheme_masterDTO)))
            .andExpect(status().isCreated());

        // Validate the Scheme_master in the database
        List<Scheme_master> scheme_masterList = scheme_masterRepository.findAll();
        assertThat(scheme_masterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteScheme_master() throws Exception {
        // Initialize the database
        scheme_masterRepository.save(scheme_master);
        int databaseSizeBeforeDelete = scheme_masterRepository.findAll().size();

        // Get the scheme_master
        restScheme_masterMockMvc.perform(delete("/api/scheme-masters/{id}", scheme_master.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Scheme_master> scheme_masterList = scheme_masterRepository.findAll();
        assertThat(scheme_masterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scheme_master.class);
        Scheme_master scheme_master1 = new Scheme_master();
        scheme_master1.setId(UUID.randomUUID());
        Scheme_master scheme_master2 = new Scheme_master();
        scheme_master2.setId(scheme_master1.getId());
        assertThat(scheme_master1).isEqualTo(scheme_master2);
        scheme_master2.setId(UUID.randomUUID());
        assertThat(scheme_master1).isNotEqualTo(scheme_master2);
        scheme_master1.setId(null);
        assertThat(scheme_master1).isNotEqualTo(scheme_master2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scheme_masterDTO.class);
        Scheme_masterDTO scheme_masterDTO1 = new Scheme_masterDTO();
        scheme_masterDTO1.setId(UUID.randomUUID());
        Scheme_masterDTO scheme_masterDTO2 = new Scheme_masterDTO();
        assertThat(scheme_masterDTO1).isNotEqualTo(scheme_masterDTO2);
        scheme_masterDTO2.setId(scheme_masterDTO1.getId());
        assertThat(scheme_masterDTO1).isEqualTo(scheme_masterDTO2);
        scheme_masterDTO2.setId(UUID.randomUUID());
        assertThat(scheme_masterDTO1).isNotEqualTo(scheme_masterDTO2);
        scheme_masterDTO1.setId(null);
        assertThat(scheme_masterDTO1).isNotEqualTo(scheme_masterDTO2);
    }
}
