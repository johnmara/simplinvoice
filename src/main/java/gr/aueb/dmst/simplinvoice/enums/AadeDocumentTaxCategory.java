package gr.aueb.dmst.simplinvoice.enums;

import java.math.BigDecimal;

import static gr.aueb.dmst.simplinvoice.enums.AadeDocumentTaxCategory.AadeDocumentTaxType.*;

public enum AadeDocumentTaxCategory {

        AADE_TAX_CAT_1_1("1", AADE_TAX_TYPE_1, new BigDecimal("15")),
        AADE_TAX_CAT_1_2("2", AADE_TAX_TYPE_1, new BigDecimal("20")),
        AADE_TAX_CAT_1_3("3", AADE_TAX_TYPE_1, new BigDecimal("20")),
        AADE_TAX_CAT_1_4("4", AADE_TAX_TYPE_1, new BigDecimal("3")),
        AADE_TAX_CAT_1_5("5", AADE_TAX_TYPE_1, new BigDecimal("1")),
        AADE_TAX_CAT_1_6("6", AADE_TAX_TYPE_1, new BigDecimal("4")),
        AADE_TAX_CAT_1_7("7", AADE_TAX_TYPE_1, new BigDecimal("8")),
        AADE_TAX_CAT_1_8("8", AADE_TAX_TYPE_1, new BigDecimal("4")),
        AADE_TAX_CAT_1_9("9", AADE_TAX_TYPE_1, new BigDecimal("10")),
        AADE_TAX_CAT_1_10("10", AADE_TAX_TYPE_1, new BigDecimal("15")),
        AADE_TAX_CAT_1_11("11", AADE_TAX_TYPE_1, null),
        AADE_TAX_CAT_1_12("12", AADE_TAX_TYPE_1, null),
        AADE_TAX_CAT_1_13("13", AADE_TAX_TYPE_1, null),
        AADE_TAX_CAT_1_14("14", AADE_TAX_TYPE_1, null),
        AADE_TAX_CAT_1_15("15", AADE_TAX_TYPE_1, null),
        AADE_TAX_CAT_2_1("1", AADE_TAX_TYPE_2, new BigDecimal("12")),
        AADE_TAX_CAT_2_2("2", AADE_TAX_TYPE_2, new BigDecimal("15")),
        AADE_TAX_CAT_2_3("3", AADE_TAX_TYPE_2, new BigDecimal("18")),
        AADE_TAX_CAT_2_4("4", AADE_TAX_TYPE_2, new BigDecimal("20")),
        AADE_TAX_CAT_2_5("5", AADE_TAX_TYPE_2, new BigDecimal("12")),
        AADE_TAX_CAT_2_6("6", AADE_TAX_TYPE_2, new BigDecimal("10")),
        AADE_TAX_CAT_2_7("7", AADE_TAX_TYPE_2, new BigDecimal("5")),
        AADE_TAX_CAT_2_8("8", AADE_TAX_TYPE_2, null),
        AADE_TAX_CAT_2_9("9", AADE_TAX_TYPE_2, new BigDecimal("2")),
        AADE_TAX_CAT_3_1("1", AADE_TAX_TYPE_3, new BigDecimal("20")),
        AADE_TAX_CAT_3_2("2", AADE_TAX_TYPE_3, new BigDecimal("20")),
        AADE_TAX_CAT_3_3("3", AADE_TAX_TYPE_3, new BigDecimal("4")),
        AADE_TAX_CAT_3_4("4", AADE_TAX_TYPE_3, new BigDecimal("15")),
        AADE_TAX_CAT_3_5("5", AADE_TAX_TYPE_3, new BigDecimal("0")),
        AADE_TAX_CAT_3_6("6", AADE_TAX_TYPE_3, null),
        AADE_TAX_CAT_3_7("7", AADE_TAX_TYPE_3, null),
        AADE_TAX_CAT_3_8("8", AADE_TAX_TYPE_3, null),
        AADE_TAX_CAT_3_9("9", AADE_TAX_TYPE_3, null),
        AADE_TAX_CAT_3_10("10", AADE_TAX_TYPE_3, null),
        AADE_TAX_CAT_3_11("11", AADE_TAX_TYPE_3, new BigDecimal("5")),
        AADE_TAX_CAT_3_12("12", AADE_TAX_TYPE_3, new BigDecimal("10")),
        AADE_TAX_CAT_3_13("13", AADE_TAX_TYPE_3, new BigDecimal("10")),
        AADE_TAX_CAT_3_14("14", AADE_TAX_TYPE_3, new BigDecimal("80")),
        AADE_TAX_CAT_4_1("1", AADE_TAX_TYPE_4, new BigDecimal("1.2")),
        AADE_TAX_CAT_4_2("2", AADE_TAX_TYPE_4, new BigDecimal("2.4")),
        AADE_TAX_CAT_4_3("3", AADE_TAX_TYPE_4, new BigDecimal("3.6")),
        AADE_TAX_CAT_5("1", AADE_TAX_TYPE_5, null);

        public final String aadeCode;
        public final AadeDocumentTaxCategory.AadeDocumentTaxType category;
        public final BigDecimal percent;

        private AadeDocumentTaxCategory(String aadeCode, AadeDocumentTaxCategory.AadeDocumentTaxType category, BigDecimal percent) {
                this.aadeCode = aadeCode;
                this.category = category;
                this.percent = percent;
        }

        public enum AadeDocumentTaxType {
                AADE_TAX_TYPE_1("1"),
                AADE_TAX_TYPE_2("2"),
                AADE_TAX_TYPE_3("3"),
                AADE_TAX_TYPE_4("4"),
                AADE_TAX_TYPE_5("5");

                public final String aadeCode;

                private AadeDocumentTaxType(String aadeCode) {
                        this.aadeCode = aadeCode;
                }
        }
}