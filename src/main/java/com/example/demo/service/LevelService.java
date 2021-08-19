package com.example.demo.service;

import com.example.demo.model.Level;
import com.example.demo.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {
    @Autowired
    private LevelRepository repository;

    public List<Level> findAll() {
        return repository.findAll();
    }

    public Level findById(Long id) {
        return repository.findById(id).get();
    }

    public void create(Level object) {
        repository.save(object);
    }

    public void update(Level object) {
        repository.save(object);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
