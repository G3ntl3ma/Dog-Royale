package com.nexusvision.server.model.messages.game;


import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Kartenziehen
 *
 * @author kellerb
 */
@Data
@Builder
public class DrawCards extends AbstractGameMessage {
    private List<Card> droppedCards;
    private List<Card> drawnCards;
}
