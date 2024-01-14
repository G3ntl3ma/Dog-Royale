package Dog.Client.Interfaces;

import Dtos.*;

import java.io.IOException;

public interface IClientObserverGameplay {
    void handleLobbyConfig(ReturnLobbyConfigDto lobbyConfig) throws IOException;

    void handleMoveValid(MoveValidDto moveValid);
    void handleDrawCards(DrawCardsDto drawCards);
    void handleBoardState(BoardStateDto boardStateDto);
    void handleUpdateDrawCards(UpdateDrawCardsDto updateDrawCards);
    void handleUpdateLiveTimer(LiveTimerDto liveTimerDto);
    void handleUpdateTurnTimer(TurnTimerDto turnTimerDto);
    void handleFreeze(FreezeDto freeze);
    void handleUnFreeze(UnfreezeDto unfreeze);
    void handleCancel(CancelDto cancel);
    void handleKick(KickDto kick);
}
