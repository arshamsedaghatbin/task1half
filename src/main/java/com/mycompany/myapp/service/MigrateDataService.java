package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.enumeration.SourceType;
import com.mycompany.myapp.repository.OfferRepository;
import com.mycompany.myapp.repository.ProductRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MigrateDataService implements CommandLineRunner {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (environment.acceptsProfiles(Profiles.of("migrate"))) {
            migrateOfferData();
        }
        }


    public void migrateOfferData(){
        productRepository.streamAll()
            .forEach((p) -> {
                setOrderForOffer(p);
            });
    }

    public void setOrderForOffer(Product p) {
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
}
