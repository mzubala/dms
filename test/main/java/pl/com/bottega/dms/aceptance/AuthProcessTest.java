package pl.com.bottega.dms.aceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.user.*;
import pl.com.bottega.dms.model.EmployeeId;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AuthProcessTest {

    @Autowired
    private AuthProcess authProcess;

    @Autowired
    private CurrentUser currentUser;

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

    @Test
    public void shouldNotAllowDuplicateEmployeeId() {
        // given
        CreateAccountCommand cmd = new CreateAccountCommand();
        cmd.setUserName("janek");
        cmd.setEmployeeId(1L);
        cmd.setPassword("xxx");
        authProcess.createAccount(cmd);

        //when
        AuthResult result = authProcess.createAccount(cmd);

        //then
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("employee already registered");
    }

    @Test
    public void shouldNotAllowDuplicateUserName() {
        // given
        CreateAccountCommand cmd = new CreateAccountCommand();
        cmd.setUserName("janek");
        cmd.setEmployeeId(1L);
        cmd.setPassword("xxx");
        authProcess.createAccount(cmd);

        //when
        cmd.setEmployeeId(2L);
        AuthResult result = authProcess.createAccount(cmd);

        //then
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("user name is occupied");
    }

    @Test
    public void shouldRememberCurrentEmployeeId() {
        // given
        CreateAccountCommand cmd = new CreateAccountCommand();
        cmd.setUserName("janek");
        cmd.setEmployeeId(1L);
        cmd.setPassword("xxx");
        authProcess.createAccount(cmd);

        // when
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setLogin("janek");
        loginCommand.setPassword("xxx");
        authProcess.login(loginCommand);

        //then
        assertThat(currentUser.getEmployeeId()).isEqualTo(new EmployeeId(1L));
    }

}
