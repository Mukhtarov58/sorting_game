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
    private GameMode mode;
    private Timer timer;
    private List<Trash> trashes;
    private GamePanel gamePanel;
    private int lives;
    private int score;
    private int currentLevelNumber;
    private Timer trashGenerationTimer;
    private List<Bin> bins;

    /**
     * Конструктор игры, инициализирует необходимые компоненты.
     *
     * @param mode режим игры (легкий, средний, сложный)
     */
    public Game(GameMode mode) {
        this.mode = mode;
        this.trashes = new ArrayList<>();
        this.bins = new ArrayList<>();
        this.lives = 5;
        this.score = 0;
        this.currentLevelNumber = 1;
        ImageIcon backgroundImageIcon = new ImageIcon("src/main/resources/backgroundImageIcon.jpg");
        Image backgroundImage = backgroundImageIcon.getImage();
        ImageIcon icon = new ImageIcon("src/main/resources/GSort.ico");
        setIconImage(icon.getImage());
        this.gamePanel = new GamePanel(this, trashes, backgroundImage, lives, score);
        initUI();
        startGame();
    }

    /**
     * Инициализация пользовательского интерфейса.
     */
    private void initUI() {
        setTitle("Sorting Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(gamePanel);
        // Обработка нажатий клавиш
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
        // Таймер для обновления положения мусора
        timer = new Timer(getSpeedForMode(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTrashes();
                gamePanel.repaint();
            }
        });
        timer.start();
        // Таймер для генерации нового мусора
        trashGenerationTimer = new Timer(getTrashGenerationSpeed(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTrash();
            }
        });
        trashGenerationTimer.start();
    }

    /**
     * Обновление положения мусора.
     */
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

    /**
     * Перемещение корзин.
     *
     * @param dx смещение по оси x
     */
    private void moveBins(int dx) {
        for (Bin bin : bins) {
            bin.move(dx);
        }
    }

    /**
     * Запуск игры.
     */
    private void startGame() {
        System.out.println("Игра началась");
        initializeLevel();
        trashes.clear();
        gamePanel.setLevelNumber(currentLevelNumber);
    }

    /**
     * Проверка столкновений мусора с корзинами.
     *
     * @param iterator итератор для удаления мусора
     * @param trash текущий мусор
     */
    private void checkCollision(Iterator<Trash> iterator, Trash trash) {
        for (Bin bin : bins) {
            if (trash.getBounds().intersects(bin.getBounds())) {
                if (trash.getType().equals(bin.getType())) {
                    iterator.remove(); // Удаляем мусор из списка, если совпадение типов
                    System.out.println("Увеличиваем очки на 5");
                    score += 5;
                    gamePanel.updateScore(score);
                    SoundPlayer.playSound("src/main/resources/correct.wav");
                    if (shouldLevelUp()) {
                        advanceToNextLevel();
                    }
                } else {
                    iterator.remove();
                    lives--;
                    gamePanel.updateLives(lives);
                    SoundPlayer.playSound("src/main/resources/wrong.wav");
                    if (lives <= 0) {
                        gameOver();
                    }
                }
                break; // Выходим из цикла, после обработки столкновения
            }
        }
    }


    /**
     * Генерация нового мусора
     */
    private void generateTrash() {
        String[] trashTypes = {"Paper", "Glass", "Plastic", "Metal", "Organic"};
        int randomIndex = (int) (Math.random() * trashTypes.length);
        String type = trashTypes[randomIndex];
        int x = (int) (Math.random() * (800 - 30)); // Генерация случайной позиции x
        Trash newTrash = new Trash(type, x, 0, 30, 30);
        trashes.add(newTrash);
    }

    /**
     * Переход на следующий уровень.
     */
    private void advanceToNextLevel() {
        currentLevelNumber++;
        if (currentLevelNumber <= 5) {
            gamePanel.setLevelNumber(currentLevelNumber); // Обновляем номер уровня в GamePanel
            gamePanel.updateLevel(currentLevelNumber); // Обновляем отображение уровня в GamePanel
            startGame(); // Запускаем новый уровень
            System.out.println("Переход на следующий уровень");
        }
        if (score >= 1000) {
            gamePanel.setGameWon(true);
            stopTimer();


        }
    }

    /**
     * Завершение игры
     */
    private void gameOver() {
        gamePanel.setGameOver(true);
        stopTimer();
    }

    /**
     * Остановка таймера.
     */
    private void stopTimer() {
        timer.stop();
    }

    /**
     * Проверка условия для перехода на следующий уровень.
     *
     * @return true если условия выполнены, иначе false
     */
    private boolean shouldLevelUp() {
        switch (currentLevelNumber) {
            case 1:
                return score >= 10;  // Условие для первого уровня
            case 2:
                return score >= 20;  // Условие для второго уровня
            case 3:
                return score >= 30;  // Условие для третьего уровня
            case 4:
                return score >= 100;  // Условие для четвертого уровня
            default:
                return false;
        }
    }

    /**
     * Получение скорости игры в зависимости от режима.
     *
     * @return скорость игры (в миллисекундах)
     */
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

    /**
     * Получение скорости генерации мусора в зависимости от режима.
     *
     * @return скорость генерации мусора (в миллисекундах)
     */
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

    /**
     * Установка корзин в зависимости от уровня
     */
    private void initializeLevel() {
        bins.clear(); // Очищаем текущие корзины, если они есть

        switch (currentLevelNumber) {
            case 1:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                break;
            case 2:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                break;
            case 3:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                bins.add(new Bin("Plastic", 300, 500, 50, 50));
                break;
            case 4:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                bins.add(new Bin("Plastic", 300, 500, 50, 50));
                bins.add(new Bin("Metal", 400, 500, 50, 50));
                break;
            case 5:
                bins.add(new Bin("Paper", 100, 500, 50, 50));
                bins.add(new Bin("Glass", 200, 500, 50, 50));
                bins.add(new Bin("Plastic", 300, 500, 50, 50));
                bins.add(new Bin("Metal", 400, 500, 50, 50));
                bins.add(new Bin("Organic", 500, 500, 50, 50));
                break;

            default:
                break;
        }
    }

    /**
     * Получение списка корзин.
     *
     * @return список корзин
     */
    public List<Bin> getBins() {
        return bins;
    }

    /**
     * Сброс игры к начальному состоянию.
     */
    private void resetGame() {
        lives = 5;
        score = 0;
        currentLevelNumber = 1;
        gamePanel.reset(lives, score);
        startGame();
    }
}
