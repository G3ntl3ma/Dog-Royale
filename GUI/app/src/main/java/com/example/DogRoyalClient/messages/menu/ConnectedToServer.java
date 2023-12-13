package com.example.DogRoyalClient.messages.menu;


import lombok.Data;

/**
 * Server bestätigt erfolgreiche Anmeldung
 *
 * @author kellerb
 */
@Data
public class ConnectedToServer extends AbstractMenuMessage {
    private int clientId;
}
