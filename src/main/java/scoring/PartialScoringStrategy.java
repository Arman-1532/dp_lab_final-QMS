package scoring;

public class PartialScoringStrategy implements ScoringStrategy {
    @Override
    public int calculateScore(String selectedAnswer, String correctAnswer) {
        if (selectedAnswer.equalsIgnoreCase(correctAnswer)) {
            return 1;
        } else {
            return 1;
        }
    }
}

