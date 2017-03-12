package pl.com.bottega.dms.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.model.commands.CommandInvalidException;
import pl.com.bottega.dms.model.commands.EmployeeAware;
import pl.com.bottega.dms.model.commands.Validatable;

@Component
@Aspect
public class ValidationAspect {

    @Before("execution(* pl.com.bottega.dms.application..*.*(..)) " +
            "&& args(pl.com.bottega.dms.model.commands.Validatable) " +
            "&& args(validatable)")
    public void validate(Validatable validatable) {
        Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
        validatable.validate(errors);
        if(!errors.isValid())
            throw new CommandInvalidException(errors);
    }

}
