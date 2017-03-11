package pl.com.bottega.dms.application.user.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.user.*;
import pl.com.bottega.dms.model.EmployeeId;

@Transactional
public class StandardAuthProcess implements AuthProcess {
    private UserRepository userRepository;

    public StandardAuthProcess(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResult createAccount(CreateAccountCommand cmd) {
        User user = new User(new EmployeeId(cmd.getEmployeeId()), cmd.getUserName(), cmd.getPassword());
        userRepository.put(user);
        return AuthResult.success();
    }

    @Override
    public AuthResult login(LoginCommand cmd) {
        User user = userRepository.findByLoginAndHashedPassword(cmd.getLogin(), cmd.getPassword());
        if(user != null)
            return  AuthResult.success();
        else
            return new AuthResult(false, "invalid login or password");
    }

}