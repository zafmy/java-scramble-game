import javax.swing.*;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

/**
 * Main class to initialize and start the game.
 */
public class Main {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // Create a new JFrame to hold the game
                    JFrame frame = new JFrame("Scramble Game");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800, 600);

                    // Set the layout manager to GridBagLayout
                    frame.setLayout(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();

                    // Create JLabels for the timer and points
                    JLabel timerLabel = new JLabel("Timer: 60");
                    JLabel pointsLabel = new JLabel("Points: 0");

                    // Add the JLabels to the frame
                    c.gridx = 0;
                    c.gridy = 0;
                    frame.add(timerLabel, c);

                    c.gridx = 1;
                    c.gridy = 0;
                    frame.add(pointsLabel, c);

                    // Create a JTextField for user input
                    JTextField inputField = new JTextField();
                    Font font = new Font("SansSerif", Font.BOLD, 24);
                    inputField.setFont(font);

                    // Create a JTextArea for the activity log
                    JTextArea logArea = new JTextArea();
                    logArea.setEditable(false); // Make the log area read-only
                    JScrollPane logScrollPane = new JScrollPane(logArea); // Add a scroll pane to the log area
                    c.gridx = 0;
                    c.gridy = 5;
                    c.gridwidth = 2; // Span across two columns
                    frame.add(logScrollPane, c);
                    // Display the frame
                    frame.setVisible(true);

                    // Set the preferred size of the JTextField based on the font size
                    int fieldWidth = 10 * font.getSize();
                    int fieldHeight = 2 * font.getSize();
                    inputField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

                    c.gridx = 0;
                    c.gridy = 1;
                    c.gridwidth = 2; // Span across two columns
                    frame.add(inputField, c);

                    // Create a JLabel to display the scrambled word
                    JLabel wordLabel = new JLabel();
                    c.gridx = 0;
                    c.gridy = 2;
                    c.gridwidth = 2; // Span across two columns
                    frame.add(wordLabel, c);

                    // Add a submit button
                    JButton submitButton = new JButton("Submit");
                    submitButton.setEnabled(false); // Initially disable the submit button
                    c.gridx = 0;
                    c.gridy = 3;
                    c.gridwidth = 1; // Reset to one column
                    frame.add(submitButton, c);

                    // Create a new Game instance
                    Game game = new Game(inputField, timerLabel, pointsLabel, wordLabel, logArea, submitButton);

                    submitButton.addActionListener(e -> game.submitInput(inputField.getText()));

                    // Add an end button
                    JButton endButton = new JButton("End");
                    endButton.setEnabled(false); // Initially disable the end button
                    c.gridx = 0;
                    c.gridy = 4;
                    frame.add(endButton, c);
                    endButton.addActionListener(e -> {
                        game.endGame();
                        game.resetGame();
                        submitButton.setEnabled(false); // Disable the submit button
                    });
                    // Add a start button
                    JButton startButton = new JButton("Start");
                    c.gridx = 1;
                    c.gridy = 3;
                    frame.add(startButton, c);
                    startButton.addActionListener(e -> {
                        game.startGame();
                        submitButton.setEnabled(true); // Enable the submit button when the start button is clicked
                        endButton.setEnabled(true); // Enable the end button when the start button is clicked
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}