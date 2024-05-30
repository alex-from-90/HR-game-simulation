package ru.simulator.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simulator.hr.entity.Chapter;
import ru.simulator.hr.entity.Topic;
import ru.simulator.hr.interfaces.ChapterService;
import ru.simulator.hr.interfaces.TopicService;
import ru.simulator.hr.repository.TopicRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final ChapterService chapterService;
    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, ChapterService chapterService) {
        this.topicRepository = topicRepository;
        this.chapterService = chapterService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topic> getTopicsByChapterId(Long chapterId) {
        return topicRepository.findByChapterId(chapterId);
    }
    @Override
    @Transactional(readOnly = true)
    public Topic getTopicById(long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.orElse(null);
    }

    @Override
    @Transactional
    public Topic createTopic(Topic topic, long chapterId) {
        Chapter chapter = chapterService.getChapterById(chapterId); // Предположим, что у вас есть репозиторий для работы с таблицей Chapter
        if (chapter != null) {
            topic.setChapter(chapter); // Устанавливаем объект Chapter в объект Topic
            return topicRepository.save(topic);
        } else {
            // Обработка сценария, когда chapterId не найден или объект Chapter не существует
            // Здесь вы можете выбрать, как обрабатывать эту ситуацию, например, выбросить исключение или вернуть null
            return null;
        }
    }

    @Override
    @Transactional
    public Topic updateTopic(long id, Topic topic) {
        Optional<Topic> optionalExistingTopic = topicRepository.findById(id);
        if (optionalExistingTopic.isPresent()) {
            Topic existingTopic = optionalExistingTopic.get();
            existingTopic.setTopicName(topic.getTopicName());
            // При необходимости можно добавить логику обновления связи с главой (Chapter)
            return topicRepository.save(existingTopic);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteTopic(long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isPresent()) {
            topicRepository.delete(optionalTopic.get());
            return true;
        }
        return false;
    }
}