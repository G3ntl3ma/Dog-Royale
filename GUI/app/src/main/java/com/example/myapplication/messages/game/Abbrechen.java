package com.example.myapplication.messages.game;

import lombok.Data;
import java.util.List;

/**
 * Abbrechen
 * @author kellerb
 */
@Data
public class Abbrechen extends AbstractGameMessage{
    //private List<WinnerOrder> winnerOrder;

    @Data
    public static class Winnerorder{
        private int Order;
    }
}
