package gr.aueb.dmst.simplinvoice.enums;

public enum DocumentPaymentMethod {
        PAYMENT_METHOD_1("1"),
        PAYMENT_METHOD_2("2"),
        PAYMENT_METHOD_3("3"),
        PAYMENT_METHOD_4("4"),
        PAYMENT_METHOD_5("5");

        public final String aadeCode;

        private DocumentPaymentMethod(String aadeCode) {
                this.aadeCode = aadeCode;
        }

}