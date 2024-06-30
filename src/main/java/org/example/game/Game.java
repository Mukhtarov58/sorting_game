package org.example.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends JFrame {
    private Level currentLevel;
    private GameMode mode;
    private Timer timer;
    private List<Trash> fallingTrashes;
    private GamePanel gamePanel;
    private int lives;
    private int score;
    private int currentLevelNumber;

    public Game(GameMode mode) {
        this.mode = mode;
        this.fallingTrashes = new ArrayList<>();
        this.lives = 5;
        this.score = 0;
        this.currentLevelNumber = 1;
        ImageIcon backgroundImageIcon = new ImageIcon("src/main/resources/backgroundImageIcon.jpg");
        Image backgroundImage = backgroundImageIcon.getImage();
        ImageIcon icon = new ImageIcon("src/main/resources/GSort.ico");
        setIconImage(icon.getImage());
        this.gamePanel = new GamePanel(currentLevel, fallingTrashes, backgroundImage, lives, score);

        initUI();
        startGame();
    }

    private void initUI() {
        setTitle("Sorting Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(gamePanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        moveBins(-10);
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveBins(10);
                        break;
                    case KeyEvent.VK_ENTER:
                        if (gamePanel.isGameOver() || gamePanel.isGameWon()) {
                            resetGame();
                        }
                        break;
                }
            }
        });

        timer = new Timer(getSpeedForMode(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
                gamePanel.repaint();
            }
        });
        timer.start();
    }

    private void moveBins(int dx) {
        if (currentLevel != null) {
            for (Bin bin : currentLevel.getBins()) {
                bin.move(dx);
            }
        }
    }

    private void startGame() {
        currentLevel = new Level(currentLevelNumber, mode, gamePanel);
        gamePanel.setCurrentLevel(currentLevel);
        gamePanel.setFallingTrashes(currentLevel.getTrashes());
        gamePanel.setLevelNumber(currentLevelNumber); // Устанавливаем текущий уровень в GamePanel
        currentLevel.start();
    }


    private void updateGame() {
        Iterator<Trash> iterator = fallingTrashes.iterator();
        while (iterator.hasNext()) {
            Trash trash = iterator.next();
            trash.updatePosition();
            checkCollision(trash);
        }
    }

    private void checkCollision(Trash trash) {
        for (Bin bin : currentLevel.getBins()) {
            if (trash.getBounds().intersects(bin.getBounds())) {
                if (trash.getType().equals(bin.getType())) {
                    fallingTrashes.remove(trash);
                    score += 5;
                    gamePanel.updateScore(score);
                    if (score >= 1000) {
                        gamePanel.setGameWon(true);
                        timer.stop();
                    } else if (shouldLevelUp()) {
                        currentLevelNumber++;
                        if (currentLevelNumber <= 4) {
                            startGame(); // Обновляем текущий уровень после увеличения номера уровня
                        }
                    }
                    SoundPlayer.playSound("src/main/resources/correct.wav");
                } else {
                    fallingTrashes.remove(trash);
                    lives--;
                    gamePanel.updateLives(lives);
                    if (lives <= 0) {
                        gamePanel.setGameOver(true);
                        timer.stop();
                    }
                    SoundPlayer.playSound("src/main/resources/wrong.wav");
                }
                break;
            }
        }
    }

    private boolean shouldLevelUp() {
        switch (currentLevelNumber) {
            case 1:
                return score >= 10;
            case 2:
                return score >= 30;
            case 3:
                return score >= 400;
            case 4:
                return score >= 1000;
            default:
                return false;
        }
    }

    private int getSpeedForMode() {
        switch (mode) {
            case EASY:
                return 1000;
            case MIDDLE:
                return 700;
            case HARD:
                return 400;
            default:
                return 1000;
        }
    }

    private void resetGame() {
        lives = 5;
        score = 0;
        currentLevelNumber = 1;
        gamePanel.reset(lives, score);
        startGame();
    }
}
