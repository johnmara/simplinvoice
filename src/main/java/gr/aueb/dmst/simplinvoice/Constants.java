package gr.aueb.dmst.simplinvoice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Constants {

    public static Map<String, String> countries = new HashMap<>();
    static {
        Arrays.stream(Locale.getISOCountries()).forEach( it ->
                countries.put(it, new Locale("", it).getDisplayCountry())
        );
    }

}
