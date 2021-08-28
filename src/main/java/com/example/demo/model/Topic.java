package com.example.demo.model;

import com.example.demo.model.dto.WordDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    private List<Word> wordList;

    @JsonBackReference
    @ManyToOne
    private Level level;

    public Topic() {
    }

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
        this.wordList = new ArrayList<>();
    }

    public Topic(String name, String description, Level level) {
        this.name = name;
        this.description = description;
        this.wordList = new ArrayList<>();
        this.level = level;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
