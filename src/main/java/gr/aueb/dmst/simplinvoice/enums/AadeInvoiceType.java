package gr.aueb.dmst.simplinvoice.enums;

import static gr.aueb.dmst.simplinvoice.enums.AadeInvoiceType.AadeInvoiceTypeCategory.*;

public enum AadeInvoiceType {

    // Antikrizomena
    AADE_INV_TYPE_1_1("1.1", CAT_1),
    AADE_INV_TYPE_1_2("1.2", CAT_1),
    AADE_INV_TYPE_1_3("1.3", CAT_1),
    AADE_INV_TYPE_1_4("1.4", CAT_1),
    AADE_INV_TYPE_1_5("1.5", CAT_1),
//    AADE_INV_TYPE_1_6("1.6", CAT_1),
    AADE_INV_TYPE_2_1("2.1", CAT_2),
    AADE_INV_TYPE_2_2("2.2", CAT_2),
    AADE_INV_TYPE_2_3("2.3", CAT_2),
//    AADE_INV_TYPE_2_4("2.4", CAT_2),
    AADE_INV_TYPE_3_1("3.1", CAT_3),
    AADE_INV_TYPE_3_2("3.2", CAT_3),
    AADE_INV_TYPE_5_1("5.1", CAT_5),
    AADE_INV_TYPE_5_2("5.2", CAT_5),
    AADE_INV_TYPE_6_1("6.1", CAT_6),
    AADE_INV_TYPE_6_2("6.2", CAT_6),
    AADE_INV_TYPE_7_1("7.1", CAT_7),
    AADE_INV_TYPE_8_1("8.1", CAT_8),
//    AADE_INV_TYPE_8_2("8.2", CAT_8),
    // Mh antikrizomena
    AADE_INV_TYPE_11_1("11.1", CAT_11),
    AADE_INV_TYPE_11_2("11.2", CAT_11),
    AADE_INV_TYPE_11_3("11.3", CAT_11),
    AADE_INV_TYPE_11_4("11.4", CAT_11),
    AADE_INV_TYPE_11_5("11.5", CAT_11),
//    AADE_INV_TYPE_12("12", CAT_12),
    AADE_INV_TYPE_13_1("13.1", CAT_13),
    AADE_INV_TYPE_13_2("13.2", CAT_13),
    AADE_INV_TYPE_13_3("13.3", CAT_13),
    AADE_INV_TYPE_13_4("13.4", CAT_13),
    AADE_INV_TYPE_13_30("13.30", CAT_13),
    AADE_INV_TYPE_13_31("13.31", CAT_13),
    AADE_INV_TYPE_14_1("14.1", CAT_14),
    AADE_INV_TYPE_14_2("14.2", CAT_14),
    AADE_INV_TYPE_14_3("14.3", CAT_14),
    AADE_INV_TYPE_14_4("14.4", CAT_14),
    AADE_INV_TYPE_14_5("14.5", CAT_14),
    AADE_INV_TYPE_14_30("14.30", CAT_14),
    AADE_INV_TYPE_14_31("14.31", CAT_14),
    AADE_INV_TYPE_15_1("15.1", CAT_15),
    AADE_INV_TYPE_16_1("16.1", CAT_16),
    AADE_INV_TYPE_17_1("17.1", CAT_17),
    AADE_INV_TYPE_17_2("17.2", CAT_17),
    AADE_INV_TYPE_17_3("17.3", CAT_17),
    AADE_INV_TYPE_17_4("17.4", CAT_17),
    AADE_INV_TYPE_17_5("17.5", CAT_17),
    AADE_INV_TYPE_17_6("17.6", CAT_17);

    public final String code;
    public final AadeInvoiceTypeCategory category;

    private AadeInvoiceType(String code, AadeInvoiceTypeCategory category) {
        this.code = code;
        this.category = category;
    }

    public enum AadeInvoiceTypeCategory {
        CAT_1, CAT_2, CAT_3, CAT_5, CAT_6, CAT_7, CAT_8, CAT_11, /*CAT_12,*/ CAT_13, CAT_14, CAT_15, CAT_16, CAT_17
    }
}
