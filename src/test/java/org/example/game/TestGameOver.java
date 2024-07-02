package org.example.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGameOver {
    private Game game;
    @Before
    public void setUp() {
        game = new Game(GameMode.EASY);
    }
//    @Test
//    public void testGameOver() {
//        // Пример использования метода isGameOver()
//        game.setGameOver(true); // Устанавливаем игру в состояние Game Over
//        assertTrue(game.isGameOver()); // Проверяем, что метод isGameOver() возвращает true
//    }
//    @Test
//    public void testGameOver() {
//        game.gameOver();
//        assertTrue(game.isGameOver());
//        assertFalse(game.isGameWon());
//        assertFalse(game.getTimer().isRunning());
//    }

}
