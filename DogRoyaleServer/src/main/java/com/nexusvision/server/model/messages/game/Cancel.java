package com.nexusvision.server.model.messages.game;

import lombok.Data;

/**
 * Cancels
 *
 * @author kellerb
 */
@Data
public class Cancel extends AbstractGameMessage{
    //private List<WinnerOrder> winnerOrder;

    @Data
    public static class Winnerorder{
        private int Order;
    }
}
