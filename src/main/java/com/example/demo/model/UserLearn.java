package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserLearn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany
    private List<WordLearn> learnedWords;

    public UserLearn() {
    }

    public UserLearn(User user) {
        this.user = user;
        this.learnedWords = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<WordLearn> getLearnedWords() {
        return learnedWords;
    }

    public void setLearnedWords(List<WordLearn> learnedWords) {
        this.learnedWords = learnedWords;
    }
}
