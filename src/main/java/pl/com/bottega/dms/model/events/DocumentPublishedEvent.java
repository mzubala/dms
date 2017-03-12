package pl.com.bottega.dms.model.events;

import pl.com.bottega.dms.model.DocumentNumber;

public class DocumentPublishedEvent {

    private DocumentNumber documentNumber;

    public DocumentPublishedEvent(DocumentNumber documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentNumber getDocumentNumber() {
        return documentNumber;
    }
}
