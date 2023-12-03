package com.nexusvision.server.model.messages.game;

import lombok.Builder;
import lombok.Data;

/**
 * Abbrechen
 * @author kellerb
 */
@Data
@Builder
public class Cancel extends AbstractGameMessage{
    //private List<WinnerOrder> winnerOrder;

    @Data
    @Builder
    public static class Winnerorder{
        private int Order;
    }
}
