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

    public Game(GameMode mode) {
        this.mode = mode;
        this.fallingTrashes = new ArrayList<>();
        ImageIcon backgroundImageIcon = new ImageIcon("src/main/resources/backgroundImageIcon.jpg");
        Image backgroundImage = backgroundImageIcon.getImage();
        ImageIcon icon = new ImageIcon("src/main/resources/GSort.ico");
        setIconImage(icon.getImage());
        this.gamePanel = new GamePanel(currentLevel, fallingTrashes, backgroundImage);

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
        for (Bin bin : currentLevel.getBins()) {
            bin.move(dx);
        }
    }

    private void startGame() {
        currentLevel = new Level(1, mode, gamePanel);
        currentLevel.start();
        timer.start();
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
                    SoundPlayer.playSound("src/main/resources/correct.wav");
                    break;
                } else {
                    fallingTrashes.remove(trash);
                    SoundPlayer.playSound("src/main/resources/wrong.wav");
                    break;
                }
            }
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
}
