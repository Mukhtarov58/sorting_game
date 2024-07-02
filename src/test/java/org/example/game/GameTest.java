package org.example.game;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game(GameMode.EASY);
    }

    @Test
    public void testAdvanceToNextLevel() {
        game.advanceToNextLevel();
        assertEquals(2, game.getCurrentLevelNumber());
    }
}