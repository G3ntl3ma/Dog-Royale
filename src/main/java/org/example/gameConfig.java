package src.main.java.org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameConfig {


        public JPanel panel1;
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
        private JButton saveConfigButton;
        private JButton initiateGameButton;
        private JButton loadConficButton;

    public gameConfig() {
        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();

        // Add radio buttons to groups
        group1.add(kickFromGameRB);
        group1.add(kickFromRoundRB);
        group2.add(randomRB);
        group2.add(notRandomRB);

        // Set initial selection
        kickFromGameRB.setSelected(true);
        randomRB.setSelected(true);

        saveConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateAndEnableButtons()){
                    return;
                }
                else{
                    System.out.println("alles gut");// perform a saving dialog TODO
                }
                closeWindow();

            }
        });

        initiateGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateAndEnableButtons()){
                    return;
                }
                else{
                    System.out.println("alles gut");// perform a  game initiation
                }
                closeWindow();
            }
        });


    }

    /**
     *
     * @return false: if the entered data is invalid, true: if the data is valide
     */
    private boolean validateAndEnableButtons() {
        try {
            //gets the values from the text boxes entered by user
            int playerCountValue = Integer.parseInt(playerCount.getText());
            int figureCountValue = Integer.parseInt(figureCount.getText());
            int fieldSizeValue = Integer.parseInt(fieldSize.getText());
            int drawCardCountValue = Integer.parseInt(drawcardCount.getText());
            int initialCardsPerPlayerValue = Integer.parseInt(initialCardsPerPlayer.getText());
            int thinkTimePerMoveValue = Integer.parseInt(thinkTimePerMove.getText());
            int visualTimeValue = Integer.parseInt(visualTime.getText());
            int maxTimeValue = Integer.parseInt(maxTime.getText());
            int maxMovesValue = Integer.parseInt(maxMoves.getText());

            // Perform validation checks
            if (playerCountValue < 2 || playerCountValue > 6) {
                showError("Player count must be between 2 and 6.");
                return false;
            }

            if (figureCountValue < 0) {
                showError("Figure count must be a positive integer.");
                return false;
            }

            if (fieldSizeValue < 0) {
                showError("Field size must be a positive integer.");
                return false;
            }

            if (drawCardCountValue < 0 || drawCardCountValue > fieldSizeValue) {
                showError("Draw card count must be between 0 and field size.");
                return false;
            }

            if (initialCardsPerPlayerValue <= 0 || initialCardsPerPlayerValue > 110 / playerCountValue) {
                showError("Initial cards per player must be between 1 and " + (110 / playerCountValue) + ".");
                return false;
            }

            if (thinkTimePerMoveValue < 0 || visualTimeValue < 0 || maxTimeValue < 0 || maxMovesValue < 0) {
                showError("Time values and maximum moves must be non-negative integers.");
                return false;
            }
            return true;// If all validations pass, return true


        } catch (NumberFormatException ex) {
            showError("Please enter valid numerical values for the configuration.");
            return false;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);

    }
    private void closeWindow() {
        // Find the parent JFrame and close it
        SwingUtilities.getWindowAncestor(panel1).dispose();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("gameConfig");
        frame.setContentPane(new gameConfig().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}