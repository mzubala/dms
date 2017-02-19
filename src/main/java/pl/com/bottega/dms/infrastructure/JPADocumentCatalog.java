package pl.com.bottega.dms.infrastructure;

import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.application.DocumentDto;
import pl.com.bottega.dms.application.DocumentQuery;
import pl.com.bottega.dms.application.DocumentSearchResults;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPADocumentCatalog implements DocumentCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentSearchResults find(DocumentQuery documentQuery) {
        return null;
    }

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        Document document = entityManager.find(Document.class, documentNumber);
        DocumentDto documentDto = new DocumentDto();
        documentDto.setNumber(documentNumber.getNumber());
        documentDto.setTitle(document.getTitle());
        return documentDto;
    }
}
