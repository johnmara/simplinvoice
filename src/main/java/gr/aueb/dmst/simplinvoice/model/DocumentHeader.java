package gr.aueb.dmst.simplinvoice.model;

import gr.aueb.dmst.simplinvoice.enums.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class DocumentHeader {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date date;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDateTime;

    private int number;
    @Enumerated(EnumType.STRING)
    private DocumentPaymentMethod paymentMethod;
    private String currency;
    private String notes;
    private BigDecimal totalNetValue;
    private BigDecimal totalDiscountValue;
    private BigDecimal totalVatValue;
    private BigDecimal totalTaxValue;
    private BigDecimal totalFinalValue;
    private VatExemptionType vatExemptionType;
    private String mark;
    private String uid;
    private String qrCodeValue;

    @ManyToOne
    @JoinColumn(name = "company_profile_id", referencedColumnName = "id")
    private CompanyProfile companyProfile;

    @ManyToOne
    @JoinColumn(name = "document_series_id", referencedColumnName = "id")
    private DocumentSeries documentSeries;

    @OneToMany(mappedBy = "documentHeader", cascade = CascadeType.ALL)
    private List<DocumentTax> documentTaxes;

    @OneToMany(mappedBy = "documentHeader", cascade = CascadeType.ALL)
    private List<DocumentItem> documentItems;

    @OneToOne
    @JoinColumn(name = "trader_id", referencedColumnName = "id")
    private Trader counterPart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(Date receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public DocumentPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(DocumentPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getTotalNetValue() {
        return totalNetValue;
    }

    public void setTotalNetValue(BigDecimal totalNetValue) {
        this.totalNetValue = totalNetValue;
    }

    public BigDecimal getTotalDiscountValue() {
        return totalDiscountValue;
    }

    public void setTotalDiscountValue(BigDecimal totalDiscountValue) {
        this.totalDiscountValue = totalDiscountValue;
    }

    public BigDecimal getTotalVatValue() {
        return totalVatValue;
    }

    public void setTotalVatValue(BigDecimal totalVatValue) {
        this.totalVatValue = totalVatValue;
    }

    public BigDecimal getTotalTaxValue() {
        return totalTaxValue;
    }

    public void setTotalTaxValue(BigDecimal totalTaxValue) {
        this.totalTaxValue = totalTaxValue;
    }

    public BigDecimal getTotalFinalValue() {
        return totalFinalValue;
    }

    public void setTotalFinalValue(BigDecimal totalFinalValue) {
        this.totalFinalValue = totalFinalValue;
    }

    public VatExemptionType getVatExemptionType() {
        return vatExemptionType;
    }

    public void setVatExemptionType(VatExemptionType vatExemptionType) {
        this.vatExemptionType = vatExemptionType;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQrCodeValue() {
        return qrCodeValue;
    }

    public void setQrCodeValue(String qrCodeValue) {
        this.qrCodeValue = qrCodeValue;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public DocumentSeries getDocumentSeries() {
        return documentSeries;
    }

    public void setDocumentSeries(DocumentSeries documentSeries) {
        this.documentSeries = documentSeries;
    }

    public List<DocumentTax> getDocumentTaxes() {
        return documentTaxes;
    }

    public void setDocumentTaxes(List<DocumentTax> documentTaxes) {
        this.documentTaxes = documentTaxes;
    }

    public List<DocumentItem> getDocumentItems() {
        return documentItems;
    }

    public void setDocumentItems(List<DocumentItem> documentItems) {
        this.documentItems = documentItems;
    }

    public Trader getCounterPart() {
        return counterPart;
    }

    public void setCounterPart(Trader counterPart) {
        this.counterPart = counterPart;
    }
}
