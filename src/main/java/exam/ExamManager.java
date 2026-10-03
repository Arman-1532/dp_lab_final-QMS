package exam;

import model.Question;
import model.Quiz;
import observer.Observer;
import observer.Subject;

import java.util.ArrayList;
import java.util.List;

// Template for central exam manager
// TODO: Implement this class as a Singleton
// Responsibilities:
//  - Manage list of quizzes
//  - Track current quiz and current question index
//  - Keep current score
//  - Notify observers when score changes (Observer pattern)
//  - Provide methods to move to next question, get current quiz/question, etc.
public class ExamManager implements Subject {
    private static ExamManager instance;
    private List<Quiz> quizzes;
    private Quiz currentQuiz;
    private int currentQuestionIndex;
    private int score;
    private List<Observer> observers;

    private ExamManager() {
        quizzes = new ArrayList<>();
        observers = new ArrayList<>();
        currentQuiz = null;
        currentQuestionIndex = 0;
        score = 0;
    }

    public static ExamManager getInstance() {
        if (instance == null) {
            instance = new ExamManager();
        }
        return instance;
    }

    // ==============================
    // Methods to manage quizzes
    // ==============================

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void selectQuiz(Quiz quiz) {
        this.currentQuiz = quiz;
        this.currentQuestionIndex = 0;
        this.score = 0;
        notifyObservers();
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public Question getCurrentQuestion() {
        if (currentQuiz == null || currentQuiz.getQuestions().isEmpty()) return null;
        if (currentQuestionIndex < 0 || currentQuestionIndex >= currentQuiz.getQuestions().size()) return null;
        return currentQuiz.getQuestions().get(currentQuestionIndex);
    }

    public void nextQuestion() {
        if (currentQuiz != null) {
            currentQuestionIndex++;
        }
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    // ==============================
    // Score management
    // ==============================

    public void setScore(int score) {
        this.score = score;
        notifyObservers();
    }

    public int getScore() {
        return score;
    }

    // ==============================
    // Observer methods
    // ==============================
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(score);
        }
    }
}
