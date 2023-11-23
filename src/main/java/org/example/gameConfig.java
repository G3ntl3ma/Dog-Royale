package src.main.java.org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameConfig {
    private JButton startGameButton;
    private JPanel panel1;
    private JTextField playerCount;
    private JTextField figureCount;
    private JTextField fieldSize;
    private JTextField drawcardCount;
    private JTextField initialCardsPerPlayer;
    private JTextField thinkTimePerMove;
    private JTextField visualTime;
    private JTextField maxTime;
    private JRadioButton kickFromGameRB;
    private JRadioButton kickFromRoundRB;
    private JTextField maxMoves;
    private JRadioButton randomRB;
    private JRadioButton notRandomRB;

    public gameConfig() {
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// reads the entered file and initiates a game with that config

            }
        });
    }
}
