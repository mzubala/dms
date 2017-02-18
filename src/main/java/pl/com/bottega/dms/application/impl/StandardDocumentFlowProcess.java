package pl.com.bottega.dms.application.impl;

import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

public class StandardDocumentFlowProcess implements DocumentFlowProcess {

    private NumberGenerator numberGenerator;
    private PrintCostCalculator printCostCalculator;

    public StandardDocumentFlowProcess(NumberGenerator numberGenerator, PrintCostCalculator printCostCalculator) {
        this.numberGenerator = numberGenerator;
        this.printCostCalculator = printCostCalculator;
    }

    @Override
    public DocumentNumber create(CreateDocumentCommand cmd) {
        System.out.println("Hello World!!!!!!");
        return null;
    }

    @Override
    public void change(ChangeDocumentCommand cmd) {

    }

    @Override
    public void verify(DocumentNumber documentNumber) {

    }

    @Override
    public void publish(PublishDocumentCommand cmd) {

    }

    @Override
    public void archive(DocumentNumber documentNumber) {

    }
}
