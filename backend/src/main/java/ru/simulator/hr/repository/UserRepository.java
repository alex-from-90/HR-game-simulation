package ru.simulator.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simulator.hr.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserTelId(long userTelId);
}