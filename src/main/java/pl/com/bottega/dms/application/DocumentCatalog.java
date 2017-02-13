package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.DocumentNumber;

import java.util.Collection;

public interface DocumentCatalog {

    DocumentSearchResults find(DocumentQuery documentQuery);

    DocumentDto get(DocumentNumber documentNumber);

}
