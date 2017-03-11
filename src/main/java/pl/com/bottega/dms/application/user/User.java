package pl.com.bottega.dms.application.user;

import pl.com.bottega.dms.model.EmployeeId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class User {

    @EmbeddedId
    private EmployeeId employeeId;
    private String userName;
    private String hashedPassword;

    User() {}

    public User(EmployeeId employeeId, String userName, String hashedPassword) {
        this.employeeId = employeeId;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
    }


    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public String getUserName() {
        return userName;
    }
}
