package gr.aueb.dmst.simplinvoice.enums;

public enum MeasurementUnit {
        UNITS("1"),
        KG("2"),
        LT("3");

        public String aadeCode;

        MeasurementUnit(String aadeCode) {
                this.aadeCode = aadeCode;
        }
}