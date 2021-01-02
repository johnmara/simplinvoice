package gr.aueb.dmst.simplinvoice.model;

import gr.aueb.dmst.simplinvoice.enums.*;

import javax.persistence.*;

@Entity
public class DocumentSeries {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable=false)
    private String code;

    private String name;
    private int lastNumber = 0;

    @Enumerated(EnumType.STRING)
    private AadeInvoiceType aadeInvoiceType;

    @ManyToOne
    @JoinColumn(name = "company_profile_id", referencedColumnName = "id")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(int lastNumber) {
        this.lastNumber = lastNumber;
    }

    public AadeInvoiceType getAadeInvoiceType() {
        return aadeInvoiceType;
    }

    public void setAadeInvoiceType(AadeInvoiceType aadeInvoiceType) {
        this.aadeInvoiceType = aadeInvoiceType;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }
}
