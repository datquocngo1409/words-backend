package com.example.demo.controller;

import com.example.demo.model.Word;
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
public class WordController {
    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public ResponseEntity<List<Word>> listAll() {
        List<Word> accounts = wordService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Word>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Word>>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/words/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Word> getById(@PathVariable("id") Long id) {
        Word object = wordService.findById(id);
        if (object == null) {
            System.out.println("Word with id " + id + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Word>(object, HttpStatus.OK);
    }

    @RequestMapping(value = "/words", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Word word, UriComponentsBuilder ucBuilder) {
        wordService.update(word);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/words/{id}").buildAndExpand(word.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addListWords", method = RequestMethod.POST)
    public ResponseEntity<Void> createList(@RequestBody List<Word> words, UriComponentsBuilder ucBuilder) {
        for (Word word : words) {
            wordService.update(word);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/words/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Word> updateAdmin(@PathVariable("id") Long id, @RequestBody Word word) {
        Word current = wordService.findById(id);

        if (current == null) {
            System.out.println("Word with id " + id + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }

        current = word;

        wordService.update(current);
        return new ResponseEntity<Word>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/words/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Word> delete(@PathVariable("id") Long id) {
        Word word = wordService.findById(id);
        if (word == null) {
            System.out.println("Unable to delete. Word with id " + id + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }

        wordService.delete(id);
        return new ResponseEntity<Word>(HttpStatus.NO_CONTENT);
    }
}
