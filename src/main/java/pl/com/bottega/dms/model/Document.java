package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import static pl.com.bottega.dms.model.DocumentStatus.DRAFT;

public class Document {

    private DocumentNumber number;
    private DocumentStatus status;
    private String title;
    private String content;

    public Document(CreateDocumentCommand cmd, NumberGenerator numberGenerator) {
        this.number = numberGenerator.generate();
        this.status = DRAFT;
        this.title = cmd.getTitle();
    }

    public void change(ChangeDocumentCommand cmd) {
        this.title = cmd.getTitle();
        this.content = cmd.getContent();
    }

    public void verify() {

    }

    public void archive() {

    }

    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {

    }

    public void confirm(ConfirmDocumentCommand cmd) {

    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {

    }

    public DocumentStatus getStatus() {
        return status;
    }

    public DocumentNumber getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
