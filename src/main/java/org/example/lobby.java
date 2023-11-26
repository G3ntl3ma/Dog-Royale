package src.main.java.org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lobby {
    private JTable table1;
    private JPanel panel1;
    private JTable table2;
    private JButton addGameButton;
    private JButton startGameButton;
    private JButton button3;
    private JButton button4;
    private JButton assignPlayerToGameButton;

    public lobby() {
        addGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGameConfigWindow();
            }
        });

        // Other initialization and logic for the lobby class
    }

    /**
     * method for opening gameConfig window that lets you config and initiate a new game
     */
    private void openGameConfigWindow() {
        // Create and show the gameConfig window
        JFrame gameConfigFrame = new JFrame("Game Configuration");
        gameConfigFrame.setContentPane(new gameConfig().panel1);
        gameConfigFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame when the close button is clicked
        gameConfigFrame.pack();
        gameConfigFrame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lobby");
        frame.setContentPane(new lobby().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
