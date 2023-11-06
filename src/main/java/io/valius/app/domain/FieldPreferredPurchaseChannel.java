package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldPreferredPurchaseChannel.
 */
@Entity
@Table(name = "field_preferred_purchase_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldPreferredPurchaseChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "preferred_purchase_channel")
    private String preferredPurchaseChannel;

    @OneToMany(mappedBy = "preferredPurchaseChannel")
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

    public FieldPreferredPurchaseChannel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreferredPurchaseChannel() {
        return this.preferredPurchaseChannel;
    }

    public FieldPreferredPurchaseChannel preferredPurchaseChannel(String preferredPurchaseChannel) {
        this.setPreferredPurchaseChannel(preferredPurchaseChannel);
        return this;
    }

    public void setPreferredPurchaseChannel(String preferredPurchaseChannel) {
        this.preferredPurchaseChannel = preferredPurchaseChannel;
    }

    public Set<Survey> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<Survey> surveys) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setPreferredPurchaseChannel(null));
        }
        if (surveys != null) {
            surveys.forEach(i -> i.setPreferredPurchaseChannel(this));
        }
        this.jsons = surveys;
    }

    public FieldPreferredPurchaseChannel jsons(Set<Survey> surveys) {
        this.setJsons(surveys);
        return this;
    }

    public FieldPreferredPurchaseChannel addJson(Survey survey) {
        this.jsons.add(survey);
        survey.setPreferredPurchaseChannel(this);
        return this;
    }

    public FieldPreferredPurchaseChannel removeJson(Survey survey) {
        this.jsons.remove(survey);
        survey.setPreferredPurchaseChannel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldPreferredPurchaseChannel)) {
            return false;
        }
        return id != null && id.equals(((FieldPreferredPurchaseChannel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldPreferredPurchaseChannel{" +
            "id=" + getId() +
            ", preferredPurchaseChannel='" + getPreferredPurchaseChannel() + "'" +
            "}";
    }
}
