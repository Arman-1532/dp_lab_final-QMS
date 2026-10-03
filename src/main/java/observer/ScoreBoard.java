package observer;

import javax.swing.JLabel;

// Observer to display score updates
public class ScoreBoard implements Observer {
    private JLabel scoreLabel;

    public ScoreBoard(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    @Override
    public void update(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
