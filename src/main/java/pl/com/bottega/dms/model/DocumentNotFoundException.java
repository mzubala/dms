package pl.com.bottega.dms.model;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(DocumentNumber nr) {
        super(String.format("Document with number %s does not exist", nr.getNumber()));
    }
}
