package src.main.java.org.example;

import org.example.Game;

import java.util.ArrayList;

public class Ausrichter {
    // how to start server?
    private Map<Integer, org.example.Game> games;
    private AtomicInteger gameIdCounter;
    public Ausrichter()
    {
        this.games = new HashMap<>();
        this.gameIdCounter = new AtomicInteger(1); // Games should have IDs to assign players to them
    }
    /**this Method Initiates Games with the configuration the Ausrichter wants
     *
     * @param playerCount how many player can play in this game
     * @param fieldCount how many fields should be in this game
     * @param drawCardFieldCount how many draw card fields should be
     * @param startPlayerOrder  what player should start in the first round, the second... // should be made into a method since the game is first initiated without players// actually should be mentioned in method start game
     * @param figureCount for each player
     * @param initialHandCardCount how many cards should be given to each player every round
     * @param thinkingTime for every move
     * @param visualsTime the time taken to visualise the move
     * @param maxTime for the whole game
     * @param illigalMoveConsequence two options: kick the player for the round, or for the whole game
     * @param maxMoves for the whole game
     */
    public void intitiateGame(int playerCount, int fieldCount, int drawCardFieldCount, int figureCount, int initialHandCardCount,
                              int thinkingTime,int visualsTime,int maxTime, String illigalMoveConsequence, int maxMoves)
    {
        int gameId = gameIdCounter.getAndIncrement();
        org.example.Game game = createGameInstance(gameId, figureCount, initialHandCardCount, maxMoves);
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
     */
    public void assignPlayerToGame(String playerName, int gameId) {
        org.example.Game game = games.get(gameId);

        if (game != null) {
            // Assuming you have a method to add a player to the game
            game.players.add(playerName);
        } else {
            System.out.println("Game ID not found: " + gameId);
        }
    }
    public void startGame(int gameId, ArrayList<org.example.Player> startPlayerOrder){
        //somehow start game with game.gameId = gameId, with the player playing in startPlayerOrder order, somewhat implemented in org.example.main
        //put in mind that startPlayerOrder is either chosen by the src.main.java.org.example.Ausrichter or he can say just make it random
    }
    public void pauseGame(int gameId){
        //pause game where game.gameId= gameId
        // this gives me the idea, new variable that implies in what state the game is
    }
    public void continueGame(int gameId){
        //continue a  game where game.gameId= gameId and game.state = paused
    }
    public void breakGame(int gameId){
        //break a game where game.gameId = gameId, state is not needed
        // "Bei Abbruch des Spiels entscheidet der src.main.java.org.example.Ausrichter über die Wertung des Spiels"? what does that mean?

    }
    public void tournament(){
        //multiple games initiated and added to this tournament
        // this method should be able to assign the winners to the next round Ex. from semi-final to final ...etc
    }

}
