package main.java.org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



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
        updateTable2();
        addGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGameConfigWindow();

            }
        });
        //HashMap<Integer, ArrayList<Integer>> playerCountPerGame = new HashMap<>();
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


//                    // Check if the game ID exists in the playerCountPerGame map
//                    if (!playerCountPerGame.containsKey(id1)) {
//                        // If the game ID doesn't exist:
//                        // Create a new list to store player IDs and add the current player ID (id2) to it
//                        //ArrayList<Integer> newList = new ArrayList<>();
//                        //newList.add(id2);
//                        // Add the new list to the playerCountPerGame map under the game ID key
//                        playerCountPerGame.put(id1, new ArrayList<>());
//                    } else {
//                        // If the game ID exists:
//                        // Retrieve the list of player IDs associated with the game ID
//                        ArrayList<Integer> players = playerCountPerGame.get(id1);
//                        // Add the current player ID (id2) to the existing list of player IDs
//                        players.add(id2);
//                        // Update the playerCountPerGame map with the modified list of player IDs
//                        playerCountPerGame.put(id1, players);
//                    }


                    // Assign a player to a specific game
                    Ausrichter.assignPlayerToGame(id2,id1, Ausrichter.Colors.color1);

                    // Retrieve the game and player based on their IDs
                    Game game = Ausrichter.games.get(id1);
                    Player player = Ausrichter.playersConnected.get(id2);

                    // Check if both the game and player exist, and if the player is assigned to the game
                    if (game != null && player != null && game.players.contains(player)) {
                        // If the conditions are met, remove the row from table2
                        DefaultTableModel model = (DefaultTableModel) table2.getModel();
                        model.removeRow(selectedRow2);
                    } else {
                        // If the conditions are not met, print an empty statement
                        System.out.print("");
                    }

                } else {
                    // If items are not selected from both tables, prompt the user
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
        Map<Integer, Game> gamesMap = Ausrichter.games;

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
        // Retrieve the list of connected players from Ausrichter
        ArrayList<Player> connectedPlayers = Ausrichter.playersConnected;

        // (just for test) Generate sample Player objects and add them to connectedPlayers (IDs from 0 to 14)
        for (int i=0; i< 15; i++){
            Player p = new Player(i, 1, 1);
            connectedPlayers.add(p);
        }

        // Initialize rowData to hold player IDs for display in table2
        Object[][] rowData = new Object[connectedPlayers.size()][1];

        // Populate rowData with player IDs from connectedPlayers
        for (int i = 0; i < connectedPlayers.size(); i++) {
            rowData[i][0] = connectedPlayers.get(i).id; // Retrieve the ID of each player and add it to rowData
        }

        // Column names
        String[] columnNames = {"ID"};

        // Create a new DefaultTableModel with rowData and columnNames
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);

        // Set the created model to table2 for displaying player IDs
        table2.setModel(model);



        // Refresh and repaint the table or its container
        table2.revalidate();
        table2.repaint();
    }
}
