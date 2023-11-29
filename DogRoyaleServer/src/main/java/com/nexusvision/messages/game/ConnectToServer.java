package com.nexusvision.messages.game;


import lombok.Data;

/**
 *  Client meldet sich mit Namen an
 *
 * @author kellerb
 */
@Data
public class ConnectToServer extends AbstractGameMessage {
    private String name;
    private boolean isObserver;
}
