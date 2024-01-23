package Dog.Client.Interfaces;

import Dtos.*;

import java.io.IOException;

public interface IClientObserverMenu {

    void handleGameListUpdate(ReturnGameListDto gameList);
    void handleReturnFindTournament(ReturnTournamentListDto findTournament);
    void handleReturnTournamentInfo(ReturnTournamentInfoDto tournamentInfo) throws IOException;
}
