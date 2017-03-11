package pl.com.bottega.dms.aceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.AuthResult;
import pl.com.bottega.dms.application.user.CreateAccountCommand;
import pl.com.bottega.dms.application.user.LoginCommand;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AuthProcessTest {

    @Autowired
    private AuthProcess authProcess;

    @Test
    public void shouldCreateAccountAndAllowLogin() {
        // when
        CreateAccountCommand cmd = new CreateAccountCommand();
        cmd.setUserName("janek");
        cmd.setEmployeeId(1L);
        cmd.setPassword("xxx");
        AuthResult createAccountResult = authProcess.createAccount(cmd);

        // then
        assertThat(createAccountResult.isSuccess()).isTrue();
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setLogin("janek");
        loginCommand.setPassword("xxx");
        AuthResult loginResult = authProcess.login(loginCommand);
        assertThat(loginResult.isSuccess()).isTrue();
    }

    @Test
    public void shouldFailLoginOnWrongPassword() {
        // given
        CreateAccountCommand cmd = new CreateAccountCommand();
        cmd.setUserName("janek");
        cmd.setEmployeeId(1L);
        cmd.setPassword("xxx");
        authProcess.createAccount(cmd);

        // when
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setLogin("janek");
        loginCommand.setPassword("wrong pass");
        AuthResult loginResult = authProcess.login(loginCommand);

        //then
        assertThat(loginResult.isSuccess()).isFalse();
        assertThat(loginResult.getErrorMessage()).isEqualTo("invalid login or password");
    }

}
