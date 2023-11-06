package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldBuyingCriteriaWeighting.
 */
@Entity
@Table(name = "field_buying_criteria_weighting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldBuyingCriteriaWeighting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buying_criteria_weighting")
    private String buyingCriteriaWeighting;

    @OneToMany(mappedBy = "buyingCriteriaWeighting")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "user", "buyingCriteriaWeighting", "preferredPurchaseChannel", "preferredCommunicationChannel" },
        allowSetters = true
    )
    private Set<Survey> jsons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldBuyingCriteriaWeighting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyingCriteriaWeighting() {
        return this.buyingCriteriaWeighting;
    }

    public FieldBuyingCriteriaWeighting buyingCriteriaWeighting(String buyingCriteriaWeighting) {
        this.setBuyingCriteriaWeighting(buyingCriteriaWeighting);
        return this;
    }

    public void setBuyingCriteriaWeighting(String buyingCriteriaWeighting) {
        this.buyingCriteriaWeighting = buyingCriteriaWeighting;
    }

    public Set<Survey> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<Survey> surveys) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setBuyingCriteriaWeighting(null));
        }
        if (surveys != null) {
            surveys.forEach(i -> i.setBuyingCriteriaWeighting(this));
        }
        this.jsons = surveys;
    }

    public FieldBuyingCriteriaWeighting jsons(Set<Survey> surveys) {
        this.setJsons(surveys);
        return this;
    }

    public FieldBuyingCriteriaWeighting addJson(Survey survey) {
        this.jsons.add(survey);
        survey.setBuyingCriteriaWeighting(this);
        return this;
    }

    public FieldBuyingCriteriaWeighting removeJson(Survey survey) {
        this.jsons.remove(survey);
        survey.setBuyingCriteriaWeighting(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldBuyingCriteriaWeighting)) {
            return false;
        }
        return id != null && id.equals(((FieldBuyingCriteriaWeighting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldBuyingCriteriaWeighting{" +
            "id=" + getId() +
            ", buyingCriteriaWeighting='" + getBuyingCriteriaWeighting() + "'" +
            "}";
    }
}
