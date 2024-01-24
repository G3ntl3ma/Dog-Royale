package Dog.Client.Interfaces;

import Dtos.*;

import java.io.IOException;

public interface IClientObservable {

    // administer observer
    void registerObserverMenu(IClientObserverMenu observer);
    void registerObserverGameplay(IClientObserverGameplay observer);
    void removeObserverMenu(IClientObserverMenu observer);
    void removeObserverGameplay(IClientObserverGameplay observer);

    // update menu
    void updateGameList(ReturnGameListDto dto);
    void updateFindTournament(ReturnTournamentListDto findTournament);
    void updateTournamentInfo(ReturnTournamentInfoDto tournamentInfo);
    void updateError(ErrorDto error);
    void updateTechData(ReturnTechDataDto techData);


    // update gameplay
    void updateGameConfig(ReturnLobbyConfigDto lobbyConfig) throws IOException;
    void updateMoveValid(MoveValidDto move);
    void updateDrawCards(DrawCardsDto drawCards);
    void updateBoardState(BoardStateDto boardState);
    void updateUpdateDrawCards(UpdateDrawCardsDto updateDrawCards);
    void updateFreeze(FreezeDto freeze);
    void updateUnfreeze(UnfreezeDto unfreeze);
    void updateCancel(CancelDto cancel);
    void updateKick(KickDto kick);

    // update sync
    void updateLiveTimer(LiveTimerDto liveTimer);
    void updateTurnTimer(TurnTimerDto turnTimerDto);

}
