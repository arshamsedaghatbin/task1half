package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;


/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select t from Product t")
    Stream<Product> streamAll();

}
