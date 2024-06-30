package org.example.game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Level {
    private int levelNumber;
    private GameMode mode;
    private List<Bin> bins;
    private List<Trash> trashes;
    private Timer timer;
    private Timer trashGenerationTimer;
    private GamePanel gamePanel;

    public Level(int levelNumber, GameMode mode, GamePanel gamePanel) {
        this.levelNumber = levelNumber;
        this.mode = mode;
        this.bins = new ArrayList<>();
        this.trashes = new ArrayList<>();
        this.gamePanel = gamePanel;
        initializeLevel(); // Вызываем инициализацию уровня
        setupTimer();
        gamePanel.setLevelNumber(levelNumber); // Устанавливаем текущий уровень в GamePanel
    }

    private void initializeLevel() {
        bins.clear(); // Очищаем текущие корзины, если они есть

        switch (levelNumber) {
            case 1:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                break;
            case 2:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                bins.add(new Bin("Plastic", 300, 500, 50, 50));
                break;
            case 3:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                bins.add(new Bin("Plastic", 300, 500, 50, 50));
                bins.add(new Bin("Metal", 400, 500, 50, 50));
                break;
            case 4:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                bins.add(new Bin("Plastic", 300, 500, 50, 50));
                bins.add(new Bin("Metal", 400, 500, 50, 50));
                bins.add(new Bin("Organic", 500, 500, 50, 50));
                break;
            default:
                // Handle unexpected levelNumber
                break;
        }
    }

    private void setupTimer() {
        timer = new Timer(getSpeedForMode(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTrashes();
                gamePanel.repaint();
            }
        });
        timer.start();

        trashGenerationTimer = new Timer(getTrashGenerationSpeed(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTrash();
            }
        });
        trashGenerationTimer.start();
    }

    private void updateTrashes() {
        Iterator<Trash> iterator = trashes.iterator();
        while (iterator.hasNext()) {
            Trash trash = iterator.next();
            trash.updatePosition();
            checkCollision(iterator, trash);
            if (trash.getY() > 600) {
                iterator.remove();
            }
        }
    }

    private void checkCollision(Iterator<Trash> iterator, Trash trash) {
        for (Bin bin : bins) {
            if (trash.getBounds().intersects(bin.getBounds())) {
                if (trash.getType().equals(bin.getType())) {
                    iterator.remove();
                    gamePanel.increaseScore(5);
                    if (gamePanel.isGameWon()) {
                        stopGame();
                    }
                } else {
                    iterator.remove();
                    gamePanel.decreaseLives();
                    if (gamePanel.isGameOver()) {
                        stopGame();
                    }
                }
                break;
            }
        }
    }

    private int getSpeedForMode() {
        switch (mode) {
            case EASY:
                return 100;
            case MIDDLE:
                return 50;
            case HARD:
                return 20;
            default:
                return 100;
        }
    }

    private int getTrashGenerationSpeed() {
        switch (mode) {
            case EASY:
                return 3000;
            case MIDDLE:
                return 2000;
            case HARD:
                return 1000;
            default:
                return 3000;
        }
    }


    private void generateTrash() {
        String[] trashTypes = {"Paper", "Glass", "Plastic", "Metal", "Organic"};
        int randomIndex = (int) (Math.random() * trashTypes.length);
        String type = trashTypes[randomIndex];
        int x = 100 + randomIndex * 100;
        Trash newTrash = new Trash(type, x, 0, 30, 30);
        trashes.add(newTrash);
    }

    public void start() {
        timer.start();
        trashGenerationTimer.start();
    }

    public void stopGame() {
        timer.stop();
        trashGenerationTimer.stop();
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Bin> getBins() {
        return bins;
    }

    public List<Trash> getTrashes() {
        return trashes;
    }
}
