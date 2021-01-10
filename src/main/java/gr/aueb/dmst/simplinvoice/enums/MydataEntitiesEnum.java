package gr.aueb.dmst.simplinvoice.enums;

import generated.RequestedProviderDoc;
import generated.ResponseDoc;
import gr.aade.mydata.invoice.v1.InvoicesDoc;
import https.www_aade_gr.mydata.expensesclassificaton.v1.ExpensesClassificationsDoc;
import https.www_aade_gr.mydata.incomeclassificaton.v1.IncomeClassificationsDoc;

public enum MydataEntitiesEnum {

    EXPENSES_CLASSIFICATION( ExpensesClassificationsDoc.class, "/myData/xsd/expensesClassification-v0.6.xsd"),
    INCOME_CLASSIFICATION(IncomeClassificationsDoc.class, "/myData/xsd/expensesClassification-v0.6.xsd"),
    INVOICES_DOC(InvoicesDoc.class, "/myData/xsd/InvoicesDoc-v0.6.xsd"),
    REQUESTED_PROVIDER_DOC(RequestedProviderDoc.class, "/myData/xsd/RequestedProviderDoc-v0.6.xsd"),
    RESPONSE(ResponseDoc.class, "/myData/xsd/Response-v0.6.xsd");

    public Class<?> classType;
    public String xsdPath;

    MydataEntitiesEnum(Class<?> classType, String xsdPath) {
        this.classType = classType;
        this.xsdPath = xsdPath;
    }


}
