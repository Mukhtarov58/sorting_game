package org.example.game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Level {
    private int levelNumber;
    private GameMode mode;
    private List<Bin> bins;
    private List<Trash> trashes;
    private Timer timer;
    private GamePanel gamePanel;

    public Level(int levelNumber, GameMode mode, GamePanel gamePanel) {
        this.levelNumber = levelNumber;
        this.mode = mode;
        this.bins = new ArrayList<>();
        this.trashes = new ArrayList<>();
        this.gamePanel = gamePanel;
        initializeLevel();
        setupTimer();
    }

    private void initializeLevel() {
        if (levelNumber >= 1) {
            bins.add(new Bin("Paper", 100, 500, 50, 50));
            bins.add(new Bin("Glass", 200, 500, 50, 50));
        }
        if (levelNumber >= 2) {
            bins.add(new Bin("Plastic", 300, 500, 50, 50));
        }
        if (levelNumber >= 3) {
            bins.add(new Bin("Metal", 400, 500, 50, 50));
        }
        if (levelNumber >= 4) {
            bins.add(new Bin("Organic", 500, 500, 50, 50));
        }

        // Добавление мусора
        trashes.add(new Trash("Paper", 100, 0, 30, 30));
        trashes.add(new Trash("Glass", 200, 0, 30, 30));
        trashes.add(new Trash("Plastic", 300, 0, 30, 30));
        trashes.add(new Trash("Metal", 400, 0, 30, 30));
        trashes.add(new Trash("Organic", 500, 0, 30, 30));

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
    }

    private void updateTrashes() {
        List<Trash> toRemove = new ArrayList<>();
        for (Trash trash : trashes) {
            trash.updatePosition();
            checkCollision(trash);
            if (trash.getY() > 600) { // Проверка, что мусор выходит за пределы экрана
                toRemove.add(trash);
            }
        }
        trashes.removeAll(toRemove);
    }

    private void checkCollision(Trash trash) {
        List<Trash> toRemove = new ArrayList<>();
        for (Bin bin : bins) {
            if (trash.getBounds().intersects(bin.getBounds())) {
                if (trash.getType().equals(bin.getType())) {
                    toRemove.add(trash);
                    SoundPlayer.playSound("src/main/resources/correct.wav");
                    break;
                } else {
                    toRemove.add(trash);
                    SoundPlayer.playSound("src/main/resources/wrong.wav");
                    break;
                }
            }
        }
        trashes.removeAll(toRemove);
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

    public void start() {
        // Запускаем таймер для обновления падающего мусора
        timer.start();
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
