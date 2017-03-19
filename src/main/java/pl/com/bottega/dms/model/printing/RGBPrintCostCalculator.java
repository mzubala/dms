package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;

import java.math.BigDecimal;

public class RGBPrintCostCalculator implements PrintCostCalculator {

    public static final int RGB_PAGE_COST = 3;

    public BigDecimal calculateCost(Document document) {
        return new BigDecimal(document.getPagesCount() * RGB_PAGE_COST);
    }


}
