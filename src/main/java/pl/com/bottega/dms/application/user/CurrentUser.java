package pl.com.bottega.dms.application.user;

import pl.com.bottega.dms.model.EmployeeId;

import java.util.Set;

public interface CurrentUser {

    void setEmployeeId(EmployeeId employeeId);

    EmployeeId getEmployeeId();

    Set<String> getRoles();

}
