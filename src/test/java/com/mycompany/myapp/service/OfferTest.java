package com.mycompany.myapp.service;


import com.mycompany.myapp.Task1HalfApp;
import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.ProductUser;
import com.mycompany.myapp.domain.enumeration.SourceType;
import com.mycompany.myapp.repository.CustomerRepository;
import com.mycompany.myapp.repository.OfferRepository;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.repository.ProductUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.schema.Example;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Task1HalfApp.class)
public class OfferTest {

    @Autowired
    private ProductUserRepository  productUserRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferService offerService;
    @Test
    @Transactional
    public void test_migrate_data(){
        Set<Product> products=new HashSet<>();
        Customer customer=new Customer();
        customer.setName("arsham");
        customer.setLastName("sedaghat");

        Product p1=new Product();
        p1.setCustomer(customer);
        p1.setName("p1");
        productRepository.save(p1);
        products.add(p1);
        customer.setProducts(products);
        customerRepository.save(customer);

        Set<Offer> offers1=new HashSet<>();
        ProductUser productUser=new ProductUser();
        productUser.setSource(SourceType.FREE);
        Offer o1=new Offer();
        o1.setProductUser(productUser);
        o1.setProduct(p1);
        o1.setDepartmentName("dTest");
        offerRepository.save(o1);
        offers1.add(o1);
        productUser.setOffers(offers1);
        productUserRepository.save(productUser);

        Set<Offer> offers2=new HashSet<>();
        ProductUser productUser2=new ProductUser();
        productUser2.setSource(SourceType.PREMIUM);
        Offer o2=new Offer();
        o2.setProductUser(productUser2);
        o2.setProduct(p1);
        o2.setDepartmentName("dTest");
        offerRepository.save(o2);
        offers2.add(o2);
        productUser2.setOffers(offers2);
        productUserRepository.save(productUser2);

        Set<Offer> offers3=new HashSet<>();
        ProductUser productUser3=new ProductUser();
        productUser3.setSource(SourceType.FREE);
        Offer o3=new Offer();
        o3.setProductUser(productUser3);
        o3.setProduct(p1);
        o3.setDepartmentName("dTest");
        offerRepository.save(o3);
        offers3.add(o3);
        productUser3.setOffers(offers3);
        productUserRepository.save(productUser3);


        offerService.migrateOfferData();
        assertThat(offerRepository.findById(1l).get().getOrderOffer()).isEqualTo(1);
        assertThat(offerRepository.findById(2l).get().getOrderOffer()).isEqualTo(0);
        assertThat(offerRepository.findById(3l).get().getOrderOffer()).isEqualTo(2);

    }
}
