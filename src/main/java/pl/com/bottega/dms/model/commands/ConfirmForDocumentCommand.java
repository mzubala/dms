package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

public class ConfirmForDocumentCommand {
    private EmployeeId employeeId;
    private EmployeeId confirmingEmployeeId;

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
}
