package pl.com.bottega.dms.application.user.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.user.*;
import pl.com.bottega.dms.model.EmployeeId;

@Transactional
public class StandardAuthProcess implements AuthProcess {
    private UserRepository userRepository;
    private CurrentUser currentUser;

    public StandardAuthProcess(UserRepository userRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    @Override
    public AuthResult createAccount(CreateAccountCommand cmd) {
        if (userRepository.findByEmployeeId(new EmployeeId(cmd.getEmployeeId())) != null)
            return new AuthResult(false, "employee already registered");
        if (userRepository.findByUserName(cmd.getUserName()) != null)
            return new AuthResult(false, "user name is occupied");
        User user = new User(new EmployeeId(cmd.getEmployeeId()), cmd.getUserName(), cmd.getPassword());
        userRepository.put(user);
        return AuthResult.success();
    }

    @Override
    public AuthResult login(LoginCommand cmd) {
        User user = userRepository.findByLoginAndHashedPassword(cmd.getLogin(), cmd.getPassword());
        if (user != null) {
            currentUser.setEmployeeId(user.getEmployeeId());
            return AuthResult.success();
        }
        else
            return new AuthResult(false, "invalid login or password");
    }

}