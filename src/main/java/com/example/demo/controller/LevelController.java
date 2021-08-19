package com.example.demo.controller;

import com.example.demo.model.Level;
import com.example.demo.model.Word;
import com.example.demo.service.LevelService;
import com.example.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class LevelController {
    @Autowired
    private LevelService levelService;
    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/level", method = RequestMethod.GET)
    public ResponseEntity<List<Level>> listAll() {
        List<Level> accounts = levelService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Level>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Level>>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/level/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Level> getById(@PathVariable("id") Long id) {
        Level object = levelService.findById(id);
        if (object == null) {
            System.out.println("Level with id " + id + " not found");
            return new ResponseEntity<Level>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Level>(object, HttpStatus.OK);
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
    public ResponseEntity<Level> updateAdmin(@PathVariable("id") Long id, @RequestBody Level level) {
        Level current = levelService.findById(id);

        if (current == null) {
            System.out.println("Level with id " + id + " not found");
            return new ResponseEntity<Level>(HttpStatus.NOT_FOUND);
        }

        current = level;

        levelService.update(current);
        return new ResponseEntity<Level>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/level/{id}/addWord", method = RequestMethod.POST)
    public ResponseEntity<Level> addWordToLevel(@PathVariable("id") Long id, @RequestBody List<Word> wordList) {
        Level level = levelService.findById(id);

        if (level == null) {
            System.out.println("Level with id " + id + " not found");
            return new ResponseEntity<Level>(HttpStatus.NOT_FOUND);
        }

        List<Word> levelWordList = level.getWordList();

        for (Word word: wordList) {
            wordService.update(word);
            levelWordList.add(word);
            level.setWordList(levelWordList);
        }

        levelService.update(level);
        return new ResponseEntity<Level>(level, HttpStatus.OK);
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
