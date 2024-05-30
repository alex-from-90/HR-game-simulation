package ru.simulator.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simulator.hr.entity.Chapter;
import ru.simulator.hr.entity.Topic;
import ru.simulator.hr.entity.User;
import ru.simulator.hr.entity.UserProgress;

import java.util.List;


@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    UserProgress findByUserAndChapterAndTopicId(User user, Chapter chapter, long topicId);
    List<UserProgress> findAllByUser(User user);
    List<UserProgress> findTop10ByOrderByProgressPercentageDesc();
    UserProgress findByUserAndTopic(User user, Topic topic);

}



