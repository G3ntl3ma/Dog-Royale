package Dog.Client.Interfaces;

import Dtos.DrawCardsDto;
import Dtos.MoveDto;
import Dtos.ReturnGameListDto;
import Dtos.ReturnLobbyConfigDto;

import java.io.IOException;

public interface IClientObserverMenu {

    void handleGameListUpdate(ReturnGameListDto runningGames);
}
