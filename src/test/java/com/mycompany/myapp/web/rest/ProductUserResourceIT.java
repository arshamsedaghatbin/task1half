package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Task1HalfApp;
import com.mycompany.myapp.domain.ProductUser;
import com.mycompany.myapp.repository.ProductUserRepository;
import com.mycompany.myapp.service.ProductUserService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.SourceType;
/**
 * Integration tests for the {@link ProductUserResource} REST controller.
 */
@SpringBootTest(classes = Task1HalfApp.class)
public class ProductUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final SourceType DEFAULT_SOURCE = SourceType.FREE;
    private static final SourceType UPDATED_SOURCE = SourceType.PREMIUM;

    @Autowired
    private ProductUserRepository productUserRepository;

    @Autowired
    private ProductUserService productUserService;

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

    private MockMvc restProductUserMockMvc;

    private ProductUser productUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductUserResource productUserResource = new ProductUserResource(productUserService);
        this.restProductUserMockMvc = MockMvcBuilders.standaloneSetup(productUserResource)
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
    public static ProductUser createEntity(EntityManager em) {
        ProductUser productUser = new ProductUser()
            .name(DEFAULT_NAME)
            .source(DEFAULT_SOURCE);
        return productUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductUser createUpdatedEntity(EntityManager em) {
        ProductUser productUser = new ProductUser()
            .name(UPDATED_NAME)
            .source(UPDATED_SOURCE);
        return productUser;
    }

    @BeforeEach
    public void initTest() {
        productUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductUser() throws Exception {
        int databaseSizeBeforeCreate = productUserRepository.findAll().size();

        // Create the ProductUser
        restProductUserMockMvc.perform(post("/api/product-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productUser)))
            .andExpect(status().isCreated());

        // Validate the ProductUser in the database
        List<ProductUser> productUserList = productUserRepository.findAll();
        assertThat(productUserList).hasSize(databaseSizeBeforeCreate + 1);
        ProductUser testProductUser = productUserList.get(productUserList.size() - 1);
        assertThat(testProductUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductUser.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    public void createProductUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productUserRepository.findAll().size();

        // Create the ProductUser with an existing ID
        productUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductUserMockMvc.perform(post("/api/product-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productUser)))
            .andExpect(status().isBadRequest());

        // Validate the ProductUser in the database
        List<ProductUser> productUserList = productUserRepository.findAll();
        assertThat(productUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductUsers() throws Exception {
        // Initialize the database
        productUserRepository.saveAndFlush(productUser);

        // Get all the productUserList
        restProductUserMockMvc.perform(get("/api/product-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductUser() throws Exception {
        // Initialize the database
        productUserRepository.saveAndFlush(productUser);

        // Get the productUser
        restProductUserMockMvc.perform(get("/api/product-users/{id}", productUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductUser() throws Exception {
        // Get the productUser
        restProductUserMockMvc.perform(get("/api/product-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductUser() throws Exception {
        // Initialize the database
        productUserService.save(productUser);

        int databaseSizeBeforeUpdate = productUserRepository.findAll().size();

        // Update the productUser
        ProductUser updatedProductUser = productUserRepository.findById(productUser.getId()).get();
        // Disconnect from session so that the updates on updatedProductUser are not directly saved in db
        em.detach(updatedProductUser);
        updatedProductUser
            .name(UPDATED_NAME)
            .source(UPDATED_SOURCE);

        restProductUserMockMvc.perform(put("/api/product-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductUser)))
            .andExpect(status().isOk());

        // Validate the ProductUser in the database
        List<ProductUser> productUserList = productUserRepository.findAll();
        assertThat(productUserList).hasSize(databaseSizeBeforeUpdate);
        ProductUser testProductUser = productUserList.get(productUserList.size() - 1);
        assertThat(testProductUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductUser.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductUser() throws Exception {
        int databaseSizeBeforeUpdate = productUserRepository.findAll().size();

        // Create the ProductUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductUserMockMvc.perform(put("/api/product-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productUser)))
            .andExpect(status().isBadRequest());

        // Validate the ProductUser in the database
        List<ProductUser> productUserList = productUserRepository.findAll();
        assertThat(productUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductUser() throws Exception {
        // Initialize the database
        productUserService.save(productUser);

        int databaseSizeBeforeDelete = productUserRepository.findAll().size();

        // Delete the productUser
        restProductUserMockMvc.perform(delete("/api/product-users/{id}", productUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductUser> productUserList = productUserRepository.findAll();
        assertThat(productUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
