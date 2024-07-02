package org.example.game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private Game game;

    private List<Trash> fallingTrashes;
    private Image backgroundImage;
    private int lives;
    private int score;
    private Image heartImage;
    private boolean isGameOver;
    private boolean isGameWon;
    private int levelNumber;

    public GamePanel(Game game, List<Trash> fallingTrashes, Image backgroundImage, int lives, int score) {
        this.game = game;
        this.fallingTrashes = fallingTrashes;
        this.backgroundImage = backgroundImage;
        this.lives = lives;
        this.score = score;
        this.levelNumber = 1; // По умолчанию начинаем с первого уровня
        this.heartImage = new ImageIcon("src/main/resources/heart.png").getImage();
        this.isGameOver = false;
        this.isGameWon = false;
    }

    /**
     * Отрисовка компонентов на панели
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        drawBins(g);
        drawTrashes(g);
        drawLives(g);
        drawScore(g);
        drawLevelNumber(g);
        if (isGameOver) {
            drawGameOver(g);
        }
        if (isGameWon) {
            drawGameWon(g);
        }
    }

    /**
     * Отрисовка корзин
     * @param g
     */
    private void drawBins(Graphics g) {
        for (Bin bin : game.getBins()) {
            g.drawImage(bin.getImage(), bin.getX(), bin.getY(), bin.getWidth(), bin.getHeight(), this);
        }

    }

    /**
     * Отрисовка мусора
     * @param g
     */
    private void drawTrashes(Graphics g) {
        for (Trash trash : fallingTrashes) {
            g.drawImage(trash.getImage(), trash.getX(), trash.getY(), trash.getWidth(), trash.getHeight(), this);
        }
    }

    /**
     * Отрисовка жизней (сердечек)
     * @param g
     */
    private void drawLives(Graphics g) {
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, 10 + i * 40, 10, 30, 30, this);
        }
    }

    /**
     * Отрисовка счета
     * @param g
     */
    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, getWidth() - 150, 30);
    }

    /**
     * Отрисовка номера уровня
     * @param g
     */
    private void drawLevelNumber(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Level: " + levelNumber, getWidth() - 150, 60);
    }

    /**
     * Отрисовка сообщения о конце игры
     * @param g
     */
    private void drawGameOver(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.RED);
        g.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2);
    }

    /**
     * Отрисовка сообщения о победе
     * @param g
     */
    private void drawGameWon(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.GREEN);
        g.drawString("You Win!", getWidth() / 2 - 150, getHeight() / 2);
    }

    /**
     * Установка состояния конца игры
     * @param isGameOver
     */
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
        repaint();
    }

    /**
     * Установка состояния победы
     * @param isGameWon
     */
    public void setGameWon(boolean isGameWon) {
        this.isGameWon = isGameWon;
        repaint();
    }

    /**
     * Установка номера уровня
     * @param levelNumber
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        repaint();
    }

    /**
     * Проверка состояния конца игры
     * @return
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Проверка состояния победы
     * @return
     */
    public boolean isGameWon() {
        return isGameWon;
    }

    /**
     * Обновление жизней
     * @param lives
     */
    public void updateLives(int lives) {
        this.lives = lives;
        repaint();
    }

    /**
     * Обновление уровня игры
     * @param levelNumber
     */
    public void updateLevel(int levelNumber) {
        this.levelNumber = levelNumber;
        repaint();
    }

    /**
     * Обновление счета игры
     * @param score
     */
    public void updateScore(int score) {
        this.score = score;
        System.out.println("обновляет счёт");
        repaint();
    }

    public void reset(int lives, int score) {
        this.lives = lives;
        this.score = score;
        this.isGameOver = false;
        this.isGameWon = false;
        repaint();
    }
}
