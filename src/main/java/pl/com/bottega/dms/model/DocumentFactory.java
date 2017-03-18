package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.numbers.NumberGenerator;

public class DocumentFactory {

    private NumberGenerator numberGenerator;

    public DocumentFactory(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public Document create(CreateDocumentCommand cmd) {
        DocumentNumber number = numberGenerator.generate();
        return new Document(cmd, number);
    }

}
