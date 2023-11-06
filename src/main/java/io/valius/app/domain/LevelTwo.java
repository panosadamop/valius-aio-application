package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;

/**
 * A LevelTwo.
 */
@Entity
@Table(name = "level_two")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelTwo implements Serializable {

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
    @Column(name = "target_market", nullable = false)
    private String targetMarket;

    @NotNull
    @Column(name = "current_market_segmentation", nullable = false)
    private String currentMarketSegmentation;

    @NotNull
    @Column(name = "segment_name", nullable = false)
    private String segmentName;

    @Column(name = "market_segmentation_type")
    private String marketSegmentationType;

    @Column(name = "unique_characteristic")
    private String uniqueCharacteristic;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "segment_description", nullable = false)
    private String segmentDescription;

    @NotNull
    @Column(name = "buying_criteria_category", nullable = false)
    private String buyingCriteriaCategory;

    @NotNull
    @Column(name = "competitor_product_name", nullable = false)
    private String competitorProductName;

    @NotNull
    @Column(name = "competitor_company_name", nullable = false)
    private String competitorCompanyName;

    @NotNull
    @Column(name = "competitor_brand_name", nullable = false)
    private String competitorBrandName;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "competitor_product_description", nullable = false)
    private String competitorProductDescription;

    @NotNull
    @Column(name = "competitor_maturity_phase", nullable = false)
    private String competitorMaturityPhase;

    @NotNull
    @Column(name = "competitor_competitive_position", nullable = false)
    private String competitorCompetitivePosition;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldBuyingCriteria buyingCriteria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LevelTwo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public LevelTwo identifier(String identifier) {
        this.setIdentifier(identifier);
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTargetMarket() {
        return this.targetMarket;
    }

    public LevelTwo targetMarket(String targetMarket) {
        this.setTargetMarket(targetMarket);
        return this;
    }

    public void setTargetMarket(String targetMarket) {
        this.targetMarket = targetMarket;
    }

    public String getCurrentMarketSegmentation() {
        return this.currentMarketSegmentation;
    }

    public LevelTwo currentMarketSegmentation(String currentMarketSegmentation) {
        this.setCurrentMarketSegmentation(currentMarketSegmentation);
        return this;
    }

    public void setCurrentMarketSegmentation(String currentMarketSegmentation) {
        this.currentMarketSegmentation = currentMarketSegmentation;
    }

    public String getSegmentName() {
        return this.segmentName;
    }

    public LevelTwo segmentName(String segmentName) {
        this.setSegmentName(segmentName);
        return this;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public String getMarketSegmentationType() {
        return this.marketSegmentationType;
    }

    public LevelTwo marketSegmentationType(String marketSegmentationType) {
        this.setMarketSegmentationType(marketSegmentationType);
        return this;
    }

    public void setMarketSegmentationType(String marketSegmentationType) {
        this.marketSegmentationType = marketSegmentationType;
    }

    public String getUniqueCharacteristic() {
        return this.uniqueCharacteristic;
    }

    public LevelTwo uniqueCharacteristic(String uniqueCharacteristic) {
        this.setUniqueCharacteristic(uniqueCharacteristic);
        return this;
    }

    public void setUniqueCharacteristic(String uniqueCharacteristic) {
        this.uniqueCharacteristic = uniqueCharacteristic;
    }

    public String getSegmentDescription() {
        return this.segmentDescription;
    }

    public LevelTwo segmentDescription(String segmentDescription) {
        this.setSegmentDescription(segmentDescription);
        return this;
    }

    public void setSegmentDescription(String segmentDescription) {
        this.segmentDescription = segmentDescription;
    }

    public String getBuyingCriteriaCategory() {
        return this.buyingCriteriaCategory;
    }

    public LevelTwo buyingCriteriaCategory(String buyingCriteriaCategory) {
        this.setBuyingCriteriaCategory(buyingCriteriaCategory);
        return this;
    }

    public void setBuyingCriteriaCategory(String buyingCriteriaCategory) {
        this.buyingCriteriaCategory = buyingCriteriaCategory;
    }

    public String getCompetitorProductName() {
        return this.competitorProductName;
    }

    public LevelTwo competitorProductName(String competitorProductName) {
        this.setCompetitorProductName(competitorProductName);
        return this;
    }

    public void setCompetitorProductName(String competitorProductName) {
        this.competitorProductName = competitorProductName;
    }

    public String getCompetitorCompanyName() {
        return this.competitorCompanyName;
    }

    public LevelTwo competitorCompanyName(String competitorCompanyName) {
        this.setCompetitorCompanyName(competitorCompanyName);
        return this;
    }

    public void setCompetitorCompanyName(String competitorCompanyName) {
        this.competitorCompanyName = competitorCompanyName;
    }

    public String getCompetitorBrandName() {
        return this.competitorBrandName;
    }

    public LevelTwo competitorBrandName(String competitorBrandName) {
        this.setCompetitorBrandName(competitorBrandName);
        return this;
    }

    public void setCompetitorBrandName(String competitorBrandName) {
        this.competitorBrandName = competitorBrandName;
    }

    public String getCompetitorProductDescription() {
        return this.competitorProductDescription;
    }

    public LevelTwo competitorProductDescription(String competitorProductDescription) {
        this.setCompetitorProductDescription(competitorProductDescription);
        return this;
    }

    public void setCompetitorProductDescription(String competitorProductDescription) {
        this.competitorProductDescription = competitorProductDescription;
    }

    public String getCompetitorMaturityPhase() {
        return this.competitorMaturityPhase;
    }

    public LevelTwo competitorMaturityPhase(String competitorMaturityPhase) {
        this.setCompetitorMaturityPhase(competitorMaturityPhase);
        return this;
    }

    public void setCompetitorMaturityPhase(String competitorMaturityPhase) {
        this.competitorMaturityPhase = competitorMaturityPhase;
    }

    public String getCompetitorCompetitivePosition() {
        return this.competitorCompetitivePosition;
    }

    public LevelTwo competitorCompetitivePosition(String competitorCompetitivePosition) {
        this.setCompetitorCompetitivePosition(competitorCompetitivePosition);
        return this;
    }

    public void setCompetitorCompetitivePosition(String competitorCompetitivePosition) {
        this.competitorCompetitivePosition = competitorCompetitivePosition;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LevelTwo user(User user) {
        this.setUser(user);
        return this;
    }

    public FieldBuyingCriteria getBuyingCriteria() {
        return this.buyingCriteria;
    }

    public void setBuyingCriteria(FieldBuyingCriteria fieldBuyingCriteria) {
        this.buyingCriteria = fieldBuyingCriteria;
    }

    public LevelTwo buyingCriteria(FieldBuyingCriteria fieldBuyingCriteria) {
        this.setBuyingCriteria(fieldBuyingCriteria);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelTwo)) {
            return false;
        }
        return id != null && id.equals(((LevelTwo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelTwo{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", targetMarket='" + getTargetMarket() + "'" +
            ", currentMarketSegmentation='" + getCurrentMarketSegmentation() + "'" +
            ", segmentName='" + getSegmentName() + "'" +
            ", marketSegmentationType='" + getMarketSegmentationType() + "'" +
            ", uniqueCharacteristic='" + getUniqueCharacteristic() + "'" +
            ", segmentDescription='" + getSegmentDescription() + "'" +
            ", buyingCriteriaCategory='" + getBuyingCriteriaCategory() + "'" +
            ", competitorProductName='" + getCompetitorProductName() + "'" +
            ", competitorCompanyName='" + getCompetitorCompanyName() + "'" +
            ", competitorBrandName='" + getCompetitorBrandName() + "'" +
            ", competitorProductDescription='" + getCompetitorProductDescription() + "'" +
            ", competitorMaturityPhase='" + getCompetitorMaturityPhase() + "'" +
            ", competitorCompetitivePosition='" + getCompetitorCompetitivePosition() + "'" +
            "}";
    }
}
