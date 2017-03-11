package pl.com.bottega.dms.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.AuthResult;
import pl.com.bottega.dms.application.user.CreateAccountCommand;

@RestController
@RequestMapping("/users")
public class UserController {

    private AuthProcess authProcess;

    public UserController(AuthProcess authProcess) {
        this.authProcess = authProcess;
    }

    @PostMapping
    public AuthResult create(@RequestBody CreateAccountCommand cmd) {
        return authProcess.createAccount(cmd);
    }

}
