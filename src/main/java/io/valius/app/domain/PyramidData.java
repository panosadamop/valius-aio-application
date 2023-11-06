package io.valius.app.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PyramidData.
 */
@Entity
@Table(name = "pyramid_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PyramidData implements Serializable {

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
    @Column(name = "category", nullable = false)
    private Integer category;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PyramidData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public PyramidData identifier(String identifier) {
        this.setIdentifier(identifier);
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getCategory() {
        return this.category;
    }

    public PyramidData category(Integer category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getValue() {
        return this.value;
    }

    public PyramidData value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrder() {
        return this.order;
    }

    public PyramidData order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public byte[] getImg() {
        return this.img;
    }

    public PyramidData img(byte[] img) {
        this.setImg(img);
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return this.imgContentType;
    }

    public PyramidData imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PyramidData)) {
            return false;
        }
        return id != null && id.equals(((PyramidData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PyramidData{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", category=" + getCategory() +
            ", value='" + getValue() + "'" +
            ", order=" + getOrder() +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            "}";
    }
}
