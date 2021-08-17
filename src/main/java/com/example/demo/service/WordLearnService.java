package com.example.demo.service;

import com.example.demo.model.Word;
import com.example.demo.model.WordLearn;
import com.example.demo.repository.WordLearnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WordLearnService {
    @Autowired
    private WordLearnRepository wordLearnRepository;

    public List<WordLearn> findAll() {
        return wordLearnRepository.findAll();
    }

    public WordLearn findById(Long id) {
        return wordLearnRepository.findById(id).get();
    }

    public void create(WordLearn object) {
        wordLearnRepository.save(object);
    }

    public void update(WordLearn object) {
        wordLearnRepository.save(object);
    }

    public void delete(Long id) {
        wordLearnRepository.deleteById(id);
    }

    public WordLearn findByWord(Word word) {
        return wordLearnRepository.findByWord(word);
    }

    public void repeat(Long id) {
        WordLearn wordLearn = wordLearnRepository.findById(id).get();
        wordLearn.setCountRepeat(wordLearn.getCountRepeat() + 1);
        wordLearnRepository.save(wordLearn);
    }

    public void repeatList(List<WordLearn> wordLearns) {
        for (WordLearn wordLearn : wordLearns) {
            wordLearn.setCountRepeat(wordLearn.getCountRepeat() + 1);
            wordLearn.setDateRepeat(new Date());
            wordLearnRepository.save(wordLearn);
        }
    }
}
