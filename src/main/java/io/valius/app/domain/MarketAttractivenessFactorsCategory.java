package io.valius.app.domain;

import io.valius.app.domain.enumeration.Language;
import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A MarketAttractivenessFactorsCategory.
 */
@Entity
@Table(name = "market_attractiveness_factors_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MarketAttractivenessFactorsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "tab", nullable = false)
    private Integer tab;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MarketAttractivenessFactorsCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public MarketAttractivenessFactorsCategory value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getTab() {
        return this.tab;
    }

    public MarketAttractivenessFactorsCategory tab(Integer tab) {
        this.setTab(tab);
        return this;
    }

    public void setTab(Integer tab) {
        this.tab = tab;
    }

    public String getDescription() {
        return this.description;
    }

    public MarketAttractivenessFactorsCategory description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return this.language;
    }

    public MarketAttractivenessFactorsCategory language(Language language) {
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
        if (!(o instanceof MarketAttractivenessFactorsCategory)) {
            return false;
        }
        return id != null && id.equals(((MarketAttractivenessFactorsCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MarketAttractivenessFactorsCategory{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", tab=" + getTab() +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
