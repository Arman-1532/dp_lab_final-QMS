package model;

import java.util.List;

public abstract class Question {
    protected String text;
    protected String answer;

    public Question(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public String getText() { return text; }
    public String getAnswer() { return answer; }

    public abstract boolean checkAnswer(String userAnswer);
    public abstract List<String> getOptions();
}
