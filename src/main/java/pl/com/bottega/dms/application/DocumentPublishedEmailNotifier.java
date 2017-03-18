package pl.com.bottega.dms.application;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.events.DocumentPublishedEvent;

@Component
public class DocumentPublishedEmailNotifier {

    @Autowired
    private DocumentRepository documentRepository;

    @TransactionalEventListener
    @Async
    public void documentPublished(DocumentPublishedEvent documentPublishedEvent) {
        Document document = documentRepository.get(documentPublishedEvent.getDocumentNumber());
        Logger.getLogger(DocumentPublishedEmailNotifier.class).info("Document status: " + document.getStatus());
        Logger.getLogger(DocumentPublishedEmailNotifier.class).info("Mailing to recipients!");
    }

}
