package model;

import java.util.Arrays;
import java.util.List;

public class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(String text, String answer) {
        super(text, answer);
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return answer.equalsIgnoreCase(userAnswer);
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("True", "False");
    }
}

