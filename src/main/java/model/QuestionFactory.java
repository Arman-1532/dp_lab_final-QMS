package model;

import java.util.List;

public class QuestionFactory {
    public static Question createQuestion(String type, String text, List<String> options, String answer) {
        switch (type) {
            case "MCQ":
                return new MCQQuestion(text, options, answer);
            case "TF":
                return new TrueFalseQuestion(text, answer);
            case "FILL":
                return new FillInBlankQuestion(text, answer);
            default:
                throw new IllegalArgumentException("Unknown question type: " + type);
        }
    }
}

