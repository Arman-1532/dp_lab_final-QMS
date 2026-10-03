package model;

import java.util.*;

public class Quiz {
    private String title;
    private List<Question> questions;

    public Quiz(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public String getTitle() { return title; }
    public void addQuestion(Question q) { questions.add(q); }
    public List<Question> getQuestions() { return questions; }
}
