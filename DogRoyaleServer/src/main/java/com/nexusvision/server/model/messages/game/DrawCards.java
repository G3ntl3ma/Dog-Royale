package com.nexusvision.server.model.messages.game;
import com.nexusvision.server.model.enums.CardType;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 * Draw cards
 *
 * @author kellerb
 */
@Data
public class DrawCards extends AbstractMessage {
    private List<CardType> droppedCards;
    private List<CardType > drawnCards;
}
