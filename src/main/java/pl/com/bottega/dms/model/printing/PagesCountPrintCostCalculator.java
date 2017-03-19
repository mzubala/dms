package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentType;

import java.math.BigDecimal;

public class PagesCountPrintCostCalculator implements PrintCostCalculator {

    private static final BigDecimal HIGH_PAGES_COUNT_COST = new BigDecimal(40);
    private static final int PAGES_COUNT_LIMIT = 100;

    private PrintCostCalculator decorated;

    public PagesCountPrintCostCalculator(PrintCostCalculator decorated) {
        this.decorated = decorated;
    }

    @Override
    public BigDecimal calculateCost(Document document) {
        BigDecimal cost = decorated.calculateCost(document);
        if (document.getPagesCount() > PAGES_COUNT_LIMIT)
            cost = cost.add(HIGH_PAGES_COUNT_COST);
        return cost;
    }
}
