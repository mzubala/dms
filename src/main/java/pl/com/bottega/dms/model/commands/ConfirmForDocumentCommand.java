package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

public class ConfirmForDocumentCommand implements EmployeeAware, Validatable {
    private EmployeeId employeeId;
    private EmployeeId confirmForEmployeeId;
    private String number;

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void validate(Validatable.ValidationErrors errors) {
        if (isEmpty(number))
            errors.add("number", "can't be blank");
        if(confirmForEmployeeId == null)
            errors.add("confirmForEmployeeId", "can't be blank");
    }

    public EmployeeId getConfirmForEmployeeId() {
        return confirmForEmployeeId;
    }

    public void setConfirmForEmployeeId(EmployeeId confirmForEmployeeId) {
        this.confirmForEmployeeId = confirmForEmployeeId;
    }
}
