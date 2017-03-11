package pl.com.bottega.dms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.User;
import pl.com.bottega.dms.application.user.UserRepository;
import pl.com.bottega.dms.model.EmployeeId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class JPAUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByEmployeeId(EmployeeId employeeId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(builder.equal(root.get("employeeId"), employeeId));
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return queryUser(query);
    }

    @Override
    public User findByUserName(String userName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(builder.equal(root.get("userName"), userName));
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return queryUser(query);
    }

    @Override
    public User findByLoginAndHashedPassword(String login, String hashedPassword) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Long longLogin = null;
        try {
            longLogin = Long.parseLong(login);
        } catch (NumberFormatException ex) {
        }
        criteriaQuery.where(
                builder.or(
                        builder.equal(root.get("employeeId").get("id"), longLogin),
                        builder.equal(root.get("userName"), login)
                ),
                builder.equal(root.get("hashedPassword"), hashedPassword)
        );
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return queryUser(query);
    }

    private User queryUser(TypedQuery<User> query) {
        List<User> users = query.getResultList();
        if (users.size() == 0)
            return null;
        else
            return users.get(0);
    }

    @Override
    public void put(User user) {
        entityManager.persist(user);
    }
}
