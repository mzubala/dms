package pl.com.bottega.dms.model.validation;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentStatus;

public class AgreeableDocumentValidator extends DocumentValidator {
    @Override
    public boolean isValid(Document document, DocumentStatus targetStatus) {
        return true;
    }
}
