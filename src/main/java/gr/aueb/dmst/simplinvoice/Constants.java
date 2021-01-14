package gr.aueb.dmst.simplinvoice;

import gr.aueb.dmst.simplinvoice.enums.AadeDocumentTaxCategory;
import gr.aueb.dmst.simplinvoice.enums.AadeInvoiceType;
import java.util.*;
import java.util.stream.Collectors;

public class Constants {

    public static Map<String, String> countries = new HashMap<>();
    static {
        Arrays.stream(Locale.getISOCountries()).forEach( it ->
                countries.put(it, new Locale("", it).getDisplayCountry())
        );
    }

    public static Map<AadeInvoiceType.AadeInvoiceTypeCategory, List<AadeInvoiceType>> aadeInvoiceTypeMap;
    static {
        aadeInvoiceTypeMap =  Arrays.stream(AadeInvoiceType.values()).collect(Collectors.groupingBy(it->it.category));
    }

    public static Map<AadeDocumentTaxCategory.AadeDocumentTaxType, List<AadeDocumentTaxCategory>> aadeDocumentTaxCategoryMap;
    static {
        aadeDocumentTaxCategoryMap =  Arrays.stream(AadeDocumentTaxCategory.values()).collect(Collectors.groupingBy(it->it.type));
    }

    public static Map<Integer, String> monthMessagesMap = new HashMap<>();
    static {
        monthMessagesMap.put(1, "month.january");
        monthMessagesMap.put(2, "month.february");
        monthMessagesMap.put(3, "month.march");
        monthMessagesMap.put(4, "month.april");
        monthMessagesMap.put(5, "month.may");
        monthMessagesMap.put(6, "month.june");
        monthMessagesMap.put(7, "month.july");
        monthMessagesMap.put(8, "month.august");
        monthMessagesMap.put(9, "month.september");
        monthMessagesMap.put(10, "month.october");
        monthMessagesMap.put(11, "month.november");
        monthMessagesMap.put(12, "month.december");
    }

}
