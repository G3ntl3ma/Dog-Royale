package com.nexusvision.server.model.messages.game;


import lombok.Data;

import java.util.List;

/**
 * Draw cards
 *
 * @author kellerb
 */
@Data
public class DrawCards extends AbstractGameMessage {
    private List<Card> droppedCards;
    private List<Card> drawnCards;
}
