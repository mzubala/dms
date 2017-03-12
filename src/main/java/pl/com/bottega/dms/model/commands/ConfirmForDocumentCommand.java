package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

public class ConfirmForDocumentCommand implements EmployeeAware {
    private EmployeeId employeeId;
    private EmployeeId confirmingEmployeeId;
    private String number;

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeId getConfirmingEmployeeId() {
        return confirmingEmployeeId;
    }

    public void setConfirmingEmployeeId(EmployeeId confirmingEmployeeId) {
        this.confirmingEmployeeId = confirmingEmployeeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
