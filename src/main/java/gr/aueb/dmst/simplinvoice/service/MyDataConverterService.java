package gr.aueb.dmst.simplinvoice.service;

import gr.aade.mydata.invoice.v1.*;
import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.XmlUtils;
import gr.aueb.dmst.simplinvoice.dao.DocumentHeaderRepository;
import gr.aueb.dmst.simplinvoice.enums.AadeDocumentTaxCategory;
import gr.aueb.dmst.simplinvoice.enums.MeasurementUnit;
import gr.aueb.dmst.simplinvoice.enums.MydataEntitiesEnum;
import gr.aueb.dmst.simplinvoice.enums.VatCategory;
import gr.aueb.dmst.simplinvoice.exception.MydataValidationException;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import gr.aueb.dmst.simplinvoice.model.DocumentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MyDataConverterService {

    @Autowired
    DocumentHeaderRepository documentHeaderRepository;

    public String convertDocumentsToXml(List<DocumentHeader> documentHeaderList) throws JAXBException, IOException, SAXException, MydataValidationException {
        InvoicesDoc invoicesDoc = new InvoicesDoc();

        documentHeaderList.forEach(documentHeader -> {
            AadeBookInvoiceType invoiceType = convertSingleDocument(documentHeader);
            addMyDataXmlToDocumentHeader(documentHeader, invoiceType);
            invoicesDoc.getInvoice().add(invoiceType);
        });

        String generatedXml = XmlUtils.convertToxml(invoicesDoc);
        XmlUtils.validate(generatedXml, MydataEntitiesEnum.INVOICES_DOC);

        return generatedXml;
    }

    AadeBookInvoiceType convertSingleDocument(DocumentHeader documentHeader) {
        AadeBookInvoiceType aadeBookInvoiceType = new AadeBookInvoiceType();

        //Counterpart
        PartyType counterpart = new PartyType();
        counterpart.setVatNumber(documentHeader.getCounterPart().getAfm());
        counterpart.setCountry(CountryType.valueOf(documentHeader.getCounterPart().getCountry()));
        counterpart.setBranch(documentHeader.getCounterPart().getBranch());
        counterpart.setName(documentHeader.getCounterPart().getName());
        counterpart.setAddress(new AddressType());
        counterpart.getAddress().setStreet(documentHeader.getCounterPart().getStreet());
        counterpart.getAddress().setNumber(String.valueOf(documentHeader.getCounterPart().getStreetNumber()));
        counterpart.getAddress().setPostalCode(documentHeader.getCounterPart().getPostalCode());
        counterpart.getAddress().setCity(documentHeader.getCounterPart().getTown());

        aadeBookInvoiceType.setCounterpart(counterpart);

        //Issuer
        PartyType issuer = new PartyType();
        issuer.setVatNumber(documentHeader.getCompanyProfile().getAfm());
        issuer.setCountry(CountryType.valueOf(documentHeader.getCompanyProfile().getCountry()));
        issuer.setBranch(documentHeader.getCompanyProfile().getBranch());
        issuer.setName(documentHeader.getCompanyProfile().getCompanyName());
        issuer.setAddress(new AddressType());
        issuer.getAddress().setStreet(documentHeader.getCompanyProfile().getStreet());
        issuer.getAddress().setNumber(String.valueOf(documentHeader.getCompanyProfile().getStreetNumber()));
        issuer.getAddress().setPostalCode(documentHeader.getCompanyProfile().getPostalCode());
        issuer.getAddress().setCity(documentHeader.getCompanyProfile().getTown());

        aadeBookInvoiceType.setIssuer(issuer);

        //Invoice Header
        InvoiceHeaderType invoiceHeaderType = new InvoiceHeaderType();
        invoiceHeaderType.setSeries(documentHeader.getDocumentSeries().getCode());
        invoiceHeaderType.setAa(String.valueOf(documentHeader.getNumber()));
        invoiceHeaderType.setIssueDate(Utils.convertDateToXmlGregorianCalendar(documentHeader.getDate()));
        invoiceHeaderType.setInvoiceType(documentHeader.getDocumentSeries().getAadeInvoiceType().aadeCode);
        invoiceHeaderType.setVatPaymentSuspension(false);
        invoiceHeaderType.setCurrency(CurrencyType.valueOf(documentHeader.getCurrency()));
        invoiceHeaderType.setSelfPricing(false);

        aadeBookInvoiceType.setInvoiceHeader(invoiceHeaderType);

        //Payment methods
        AadeBookInvoiceType.PaymentMethods paymentMethods = new AadeBookInvoiceType.PaymentMethods();
        PaymentMethodDetailType paymentMethodDetailType = new PaymentMethodDetailType();
        paymentMethodDetailType.setType(Integer.parseInt(documentHeader.getPaymentMethod().aadeCode));
        paymentMethodDetailType.setAmount(documentHeader.getTotalFinalValue());

        paymentMethods.getPaymentMethodDetails().add(paymentMethodDetailType);
        aadeBookInvoiceType.setPaymentMethods(paymentMethods);

        //Invoice Details
        Map<VatCategory, List<DocumentItem>> vatItemsMap = documentHeader.getDocumentItems().stream()
                .collect(Collectors.groupingBy(it -> it.getMaterial().getVatCategory()));

        int index = 1;
        for (Map.Entry<VatCategory, List<DocumentItem>> entry : vatItemsMap.entrySet()) {
            boolean singleItem = entry.getValue().size() == 1;
            VatCategory vatCategory = entry.getValue().get(0).getMaterial().getVatCategory();

            InvoiceRowType invoiceRowType = new InvoiceRowType();
            invoiceRowType.setLineNumber(index++);

            boolean sameMeasurementUnit = entry.getValue().stream().map(it -> it.getMaterial().getMeasurementUnit())
                    .distinct()
                    .count() <= 1;

            if(sameMeasurementUnit || singleItem) {
                MeasurementUnit measurementUnit = entry.getValue().get(0).getMaterial().getMeasurementUnit();
                if(measurementUnit != null) {
                    BigDecimal quantitySum = entry.getValue().stream().map(DocumentItem::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
                    invoiceRowType.setQuantity(quantitySum);
                    invoiceRowType.setMeasurementUnit(Integer.parseInt(measurementUnit.aadeCode));
                }
            }

            if(singleItem) {
               invoiceRowType.setNetValue(entry.getValue().get(0).getNetValue());
               invoiceRowType.setVatAmount(entry.getValue().get(0).getVatValue());
            } else {
                BigDecimal netValueSum = entry.getValue().stream()
                        .map(DocumentItem::getNetValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                invoiceRowType.setNetValue(netValueSum);
                BigDecimal vatValueSum = entry.getValue().stream()
                        .map(DocumentItem::getVatValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                invoiceRowType.setVatAmount(vatValueSum);
            }

            invoiceRowType.setVatCategory(Integer.parseInt(vatCategory.aadeCode));


            boolean sameDiscount = entry.getValue().stream().map(DocumentItem::getDiscountValue).allMatch(it -> it == null || it.equals(BigDecimal.ZERO));

            if(sameDiscount || singleItem) {
                BigDecimal discountValue = entry.getValue().get(0).getDiscountValue();
                invoiceRowType.setDiscountOption(discountValue != null && !discountValue.equals(BigDecimal.ZERO));
            }

            if(vatCategory.equals(VatCategory.VAT_0)) {
                invoiceRowType.setVatExemptionCategory(Integer.parseInt(documentHeader.getVatExemptionType().aadeCode));
            }

            aadeBookInvoiceType.getInvoiceDetails().add(invoiceRowType);
        }


        //Taxes Total
        if(!ObjectUtils.isEmpty(documentHeader.getDocumentTaxes())) {
            AadeBookInvoiceType.TaxesTotals taxesTotals = new AadeBookInvoiceType.TaxesTotals();
            documentHeader.getDocumentTaxes().forEach(tax -> {
                TaxTotalsType taxTotalsType = new TaxTotalsType();
                taxTotalsType.setTaxType(Byte.parseByte(tax.getCategory().type.aadeCode));
                if(tax.getCategory().aadeCode != null)
                    taxTotalsType.setTaxCategory(Byte.parseByte(tax.getCategory().aadeCode));
                taxTotalsType.setUnderlyingValue(tax.calculateUnderlyingValue());
                taxTotalsType.setTaxAmount(tax.getAmount());
                taxesTotals.getTaxes().add(taxTotalsType);
            });
            aadeBookInvoiceType.setTaxesTotals(taxesTotals);
        }

        //Invoice Summary
        InvoiceSummaryType invoiceSummaryType = new InvoiceSummaryType();
        invoiceSummaryType.setTotalNetValue(documentHeader.getTotalNetValue());
        invoiceSummaryType.setTotalVatAmount(documentHeader.getTotalVatValue());

        invoiceSummaryType.setTotalWithheldAmount(
                Utils.getAadeDocumentTaxTypeTotalValue(documentHeader.getDocumentTaxes(), AadeDocumentTaxCategory.AadeDocumentTaxType.AADE_TAX_TYPE_1));
        invoiceSummaryType.setTotalFeesAmount(
                Utils.getAadeDocumentTaxTypeTotalValue(documentHeader.getDocumentTaxes(), AadeDocumentTaxCategory.AadeDocumentTaxType.AADE_TAX_TYPE_2));
        invoiceSummaryType.setTotalStampDutyAmount(
                Utils.getAadeDocumentTaxTypeTotalValue(documentHeader.getDocumentTaxes(), AadeDocumentTaxCategory.AadeDocumentTaxType.AADE_TAX_TYPE_4));
        invoiceSummaryType.setTotalOtherTaxesAmount(
                Utils.getAadeDocumentTaxTypeTotalValue(documentHeader.getDocumentTaxes(), AadeDocumentTaxCategory.AadeDocumentTaxType.AADE_TAX_TYPE_3));
        invoiceSummaryType.setTotalDeductionsAmount(
                Utils.getAadeDocumentTaxTypeTotalValue(documentHeader.getDocumentTaxes(), AadeDocumentTaxCategory.AadeDocumentTaxType.AADE_TAX_TYPE_5));
        invoiceSummaryType.setTotalGrossValue(documentHeader.getTotalFinalValue());

        aadeBookInvoiceType.setInvoiceSummary(invoiceSummaryType);

        return aadeBookInvoiceType;
    }

    private void addMyDataXmlToDocumentHeader(DocumentHeader documentHeader, AadeBookInvoiceType aadeBookInvoiceType) {
        InvoicesDoc invoicesDoc = new InvoicesDoc();
        invoicesDoc.getInvoice().add(aadeBookInvoiceType);

        try {
            String xmlText = XmlUtils.convertToxml(invoicesDoc);
            documentHeader.setMydataXml(xmlText);
            XmlUtils.validate(xmlText, MydataEntitiesEnum.INVOICES_DOC);
        } catch (MydataValidationException ex) {
            documentHeader.setMyDataErrorsList(Collections.singletonList(ex.getMessage()));
        } catch (Exception ex) {}
        finally {
          documentHeaderRepository.save(documentHeader);
        }

    }

}
