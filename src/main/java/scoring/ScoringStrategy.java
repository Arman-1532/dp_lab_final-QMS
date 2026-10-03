package scoring;

// TODO: Define scoring strategy interface
public interface ScoringStrategy {
    // Calculate score based on given answer
    // TODO: Implement different strategies: Normal, Negative, Partial
    int calculateScore(String selectedAnswer, String correctAnswer);
}