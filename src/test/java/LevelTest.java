import org.davidfabio.game.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelTest {
    @Test
    @DisplayName("Create new Level")
    public void testCreateLevel() {
        float width = 100, height = 100;
        Level level = new Level(width,height);

        assertEquals(level.getWidth(),width);
        assertEquals(level.getHeight(),height);
    }
}
