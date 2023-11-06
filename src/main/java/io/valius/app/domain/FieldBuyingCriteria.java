package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldBuyingCriteria.
 */
@Entity
@Table(name = "field_buying_criteria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldBuyingCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buying_criteria")
    private String buyingCriteria;

    @OneToMany(mappedBy = "buyingCriteria")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "buyingCriteria" }, allowSetters = true)
    private Set<LevelTwo> jsons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldBuyingCriteria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyingCriteria() {
        return this.buyingCriteria;
    }

    public FieldBuyingCriteria buyingCriteria(String buyingCriteria) {
        this.setBuyingCriteria(buyingCriteria);
        return this;
    }

    public void setBuyingCriteria(String buyingCriteria) {
        this.buyingCriteria = buyingCriteria;
    }

    public Set<LevelTwo> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<LevelTwo> levelTwos) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setBuyingCriteria(null));
        }
        if (levelTwos != null) {
            levelTwos.forEach(i -> i.setBuyingCriteria(this));
        }
        this.jsons = levelTwos;
    }

    public FieldBuyingCriteria jsons(Set<LevelTwo> levelTwos) {
        this.setJsons(levelTwos);
        return this;
    }

    public FieldBuyingCriteria addJson(LevelTwo levelTwo) {
        this.jsons.add(levelTwo);
        levelTwo.setBuyingCriteria(this);
        return this;
    }

    public FieldBuyingCriteria removeJson(LevelTwo levelTwo) {
        this.jsons.remove(levelTwo);
        levelTwo.setBuyingCriteria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldBuyingCriteria)) {
            return false;
        }
        return id != null && id.equals(((FieldBuyingCriteria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldBuyingCriteria{" +
            "id=" + getId() +
            ", buyingCriteria='" + getBuyingCriteria() + "'" +
            "}";
    }
}
