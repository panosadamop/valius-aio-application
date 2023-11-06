package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldKpi.
 */
@Entity
@Table(name = "field_kpi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldKpi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kpis")
    private String kpis;

    @OneToMany(mappedBy = "kpis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "companyObjectives", "kpis", "productType" }, allowSetters = true)
    private Set<LevelOne> jsons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldKpi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKpis() {
        return this.kpis;
    }

    public FieldKpi kpis(String kpis) {
        this.setKpis(kpis);
        return this;
    }

    public void setKpis(String kpis) {
        this.kpis = kpis;
    }

    public Set<LevelOne> getJsons() {
        return this.jsons;
    }

    public void setJsons(Set<LevelOne> levelOnes) {
        if (this.jsons != null) {
            this.jsons.forEach(i -> i.setKpis(null));
        }
        if (levelOnes != null) {
            levelOnes.forEach(i -> i.setKpis(this));
        }
        this.jsons = levelOnes;
    }

    public FieldKpi jsons(Set<LevelOne> levelOnes) {
        this.setJsons(levelOnes);
        return this;
    }

    public FieldKpi addJson(LevelOne levelOne) {
        this.jsons.add(levelOne);
        levelOne.setKpis(this);
        return this;
    }

    public FieldKpi removeJson(LevelOne levelOne) {
        this.jsons.remove(levelOne);
        levelOne.setKpis(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldKpi)) {
            return false;
        }
        return id != null && id.equals(((FieldKpi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldKpi{" +
            "id=" + getId() +
            ", kpis='" + getKpis() + "'" +
            "}";
    }
}
