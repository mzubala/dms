package pl.com.bottega.dms.application.user.impl;

import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.model.EmployeeId;

public class StandardCurrentUser implements CurrentUser {

    private EmployeeId employeeId;

    @Override
    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public EmployeeId getEmployeeId() {
        return employeeId;
    }
}
