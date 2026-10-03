package application;

import exam.ExamManager;
import model.*;
import observer.ScoreBoard;
import scoring.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;

public class Main {

    private JFrame frame;
    private JComboBox<String> quizSelector;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup group;
    private JButton nextButton;
    private JLabel scoreLabel;
    private JTextField fillInput;
    private JComboBox<String> scoringSelector; // Dropdown for scoring method

    private ExamManager examManager;
    private ScoringStrategy scoringStrategy;
    private ScoreBoard scoreBoard;

    public Main() {
        frame = new JFrame("Quiz System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Top panel: Quiz selector + scoring selector + score
        JPanel topPanel = new JPanel(new FlowLayout());
        quizSelector = new JComboBox<>();
        topPanel.add(new JLabel("Select Quiz:"));
        topPanel.add(quizSelector);

        // Scoring method dropdown
        scoringSelector = new JComboBox<>(new String[]{"Normal", "Negative", "Partial"});
        topPanel.add(new JLabel("Scoring:"));
        topPanel.add(scoringSelector);

        scoreLabel = new JLabel("Score: 0");
        topPanel.add(scoreLabel);

        frame.add(topPanel, BorderLayout.NORTH);

        // Question label
        questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(questionLabel, BorderLayout.CENTER);

        // Options panel
        JPanel optionsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        optionButtons = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            group.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        fillInput = new JTextField();
        fillInput.setVisible(false);
        optionsPanel.add(fillInput);

        frame.add(optionsPanel, BorderLayout.SOUTH);

        nextButton = new JButton("Next Question");
        frame.add(nextButton, BorderLayout.EAST);

        examManager = ExamManager.getInstance();
        scoreBoard = new ScoreBoard(scoreLabel);
        examManager.attach(scoreBoard);

        // Setup sample quizzes
        setupSampleQuizzes();
        populateQuizSelector();

        // Auto-load first quiz
        quizSelector.setSelectedIndex(0);
        startSelectedQuiz();

        // Event listeners
        quizSelector.addActionListener(e -> startSelectedQuiz());
        nextButton.addActionListener(e -> handleNext());

        frame.setVisible(true);
    }

    // TODO: flexible way of constructing different question types
    private void setupSampleQuizzes() {
        Quiz javaQuiz = new Quiz("Java Basics");
        javaQuiz.addQuestion(QuestionFactory.createQuestion("MCQ", "Java is a ____ language?", Arrays.asList("Compiled", "Interpreted", "Both", "None"), "Both"));
        javaQuiz.addQuestion(QuestionFactory.createQuestion("TF", "The Earth is flat?", null, "false"));
        javaQuiz.addQuestion(QuestionFactory.createQuestion("FILL", "2 + 2 = ?", null, "4"));

        Quiz mathQuiz = new Quiz("Math Basics");
        mathQuiz.addQuestion(QuestionFactory.createQuestion("MCQ", "5 * 5 = ?", Arrays.asList("10", "20", "25", "30"), "25"));
        mathQuiz.addQuestion(QuestionFactory.createQuestion("TF", "0 is an even number?", null, "true"));

        examManager.addQuiz(javaQuiz);
        examManager.addQuiz(mathQuiz);
    }

    private void populateQuizSelector() {
        quizSelector.removeAllItems();
        for (Quiz q : examManager.getQuizzes()) {
            quizSelector.addItem(q.getTitle());
        }
    }

    private void startSelectedQuiz() {
        int index = quizSelector.getSelectedIndex();
        Quiz selected = examManager.getQuizzes().get(index);
        examManager.selectQuiz(selected);
        nextButton.setEnabled(true);
        loadQuestion();
    }

    private void loadQuestion() {
        Question q = examManager.getCurrentQuestion();
        if (q == null) {
            JOptionPane.showMessageDialog(frame, "Quiz Finished! Final Score: " + examManager.getScore());
            nextButton.setEnabled(false);
            return;
        }
        questionLabel.setText(q.getText());
        group.clearSelection();
        fillInput.setVisible(false);
        List<String> opts = q.getOptions();
        if (opts == null || opts.isEmpty()) {
            fillInput.setText("");
            fillInput.setVisible(true);
            for (JRadioButton btn : optionButtons) btn.setVisible(false);
        } else {
            for (int i = 0; i < optionButtons.length; i++) {
                if (i < opts.size()) {
                    optionButtons[i].setText(opts.get(i));
                    optionButtons[i].setVisible(true);
                } else {
                    optionButtons[i].setVisible(false);
                }
            }
            optionButtons[0].setSelected(true);
        }
    }

    private void handleNext() {
        Question current = examManager.getCurrentQuestion();
        String selectedAnswer = null;
        List<String> opts = current.getOptions();
        if (opts == null || opts.isEmpty()) {
            selectedAnswer = fillInput.getText().trim();
        } else {
            for (JRadioButton rb : optionButtons) {
                if (rb.isVisible() && rb.isSelected()) {
                    selectedAnswer = rb.getText();
                    break;
                }
            }
        }
        if (selectedAnswer != null && !selectedAnswer.isEmpty()) {
            String method = (String) scoringSelector.getSelectedItem();
            if (method == null) method = "Normal";
            switch (method) {
                case "Normal":
                    scoringStrategy = new NormalScoringStrategy();
                    break;
                case "Negative":
                    scoringStrategy = new NegativeScoringStrategy();
                    break;
                case "Partial":
                    scoringStrategy = new PartialScoringStrategy();
                    break;
                default:
                    scoringStrategy = new NormalScoringStrategy();
            }
            // Directly update score
            examManager.setScore(examManager.getScore() + scoringStrategy.calculateScore(selectedAnswer, current.getAnswer()));
            // Only advance if answer is correct (or partial credit for Partial strategy)
            boolean isCorrect = current.checkAnswer(selectedAnswer);
            if (isCorrect || method.equals("Partial")) {
                examManager.nextQuestion();
                loadQuestion();
            }
            // No dialog for incorrect answer, just stay on the same question
        } else {
            JOptionPane.showMessageDialog(frame, "Please select or enter an answer!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
