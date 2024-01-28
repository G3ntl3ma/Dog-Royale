package Dog.Client;

import Dtos.*;
import Enums.Card;
import javafx.fxml.FXML;

/**
 * This class is responsible for sending messages to the server.
 *
 * @author tabbi
 */
public class MessageSender {
    @FXML
    Client client;

    /**
     * message used to notify the server or other players that the current player intends to leave the game
     */
    public void sendLeavePlayer() {
        client.sendMessage(new LeavePlayerDto().toJson());
    }

    /**
     * message used to notify the server the player's attempt to join a specific game
     *
     * @param gameId  the id of the game the player wants to join
     * @param clientId the id of the client
     * @param playerName the name of the player
     */
    public void sendJoinGameAsPlayer(int gameId,int clientId,String playerName){
        client.sendMessage(new JoinGameAsPlayerDto(gameId, clientId, playerName).toJson());
    }

    /**
     * message used to notify the server the player's attempt to register for a specific tournament
     *
     * @param tournamentId the id of the tournament the player wants to register for
     * @param clientId the id of the client
     */
    public void sendRegisterForTournament(int tournamentId, int clientId){
        client.sendMessage(new RegisterForTournamentDto(tournamentId, clientId).toJson());
    }

    /**
     * message used to notify the server the player's move
     *
     * @param skip A boolean representing whether the player wants to skip their turn
     * @param card The card the player wants to play
     * @param selectedValue The value the player wants to play
     * @param pieceId The id of the piece the player wants to move
     * @param isStarter A boolean representing whether the player wants to play a starter card
     * @param opponentPieceId The id of the piece the player interact with
     */
    public void sendMove (boolean skip, Card card, int selectedValue, int pieceId, boolean isStarter, int opponentPieceId){
        client.sendMessage(new MoveDto(skip, card.ordinal(), selectedValue, pieceId, isStarter, opponentPieceId).toJson());
    }

}
