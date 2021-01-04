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
    private BigDecimal underlyingValue;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "document_header_id", referencedColumnName = "id")
    private DocumentHeader documentHeader;

}
