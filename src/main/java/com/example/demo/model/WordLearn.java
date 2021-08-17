package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WordLearn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Word word;

    private int countRepeat;
    private Date dateRepeat;
    private Date dateLearned;

    public WordLearn() {
    }

    public WordLearn(Word word) {
        this.word = word;
        this.countRepeat = 1;
        this.dateRepeat = new Date();
        this.dateLearned = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getCountRepeat() {
        return countRepeat;
    }

    public void setCountRepeat(int timeRepeat) {
        this.countRepeat = timeRepeat;
    }

    public Date getDateRepeat() {
        return dateRepeat;
    }

    public void setDateRepeat(Date dateRepeat) {
        this.dateRepeat = dateRepeat;
    }

    public Date getDateLearned() {
        return dateLearned;
    }

    public void setDateLearned(Date dateLearned) {
        this.dateLearned = dateLearned;
    }
}
