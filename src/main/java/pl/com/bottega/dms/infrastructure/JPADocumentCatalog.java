package pl.com.bottega.dms.infrastructure;

import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.application.DocumentDto;
import pl.com.bottega.dms.application.DocumentQuery;
import pl.com.bottega.dms.application.DocumentSearchResults;
import pl.com.bottega.dms.model.DocumentNumber;

public class JPADocumentCatalog implements DocumentCatalog {
    @Override
    public DocumentSearchResults find(DocumentQuery documentQuery) {
        return null;
    }

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        return null;
    }
}
