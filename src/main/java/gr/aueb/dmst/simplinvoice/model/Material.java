package gr.aueb.dmst.simplinvoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import gr.aueb.dmst.simplinvoice.enums.AadeIncomeClassification;
import gr.aueb.dmst.simplinvoice.enums.MaterialType;
import gr.aueb.dmst.simplinvoice.enums.MeasurementUnit;
import gr.aueb.dmst.simplinvoice.enums.VatCategory;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Material {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable=false)
    private String code;

    @Enumerated(EnumType.STRING)
    private MaterialType type;
    private String description;
    @Enumerated(EnumType.STRING)
    private VatCategory vatCategory;
    private Boolean acquired;
    @Transient
    @JsonInclude()
    private BigDecimal vatPercent;
    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;
    @Enumerated(EnumType.STRING)
    private AadeIncomeClassification aadeIncomeClassification;
    private BigDecimal wholesalePrice;
    private BigDecimal retailPrice; //me fpa

    @ManyToOne
    @JoinColumn(name = "company_profile_id", referencedColumnName = "id")
    @JsonIgnore
    private CompanyProfile companyProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VatCategory getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(VatCategory vatCategory) {
        this.vatCategory = vatCategory;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public AadeIncomeClassification getAadeIncomeClassification() {
        return aadeIncomeClassification;
    }

    public void setAadeIncomeClassification(AadeIncomeClassification aadeIncomeClassification) {
        this.aadeIncomeClassification = aadeIncomeClassification;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public BigDecimal getVatPercent() {
        return this.vatCategory != null ? this.vatCategory.value : null;
    }

    public Boolean getAcquired() {
        return acquired;
    }

    public void setAcquired(Boolean acquired) {
        this.acquired = acquired;
    }

    public void setVatPercent(BigDecimal vatPercent) {
        this.vatPercent = vatPercent;
    }
}
