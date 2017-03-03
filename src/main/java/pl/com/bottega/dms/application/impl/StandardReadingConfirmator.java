package pl.com.bottega.dms.application.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.ReadingConfirmator;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;

@Transactional
public class StandardReadingConfirmator implements ReadingConfirmator {

    private DocumentRepository documentRepository;

    public StandardReadingConfirmator(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void confirm(ConfirmDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        document.confirm(cmd);
    }

    @Override
    public void confirmFor(ConfirmForDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        document.confirmFor(cmd);
    }
}
