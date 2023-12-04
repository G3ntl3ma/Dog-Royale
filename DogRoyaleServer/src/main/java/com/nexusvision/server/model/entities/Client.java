package com.nexusvision.server.model.entities;

import lombok.Data;

@Data
public class Client {
    private int gameID;
    private String name;
    private String playerName;
    private boolean isObserver;
}
