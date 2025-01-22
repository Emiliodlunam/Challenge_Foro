package com.example.Foro.service;



import com.example.Foro.model.Topic;
import com.example.Foro.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    public Topic updateTopic(Long id, Topic topic) {
        topic.setId(id);
        return topicRepository.save(topic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
