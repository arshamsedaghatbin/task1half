package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ProductUser;
import com.mycompany.myapp.service.ProductUserService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ProductUser}.
 */
@RestController
@RequestMapping("/api")
public class ProductUserResource {

    private final Logger log = LoggerFactory.getLogger(ProductUserResource.class);

    private static final String ENTITY_NAME = "productUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductUserService productUserService;

    public ProductUserResource(ProductUserService productUserService) {
        this.productUserService = productUserService;
    }

    /**
     * {@code POST  /product-users} : Create a new productUser.
     *
     * @param productUser the productUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productUser, or with status {@code 400 (Bad Request)} if the productUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-users")
    public ResponseEntity<ProductUser> createProductUser(@RequestBody ProductUser productUser) throws URISyntaxException {
        log.debug("REST request to save ProductUser : {}", productUser);
        if (productUser.getId() != null) {
            throw new BadRequestAlertException("A new productUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductUser result = productUserService.save(productUser);
        return ResponseEntity.created(new URI("/api/product-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-users} : Updates an existing productUser.
     *
     * @param productUser the productUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productUser,
     * or with status {@code 400 (Bad Request)} if the productUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-users")
    public ResponseEntity<ProductUser> updateProductUser(@RequestBody ProductUser productUser) throws URISyntaxException {
        log.debug("REST request to update ProductUser : {}", productUser);
        if (productUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductUser result = productUserService.save(productUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-users} : get all the productUsers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productUsers in body.
     */
    @GetMapping("/product-users")
    public List<ProductUser> getAllProductUsers() {
        log.debug("REST request to get all ProductUsers");
        return productUserService.findAll();
    }

    /**
     * {@code GET  /product-users/:id} : get the "id" productUser.
     *
     * @param id the id of the productUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-users/{id}")
    public ResponseEntity<ProductUser> getProductUser(@PathVariable Long id) {
        log.debug("REST request to get ProductUser : {}", id);
        Optional<ProductUser> productUser = productUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productUser);
    }

    /**
     * {@code DELETE  /product-users/:id} : delete the "id" productUser.
     *
     * @param id the id of the productUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-users/{id}")
    public ResponseEntity<Void> deleteProductUser(@PathVariable Long id) {
        log.debug("REST request to delete ProductUser : {}", id);
        productUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
