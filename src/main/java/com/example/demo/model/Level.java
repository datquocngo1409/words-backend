package com.example.demo.model;

import com.example.demo.model.dto.WordDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonBackReference
    @OneToMany
    private List<Topic> topicList;

    @ManyToMany
    private List<Word> wordList;

    public Level() {
    }

    public Level(String name) {
        this.name = name;
        this.topicList = new ArrayList<>();
        this.wordList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public List<WordDto> getWordDtoList() {
        List<WordDto> dtos = new ArrayList<>();
        for (Word word : wordList) {
            WordDto dto = new WordDto(word);
            dtos.add(dto);
        }
        return dtos;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }
}
