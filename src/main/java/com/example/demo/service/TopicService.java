package com.example.demo.service;

import com.example.demo.model.Topic;
import com.example.demo.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicRepository repository;

    public List<Topic> findAll() {
        return repository.findAll();
    }

    public Topic findById(Long id) {
        return repository.findById(id).get();
    }

    public void create(Topic object) {
        repository.save(object);
    }

    public void update(Topic object) {
        repository.save(object);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
