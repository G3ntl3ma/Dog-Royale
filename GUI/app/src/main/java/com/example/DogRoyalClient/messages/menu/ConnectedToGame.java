package com.example.DogRoyalClient.messages.menu;

import lombok.Data;

/**
 * Server sendet Bestätigung bei erfolgreicher Verbindung
 *
 * @author kellerb
 */
@Data
public class ConnectedToGame extends AbstractMenuMessage {
    private boolean success;


}
