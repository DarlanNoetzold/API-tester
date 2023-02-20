package tech.noetzold.APItester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByLogin(String login);

}