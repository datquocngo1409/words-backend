package com.example.demo.model.dto;

import com.example.demo.model.Topic;
import com.example.demo.model.Word;
import lombok.Data;

import java.util.List;

@Data
public class TopicDto {
    private Long id;
    private String name;
    private String description;
    private List<Word> wordList;
    private Long levelId;
    private String levelName;

    public TopicDto() {
    }

    public TopicDto(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.description = topic.getDescription();
        this.wordList = topic.getWordList();
        this.levelId = topic.getLevel().getId();
        this.levelName = topic.getLevel().getName();
    }
}
