package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LevelThree.
 */
@Entity
@Table(name = "level_three")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelThree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @NotNull
    @Column(name = "maf_category", nullable = false)
    private String mafCategory;

    @NotNull
    @Column(name = "weighting_maf", nullable = false)
    private String weightingMaf;

    @NotNull
    @Column(name = "low_attractiveness_range_maf", nullable = false)
    private String lowAttractivenessRangeMaf;

    @NotNull
    @Column(name = "medium_attractiveness_range_maf", nullable = false)
    private String mediumAttractivenessRangeMaf;

    @NotNull
    @Column(name = "high_attractiveness_range_maf", nullable = false)
    private String highAttractivenessRangeMaf;

    @Column(name = "segment_score_maf")
    private String segmentScoreMaf;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldAttractivenessFactors attractivenessFactors;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LevelThree id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public LevelThree identifier(String identifier) {
        this.setIdentifier(identifier);
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMafCategory() {
        return this.mafCategory;
    }

    public LevelThree mafCategory(String mafCategory) {
        this.setMafCategory(mafCategory);
        return this;
    }

    public void setMafCategory(String mafCategory) {
        this.mafCategory = mafCategory;
    }

    public String getWeightingMaf() {
        return this.weightingMaf;
    }

    public LevelThree weightingMaf(String weightingMaf) {
        this.setWeightingMaf(weightingMaf);
        return this;
    }

    public void setWeightingMaf(String weightingMaf) {
        this.weightingMaf = weightingMaf;
    }

    public String getLowAttractivenessRangeMaf() {
        return this.lowAttractivenessRangeMaf;
    }

    public LevelThree lowAttractivenessRangeMaf(String lowAttractivenessRangeMaf) {
        this.setLowAttractivenessRangeMaf(lowAttractivenessRangeMaf);
        return this;
    }

    public void setLowAttractivenessRangeMaf(String lowAttractivenessRangeMaf) {
        this.lowAttractivenessRangeMaf = lowAttractivenessRangeMaf;
    }

    public String getMediumAttractivenessRangeMaf() {
        return this.mediumAttractivenessRangeMaf;
    }

    public LevelThree mediumAttractivenessRangeMaf(String mediumAttractivenessRangeMaf) {
        this.setMediumAttractivenessRangeMaf(mediumAttractivenessRangeMaf);
        return this;
    }

    public void setMediumAttractivenessRangeMaf(String mediumAttractivenessRangeMaf) {
        this.mediumAttractivenessRangeMaf = mediumAttractivenessRangeMaf;
    }

    public String getHighAttractivenessRangeMaf() {
        return this.highAttractivenessRangeMaf;
    }

    public LevelThree highAttractivenessRangeMaf(String highAttractivenessRangeMaf) {
        this.setHighAttractivenessRangeMaf(highAttractivenessRangeMaf);
        return this;
    }

    public void setHighAttractivenessRangeMaf(String highAttractivenessRangeMaf) {
        this.highAttractivenessRangeMaf = highAttractivenessRangeMaf;
    }

    public String getSegmentScoreMaf() {
        return this.segmentScoreMaf;
    }

    public LevelThree segmentScoreMaf(String segmentScoreMaf) {
        this.setSegmentScoreMaf(segmentScoreMaf);
        return this;
    }

    public void setSegmentScoreMaf(String segmentScoreMaf) {
        this.segmentScoreMaf = segmentScoreMaf;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LevelThree user(User user) {
        this.setUser(user);
        return this;
    }

    public FieldAttractivenessFactors getAttractivenessFactors() {
        return this.attractivenessFactors;
    }

    public void setAttractivenessFactors(FieldAttractivenessFactors fieldAttractivenessFactors) {
        this.attractivenessFactors = fieldAttractivenessFactors;
    }

    public LevelThree attractivenessFactors(FieldAttractivenessFactors fieldAttractivenessFactors) {
        this.setAttractivenessFactors(fieldAttractivenessFactors);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelThree)) {
            return false;
        }
        return id != null && id.equals(((LevelThree) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelThree{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", mafCategory='" + getMafCategory() + "'" +
            ", weightingMaf='" + getWeightingMaf() + "'" +
            ", lowAttractivenessRangeMaf='" + getLowAttractivenessRangeMaf() + "'" +
            ", mediumAttractivenessRangeMaf='" + getMediumAttractivenessRangeMaf() + "'" +
            ", highAttractivenessRangeMaf='" + getHighAttractivenessRangeMaf() + "'" +
            ", segmentScoreMaf='" + getSegmentScoreMaf() + "'" +
            "}";
    }
}
