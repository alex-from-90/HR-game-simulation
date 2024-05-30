package ru.simulator.hr.interfaces;

import ru.simulator.hr.entity.User;

public interface UserService {
    User findByUserTelId(long userTelId);
    User createUser(User user);
}