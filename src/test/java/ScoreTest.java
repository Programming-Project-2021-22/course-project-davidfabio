import org.davidfabio.game.Score;
import org.davidfabio.utils.Settings;
import org.junit.jupiter.api.*;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ScoreTest {
    @Test
    @DisplayName("Create new Score init values")
    public void testCreateScore() {
        Score score = new Score();

        assertEquals(0,score.getDuration());
        assertEquals(Settings.username,score.getUsername());
        assertEquals(0,score.getPoints());
        assertEquals(0,score.getPickups());
    }

    @Test
    @DisplayName("Ended new Score values")
    public void testEndedNewScore() throws InterruptedException {
        Score score = new Score();
        sleep(200);
        score.end(25);

        assertNotEquals(0,score.getDuration());
        assertEquals(Settings.username,score.getUsername());
        assertEquals(0,score.getPoints());
        assertEquals(25,score.getPickups());
    }

    @Test
    @DisplayName("Points are accumulated correctly")
    public void testPointAccumulation() throws InterruptedException {
        Score score = new Score();
        sleep(200);
        score.end(0);

        score.addPoints(10);
        score.addPoints(10);
        score.addPoints(-5);
        score.addPoints(20);

        assertNotEquals(0,score.getDuration());
        assertEquals(Settings.username,score.getUsername());
        assertEquals(35,score.getPoints());
        assertEquals(0,score.getPickups());
    }

    @Test
    @DisplayName("Compare two Scores")
    public void testCompareTwoScores() {
        Score score1 = new Score();
        score1.end(20);

        Score score2 = new Score();
        score2.end(40);

        score1.addPoints(10);
        score2.addPoints(5);

        assertEquals(true, score1.compareTo(score2) < 0);
    }
}