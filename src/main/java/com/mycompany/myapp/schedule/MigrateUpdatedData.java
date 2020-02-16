package com.mycompany.myapp.schedule;

import com.mycompany.myapp.repository.OfferRepository;
import com.mycompany.myapp.service.MigrateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Component
@Profile("migrate")
public class MigrateUpdatedData {
    public static Long lastUpdateSecond;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private MigrateDataService migrateDataService;
    @Scheduled(fixedDelay =30000)
    @Transactional
    public void migrateUpdateData(){
        if (lastUpdateSecond == null) {
            lastUpdateSecond = Calendar.getInstance().getTimeInMillis();
        }
        else{
            offerRepository.streamByUpdatedAtAndUpdatedAtNotNullOrCreatedAtAndCreatedAtNotNull(lastUpdateSecond-30000,lastUpdateSecond-30000)
                .forEach(o->{
                    migrateDataService.setOrderForOffer(o.getProduct());
                });
            lastUpdateSecond=Calendar.getInstance().getTimeInMillis();
        }
    }
}
