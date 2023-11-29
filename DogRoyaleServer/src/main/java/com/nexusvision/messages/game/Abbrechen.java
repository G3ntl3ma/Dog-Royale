package com.nexusvision.messages.game;

import lombok.Data;

/**
 * Abbrechen
 * @author kellerb
 */
@Data
public class Abbrechen extends AbstractGameMessage{
    private List<WinnerOrder> winnerOrder;

    @Data
    public static class Winnerorder{
        private int Order;
    }
}
