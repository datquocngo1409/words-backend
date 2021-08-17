package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserLearn;
import com.example.demo.model.Word;
import com.example.demo.model.WordLearn;
import com.example.demo.repository.UserLearnRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WordLearnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLearnService {
    @Autowired
    private UserLearnRepository userLearnRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WordLearnRepository wordLearnRepository;

    public List<UserLearn> findAll() {
        return userLearnRepository.findAll();
    }

    public UserLearn findById(Long id) {
        return userLearnRepository.findById(id).get();
    }

    public void create(UserLearn object) {
        userLearnRepository.save(object);
    }

    public void update(UserLearn object) {
        userLearnRepository.save(object);
    }

    public void delete(Long id) {
        userLearnRepository.deleteById(id);
    }

    public UserLearn findByUser(User user) {
        return userLearnRepository.findByUser(user);
    }

    public boolean contain(List<WordLearn> userLearnedWords, Word word) {
        for (WordLearn wordLearn : userLearnedWords) {
            if (wordLearn.getWord().getWord().equals(word.getWord())) {
                return true;
            }
        }
        return false;
    }

    public void addWords(Long id, List<Word> words) {
        User user = userRepository.findById(id).get();
        UserLearn userLearn = userLearnRepository.findByUser(user);
        List<WordLearn> userLearnedWords = userLearn.getLearnedWords();
        for (Word word : words) {
            WordLearn wordLearn = new WordLearn(word);
            if (!contain(userLearnedWords, word)) {
                wordLearnRepository.save(wordLearn);
                userLearnedWords.add(wordLearn);
            }
        }
        userLearn.setLearnedWords(userLearnedWords);
        userLearnRepository.save(userLearn);
    }
}
