package io.valius.app.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LevelFour.
 */
@Entity
@Table(name = "level_four")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelFour implements Serializable {

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
    @Column(name = "critical_success_factors", nullable = false)
    private String criticalSuccessFactors;

    @NotNull
    @Column(name = "population_size", nullable = false)
    private String populationSize;

    @NotNull
    @Column(name = "statistical_error", nullable = false)
    private String statisticalError;

    @NotNull
    @Column(name = "confidence_level", nullable = false)
    private String confidenceLevel;

    @NotNull
    @Column(name = "required_sample_size", nullable = false)
    private String requiredSampleSize;

    @NotNull
    @Column(name = "estimated_response_rate", nullable = false)
    private String estimatedResponseRate;

    @NotNull
    @Column(name = "survey_participants_number", nullable = false)
    private Integer surveyParticipantsNumber;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LevelFour id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public LevelFour identifier(String identifier) {
        this.setIdentifier(identifier);
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCriticalSuccessFactors() {
        return this.criticalSuccessFactors;
    }

    public LevelFour criticalSuccessFactors(String criticalSuccessFactors) {
        this.setCriticalSuccessFactors(criticalSuccessFactors);
        return this;
    }

    public void setCriticalSuccessFactors(String criticalSuccessFactors) {
        this.criticalSuccessFactors = criticalSuccessFactors;
    }

    public String getPopulationSize() {
        return this.populationSize;
    }

    public LevelFour populationSize(String populationSize) {
        this.setPopulationSize(populationSize);
        return this;
    }

    public void setPopulationSize(String populationSize) {
        this.populationSize = populationSize;
    }

    public String getStatisticalError() {
        return this.statisticalError;
    }

    public LevelFour statisticalError(String statisticalError) {
        this.setStatisticalError(statisticalError);
        return this;
    }

    public void setStatisticalError(String statisticalError) {
        this.statisticalError = statisticalError;
    }

    public String getConfidenceLevel() {
        return this.confidenceLevel;
    }

    public LevelFour confidenceLevel(String confidenceLevel) {
        this.setConfidenceLevel(confidenceLevel);
        return this;
    }

    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public String getRequiredSampleSize() {
        return this.requiredSampleSize;
    }

    public LevelFour requiredSampleSize(String requiredSampleSize) {
        this.setRequiredSampleSize(requiredSampleSize);
        return this;
    }

    public void setRequiredSampleSize(String requiredSampleSize) {
        this.requiredSampleSize = requiredSampleSize;
    }

    public String getEstimatedResponseRate() {
        return this.estimatedResponseRate;
    }

    public LevelFour estimatedResponseRate(String estimatedResponseRate) {
        this.setEstimatedResponseRate(estimatedResponseRate);
        return this;
    }

    public void setEstimatedResponseRate(String estimatedResponseRate) {
        this.estimatedResponseRate = estimatedResponseRate;
    }

    public Integer getSurveyParticipantsNumber() {
        return this.surveyParticipantsNumber;
    }

    public LevelFour surveyParticipantsNumber(Integer surveyParticipantsNumber) {
        this.setSurveyParticipantsNumber(surveyParticipantsNumber);
        return this;
    }

    public void setSurveyParticipantsNumber(Integer surveyParticipantsNumber) {
        this.surveyParticipantsNumber = surveyParticipantsNumber;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LevelFour user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelFour)) {
            return false;
        }
        return id != null && id.equals(((LevelFour) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelFour{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", criticalSuccessFactors='" + getCriticalSuccessFactors() + "'" +
            ", populationSize='" + getPopulationSize() + "'" +
            ", statisticalError='" + getStatisticalError() + "'" +
            ", confidenceLevel='" + getConfidenceLevel() + "'" +
            ", requiredSampleSize='" + getRequiredSampleSize() + "'" +
            ", estimatedResponseRate='" + getEstimatedResponseRate() + "'" +
            ", surveyParticipantsNumber=" + getSurveyParticipantsNumber() +
            "}";
    }
}
