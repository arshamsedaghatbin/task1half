package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.Product;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Offer}.
 */
public interface OfferService {

    /**
     * Save a offer.
     *
     * @param offer the entity to save.
     * @return the persisted entity.
     */
    Offer save(Offer offer);

    /**
     * Get all the offers.
     *
     * @return the list of entities.
     */
    List<Offer> findAll();


    /**
     * Get the "id" offer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Offer> findOne(Long id);

    /**
     * Delete the "id" offer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void migrateOfferData();

}
