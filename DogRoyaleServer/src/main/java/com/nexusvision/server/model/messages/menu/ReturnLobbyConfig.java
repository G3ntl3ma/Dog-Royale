package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.utils.*;
import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Returning the lobby configuration
 *
 * @author felixwr
 */
@Data
public class ReturnLobbyConfig extends AbstractMessage {

    private String gameName;
    private int maxPlayerCount;
    private int fieldsize;
    private int figuresPerPlayer;
    private List<ColorMapping> Colors;
    private DrawCardFields drawCardFields;
    private StartFields startFields;
    private int initialCardsPerPlayer;
    private PlayerOrder playerOrder;
    private List<WinnerOrderElement> winnerOrder;
    private List<ObserverElement> observer;
    private int thinkTimePerMove;
    private int visualizationTimePerMove;
    private int consequencesForInvalidMove;
    private int maximumGameDuration;
    private int maximumTotalMoves;
}
