package org.example.game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private Level currentLevel;
    private List<Trash> fallingTrashes;
    private Image backgroundImage;

    public GamePanel(Level currentLevel, List<Trash> fallingTrashes, Image backgroundImage) {
        this.currentLevel = currentLevel;
        this.fallingTrashes = fallingTrashes;
        this.backgroundImage = backgroundImage;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        drawBins(g);
        drawTrashes(g);
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
}
