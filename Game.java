
import javax.swing.*;

/**
 * Game class manages the main game logic, word selection, and overall game
 * flow.
 * This class is thread-safe to prevent race conditions when multiple threads
 * access the game state.
 */
public class Game {
    private WordPool wordPool;
    private Timer timer;
    private PointsManager pointsManager;
    private InputDisplay inputDisplay;
    private String currentWord;
    private String currentInput;
    private int points;
    private JTextField inputField; // Store the input field
    private JLabel timerLabel; // Store the timer label
    private JLabel pointsLabel; // Store the points label
    private JLabel wordLabel; // Store the word label
    private JTextArea logArea; // Store the log area
    private JButton submitButton; // Store the submit button

    public Game(JTextField inputField, JLabel timerLabel, JLabel pointsLabel, JLabel wordLabel, JTextArea logArea,
            JButton submitButton) {
        wordPool = new WordPool();
        this.inputField = inputField; // Save the input field for later use
        currentInput = "";
        points = 0;
        this.timerLabel = timerLabel; // Save the timer label for later use
        this.pointsLabel = pointsLabel; // Save the points label for later use
        this.wordLabel = wordLabel; // Save the word label for later use
        this.logArea = logArea; // Save the log area for later use
        this.submitButton = submitButton; // Save the submit button for later use

    }

    private void log(String message) {
        // Append the message to the log area
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                logArea.append(message + "\n");
            }
        });
    }

    public void startGame() {
        // Create new instances of the threads each time the game starts
        timer = new Timer(this, 60); // 60 seconds countdown
        pointsManager = new PointsManager(this);
        inputDisplay = new InputDisplay(inputField, this);

        // Get the first word
        currentWord = wordPool.getNextWord();
        // Update the word label with the first scrambled word
        updateWordLabel();
        log("Game has started. Let's roll!");
        timer.start();
        pointsManager.start();
        inputDisplay.start();
    }

    public void endGame() {
        timer.stopTimer();
        pointsManager.stopPointsManager();
        inputDisplay.stopInputDisplay();
    }

    public void updateTimer(int countdown) {
        // Update the timer display in the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                timerLabel.setText("Timer: " + countdown);
            }
        });
    }

    public void timeUp() {
        // The player ran out of time
        // Stop the game
        endGame();
        // Create a new Game instance
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                inputField.setEnabled(false);
                submitButton.setEnabled(false);
            }
        });
    }

    private void updateWordLabel() {
        // Update the word label in the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                wordLabel.setText("Scrambled Word: " + wordPool.scrambleWord(currentWord));
            }
        });
    }

    public void submitInput(String input) {
        // The player submitted their guess
        // Check if the input matches the current word
        if (input.equals(currentWord)) {
            // If the guess is correct, increase the points by 10
            points += 10;
            log("Nice guess! Correct! You received " + points + " points.");

            // Decrease the countdown time by 10 seconds, if the timer is not null
            if (timer != null) {
                timer.adjustCountdown(-10);
            }
            // Get the next word
            currentWord = wordPool.getNextWord();
        } else {
            log("Try again! Your points deduct " + points + " points.");

            // If the guess is incorrect and there are more than 0 points, deduct 5 points
            if (points > 0) {
                points -= 5;
            }
        }
        // Reset the current input
        currentInput = "";
        // Update the word label with the next scrambled word
        updateWordLabel();
        // Update the points display
        getPoints();
    }

    public void adjustDifficulty() {
        // Adjust the difficulty based on the player's performance
        // For example, you can adjust the countdown time in the Timer
        // If the player has more points, decrease the countdown time
        if (points > 10) {
            timer.adjustCountdown(-5); // Decrease the countdown time by 5 seconds
        } else if (points < 5) {
            timer.adjustCountdown(5); // Increase the countdown time by 5 seconds
        }
    }

    public String getCurrentInput() {
        return currentInput;
    }

    public int getPoints() {
        // Update the points display in the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                pointsLabel.setText("Points: " + points);
            }
        });

        return points;
    }

    public void resetGame() {
        // Reset the game state
        currentWord = null;
        currentInput = "";
        points = 0;
        timer = null;
        pointsManager = null;
        inputDisplay = null;

        // Update the GUI
        updateWordLabel();
        getPoints();
        updateTimer(60);
    }
}
