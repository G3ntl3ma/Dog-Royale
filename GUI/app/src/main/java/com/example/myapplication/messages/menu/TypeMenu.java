package GUI.app.src.main.java.com.example.myapplication.messages.menu;

public enum TypeMenu {
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
    public static TypeMenu getType(int ordinal) {
        return values()[ordinal - 100];
    }
}

