package com.example.demo.controller;

import com.example.demo.model.Topic;
import com.example.demo.model.Word;
import com.example.demo.service.TopicService;
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
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public ResponseEntity<List<Topic>> listAll() {
        List<Topic> accounts = topicService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Topic>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Topic>>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Topic> getById(@PathVariable("id") Long id) {
        Topic object = topicService.findById(id);
        if (object == null) {
            System.out.println("Topic with id " + id + " not found");
            return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Topic>(object, HttpStatus.OK);
    }

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Topic topic, UriComponentsBuilder ucBuilder) {
        topicService.update(topic);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/topic/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Topic> updateAdmin(@PathVariable("id") Long id, @RequestBody Topic topic) {
        Topic current = topicService.findById(id);

        if (current == null) {
            System.out.println("Topic with id " + id + " not found");
            return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
        }

        current = topic;

        topicService.update(current);
        return new ResponseEntity<Topic>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/topic/{id}/addWord", method = RequestMethod.POST)
    public ResponseEntity<Topic> addWordToTopic(@PathVariable("id") Long id, @RequestBody List<Word> wordList) {
        Topic topic = topicService.findById(id);

        if (topic == null) {
            System.out.println("Topic with id " + id + " not found");
            return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
        }

        List<Word> topicWordList = topic.getWordList();

        for (Word word: wordList) {
            wordService.update(word);
            topicWordList.add(word);
            topic.setWordList(topicWordList);
        }

        topicService.update(topic);
        return new ResponseEntity<Topic>(topic, HttpStatus.OK);
    }

    @RequestMapping(value = "/topic/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Topic> delete(@PathVariable("id") Long id) {
        Topic topic = topicService.findById(id);
        if (topic == null) {
            System.out.println("Unable to delete. Topic with id " + id + " not found");
            return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
        }

        topicService.delete(id);
        return new ResponseEntity<Topic>(HttpStatus.NO_CONTENT);
    }

}
