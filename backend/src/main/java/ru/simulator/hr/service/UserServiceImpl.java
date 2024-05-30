package ru.simulator.hr.service;

import org.springframework.stereotype.Service;
import ru.simulator.hr.entity.User;
import ru.simulator.hr.interfaces.UserService;
import ru.simulator.hr.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserTelId(long userTelId) {
        return userRepository.findByUserTelId(userTelId);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        long userTelId = user.getUserTelId();

        User existingUser = userRepository.findByUserTelId(userTelId);

        if (existingUser != null) {
            // Пользователь с таким userTelId уже существует
            if (!existingUser.getUsername().equals(user.getUsername())) {
                // Проверяем, изменился ли username
                existingUser.setUsername(user.getUsername());
            }
            existingUser.setLastLogin(LocalDateTime.now());
            return userRepository.save(existingUser);
        } else {
            // Создаем нового пользователя, так как пользователь с userTelId не существует
            user.setFirstLogin(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());
            return userRepository.save(user);
        }
    }
}