package pl.com.bottega.dms.application.user;

public interface AuthProcess {

    AuthResult createAccount(CreateAccountCommand cmd);

    AuthResult login(LoginCommand cmd);

}
