package pl.com.bottega.dms.application.user;

import pl.com.bottega.dms.model.commands.Validatable;

public class CreateAccountCommand implements Validatable {

    private String userName;
    private Long employeeId;
    private String password;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void validate(ValidationErrors errors) {
        if (userName == null || userName.isEmpty())
            errors.add("userName", "can't be blank");
        if (employeeId == null)
            errors.add("employeeId", "can't be blank");
        if (password == null || password.isEmpty())
            errors.add("password", "can't be blank");
    }
}
