package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.enumeration.SourceType;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.service.OfferService;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service Implementation for managing {@link Offer}.
 */
@Service
public class OfferServiceImpl implements OfferService, CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private ProductRepository productRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Save a offer.
     *
     * @param offer the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Offer save(Offer offer) {
        log.debug("Request to save Offer : {}", offer);
        return offerRepository.save(offer);
    }

    /**
     * Get all the offers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Offer> findAll() {
        log.debug("Request to get all Offers");
        return offerRepository.findAll();
    }


    /**
     * Get one offer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id);
    }

    /**
     * Delete the offer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.deleteById(id);
    }

    @Override
    public void migrateOfferData(){
        productRepository.streamAll()
            .forEach((p) -> {
                setOrderForOffer(p);
            });
    }

    private void setOrderForOffer(Product p) {
        AtomicInteger x = new AtomicInteger();
        offerRepository.streamByProduct(p)
            .forEach(o -> {
                if (o.getProductUser().getSource().equals(SourceType.FREE)) {
                    x.getAndIncrement();
                    o.setOrderOffer(x.get() );
                } else {
                    o.setOrderOffer(0);
                }
                offerRepository.save(o);
            });
    }

    @Override
    @Transactional
    public void run(String... args){
        if (environment.acceptsProfiles(Profiles.of("migrate"))) {
            migrateOfferData();
        }
    }
}
