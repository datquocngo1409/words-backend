package com.example.demo.repository;

import com.example.demo.model.Word;
import com.example.demo.model.WordLearn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordLearnRepository extends JpaRepository<WordLearn, Long> {
    WordLearn findByWord(Word word);
}
