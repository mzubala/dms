package pl.com.bottega.dms.application.user;

import pl.com.bottega.dms.model.EmployeeId;

public interface CurrentUser {

    void setEmployeeId(EmployeeId employeeId);

    EmployeeId getEmployeeId();

}
