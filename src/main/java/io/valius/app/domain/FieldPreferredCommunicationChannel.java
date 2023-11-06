package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldPreferredCommunicationChannel.
 */
@Entity
@Table(name = "field_preferred_communication_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldPreferredCommunicationChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "preferred_communication_channel")
    private String preferredCommunicationChannel;

    @OneToMany(mappedBy = "preferredCommunicationChannel")
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

    public FieldPreferredCommunicationChannel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreferredCommunicationChannel() {
        return this.preferredCommunicationChannel;
    }

    public FieldPreferredCommunicationChannel preferredCommunicationChannel(String preferredCommunicationChannel) {
        this.setPreferredCommunicationChannel(preferredCommunicationChannel);
        return this;
    }

    public void setPreferredCommunicationChannel(String preferredCommunicationChannel) {
        this.preferredCommunicationChannel = preferredCommunicationChannel;
    }

    public Set<Survey> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<Survey> surveys) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setPreferredCommunicationChannel(null));
        }
        if (surveys != null) {
            surveys.forEach(i -> i.setPreferredCommunicationChannel(this));
        }
        this.jsons = surveys;
    }

    public FieldPreferredCommunicationChannel jsons(Set<Survey> surveys) {
        this.setJsons(surveys);
        return this;
    }

    public FieldPreferredCommunicationChannel addJson(Survey survey) {
        this.jsons.add(survey);
        survey.setPreferredCommunicationChannel(this);
        return this;
    }

    public FieldPreferredCommunicationChannel removeJson(Survey survey) {
        this.jsons.remove(survey);
        survey.setPreferredCommunicationChannel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldPreferredCommunicationChannel)) {
            return false;
        }
        return id != null && id.equals(((FieldPreferredCommunicationChannel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldPreferredCommunicationChannel{" +
            "id=" + getId() +
            ", preferredCommunicationChannel='" + getPreferredCommunicationChannel() + "'" +
            "}";
    }
}
