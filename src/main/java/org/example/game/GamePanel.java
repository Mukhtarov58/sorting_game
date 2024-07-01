package org.example.game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private Level currentLevel;
    private List<Trash> fallingTrashes;
    private Image backgroundImage;
    private int lives;
    private int score;
    private Image heartImage;
    private boolean isGameOver;
    private boolean isGameWon;
    private int levelNumber;

    public GamePanel(Level currentLevel, List<Trash> fallingTrashes, Image backgroundImage, int lives, int score) {
        this.currentLevel = currentLevel;
        this.fallingTrashes = fallingTrashes;
        this.backgroundImage = backgroundImage;
        this.lives = lives;
        this.score = score;
        this.levelNumber = 1; // По умолчанию начинаем с первого уровня
        this.heartImage = new ImageIcon("src/main/resources/heart.png").getImage();
        this.isGameOver = false;
        this.isGameWon = false;
    }

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

    private void drawBins(Graphics g) {
        if (currentLevel != null) {
            for (Bin bin : currentLevel.getBins()) {
                g.drawImage(bin.getImage(), bin.getX(), bin.getY(), bin.getWidth(), bin.getHeight(), this);
            }
        }
    }

    private void drawTrashes(Graphics g) {
        for (Trash trash : fallingTrashes) {
            g.drawImage(trash.getImage(), trash.getX(), trash.getY(), trash.getWidth(), trash.getHeight(), this);
        }
    }

    private void drawLives(Graphics g) {
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, 10 + i * 40, 10, 30, 30, this);
        }
    }

    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, getWidth() - 150, 30);
    }
    private void drawLevelNumber(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Level: " + levelNumber, getWidth() - 150, 60);
    }

    private void drawGameOver(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.RED);
        g.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2);
    }

    private void drawGameWon(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.GREEN);
        g.drawString("You Win!", getWidth() / 2 - 150, getHeight() / 2);
    }
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
        repaint();
    }

    public void setGameWon(boolean isGameWon) {
        this.isGameWon = isGameWon;
        repaint();
    }
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        repaint();
    }


    public void decreaseLives() {
        if (lives > 0) {
            lives--;
            if (lives == 0) {
                isGameOver = true;
            }
        }
        repaint();
    }

    public void increaseScore(int increment) {
        score += increment;
        if (score >= 1000) {
            isGameWon = true;
        }
        repaint();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setFallingTrashes(List<Trash> fallingTrashes) {
        this.fallingTrashes = fallingTrashes;
    }

    public void updateLives(int lives) {
        this.lives = lives;
        repaint();
    }
    public void updateLevel(int levelNumber) {
        this.levelNumber = levelNumber;
        repaint();
    }

    public void updateScore(int score) {
        this.score = score;
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
