package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private ProductUser productUser;

    private Integer orderOffer;


    public Integer getOrderOffer() {
        return orderOffer;
    }

    public void setOrderOffer(Integer orderOffer) {
        this.orderOffer = orderOffer;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Offer departmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Product getProduct() {
        return product;
    }

    public Offer product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductUser getProductUser() {
        return productUser;
    }

    public Offer productUser(ProductUser productUser) {
        this.productUser = productUser;
        return this;
    }

    public void setProductUser(ProductUser productUser) {
        this.productUser = productUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", departmentName='" + getDepartmentName() + "'" +
            "}";
    }
}
