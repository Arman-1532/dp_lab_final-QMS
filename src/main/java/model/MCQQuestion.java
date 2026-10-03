package model;

import java.util.List;

public class MCQQuestion extends Question {
    private List<String> options;

    public MCQQuestion(String text, List<String> options, String answer) {
        super(text, answer);
        this.options = options;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return answer.equalsIgnoreCase(userAnswer);
    }

    @Override
    public List<String> getOptions() {
        return options;
    }
}

