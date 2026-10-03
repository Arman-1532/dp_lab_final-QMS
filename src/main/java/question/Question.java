package question;

// TODO: Base class for all question types
// Base abstract class for all types of questions
public abstract class Question {
    protected String text;         // Question text
    protected String answer;       // Correct answer

    public Question(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    // TODO: Add method to get type of question (MCQ, TF, FILL)
    public abstract String getType();

    // TODO: Add method to get options if applicable (MCQ, TF)
    public abstract java.util.List<String> getOptions();

    // TODO: Optionally implement clone() if using Prototype pattern
    public abstract Question clone();
}
