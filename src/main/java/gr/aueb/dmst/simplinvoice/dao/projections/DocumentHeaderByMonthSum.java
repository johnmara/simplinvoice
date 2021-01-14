package gr.aueb.dmst.simplinvoice.dao.projections;

import java.math.BigDecimal;

public interface DocumentHeaderByMonthSum {

    Integer getYear();
    Integer getMonth();
    BigDecimal getSum();

}
