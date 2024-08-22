package telran.java53.accounting.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import telran.java53.accounting.model.User;

public interface UserAccountRepository extends MongoRepository<User, String> {

    @Transactional(readOnly = true)
    Optional<User> findByLogin(String login);

    @Transactional(readOnly = true)
    Stream<User> findByRolesContains(String role);

    @Transactional(readOnly = true)
    Optional<User> findByLoginAndPassword(String login, String password);
}
