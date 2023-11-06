package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldProductype.
 */
@Entity
@Table(name = "field_productype")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldProductype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "product_type")
    private String productType;

    @OneToMany(mappedBy = "productType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "companyObjectives", "kpis", "productType" }, allowSetters = true)
    private Set<LevelOne> jsons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldProductype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductType() {
        return this.productType;
    }

    public FieldProductype productType(String productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Set<LevelOne> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<LevelOne> levelOnes) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setProductType(null));
        }
        if (levelOnes != null) {
            levelOnes.forEach(i -> i.setProductType(this));
        }
        this.jsons = levelOnes;
    }

    public FieldProductype jsons(Set<LevelOne> levelOnes) {
        this.setJsons(levelOnes);
        return this;
    }

    public FieldProductype addJson(LevelOne levelOne) {
        this.jsons.add(levelOne);
        levelOne.setProductType(this);
        return this;
    }

    public FieldProductype removeJson(LevelOne levelOne) {
        this.jsons.remove(levelOne);
        levelOne.setProductType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldProductype)) {
            return false;
        }
        return id != null && id.equals(((FieldProductype) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldProductype{" +
            "id=" + getId() +
            ", productType='" + getProductType() + "'" +
            "}";
    }
}
