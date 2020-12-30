package gr.aueb.dmst.simplinvoice.enums;

import java.math.BigDecimal;

public enum VatCategory {
        VAT_24(new BigDecimal("24")),
        VAT_13(new BigDecimal("13")),
        VAT_0(BigDecimal.ZERO),
        VAT_6(new BigDecimal("6")),
        VAT_17(new BigDecimal("17")),
        VAT_9(new BigDecimal("9")),
        VAT_4(new BigDecimal("4")),
        VAT_10(new BigDecimal("10")),
        VAT_20(new BigDecimal("20")),
        VAT_22(new BigDecimal("22"));

        public final BigDecimal value;

        private VatCategory(BigDecimal value) {
            this.value = value;
        }
}