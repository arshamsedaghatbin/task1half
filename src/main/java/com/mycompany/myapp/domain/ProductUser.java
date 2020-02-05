package com.mycompany.myapp.domain;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.SourceType;

/**
 * Task entity.\n@author The JHipster team.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "product_user")
public class ProductUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private SourceType source;

    @OneToMany(mappedBy = "productUser")
    private Set<Offer> offers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceType getSource() {
        return source;
    }

    public ProductUser source(SourceType source) {
        this.source = source;
        return this;
    }

    public void setSource(SourceType source) {
        this.source = source;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public ProductUser offers(Set<Offer> offers) {
        this.offers = offers;
        return this;
    }

    public ProductUser addOffers(Offer offer) {
        this.offers.add(offer);
        offer.setProductUser(this);
        return this;
    }

    public ProductUser removeOffers(Offer offer) {
        this.offers.remove(offer);
        offer.setProductUser(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductUser)) {
            return false;
        }
        return id != null && id.equals(((ProductUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", source='" + getSource() + "'" +
            "}";
    }
}
