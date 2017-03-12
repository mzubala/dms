package pl.com.bottega.dms.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.AuthRequiredException;
import pl.com.bottega.dms.application.user.CurrentUser;

@Component
@Aspect
public class AuthAspect {

    private CurrentUser currentUser;

    public AuthAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Before("@within(pl.com.bottega.dms.application.user.RequiresAuth) || @annotation(pl.com.bottega.dms.application.user.RequiresAuth)")
    public void ensureAuth() {
        if(currentUser.getEmployeeId() == null)
            throw new AuthRequiredException();
    }

}
