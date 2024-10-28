package org.example.game;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

// Класс мусорных корзин
public class Bin {
    private String type;
    private int x, y, width, height;
    private Image image;

    public Bin(String type, int x, int y, int width, int height) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadImage();
    }

    /**
     * Загрузка изображения корзины на основе типа
     */
    private void loadImage() {
        String imagePath = "/images/bin_" + type.toLowerCase() + ".png";  // Путь от корня ресурсов
        ImageIcon ii = new ImageIcon(getClass().getResource(imagePath));
        this.image = ii.getImage();
    }

    public String getType() {
        return type;
    }

    /**
     * Метод для перемещения корзины по оси X
     * @param dx
     */
    public void move(int dx) {
        this.x += dx;
    }

    /**
     * Метод для получения прямоугольных границ корзины (для столкновений)
     * @return
     */
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
