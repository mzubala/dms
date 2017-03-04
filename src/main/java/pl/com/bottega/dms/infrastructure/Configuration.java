package pl.com.bottega.dms.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.application.ReadingConfirmator;
import pl.com.bottega.dms.application.impl.StandardDocumentFlowProcess;
import pl.com.bottega.dms.application.impl.StandardReadingConfirmator;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.numbers.ISONumberGenerator;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;
import pl.com.bottega.dms.model.printing.RGBPrintCostCalculator;

@org.springframework.context.annotation.Configuration
public class Configuration {

}
