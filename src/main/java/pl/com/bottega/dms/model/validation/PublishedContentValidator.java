package pl.com.bottega.dms.model.validation;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentStatus;

public class PublishedContentValidator extends DocumentValidator {
    @Override
    public boolean isValid(Document document, DocumentStatus targetStatus) {
        if(targetStatus.equals(DocumentStatus.PUBLISHED) &&
                (document.getContent() == null || document.getContent().isEmpty()))
            return false;
        return next.isValid(document, targetStatus);
    }
}
