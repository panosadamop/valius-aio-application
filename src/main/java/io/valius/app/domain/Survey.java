package io.valius.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Survey.
 */
@Entity
@Table(name = "survey")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "consumer_assessed_brands", nullable = false)
    private String consumerAssessedBrands;

    @NotNull
    @Column(name = "critical_success_factors", nullable = false)
    private String criticalSuccessFactors;

    @NotNull
    @Column(name = "additional_buying_criteria", nullable = false)
    private String additionalBuyingCriteria;

    @NotNull
    @Column(name = "consumer_segment_group", nullable = false)
    private String consumerSegmentGroup;

    @NotNull
    @Column(name = "segment_csf", nullable = false)
    private String segmentCsf;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Column(name = "age_group", nullable = false)
    private String ageGroup;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "education", nullable = false)
    private String education;

    @NotNull
    @Column(name = "occupation", nullable = false)
    private String occupation;

    @NotNull
    @Column(name = "net_promoter_score", nullable = false)
    private String netPromoterScore;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldBuyingCriteriaWeighting buyingCriteriaWeighting;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldPreferredPurchaseChannel preferredPurchaseChannel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldPreferredCommunicationChannel preferredCommunicationChannel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Survey id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsumerAssessedBrands() {
        return this.consumerAssessedBrands;
    }

    public Survey consumerAssessedBrands(String consumerAssessedBrands) {
        this.setConsumerAssessedBrands(consumerAssessedBrands);
        return this;
    }

    public void setConsumerAssessedBrands(String consumerAssessedBrands) {
        this.consumerAssessedBrands = consumerAssessedBrands;
    }

    public String getCriticalSuccessFactors() {
        return this.criticalSuccessFactors;
    }

    public Survey criticalSuccessFactors(String criticalSuccessFactors) {
        this.setCriticalSuccessFactors(criticalSuccessFactors);
        return this;
    }

    public void setCriticalSuccessFactors(String criticalSuccessFactors) {
        this.criticalSuccessFactors = criticalSuccessFactors;
    }

    public String getAdditionalBuyingCriteria() {
        return this.additionalBuyingCriteria;
    }

    public Survey additionalBuyingCriteria(String additionalBuyingCriteria) {
        this.setAdditionalBuyingCriteria(additionalBuyingCriteria);
        return this;
    }

    public void setAdditionalBuyingCriteria(String additionalBuyingCriteria) {
        this.additionalBuyingCriteria = additionalBuyingCriteria;
    }

    public String getConsumerSegmentGroup() {
        return this.consumerSegmentGroup;
    }

    public Survey consumerSegmentGroup(String consumerSegmentGroup) {
        this.setConsumerSegmentGroup(consumerSegmentGroup);
        return this;
    }

    public void setConsumerSegmentGroup(String consumerSegmentGroup) {
        this.consumerSegmentGroup = consumerSegmentGroup;
    }

    public String getSegmentCsf() {
        return this.segmentCsf;
    }

    public Survey segmentCsf(String segmentCsf) {
        this.setSegmentCsf(segmentCsf);
        return this;
    }

    public void setSegmentCsf(String segmentCsf) {
        this.segmentCsf = segmentCsf;
    }

    public String getGender() {
        return this.gender;
    }

    public Survey gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeGroup() {
        return this.ageGroup;
    }

    public Survey ageGroup(String ageGroup) {
        this.setAgeGroup(ageGroup);
        return this;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getLocation() {
        return this.location;
    }

    public Survey location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEducation() {
        return this.education;
    }

    public Survey education(String education) {
        this.setEducation(education);
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public Survey occupation(String occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getNetPromoterScore() {
        return this.netPromoterScore;
    }

    public Survey netPromoterScore(String netPromoterScore) {
        this.setNetPromoterScore(netPromoterScore);
        return this;
    }

    public void setNetPromoterScore(String netPromoterScore) {
        this.netPromoterScore = netPromoterScore;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Survey user(User user) {
        this.setUser(user);
        return this;
    }

    public FieldBuyingCriteriaWeighting getBuyingCriteriaWeighting() {
        return this.buyingCriteriaWeighting;
    }

    public void setBuyingCriteriaWeighting(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting) {
        this.buyingCriteriaWeighting = fieldBuyingCriteriaWeighting;
    }

    public Survey buyingCriteriaWeighting(FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting) {
        this.setBuyingCriteriaWeighting(fieldBuyingCriteriaWeighting);
        return this;
    }

    public FieldPreferredPurchaseChannel getPreferredPurchaseChannel() {
        return this.preferredPurchaseChannel;
    }

    public void setPreferredPurchaseChannel(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel) {
        this.preferredPurchaseChannel = fieldPreferredPurchaseChannel;
    }

    public Survey preferredPurchaseChannel(FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel) {
        this.setPreferredPurchaseChannel(fieldPreferredPurchaseChannel);
        return this;
    }

    public FieldPreferredCommunicationChannel getPreferredCommunicationChannel() {
        return this.preferredCommunicationChannel;
    }

    public void setPreferredCommunicationChannel(FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel) {
        this.preferredCommunicationChannel = fieldPreferredCommunicationChannel;
    }

    public Survey preferredCommunicationChannel(FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel) {
        this.setPreferredCommunicationChannel(fieldPreferredCommunicationChannel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Survey)) {
            return false;
        }
        return id != null && id.equals(((Survey) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Survey{" +
            "id=" + getId() +
            ", consumerAssessedBrands='" + getConsumerAssessedBrands() + "'" +
            ", criticalSuccessFactors='" + getCriticalSuccessFactors() + "'" +
            ", additionalBuyingCriteria='" + getAdditionalBuyingCriteria() + "'" +
            ", consumerSegmentGroup='" + getConsumerSegmentGroup() + "'" +
            ", segmentCsf='" + getSegmentCsf() + "'" +
            ", gender='" + getGender() + "'" +
            ", ageGroup='" + getAgeGroup() + "'" +
            ", location='" + getLocation() + "'" +
            ", education='" + getEducation() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", netPromoterScore='" + getNetPromoterScore() + "'" +
            "}";
    }
}
