package Dog.Client;

import Dtos.*;
import Enums.Card;
import javafx.fxml.FXML;

public class MessageSender {
    @FXML
    Client client;

    public void sendLeavePlayer() {
        client.sendMessage(new LeavePlayerDto().toJson());
    }
    public void sendJoinGameAsPlayer(int gameId,int clientId,String playerName){
        client.sendMessage(new JoinGameAsPlayerDto(gameId, clientId, playerName).toJson());
    }
    public void sendRegisterForTournament(int tournamentId, int clientId){
        client.sendMessage(new RegisterForTournamentDto(tournamentId, clientId).toJson());
    }
    public void sendMove (boolean skip, Card card, int selectedValue, int pieceId, boolean isStarter, int opponentPieceId){
        client.sendMessage(new MoveDto(skip, card.ordinal(), selectedValue, pieceId, isStarter, opponentPieceId).toJson());
    }

}
