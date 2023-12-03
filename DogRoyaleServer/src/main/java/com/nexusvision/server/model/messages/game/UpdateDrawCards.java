package com.nexusvision.server.model.messages.game;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Kartenziehen synchronisieren
 *
 * @author kellerb
 */
@Data
@Builder
public class UpdateDrawCards extends AbstractGameMessage{
    private List<HandCard> handCards;

    @Data
    @Builder
    public static class HandCard{
        private int clientId;
        private int count;
    }
}
