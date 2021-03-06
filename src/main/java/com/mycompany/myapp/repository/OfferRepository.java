package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * Spring Data  repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("select o from Offer o where o.product= :p1")
    Stream<Offer> streamByProduct(@Param(value = "p1") Product product);

    @Query("select o from Offer o where o.updatedAt > :p1 and o.updatedAt is not null or o.createdAt> :p2 and o.createdAt is not null ")
    Stream<Offer> streamByUpdatedAtAndUpdatedAtNotNullOrCreatedAtAndCreatedAtNotNull(@Param(value = "p1") Long updateEpoch,@Param(value = "p2") Long createEpoch);

}
