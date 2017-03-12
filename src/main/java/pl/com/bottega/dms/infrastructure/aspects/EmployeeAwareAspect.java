package pl.com.bottega.dms.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.model.commands.EmployeeAware;

@Component
@Aspect
public class EmployeeAwareAspect {

    private CurrentUser currentUser;

    public EmployeeAwareAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Before("execution(* pl.com.bottega.dms.application..*.*(..)) " +
            "&& args(pl.com.bottega.dms.model.commands.EmployeeAware) " +
            "&& args(employeeAware)")
    public void setEmployeeId(EmployeeAware employeeAware) {
        employeeAware.setEmployeeId(currentUser.getEmployeeId());
    }

}
