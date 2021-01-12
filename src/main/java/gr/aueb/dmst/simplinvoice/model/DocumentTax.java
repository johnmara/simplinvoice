package gr.aueb.dmst.simplinvoice.model;

import gr.aueb.dmst.simplinvoice.enums.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class DocumentTax {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AadeDocumentTaxCategory category;
    @Transient
    private BigDecimal underlyingValue;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "document_header_id", referencedColumnName = "id")
    private DocumentHeader documentHeader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AadeDocumentTaxCategory getCategory() {
        return category;
    }

    public void setCategory(AadeDocumentTaxCategory type) {
        this.category = type;
    }

    public BigDecimal getUnderlyingValue() {
        return underlyingValue;
    }

    public BigDecimal calculateUnderlyingValue() {
        if(this.amount == null || this.amount.equals(BigDecimal.ZERO))
            return null;
        if (this.category.percent == null)
            return null;

        return this.amount.multiply(new BigDecimal(100)).divide(this.category.percent);

    }

    public void setUnderlyingValue(BigDecimal underlyingValue) {
        this.underlyingValue = underlyingValue;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public DocumentHeader getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(DocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }
}
