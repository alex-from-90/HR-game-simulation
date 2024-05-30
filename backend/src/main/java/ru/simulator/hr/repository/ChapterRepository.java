package ru.simulator.hr.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.simulator.hr.entity.Chapter;
import ru.simulator.hr.entity.Topic;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    @EntityGraph(attributePaths = { "topics" })
    @Query("SELECT c FROM Chapter c")
    List<Chapter> findAllChaptersWithTopics();

}
