package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.DocumentType;

public class CreateDocumentCommand implements EmployeeAware , Validatable {
    private String title;
    private EmployeeId employeeId;
    private DocumentType documentType;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public void validate(ValidationErrors errors) {
        if(isEmpty(title))
            errors.add("title", "can't be blank");
        if(documentType == null)
            errors.add("documentType", "can't be blank");
    }
}
