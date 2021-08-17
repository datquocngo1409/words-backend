package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserLearn;
import com.example.demo.model.Word;
import com.example.demo.model.WordLearn;
import com.example.demo.service.UserLearnService;
import com.example.demo.service.UserService;
import com.example.demo.service.WordLearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class UserLearnController {
    @Autowired
    private UserLearnService userLearnService;
    @Autowired
    private WordLearnService wordLearnService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/learnedWords/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserLearn> addWords(@PathVariable("id") Long id) {
        UserLearn userLearn = userLearnService.findByUser(userService.findById(id));
        return new ResponseEntity<UserLearn>(userLearn, HttpStatus.OK);
    }

    @RequestMapping(value = "/addWords/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addWords(@RequestBody List<Word> words, @PathVariable("id") Long id) {
        userLearnService.addWords(id, words);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/repeatWords/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> repeatWords(@RequestBody List<Word> words, @PathVariable("id") Long id) {
        List<WordLearn> wordLearnList = new ArrayList<>();
        UserLearn userLearn = userLearnService.findByUser(userService.findById(id));
        for (Word word : words) {
            if (userLearnService.contain(userLearn.getLearnedWords(), word)) {
                wordLearnList.add(wordLearnService.findByWord(word));
            }
        }
        wordLearnService.repeatList(wordLearnList);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
