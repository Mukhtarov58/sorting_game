package org.example;

import org.example.game.Game;
import org.example.game.GameMode;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameMode mode = selectGameMode();
            if (mode != null) {
                Game game = new Game(mode);
                game.setVisible(true);
            } else {
                System.exit(0); // Выход из приложения, если пользователь отменил выбор
            }
        });
    }

    private static GameMode selectGameMode() {
        Object[] options = {"Easy", "Middle", "Hard"};
        int choice = JOptionPane.showOptionDialog(null, "Select Game Difficulty", "Game Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                return GameMode.EASY;
            case 1:
                return GameMode.MIDDLE;
            case 2:
                return GameMode.HARD;
            default:
                return null; // Если пользователь отменил выбор или закрыл диалог
        }
    }
}
