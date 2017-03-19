package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentType;

import java.math.BigDecimal;

public class ManualPrintCostCalculator implements PrintCostCalculator {

    private static final BigDecimal COST_FACTOR = new BigDecimal(1.3);

    private PrintCostCalculator decorated;

    public ManualPrintCostCalculator(PrintCostCalculator decorated) {
        this.decorated = decorated;
    }

    @Override
    public BigDecimal calculateCost(Document document) {
        BigDecimal cost = decorated.calculateCost(document);
        if (document.getType().equals(DocumentType.MANUAL))
            cost = cost.multiply(COST_FACTOR);
        return cost;
    }
}
