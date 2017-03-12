package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

import java.util.Collection;
import java.util.List;

public class PublishDocumentCommand implements EmployeeAware, Validatable {
    private EmployeeId employeeId;
    private Collection<EmployeeId> recipients;
    private String number;

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public void setRecipients(Collection<EmployeeId> recipients) {
        this.recipients = recipients;
    }

    public Collection<EmployeeId> getRecipients() {
        return recipients;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void validate(ValidationErrors errors) {
        if(number == null || number.isEmpty())
            errors.add("number", "can't be blank");
        if(recipients == null || recipients.size() == 0)
            errors.add("recipients", "can't be empty");
    }
}
