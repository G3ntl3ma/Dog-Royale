package com.example.DogRoyalClient.handler.messageHandler.menu;

import com.example.DogRoyalClient.controller.ClientController;
import com.example.DogRoyalClient.handler.Handler;
import com.example.DogRoyalClient.handler.HandlingException;
import com.example.DogRoyalClient.messages.menu.ReturnFindTournament;
import com.example.DogRoyalClient.messages.menu.TypeMenu;
import com.example.DogRoyalClient.messages.menu.RequestGameList;

import lombok.Data;

@Data
public class ReturnFindTournamentHandler extends Handler implements MenuMessageHandler<ReturnFindTournament> {
    /**
     *
     * @param message The deserialized json string that's getting processed
     * @return sends RequestGameList request
     * @throws HandlingException
     */
    @Override
    public String handle(ReturnFindTournament message) throws HandlingException {
        try{
            //TODO save the Tournaments to use them later
            //remains unhandled, just sends RequestGameList request
            RequestGameList requestGameList = new RequestGameList();
            requestGameList.setType(TypeMenu.requestGameList.getOrdinal());
            requestGameList.setClientID(ClientController.getClientID());
            requestGameList.setGameCountStarting(10);//10 games To show
            requestGameList.setGameCountInProgress(10);
            requestGameList.setGameCountFinished(10);
            return gson.toJson(requestGameList);

        }catch (Exception e){
            throw new HandlingException("Exception while handling ReturnFindTournament",
                    e,TypeMenu.returnFindTournament.getOrdinal());
        }

    };
}
