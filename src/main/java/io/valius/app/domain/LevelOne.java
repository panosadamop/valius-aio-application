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
 * A LevelOne.
 */
@Entity
@Table(name = "level_one")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelOne implements Serializable {

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
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Lob
    @Column(name = "company_logo")
    private byte[] companyLogo;

    @Column(name = "company_logo_content_type")
    private String companyLogoContentType;

    @Column(name = "brand_name")
    private String brandName;

    @Lob
    @Column(name = "product_logo")
    private byte[] productLogo;

    @Column(name = "product_logo_content_type")
    private String productLogoContentType;

    @Column(name = "industry")
    private String industry;

    @Column(name = "organization_type")
    private String organizationType;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "products_services", nullable = false)
    private String productsServices;

    @Column(name = "territory")
    private String territory;

    @Column(name = "no_employees")
    private String noEmployees;

    @Column(name = "revenues")
    private String revenues;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "mission", nullable = false)
    private String mission;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "vision", nullable = false)
    private String vision;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "company_values", nullable = false)
    private String companyValues;

    @Column(name = "strategic_focus")
    private String strategicFocus;

    @Column(name = "marketing_budget")
    private String marketingBudget;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "maturity_phase")
    private String maturityPhase;

    @Column(name = "competitive_position")
    private String competitivePosition;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "target_audience_description", nullable = false)
    private String targetAudienceDescription;

    @NotNull
    @Column(name = "potential_customers_groups", nullable = false)
    private String potentialCustomersGroups;

    @NotNull
    @Column(name = "strengths", nullable = false)
    private String strengths;

    @NotNull
    @Column(name = "weaknesses", nullable = false)
    private String weaknesses;

    @NotNull
    @Column(name = "opportunities", nullable = false)
    private String opportunities;

    @NotNull
    @Column(name = "threats", nullable = false)
    private String threats;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldCompanyObjectives companyObjectives;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldKpi kpis;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jsons" }, allowSetters = true)
    private FieldProductype productType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LevelOne id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public LevelOne identifier(String identifier) {
        this.setIdentifier(identifier);
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public LevelOne companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public byte[] getCompanyLogo() {
        return this.companyLogo;
    }

    public LevelOne companyLogo(byte[] companyLogo) {
        this.setCompanyLogo(companyLogo);
        return this;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogoContentType() {
        return this.companyLogoContentType;
    }

    public LevelOne companyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
        return this;
    }

    public void setCompanyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public LevelOne brandName(String brandName) {
        this.setBrandName(brandName);
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public byte[] getProductLogo() {
        return this.productLogo;
    }

    public LevelOne productLogo(byte[] productLogo) {
        this.setProductLogo(productLogo);
        return this;
    }

    public void setProductLogo(byte[] productLogo) {
        this.productLogo = productLogo;
    }

    public String getProductLogoContentType() {
        return this.productLogoContentType;
    }

    public LevelOne productLogoContentType(String productLogoContentType) {
        this.productLogoContentType = productLogoContentType;
        return this;
    }

    public void setProductLogoContentType(String productLogoContentType) {
        this.productLogoContentType = productLogoContentType;
    }

    public String getIndustry() {
        return this.industry;
    }

    public LevelOne industry(String industry) {
        this.setIndustry(industry);
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOrganizationType() {
        return this.organizationType;
    }

    public LevelOne organizationType(String organizationType) {
        this.setOrganizationType(organizationType);
        return this;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getProductsServices() {
        return this.productsServices;
    }

    public LevelOne productsServices(String productsServices) {
        this.setProductsServices(productsServices);
        return this;
    }

    public void setProductsServices(String productsServices) {
        this.productsServices = productsServices;
    }

    public String getTerritory() {
        return this.territory;
    }

    public LevelOne territory(String territory) {
        this.setTerritory(territory);
        return this;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getNoEmployees() {
        return this.noEmployees;
    }

    public LevelOne noEmployees(String noEmployees) {
        this.setNoEmployees(noEmployees);
        return this;
    }

    public void setNoEmployees(String noEmployees) {
        this.noEmployees = noEmployees;
    }

    public String getRevenues() {
        return this.revenues;
    }

    public LevelOne revenues(String revenues) {
        this.setRevenues(revenues);
        return this;
    }

    public void setRevenues(String revenues) {
        this.revenues = revenues;
    }

    public String getMission() {
        return this.mission;
    }

    public LevelOne mission(String mission) {
        this.setMission(mission);
        return this;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getVision() {
        return this.vision;
    }

    public LevelOne vision(String vision) {
        this.setVision(vision);
        return this;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getCompanyValues() {
        return this.companyValues;
    }

    public LevelOne companyValues(String companyValues) {
        this.setCompanyValues(companyValues);
        return this;
    }

    public void setCompanyValues(String companyValues) {
        this.companyValues = companyValues;
    }

    public String getStrategicFocus() {
        return this.strategicFocus;
    }

    public LevelOne strategicFocus(String strategicFocus) {
        this.setStrategicFocus(strategicFocus);
        return this;
    }

    public void setStrategicFocus(String strategicFocus) {
        this.strategicFocus = strategicFocus;
    }

    public String getMarketingBudget() {
        return this.marketingBudget;
    }

    public LevelOne marketingBudget(String marketingBudget) {
        this.setMarketingBudget(marketingBudget);
        return this;
    }

    public void setMarketingBudget(String marketingBudget) {
        this.marketingBudget = marketingBudget;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public LevelOne productDescription(String productDescription) {
        this.setProductDescription(productDescription);
        return this;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getMaturityPhase() {
        return this.maturityPhase;
    }

    public LevelOne maturityPhase(String maturityPhase) {
        this.setMaturityPhase(maturityPhase);
        return this;
    }

    public void setMaturityPhase(String maturityPhase) {
        this.maturityPhase = maturityPhase;
    }

    public String getCompetitivePosition() {
        return this.competitivePosition;
    }

    public LevelOne competitivePosition(String competitivePosition) {
        this.setCompetitivePosition(competitivePosition);
        return this;
    }

    public void setCompetitivePosition(String competitivePosition) {
        this.competitivePosition = competitivePosition;
    }

    public String getTargetAudienceDescription() {
        return this.targetAudienceDescription;
    }

    public LevelOne targetAudienceDescription(String targetAudienceDescription) {
        this.setTargetAudienceDescription(targetAudienceDescription);
        return this;
    }

    public void setTargetAudienceDescription(String targetAudienceDescription) {
        this.targetAudienceDescription = targetAudienceDescription;
    }

    public String getPotentialCustomersGroups() {
        return this.potentialCustomersGroups;
    }

    public LevelOne potentialCustomersGroups(String potentialCustomersGroups) {
        this.setPotentialCustomersGroups(potentialCustomersGroups);
        return this;
    }

    public void setPotentialCustomersGroups(String potentialCustomersGroups) {
        this.potentialCustomersGroups = potentialCustomersGroups;
    }

    public String getStrengths() {
        return this.strengths;
    }

    public LevelOne strengths(String strengths) {
        this.setStrengths(strengths);
        return this;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return this.weaknesses;
    }

    public LevelOne weaknesses(String weaknesses) {
        this.setWeaknesses(weaknesses);
        return this;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }

    public String getOpportunities() {
        return this.opportunities;
    }

    public LevelOne opportunities(String opportunities) {
        this.setOpportunities(opportunities);
        return this;
    }

    public void setOpportunities(String opportunities) {
        this.opportunities = opportunities;
    }

    public String getThreats() {
        return this.threats;
    }

    public LevelOne threats(String threats) {
        this.setThreats(threats);
        return this;
    }

    public void setThreats(String threats) {
        this.threats = threats;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LevelOne user(User user) {
        this.setUser(user);
        return this;
    }

    public FieldCompanyObjectives getCompanyObjectives() {
        return this.companyObjectives;
    }

    public void setCompanyObjectives(FieldCompanyObjectives fieldCompanyObjectives) {
        this.companyObjectives = fieldCompanyObjectives;
    }

    public LevelOne companyObjectives(FieldCompanyObjectives fieldCompanyObjectives) {
        this.setCompanyObjectives(fieldCompanyObjectives);
        return this;
    }

    public FieldKpi getKpis() {
        return this.kpis;
    }

    public void setKpis(FieldKpi fieldKpi) {
        this.kpis = fieldKpi;
    }

    public LevelOne kpis(FieldKpi fieldKpi) {
        this.setKpis(fieldKpi);
        return this;
    }

    public FieldProductype getProductType() {
        return this.productType;
    }

    public void setProductType(FieldProductype fieldProductype) {
        this.productType = fieldProductype;
    }

    public LevelOne productType(FieldProductype fieldProductype) {
        this.setProductType(fieldProductype);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelOne)) {
            return false;
        }
        return id != null && id.equals(((LevelOne) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelOne{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", companyLogo='" + getCompanyLogo() + "'" +
            ", companyLogoContentType='" + getCompanyLogoContentType() + "'" +
            ", brandName='" + getBrandName() + "'" +
            ", productLogo='" + getProductLogo() + "'" +
            ", productLogoContentType='" + getProductLogoContentType() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", organizationType='" + getOrganizationType() + "'" +
            ", productsServices='" + getProductsServices() + "'" +
            ", territory='" + getTerritory() + "'" +
            ", noEmployees='" + getNoEmployees() + "'" +
            ", revenues='" + getRevenues() + "'" +
            ", mission='" + getMission() + "'" +
            ", vision='" + getVision() + "'" +
            ", companyValues='" + getCompanyValues() + "'" +
            ", strategicFocus='" + getStrategicFocus() + "'" +
            ", marketingBudget='" + getMarketingBudget() + "'" +
            ", productDescription='" + getProductDescription() + "'" +
            ", maturityPhase='" + getMaturityPhase() + "'" +
            ", competitivePosition='" + getCompetitivePosition() + "'" +
            ", targetAudienceDescription='" + getTargetAudienceDescription() + "'" +
            ", potentialCustomersGroups='" + getPotentialCustomersGroups() + "'" +
            ", strengths='" + getStrengths() + "'" +
            ", weaknesses='" + getWeaknesses() + "'" +
            ", opportunities='" + getOpportunities() + "'" +
            ", threats='" + getThreats() + "'" +
            "}";
    }
}
