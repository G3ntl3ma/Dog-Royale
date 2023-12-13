package com.example.DogRoyalClient.handler.messageHandler.menu;

import com.example.DogRoyalClient.controller.ClientController;
import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.menu.ConnectedToServer;
import com.example.DogRoyalClient.messages.menu.FindTournament;
import com.example.DogRoyalClient.messages.menu.TypeMenu;
import lombok.Data;

@Data
public class ConnectedToServerHandler extends Handler implements MenuMessageHandler<ConnectedToServer>  {
    /**
     *
     * @param message: message from server that contains clientId
     *sets ServerHandler.clientId and sends findTournament request to the server
     */

    @Override
    public String handle(ConnectedToServer message) throws HandlingException {
        try{
            ClientController.setClientID(message.getClientId());
            FindTournament findTournament = new FindTournament();
            findTournament.setType(TypeMenu.requestTournamentInfo.getOrdinal());
            findTournament.setClientId(ClientController.getClientID());
            findTournament.setTournamentStarting(10);//gets only 10 tournaments
            findTournament.setTournamentInProgress(10);
            findTournament.setTournamentFinished(10);
            return gson.toJson(findTournament);

        } catch (Exception e) {
        throw new HandlingException("Exception while handling connectedToServer",
                e, TypeMenu.connectedToServer.getOrdinal());
    }

}

}
