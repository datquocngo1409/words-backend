package com.example.demo.model.dto;

import com.example.demo.model.Level;
import com.example.demo.model.Topic;
import com.example.demo.model.Word;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LevelDto {
    private Long id;
    private String name;
    private List<TopicDto> topicDtoList;
    private List<WordDto> wordList;

    public LevelDto(Level level) {
        this.id = level.getId();
        this.name = level.getName();
        this.wordList = level.getWordDtoList();
        List<TopicDto> dtos = new ArrayList<>();
        for (Topic topic : level.getTopicList()) {
            TopicDto dto = new TopicDto(topic);
            dtos.add(dto);
        }
        this.topicDtoList = dtos;
    }
}
