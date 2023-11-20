
import javax.swing.*;

public class InputDisplay extends Thread {
    private String currentInput;
    private boolean running;
    private JTextField inputField;
    private Game game;

    public InputDisplay(JTextField inputField, Game game) {
        this.inputField = inputField;
        this.game = game;
        this.currentInput = "";
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized (this) {
                    // Wait for the player's input
                    wait();

                    // Update the current input
                    currentInput = game.getCurrentInput();

                    // Update the input field in the GUI
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            inputField.setText(currentInput);
                        }
                    });

                    // Sleep for a while to reduce CPU usage
                    sleep(100);
                }
            } catch (InterruptedException e) {
                // This exception is thrown when the game is terminated
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    // This method is used to stop the input display when the game ends
    public void stopInputDisplay() {
        running = false;
    }

    // This method is used to get the current input
    public String getCurrentInput() {
        return currentInput;
    }

    // This method is used to reset the current input to an empty string
    public void resetCurrentInput() {
        currentInput = "";
    }
}

