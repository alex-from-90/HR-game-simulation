package ru.simulator.hr.service;

import org.springframework.stereotype.Service;
import ru.simulator.hr.entity.Topic;
import ru.simulator.hr.entity.User;
import ru.simulator.hr.entity.UserProgress;
import ru.simulator.hr.interfaces.TopicService;
import ru.simulator.hr.interfaces.UserProgressService;
import ru.simulator.hr.interfaces.UserService;
import ru.simulator.hr.repository.UserProgressRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserProgressServiceImpl implements UserProgressService {

    private final UserService userService;
    private final UserProgressRepository userProgressRepository;
    private final TopicService topicService;
    public UserProgressServiceImpl(UserService userService, UserProgressRepository userProgressRepository, TopicService topicService) {
        this.userService = userService;
        this.userProgressRepository = userProgressRepository;
        this.topicService = topicService;
    }
    @Override
    @Transactional(readOnly = true)
    public List<UserProgress> getAllUserProgress(Long userTelId) {
        User user = userService.findByUserTelId(userTelId);

        if (user != null) {
            return userProgressRepository.findAllByUser(user);
        } else {
            return Collections.emptyList(); // Если пользователя не существует, вернуть пустой список
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProgress> getAllUserProgress() {
        return userProgressRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<UserProgress> getTop10UsersByProgress() {
        return userProgressRepository.findTop10ByOrderByProgressPercentageDesc();
    }
    @Override
    @Transactional
    public UserProgress updateUserProgress(Long userTelId, UserProgress userProgress) {
        User user = userService.findByUserTelId(userTelId);

        if (user != null) {
            UserProgress existingUserProgress = userProgressRepository.findByUserAndChapterAndTopicId(
                    user, userProgress.getChapter(), userProgress.getTopic().getId());

            if (existingUserProgress != null) {
                existingUserProgress.setCorrectAnswers(userProgress.getCorrectAnswers());
                existingUserProgress.setWrongAnswers(userProgress.getWrongAnswers());
                existingUserProgress.setTotalQuestions(userProgress.getTotalQuestions());
                existingUserProgress.setChapter(userProgress.getChapter());
                existingUserProgress.setTopic(userProgress.getTopic());
                existingUserProgress.setProgressPercentage(userProgress.getProgressPercentage());
                existingUserProgress.setElapsedTime(userProgress.getElapsedTime());

                return userProgressRepository.save(existingUserProgress);
            } else {
                userProgress.setUser(user);
                return userProgressRepository.save(userProgress);
            }
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public UserProgress getUserProgressByTopicId(Long userTelId, Long topicId) {
        User user = userService.findByUserTelId(userTelId);
        if (user != null) {
            Topic topic = topicService.getTopicById(topicId); // Используем TopicService для получения топика по ID
            if (topic != null) {
                return userProgressRepository.findByUserAndTopic(user, topic);
            }
        }
        return null;
    }
}
