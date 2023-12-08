package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Cancels
 *
 * @author kellerb
 */
@Data
public class Cancel extends AbstractMessage {
    //private List<WinnerOrder> winnerOrder;

    @Data
    public static class Winnerorder{
        private int Order;
    }
}
