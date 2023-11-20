
public class PointsManager extends Thread {
    private int points;
    private boolean running;
    private Game game;

    public PointsManager(Game game) {
        this.game = game;
        this.points = 0;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized (this) {
                    // Wait for the player's input
                    wait();

                    // Update points
                    points += game.getPoints();

                    // Notify other threads of the points update
                    notifyAll();
                }
            } catch (InterruptedException e) {
                // This exception is thrown when the game is terminated
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    // This method is used to stop the points manager when the game ends
    public void stopPointsManager() {
        running = false;
    }

    // This method is used to get the current points
    public int getPoints() {
        return points;
    }

    // This method is used to reset the points to zero
    public void resetPoints() {
        points = 0;
    }
}

