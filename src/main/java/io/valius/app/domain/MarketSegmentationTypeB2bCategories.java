package io.valius.app.domain;

import io.valius.app.domain.enumeration.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;

/**
 * A MarketSegmentationTypeB2bCategories.
 */
@Entity
@Table(name = "market_segmentation_type_b_2_b_categories")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MarketSegmentationTypeB2bCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "description")
    private String description;

    @Column(name = "placeholder")
    private String placeholder;

    @NotNull
    @Column(name = "unique_characteristic", nullable = false)
    private String uniqueCharacteristic;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MarketSegmentationTypeB2bCategories id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public MarketSegmentationTypeB2bCategories value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public MarketSegmentationTypeB2bCategories description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public MarketSegmentationTypeB2bCategories placeholder(String placeholder) {
        this.setPlaceholder(placeholder);
        return this;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getUniqueCharacteristic() {
        return this.uniqueCharacteristic;
    }

    public MarketSegmentationTypeB2bCategories uniqueCharacteristic(String uniqueCharacteristic) {
        this.setUniqueCharacteristic(uniqueCharacteristic);
        return this;
    }

    public void setUniqueCharacteristic(String uniqueCharacteristic) {
        this.uniqueCharacteristic = uniqueCharacteristic;
    }

    public Language getLanguage() {
        return this.language;
    }

    public MarketSegmentationTypeB2bCategories language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarketSegmentationTypeB2bCategories)) {
            return false;
        }
        return id != null && id.equals(((MarketSegmentationTypeB2bCategories) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MarketSegmentationTypeB2bCategories{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", placeholder='" + getPlaceholder() + "'" +
            ", uniqueCharacteristic='" + getUniqueCharacteristic() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
