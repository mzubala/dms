package pl.com.bottega.dms.application.user.impl;

import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.model.EmployeeId;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    public Set<String> getRoles() {
        Set<String> roles = new HashSet<>();
        if (employeeId.getId().equals(1L) || employeeId.getId().equals(0L))
            roles.addAll(Arrays.asList("STAFF", "QUALITY_STAFF", "QUALITY_MANAGER"));
        else if (employeeId.getId().equals(2L))
            roles.addAll(Arrays.asList("STAFF", "QUALITY_STAFF"));
        else
            roles.add("STAFF");
        return roles;
    }
}
