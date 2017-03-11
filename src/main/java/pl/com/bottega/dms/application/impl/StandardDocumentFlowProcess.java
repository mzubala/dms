package pl.com.bottega.dms.application.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.application.user.AuthRequiredException;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

@Transactional
public class StandardDocumentFlowProcess implements DocumentFlowProcess {

    private NumberGenerator numberGenerator;
    private PrintCostCalculator printCostCalculator;
    private DocumentRepository documentRepository;
    private CurrentUser currentUser;

    public StandardDocumentFlowProcess(NumberGenerator numberGenerator, PrintCostCalculator printCostCalculator,
                                       DocumentRepository documentRepository, CurrentUser currentUser) {
        this.numberGenerator = numberGenerator;
        this.printCostCalculator = printCostCalculator;
        this.documentRepository = documentRepository;
        this.currentUser = currentUser;
    }

    @Override
    public DocumentNumber create(CreateDocumentCommand cmd) {
        if(currentUser.getEmployeeId() == null)
            throw new AuthRequiredException();
        Document document = new Document(cmd, numberGenerator);
        documentRepository.put(document);
        return document.getNumber();
    }

    @Override
    public void change(ChangeDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        document.change(cmd);
    }

    @Override
    public void verify(DocumentNumber documentNumber) {
        Document document = documentRepository.get(documentNumber);
        document.verify(new EmployeeId(1L));
    }

    @Override
    public void publish(PublishDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        document.publish(cmd, printCostCalculator);
    }

    @Override
    public void archive(DocumentNumber documentNumber) {
        Document document = documentRepository.get(documentNumber);
        document.archive(new EmployeeId(1L));
    }
}
