package ru.simulator.hr.interfaces;

import ru.simulator.hr.entity.UserProgress;

import java.util.List;

public interface UserProgressService {
    List<UserProgress> getAllUserProgress(Long userTelId);
    List<UserProgress> getAllUserProgress();
    List<UserProgress> getTop10UsersByProgress();
    UserProgress updateUserProgress(Long userTelId, UserProgress userProgress);
    UserProgress getUserProgressByTopicId(Long userTelId, Long topicId);


}