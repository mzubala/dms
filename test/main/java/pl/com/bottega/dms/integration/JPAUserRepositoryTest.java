package pl.com.bottega.dms.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.user.User;
import pl.com.bottega.dms.infrastructure.JPAUserRepository;
import pl.com.bottega.dms.model.EmployeeId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class JPAUserRepositoryTest {

    @Autowired
    private JPAUserRepository jpaUserRepository;

    @Test
    public void shouldFindUserByUsernameAndPassword() {
        //given
        User user = new User(new EmployeeId(1L), "janek", "xxx");
        jpaUserRepository.put(user);

        //when
        User repoUser = jpaUserRepository.findByLoginAndHashedPassword("janek", "xxx");

        // then
        assertThat(repoUser).isNotNull();
        assertThat(repoUser.getEmployeeId()).isEqualTo(new EmployeeId(1L));
    }

    @Test
    public void shouldFindUserByEmployeeIdAndPassword() {
        //given
        User user = new User(new EmployeeId(1L), "janek", "xxx");
        jpaUserRepository.put(user);

        //when
        User repoUser = jpaUserRepository.findByLoginAndHashedPassword("1", "xxx");

        // then
        assertThat(repoUser).isNotNull();
        assertThat(repoUser.getUserName()).isEqualTo("janek");
    }

    @Test
    public void shouldNotFindUserWhenLoginOrPasswordAreWrong() {
        //given
        User user = new User(new EmployeeId(1L), "janek", "xxx");
        jpaUserRepository.put(user);

        //then
        assertThat(jpaUserRepository.findByLoginAndHashedPassword("1", "uuu")).isNull();
        assertThat(jpaUserRepository.findByLoginAndHashedPassword("janek", "uuu")).isNull();
        assertThat(jpaUserRepository.findByLoginAndHashedPassword("2", "xxx")).isNull();
    }

}
