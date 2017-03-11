package pl.com.bottega.dms.ui;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.AuthResult;
import pl.com.bottega.dms.application.user.LoginCommand;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private AuthProcess authProcess;

    public SessionController(AuthProcess authProcess) {
        this.authProcess = authProcess;
    }

    @PostMapping
    public AuthResult create(@RequestBody LoginCommand loginCommand) {
        return authProcess.login(loginCommand);
    }

    @DeleteMapping
    public void destroy(HttpSession httpSession) {
        httpSession.invalidate();
    }

}
