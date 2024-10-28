import game.GameManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameManager gameManager = GameManager.getInstance();
            gameManager.startWindow();
        });
    }
}