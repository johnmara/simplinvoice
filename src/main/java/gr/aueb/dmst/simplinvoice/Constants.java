package gr.aueb.dmst.simplinvoice;

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

}
