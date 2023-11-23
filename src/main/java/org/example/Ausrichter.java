package src.main.java.org.example;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Ausrichter {
    // how to start server?
    public enum Colors{
        color1,color2,color3,color4,color5,color6
    }
    public enum OrderType{
        fixed, random
    }
    public enum Penalty{
        excludeFromRound, kickFromGame
    }
    private Map<Integer, org.example.Game> games;
    private AtomicInteger gameIdCounter;
    public Ausrichter()
    {
        this.games = new HashMap<>();
        this.gameIdCounter = new AtomicInteger(1); // Games should have IDs to assign players to them
    }

    /**starts the server, TODO
     *
     */
    public void startServer(){
        //MiniServer server = MiniServer(Socket socket,  HashMap<String, ArrayList<String>> messageLog);
        server.run();
    }
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
    public void intitiateGame(int playerCount, int fieldSize, int drawCardFieldCount, int figuresPerPlayer, int initialCardsPerPlayer,
                              int thinkTimePerMove,int visualizationTimePerMove,int maximumGameDuration, penalty penalty, int maximumTotalMoves, Ordertype ordertype)
    {
        int gameId = gameIdCounter.getAndIncrement();

        org.example.Game game = createGameInstance(gameId, figureCount, initialHandCardCount, maxMoves);
        game.OrderType = ordertype;// OrderType should be implemented in Game class
        // Set other game configurations
        // ...

        // Store the game
        games.put(gameId, game);
    }
    /**
     * Assign a player to a specific game.
     *
     * @param playerName player to be assigned // or maybe we should make it a list of players?
     * @param gameId     ID of the game to which the player should be assigned
     * @param color      Color that should be given to the player
     */
    public void assignPlayerToGame(String playerName, int gameId, Colors color) {
        org.example.Game game = games.get(gameId);

        if (game != null) {
            // Assuming you have a method to add a player to the game
            game.players.add(playerName);
        } else {
            System.out.println("Game ID not found: " + gameId);
        }
    }
    public void startGame(int gameId){
        //somehow start game with game.gameId = gameId, with the player playing in startPlayerOrder order, somewhat implemented in org.example.main
        //put in mind that startPlayerOrder is either chosen by the src.main.java.org.example.Ausrichter or he can say just make it random
        org.example.Game gameToStart = games.get(gameId);
        if (gameToStart.ordertype == random){
            Collections.shuffle(gameToStart.players);
        }
        else{
            //TODO
        }
        gameToStart.start();
    }
    public void pauseGame(int gameId){
        org.example.Game gameToPause = games.get(gameId);
        if(gameToPause.status == "running"){
            gameToContinue.status = "paused";
            gameToContinue.pause();
        }
        else{
            System.out.println("Game is not running");
        }
        // this gives me the idea, new variable that implies in what state the game is
    }
    public void continueGame(int gameId){
        org.example.Game gameToContinue = games.get(gameId);
        if(gameToContinue.status == "paused"){
            gameToContinue.status = "running";
            gameToContinue.continue();
        }
        else{
            System.out.println("Game is not paused");
        }
        //continue a  game where game.gameId= gameId and game.state = paused
    }
    public void breakGame(int gameId, boolean ranked){
        org.example.Game gameToBreak = games.get(gameId);
        if (gameToBreak.status != "notStarted"){
            gameToBreak.status = "Stopped";
            gameToBreak.break();
            if(ranked){
                gameToBreak.getWinners();
            }}

        else{
            System.out.println("Game is not started");
        }

        //break a game where game.gameId = gameId, state is not needed
        // "Bei Abbruch des Spiels entscheidet der src.main.java.org.example.Ausrichter über die Wertung des Spiels"? what does that mean?

    }
    public void tournament(){
        //multiple games initiated and added to this tournament
        // this method should be able to assign the winners to the next round Ex. from semi-final to final ...etc
    }

}
