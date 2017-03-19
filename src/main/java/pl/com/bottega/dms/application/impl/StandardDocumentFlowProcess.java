package pl.com.bottega.dms.application.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.application.user.RequiresAuth;
import pl.com.bottega.dms.model.*;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.events.DocumentPublishedEvent;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;
import pl.com.bottega.dms.model.validation.DocumentValidator;
import pl.com.bottega.dms.model.validation.InvalidDocumentException;

@Transactional
public class StandardDocumentFlowProcess implements DocumentFlowProcess {

    private DocumentFactory documentFactory;
    private PrintCostCalculator printCostCalculator;
    private DocumentRepository documentRepository;
    private CurrentUser currentUser;
    private ApplicationEventPublisher publisher;
    private DocumentValidator documentValidator;

    public StandardDocumentFlowProcess(DocumentFactory documentFactory, PrintCostCalculator printCostCalculator,
                                       DocumentRepository documentRepository, CurrentUser currentUser,
                                       ApplicationEventPublisher publisher, DocumentValidator documentValidator) {
        this.documentFactory = documentFactory;
        this.printCostCalculator = printCostCalculator;
        this.documentRepository = documentRepository;
        this.currentUser = currentUser;
        this.publisher = publisher;
        this.documentValidator = documentValidator;
    }

    @Override
    @RequiresAuth("QUALITY_STAFF")
    public DocumentNumber create(CreateDocumentCommand cmd) {
        Document document = documentFactory.create(cmd);
        documentRepository.put(document);
        return document.getNumber();
    }

    @Override
    @RequiresAuth("QUALITY_STAFF")
    public void change(ChangeDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        document.change(cmd);
    }

    @Override
    @RequiresAuth("QUALITY_MANAGER")
    public void verify(DocumentNumber documentNumber) {
        Document document = documentRepository.get(documentNumber);
        if(!documentValidator.isValid(document, DocumentStatus.VERIFIED))
            throw new InvalidDocumentException();
        document.verify(currentUser.getEmployeeId());
    }

    @Override
    @RequiresAuth("QUALITY_MANAGER")
    public void publish(PublishDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        if(!documentValidator.isValid(document, DocumentStatus.PUBLISHED))
            throw new InvalidDocumentException();
        document.publish(cmd, printCostCalculator);
        publisher.publishEvent(new DocumentPublishedEvent(documentNumber));
    }

    @Override
    @RequiresAuth("QUALITY_MANAGER")
    public void archive(DocumentNumber documentNumber) {
        Document document = documentRepository.get(documentNumber);
        document.archive(currentUser.getEmployeeId());
    }
}
