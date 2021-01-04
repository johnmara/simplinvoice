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
    private AadeDocumentTaxCategory type;
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

    public AadeDocumentTaxCategory getType() {
        return type;
    }

    public void setType(AadeDocumentTaxCategory type) {
        this.type = type;
    }

    public BigDecimal getUnderlyingValue() {
        return underlyingValue;
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
