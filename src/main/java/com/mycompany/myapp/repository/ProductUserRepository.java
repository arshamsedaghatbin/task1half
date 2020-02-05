package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductUserRepository extends JpaRepository<ProductUser, Long> {

}
