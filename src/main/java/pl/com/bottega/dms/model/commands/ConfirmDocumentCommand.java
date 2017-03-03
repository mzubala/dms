package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

public class ConfirmDocumentCommand {
    private EmployeeId employeeId;
    private String number;

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
