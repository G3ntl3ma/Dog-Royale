package com.example.myapplication.messages.game;

import lombok.Data;

import java.util.List;

/**
 * Kartenziehen synchronisieren
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
