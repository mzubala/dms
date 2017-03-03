package pl.com.bottega.dms.model;

public interface DocumentRepository {
    void put(Document document);

    Document get(DocumentNumber nr);
}
