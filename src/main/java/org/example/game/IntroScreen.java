package org.example.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroScreen extends JFrame {
    private GameMode selectedMode;

    public IntroScreen() {
        initUI();
    }

    private void initUI() {
        setTitle("Sorting Game");

        // Установка иконки
        ImageIcon icon = new ImageIcon("src/main/resources/GSort.ico");
        setIconImage(icon.getImage());

        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Установка фонового изображения
        ImageIcon backgroundImageIcon = new ImageIcon("src/main/resources/IntroScreen.png");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        // Создание панели с кнопками выбора уровня сложности
        JPanel panel = new JPanel();
        panel.setOpaque(false); // Сделать панель прозрачной
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Стилизация кнопок
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        // Кнопка "Easy"
        JButton easyButton = new JButton("Easy");
        easyButton.setFont(buttonFont);
        easyButton.setBackground(Color.GREEN);
        easyButton.setForeground(Color.WHITE);
        easyButton.setFocusPainted(false); // Убираем рамку фокуса
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMode = GameMode.EASY;
                startGame();
            }
        });
        panel.add(Box.createVerticalStrut(20)); // Отступы между кнопками
        panel.add(easyButton);

        // Кнопка "Middle"
        JButton middleButton = new JButton("Middle");
        middleButton.setFont(buttonFont);
        middleButton.setBackground(Color.YELLOW);
        middleButton.setForeground(Color.BLACK);
        middleButton.setFocusPainted(false);
        middleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMode = GameMode.MIDDLE;
                startGame();
            }
        });
        panel.add(Box.createVerticalStrut(20));
        panel.add(middleButton);

        // Кнопка "Hard"
        JButton hardButton = new JButton("Hard");
        hardButton.setFont(buttonFont);
        hardButton.setBackground(Color.RED);
        hardButton.setForeground(Color.WHITE);
        hardButton.setFocusPainted(false);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMode = GameMode.HARD;
                startGame();
            }
        });
        panel.add(Box.createVerticalStrut(20));
        panel.add(hardButton);
        add(panel, BorderLayout.CENTER);
    }

    private void startGame() {
        // Закрытие заставки и запуск основного игрового окна
        setVisible(false);
        dispose(); // Освобождение ресурсов заставки
        EventQueue.invokeLater(() -> {
            Game game = new Game(selectedMode);
            System.out.println("Начало игры");
            game.setVisible(true);
        });
    }
}
