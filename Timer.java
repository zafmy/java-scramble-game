
import java.util.concurrent.TimeUnit;

public class Timer extends Thread {
    private int countdown;
    private boolean running;
    private Game game;

    public Timer(Game game, int countdown) {
        this.game = game;
        this.countdown = countdown;
        this.running = true;
    }

    @Override
    public void run() {
        while (running && countdown > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
                countdown--;
                game.updateTimer(countdown);
                if (countdown == 0) {
                    game.timeUp();
                }
            } catch (InterruptedException e) {
                // This exception is thrown when the game is terminated before the countdown ends
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    // This method is used to dynamically adjust the difficulty based on player's performance
    public void adjustCountdown(int adjustment) {
        countdown += adjustment;
    }

    // This method is used to stop the timer when the game ends
    public void stopTimer() {
        running = false;
    }
}

