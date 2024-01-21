package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.utils.*;
import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 * Returning the lobby configuration
 *
 * @author felixwr
 */
@Data
public class ReturnLobbyConfig extends AbstractMessage {

    private Integer playerCount;
    private Integer fieldsize; // named like this in the interface document
    private Integer figuresPerPlayer;
    private List<ColorMapping> Colors; // named like this in the interface document
    private DrawCardFields drawCardFields;
    private StartFields startFields;
    private Integer initialCardsPerPlayer;
    private PlayerOrder playerOrder;
    private List<WinnerOrderElement> winnerOrder;
    private List<ObserverElement> observer;
    private Integer thinkTimePerMove;
    private Integer visualizationTimePerMove;
    private Integer consequencesForInvalidMove;
    private Integer maximumGameDuration;
    private Integer maximumTotalMoves;
}
