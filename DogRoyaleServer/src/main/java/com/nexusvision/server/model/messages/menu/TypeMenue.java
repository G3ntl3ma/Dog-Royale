package com.nexusvision.server.model.messages.menu;

/**
 * Enum that specifies the state in the menu stage
 *
 * @author felixwr
 */
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
    returnTechData;

    /**
     * Maps the correct ordinal value by taking care of the shift
     *
     * @return The correct ordinal value
     */
    public int getOrdinal() {
        return ordinal() + 100;
    }

    /**
     * Maps the correct type by taking care of the shift
     *
     * @param ordinal The ordinal that will be mapped to it's type
     * @return The correct type
     */
    public static TypeMenue getType(int ordinal) {
        return values()[ordinal - 100];
    }
}
