package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldCompanyObjectives.
 */
@Entity
@Table(name = "field_company_objectives")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldCompanyObjectives implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "company_objectives")
    private String companyObjectives;

    @OneToMany(mappedBy = "companyObjectives")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "companyObjectives", "kpis", "productType" }, allowSetters = true)
    private Set<LevelOne> jsons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldCompanyObjectives id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyObjectives() {
        return this.companyObjectives;
    }

    public FieldCompanyObjectives companyObjectives(String companyObjectives) {
        this.setCompanyObjectives(companyObjectives);
        return this;
    }

    public void setCompanyObjectives(String companyObjectives) {
        this.companyObjectives = companyObjectives;
    }

    public Set<LevelOne> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<LevelOne> levelOnes) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setCompanyObjectives(null));
        }
        if (levelOnes != null) {
            levelOnes.forEach(i -> i.setCompanyObjectives(this));
        }
        this.jsons = levelOnes;
    }

    public FieldCompanyObjectives jsons(Set<LevelOne> levelOnes) {
        this.setJsons(levelOnes);
        return this;
    }

    public FieldCompanyObjectives addJson(LevelOne levelOne) {
        this.jsons.add(levelOne);
        levelOne.setCompanyObjectives(this);
        return this;
    }

    public FieldCompanyObjectives removeJson(LevelOne levelOne) {
        this.jsons.remove(levelOne);
        levelOne.setCompanyObjectives(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldCompanyObjectives)) {
            return false;
        }
        return id != null && id.equals(((FieldCompanyObjectives) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldCompanyObjectives{" +
            "id=" + getId() +
            ", companyObjectives='" + getCompanyObjectives() + "'" +
            "}";
    }
}
