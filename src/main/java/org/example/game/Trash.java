package org.example.game;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Trash {
    private String type;
    private int x, y, width, height;
    private Image image;

    public Trash(String type, int x, int y, int width, int height) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadImage();
    }

    private void loadImage() {
        String imagePath = "src/main/resources/images/" + type.toLowerCase() + ".png";
        ImageIcon ii = new ImageIcon(imagePath);
        this.image = ii.getImage();
    }

    public String getType() {
        return type;
    }

    public void updatePosition() {
        this.y += 5; // Скорость падения
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }
}
