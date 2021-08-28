package com.example.demo.model.dto;

import com.example.demo.model.Word;
import lombok.Data;

@Data
public class WordDto {
    private Long id;
    private String word;
    private String meaning;
    private String type;
    private String pronounce;
    private String description;

    public WordDto(Word word) {
        this.id = word.getId();
        this.word = word.getWord();
        this.meaning = word.getMeaning();
        this.type = word.getType();
        this.pronounce = word.getPronounce();
        this.description = word.getDescription();
    }
}
