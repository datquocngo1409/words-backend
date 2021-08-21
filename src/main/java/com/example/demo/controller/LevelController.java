package com.example.demo.controller;

import com.example.demo.model.Level;
import com.example.demo.model.Word;
import com.example.demo.model.dto.LevelDto;
import com.example.demo.service.LevelService;
import com.example.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class LevelController {
    @Autowired
    private LevelService levelService;
    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/level", method = RequestMethod.GET)
    public ResponseEntity<List<LevelDto>> listAll() {
        List<Level> accounts = levelService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<LevelDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        List<LevelDto> dtos = new ArrayList<>();
        for (Level level : accounts) {
            LevelDto dto = new LevelDto(level);
            dtos.add(dto);
        }
        return new ResponseEntity<List<LevelDto>>(dtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/level/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LevelDto> getById(@PathVariable("id") Long id) {
        Level object = levelService.findById(id);
        if (object == null) {
            System.out.println("Level with id " + id + " not found");
            return new ResponseEntity<LevelDto>(HttpStatus.NOT_FOUND);
        }
        LevelDto dto = new LevelDto(object);
        return new ResponseEntity<LevelDto>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/level", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Level level, UriComponentsBuilder ucBuilder) {
        levelService.update(level);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/level/{id}").buildAndExpand(level.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addListLevels", method = RequestMethod.POST)
    public ResponseEntity<Void> createList(@RequestBody List<Level> levels, UriComponentsBuilder ucBuilder) {
        for (Level level : levels) {
            levelService.update(level);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/level/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<LevelDto> updateAdmin(@PathVariable("id") Long id, @RequestBody Level level) {
        Level current = levelService.findById(id);

        if (current == null) {
            System.out.println("Level with id " + id + " not found");
            return new ResponseEntity<LevelDto>(HttpStatus.NOT_FOUND);
        }

        current = level;

        levelService.update(current);
        LevelDto dto = new LevelDto(current);
        return new ResponseEntity<LevelDto>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/level/{id}/addWord", method = RequestMethod.POST)
    public ResponseEntity<LevelDto> addWordToLevel(@PathVariable("id") Long id, @RequestBody List<Word> wordList) {
        Level level = levelService.findById(id);

        if (level == null) {
            System.out.println("Level with id " + id + " not found");
            return new ResponseEntity<LevelDto>(HttpStatus.NOT_FOUND);
        }

        List<Word> levelWordList = level.getWordList();

        for (Word word: wordList) {
            wordService.update(word);
            levelWordList.add(word);
            level.setWordList(levelWordList);
        }

        levelService.update(level);
        LevelDto dto = new LevelDto(level);
        return new ResponseEntity<LevelDto>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/level/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Level> delete(@PathVariable("id") Long id) {
        Level level = levelService.findById(id);
        if (level == null) {
            System.out.println("Unable to delete. Level with id " + id + " not found");
            return new ResponseEntity<Level>(HttpStatus.NOT_FOUND);
        }

        levelService.delete(id);
        return new ResponseEntity<Level>(HttpStatus.NO_CONTENT);
    }
}
