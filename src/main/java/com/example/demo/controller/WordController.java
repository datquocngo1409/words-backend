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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class WordController {
    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/word", method = RequestMethod.GET)
    public ResponseEntity<List<Word>> listAll() {
        List<Word> accounts = wordService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Word>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Word>>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/word/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Word> getById(@PathVariable("id") Long id) {
        Word object = wordService.findById(id);
        if (object == null) {
            System.out.println("Word with id " + id + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Word>(object, HttpStatus.OK);
    }

    @RequestMapping(value = "/word/random3", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Word>> getRandom3Word(@RequestBody Word word) {
        List<Word> wordList = wordService.findAll();
        int maxRandom = wordList.size();
        List<Word> result = new ArrayList<>();
        do {
            int random = (int)(Math.random() * maxRandom);
            Word wordToAdd = wordList.get(random);
            if (!wordToAdd.getWord().equals(word.getWord()) && !result.contains(wordToAdd)) {
                result.add(wordToAdd);
            }
        } while (result.size() < 3);
        return new ResponseEntity<List<Word>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Word word, UriComponentsBuilder ucBuilder) {
        wordService.update(word);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/word/{id}").buildAndExpand(word.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/word/addList", method = RequestMethod.POST)
    public ResponseEntity<Void> createList(@RequestBody List<Word> words, UriComponentsBuilder ucBuilder) {
        for (Word word : words) {
            wordService.update(word);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/word/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Word> update(@PathVariable("id") Long id, @RequestBody Word word) {
        Word current = wordService.findById(id);

        if (current == null) {
            System.out.println("Word with id " + id + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }

        current = word;

        wordService.update(current);
        return new ResponseEntity<Word>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/word/{id}/image", method = RequestMethod.PATCH)
    public ResponseEntity<Word> updateImage(@PathVariable("id") Long id, @RequestBody String imageUrl) {
        Word current = wordService.findById(id);

        if (current == null) {
            System.out.println("Word with id " + id + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }

        current.setImageUrl(imageUrl);

        wordService.update(current);
        return new ResponseEntity<Word>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/word/{id}", method = RequestMethod.DELETE)
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
