package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ProductUserService;
import com.mycompany.myapp.domain.ProductUser;
import com.mycompany.myapp.repository.ProductUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductUser}.
 */
@Service
@Transactional
public class ProductUserServiceImpl implements ProductUserService {

    private final Logger log = LoggerFactory.getLogger(ProductUserServiceImpl.class);

    private final ProductUserRepository productUserRepository;

    public ProductUserServiceImpl(ProductUserRepository productUserRepository) {
        this.productUserRepository = productUserRepository;
    }

    /**
     * Save a productUser.
     *
     * @param productUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductUser save(ProductUser productUser) {
        log.debug("Request to save ProductUser : {}", productUser);
        return productUserRepository.save(productUser);
    }

    /**
     * Get all the productUsers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductUser> findAll() {
        log.debug("Request to get all ProductUsers");
        return productUserRepository.findAll();
    }


    /**
     * Get one productUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductUser> findOne(Long id) {
        log.debug("Request to get ProductUser : {}", id);
        return productUserRepository.findById(id);
    }

    /**
     * Delete the productUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductUser : {}", id);
        productUserRepository.deleteById(id);
    }
}
