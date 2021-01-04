package gr.aueb.dmst.simplinvoice.enums;

import java.math.BigDecimal;

public enum VatCategory {
        VAT_24("1", new BigDecimal("24")),
        VAT_13("2", new BigDecimal("13")),
        VAT_6("3", new BigDecimal("6")),
        VAT_17("4", new BigDecimal("17")),
        VAT_9("5", new BigDecimal("9")),
        VAT_4("6", new BigDecimal("4")),
        VAT_0("7", BigDecimal.ZERO);

        public final String aadeCode;
        public final BigDecimal value;

        private VatCategory(String aadeCode, BigDecimal value) {
                this.aadeCode = aadeCode;
                this.value = value;
        }
}