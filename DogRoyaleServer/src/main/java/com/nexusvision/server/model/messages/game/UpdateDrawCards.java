package com.nexusvision.server.model.messages.game;

import lombok.Data;

import java.util.List;

/**
 * drawcards gets updates
 *
 * @author kellerb
 */
@Data
public class UpdateDrawCards extends AbstractGameMessage{
    private List<HandCard> handCards;

    @Data
    public static class HandCard{
        private int clientId;
        private int count;
    }
}
