package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.DocumentHeaderRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentItemRepository;
import gr.aueb.dmst.simplinvoice.dao.projections.DocumentHeaderByCounterpart;
import gr.aueb.dmst.simplinvoice.dao.projections.DocumentHeaderByMonthSum;
import gr.aueb.dmst.simplinvoice.dao.projections.DocumentHeaderTotal;
import gr.aueb.dmst.simplinvoice.dao.projections.DocumentItemByMaterial;
import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static gr.aueb.dmst.simplinvoice.Constants.monthMessagesMap;

@Service
public class DashboardService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    DocumentHeaderRepository documentHeaderRepository;

    @Autowired
    DocumentItemRepository documentItemRepository;

    static Integer DEFAULT_LAST_MONTHS = 12;

    public BigDecimal calculateTotalRevenue(Long companyProfileId) {
        DocumentHeaderTotal documentHeaderTotal = documentHeaderRepository.getDocumentTotalValue(DEFAULT_LAST_MONTHS, DocumentType.ISSUED.name(), companyProfileId);

        return documentHeaderTotal != null ? documentHeaderTotal.getSum() : BigDecimal.ZERO;
    }

    public BigDecimal calculateTotalExpenses(Long companyProfileId) {
        DocumentHeaderTotal documentHeaderTotal = documentHeaderRepository.getDocumentTotalValue(DEFAULT_LAST_MONTHS, DocumentType.RECEIVED.name(), companyProfileId);

        return documentHeaderTotal != null ? documentHeaderTotal.getSum() : BigDecimal.ZERO;
    }

    public Map<String, BigDecimal> constructRevenueByMonthData(Long companyProfileId, Locale locale) {
        Map<String, BigDecimal> revenueByMonth = new LinkedHashMap<>();

        List<DocumentHeaderByMonthSum> invoicesList = documentHeaderRepository.getSumInvoicesByMonth(DEFAULT_LAST_MONTHS, DocumentType.ISSUED.name(), companyProfileId);
        Collections.reverse(invoicesList);
        invoicesList.forEach(it -> {
            revenueByMonth.put(getMonthLabel(it.getMonth(), locale), it.getSum());
        });

        return revenueByMonth;
    }

    public Map<String, BigDecimal> constructExpensesByMonthData(Long companyProfileId, Locale locale) {
        Map<String, BigDecimal> revenueByMonth = new LinkedHashMap<>();

        List<DocumentHeaderByMonthSum> invoicesList = documentHeaderRepository.getSumInvoicesByMonth(DEFAULT_LAST_MONTHS, DocumentType.RECEIVED.name(), companyProfileId);
        Collections.reverse(invoicesList);
        invoicesList.forEach(it -> {
            revenueByMonth.put(getMonthLabel(it.getMonth(), locale), it.getSum());
        });

        return revenueByMonth;
    }


    public Map<String, Long> constructProductsSalesData(Long companyProfileId) {
        Map<String, Long> productsSalesMap = new HashMap<>();

        List<DocumentItemByMaterial> documentItemByMaterialList = documentItemRepository.getDocumentItemByMaterial(DEFAULT_LAST_MONTHS, DocumentType.ISSUED.name(), companyProfileId);
        documentItemByMaterialList.forEach(it -> {
            productsSalesMap.put(it.getName(), it.getTotal());
        });

        return productsSalesMap;
    }

    public Map<String, Long> constructSuppliersInvoicesData(Long companyProfileId) {
        Map<String, Long> invoicesBySupplierMap = new HashMap<>();

        List<DocumentHeaderByCounterpart> invoicesBySuppliersList = documentHeaderRepository.getDocumentByCounterpart(DEFAULT_LAST_MONTHS, DocumentType.RECEIVED.name(), companyProfileId);
        invoicesBySuppliersList.forEach(it -> {
            invoicesBySupplierMap.put(it.getName(), it.getTotal());
        });

        return invoicesBySupplierMap;
    }

    String getMonthLabel(Integer month, Locale locale) {
        return messageSource.getMessage(monthMessagesMap.get(month), null, locale);
    }

}
