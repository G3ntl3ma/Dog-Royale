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

        public HandCard(int clientId, int count) {
            this.clientId = clientId;
            this.count = count;
        }

        public int getClientId() {
            return clientId;
        }

        public int getCount() {
            return count;
        }

    }
    public List<HandCard> getHandCards() {
        return handCards;
    }

}
