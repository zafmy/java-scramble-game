
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * WordPool class provides the pool of scramble words.
 * This class is thread-safe to prevent race conditions when multiple threads
 * access the word pool.
 */
public class WordPool {

    // List of words for the game
    private List<String> words = new ArrayList<>(Arrays.asList(
            "java", "input", "display", "timer", "points", "game", "word", "pool"));

    // Index of the current word
    private int currentIndex = 0;

    /**
     * Get the next word from the pool.
     * This method is synchronized to prevent race conditions.
     * 
     * @return the next word
     */
    public synchronized String getNextWord() {
        // If we've gone through all the words, shuffle them for variety
        if (currentIndex >= words.size()) {
            Collections.shuffle(words);
            currentIndex = 0;
        }

        // Return the next word and increment the index
        return words.get(currentIndex++);
    }

    /**
     * Scramble a word.
     * This method is not synchronized because it does not modify any shared data.
     * 
     * @param word the word to scramble
     * @return the scrambled word
     */
    public String scrambleWord(String word) {
        if (word == null) {
            return "";
        }
        List<String> letters = new ArrayList<>(Arrays.asList(word.split("")));
        Collections.shuffle(letters);
        return String.join("", letters);
    }
}
