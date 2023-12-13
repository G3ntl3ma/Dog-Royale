package com.example.DogRoyalClient.messages.menu;

import lombok.Data;

/**
 * Beitritt des Spiels als Beobachter
 *
 * @author kellerb
 */
@Data
public class JoinGameAsObserver extends AbstractMenuMessage {
    private int gameId;
    private int clientId;
}
