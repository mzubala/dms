package pl.com.bottega.dms.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.AuthRequiredException;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.application.user.RequiresAuth;

import java.util.Arrays;

@Component
@Aspect
public class AuthAspect {

    private CurrentUser currentUser;

    public AuthAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Before("@annotation(requiresAuth)")
    public void ensureAuthAnnotation(RequiresAuth requiresAuth) {
        checkAuth(requiresAuth);
    }

    @Before("@within(requiresAuth)")
    public void ensureAuthWithin(RequiresAuth requiresAuth) {
        checkAuth(requiresAuth);
    }

    private void checkAuth(RequiresAuth requiresAuth) {
        if(currentUser.getEmployeeId() == null)
            throw new AuthRequiredException("No authenticated user");
        if(!currentUser.getRoles().containsAll(Arrays.asList(requiresAuth.value())))
            throw new AuthRequiredException("User is not authorized");
    }

}
