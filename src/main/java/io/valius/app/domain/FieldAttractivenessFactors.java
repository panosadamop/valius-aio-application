package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldAttractivenessFactors.
 */
@Entity
@Table(name = "field_attractiveness_factors")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldAttractivenessFactors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "attractiveness_factors")
    private String attractivenessFactors;

    @OneToMany(mappedBy = "attractivenessFactors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "attractivenessFactors" }, allowSetters = true)
    private Set<LevelThree> jsons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldAttractivenessFactors id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttractivenessFactors() {
        return this.attractivenessFactors;
    }

    public FieldAttractivenessFactors attractivenessFactors(String attractivenessFactors) {
        this.setAttractivenessFactors(attractivenessFactors);
        return this;
    }

    public void setAttractivenessFactors(String attractivenessFactors) {
        this.attractivenessFactors = attractivenessFactors;
    }

    public Set<LevelThree> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<LevelThree> levelThrees) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setAttractivenessFactors(null));
        }
        if (levelThrees != null) {
            levelThrees.forEach(i -> i.setAttractivenessFactors(this));
        }
        this.jsons = levelThrees;
    }

    public FieldAttractivenessFactors jsons(Set<LevelThree> levelThrees) {
        this.setJsons(levelThrees);
        return this;
    }

    public FieldAttractivenessFactors addJson(LevelThree levelThree) {
        this.jsons.add(levelThree);
        levelThree.setAttractivenessFactors(this);
        return this;
    }

    public FieldAttractivenessFactors removeJson(LevelThree levelThree) {
        this.jsons.remove(levelThree);
        levelThree.setAttractivenessFactors(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldAttractivenessFactors)) {
            return false;
        }
        return id != null && id.equals(((FieldAttractivenessFactors) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldAttractivenessFactors{" +
            "id=" + getId() +
            ", attractivenessFactors='" + getAttractivenessFactors() + "'" +
            "}";
    }
}
