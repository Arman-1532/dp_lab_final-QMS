package model;

import java.util.Collections;
import java.util.List;

public class FillInBlankQuestion extends Question {
    public FillInBlankQuestion(String text, String answer) {
        super(text, answer);
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return answer.equalsIgnoreCase(userAnswer.trim());
    }

    @Override
    public List<String> getOptions() {
        return Collections.emptyList();
    }
}

