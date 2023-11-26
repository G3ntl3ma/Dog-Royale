package src.main.java.org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static src.main.java.org.example.Ausrichter.assignPlayerToGame;
import static src.main.java.org.example.Ausrichter.playersConnected;

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

        assignPlayerToGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// not working TODO
                // Get selected items from table1
                int selectedRow1 = table1.getSelectedRow();
                Object selectedItem1 = selectedRow1 != -1 ? table1.getValueAt(selectedRow1, 0) : null;
                int id1 = selectedItem1 instanceof Integer ? (int) selectedItem1 : -1;

                // Get selected items from table2
                int selectedRow2 = table2.getSelectedRow();
                Object selectedItem2 = selectedRow2 != -1 ? table2.getValueAt(selectedRow2, 0) : null;
                int id2 = selectedItem2 instanceof Integer ? (int) selectedItem2 : -1;

                // Perform the desired action with the caught IDs
                if (id1 != -1 && id2 != -1) {
                    assignPlayerToGame(id2,id1, Ausrichter.Colors.color1);


                } else {
                    System.out.println("Please select items from both tables.");
                }

            }
        });
    }

    /**
     * method for opening gameConfig window that lets you config and initiate a new game
     */
    private void openGameConfigWindow() {
        // Create and show the gameConfig window
        JFrame gameConfigFrame = new JFrame("Game Configuration");
        gameConfigFrame.setContentPane(new gameConfig().panel1);
        gameConfigFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameConfigFrame.pack();
        gameConfigFrame.setVisible(true);

        // After the gameConfig window is closed, update the table with the latest data
        gameConfigFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                updateTable1();
                updateTable2();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lobby");
        frame.setContentPane(new lobby().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private void updateTable1() {
        Map<Integer, org.example.Game> gamesMap = Ausrichter.games;

        Object[][] rowData = gamesMap.values().stream()
                .map(game -> new Object[]{
                        game.getId(),
                        game.getPlayerCount(),
                        game.getPenalty(),
                        game.getPlayers(),
                        game.getOrderType()
                })
                .toArray(Object[][]::new);

        // Column names
        String[] columnNames = {"ID", "Player Count", "Penalty","Players", "Order Type"};

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        table1.setModel(model);



        // Refresh and repaint the table or its container
        table1.revalidate();
        table1.repaint();
    }
    private void updateTable2() {
        ArrayList<org.example.Player> connectedPlayers = playersConnected;
        //test Array of IDs
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);

        Object[][] rowData = new Object[ids.size()][1];
        for (int i = 0; i < ids.size(); i++) {
            rowData[i][0] = ids.get(i);
        }

        // Column names
        String[] columnNames = {"ID"};

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        table2.setModel(model);



        // Refresh and repaint the table or its container
        table2.revalidate();
        table2.repaint();
    }
}
