package com.example.demo.service;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class WordService {
    @Autowired
    private WordRepository wordRepository;

    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    public Word findById(Long id) {
        return wordRepository.findById(id).get();
    }

    public void create(Word object) {
        wordRepository.save(object);
    }

    public void update(Word object) {
        wordRepository.save(object);
    }

    public void delete(Long id) {
        wordRepository.deleteById(id);
    }
}
