package pl.com.bottega.dms.infrastructure;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNotFoundException;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void put(Document document) {
        entityManager.persist(document);
    }

    @Override
    public Document get(DocumentNumber nr) {
        Document document = entityManager.find(Document.class, nr);
        if(document == null)
            throw new DocumentNotFoundException(nr);
        return entityManager.find(Document.class, nr);
    }
}
