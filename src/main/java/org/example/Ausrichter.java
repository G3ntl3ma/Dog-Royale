package main.java.org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Ausrichter {
    // how to start server?
    public enum Colors{
        color1,color2,color3,color4,color5,color6
    }
//    public enum OrderType{
//        fixed, random
//    }
//    public enum Penalty{
//        excludeFromRound, kickFromGame
//    }
    static Map<Integer, Game> games = new HashMap<>();
    private static final AtomicInteger gameIdCounter = new AtomicInteger(1);
    static ArrayList<Player> playersConnected = new ArrayList<>();
    public Ausrichter()
    {
        //games = new HashMap<>();
        //gameIdCounter = new AtomicInteger(1); // Games should have IDs to assign players to them
    }

    /**starts the server, TODO
     *
     */
    //public void startServer(){
        //MiniServer server = MiniServer(Socket socket,  HashMap<String, ArrayList<String>> messageLog);
        //server.run();
   // }
    /**this Method Initiates Games with the configuration the Ausrichter wants
     *
     * @param playerCount how many player can play in this game
     * @param fieldSize how many fields should be in this game
     * @param drawCardFieldCount how many draw card fields should be
     * @param figuresPerPlayer for each player
     * @param  initialCardsPerPlayer many cards should be given to each player every round
     * @param thinkTimePerMove for every move
     * @param visualizationTimePerMove the time taken to visualise the move
     * @param maximumGameDuration max game Duration
     * @param penalty two options: Exclude from round or kick from game // actually it is an Enum
     * @param maximumTotalMoves for the whole game
     * @param ordertype Fixed or random
     */
    public static void initiateGame(int playerCount, int fieldSize, int drawCardFieldCount, int figuresPerPlayer, int initialCardsPerPlayer,
                              int thinkTimePerMove,int visualizationTimePerMove,int maximumGameDuration, Game.Penalty penalty, int maximumTotalMoves, Game.OrderType ordertype)
    {
        int gameId = gameIdCounter.getAndIncrement();
        GameBuilder gameBuilder = new GameBuilder();

        Game myGame = gameBuilder
                .setId(gameId)
                .setPlayerCount(playerCount)
                .setMainFieldCount(fieldSize)
                .setDrawCardFieldCount(drawCardFieldCount)
                .setFiguresPerPlayer(figuresPerPlayer)
                .setInitialCardsPerPlayer(initialCardsPerPlayer)
                .setThinkTimePerMove(thinkTimePerMove)
                .setVisualizationTimePerMove(visualizationTimePerMove)
                .setMaximumGameDuration(maximumGameDuration)
                .setPenalty(penalty)
                .setMaximumTotalMoves(maximumTotalMoves)
                .setOrderType(ordertype)
                .build();
        games.put(gameId, myGame);
        System.out.println(myGame);
        System.out.println(games);
    }



    /**
     * Assign a player to a specific game.
     *
     * @param playerId player to be assigned // or maybe we should make it a list of players?
     * @param gameId     ID of the game to which the player should be assigned
     * @param color      Color that should be given to the player
     */

    public static void assignPlayerToGame(int playerId, int gameId, Colors color) {
        Game game = games.get(gameId);

        // Check if the player list contains enough players
        if (playerId != -1  && game != null) {
            Player player = playersConnected.get(playerId);
            int nbPlayersInGame =game.players.size();

            int maxPlayerCount = game.getPlayerCount();

            // Check if the game has room for the player and if the player is already in the game
            if (nbPlayersInGame < maxPlayerCount && !game.players.contains(playerId)) {
                game.players.add(player);
                player.color = color.ordinal();
                System.out.println("Player " + playerId + " added to the game " + gameId + ".");
            } else {
                System.out.println("The maximum number of players has already been reached.");
            }
        } else {
            System.out.println("Game or player not found.");
        }
    }

}





//    public void startGame(int gameId){
//        //somehow start game with game.gameId = gameId, with the player playing in startPlayerOrder order, somewhat implemented in org.example.main
//        //put in mind that startPlayerOrder is either chosen by the src.main.java.org.example.Ausrichter or he can say just make it random
//        org.example.Game gameToStart = games.get(gameId);
//        if (gameToStart.ordertype == random){
//            Collections.shuffle(gameToStart.players);
//        }
//        else{
//            //TODO
//        }
//        gameToStart.start();
//    }
//    public void pauseGame(int gameId){
//        org.example.Game gameToPause = games.get(gameId);
//        if(gameToPause.status == "running"){
//            gameToContinue.status = "paused";
//            gameToContinue.pause();
//        }
//        else{
//            System.out.println("Game is not running");
//        }
//        // this gives me the idea, new variable that implies in what state the game is
//    }
//    public void continueGame(int gameId){
//        org.example.Game gameToContinue = games.get(gameId);
//        if(gameToContinue.status == "paused"){
//            gameToContinue.status = "running";
//            //gameToContinue.continue();
//        }
//        else{
//            System.out.println("Game is not paused");
//        }
//        //continue a  game where game.gameId= gameId and game.state = paused
//    }
//    public void breakGame(int gameId, boolean ranked){
//        org.example.Game gameToBreak = games.get(gameId);
//        if (gameToBreak.status != "notStarted"){
//            gameToBreak.status = "Stopped";
//            //gameToBreak.break();
//            if(ranked){
//                gameToBreak.getWinners();
//            }}
//
//        else{
//            System.out.println("Game is not started");
//        }

        //break a game where game.gameId = gameId, state is not needed
        // "Bei Abbruch des Spiels entscheidet der src.main.java.org.example.Ausrichter über die Wertung des Spiels"? what does that mean?

        //}
//    public void tournament(){
//        //multiple games initiated and added to this tournament
//        // this method should be able to assign the winners to the next round Ex. from semi-final to final ...etc
//    }

//}
