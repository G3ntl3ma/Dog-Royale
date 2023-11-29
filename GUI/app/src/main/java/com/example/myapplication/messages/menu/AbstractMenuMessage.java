package com.example.myapplication.messages.menu;

import lombok.Data;

/**
 * @author felixwr
 */
@Data
public abstract class AbstractMenuMessage {
    public enum TypeMenue {
        connectToServer,
        connectedToServer,
        disconnect,
        requestGameList,
        returnGameList,
        joinGameAsParticipant,
        joinGameAsObserver,
        connectedToGame,
        findTournament,
        returnFindTournament,
        registerForTournament,
        registeredForTournament,
        requestTournamentInfo,
        returnTournamentInfo,
        returnLobbyConfig,
        error ,
        requestTechData ,
        returnTechData
    }

    protected TypeMenue type;
}
