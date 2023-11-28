package com.nexusvision.messages.menu;

import lombok.Data;

@Data
public class ConnectToServer extends AbstractMenuMessage {
    private String name;
    private boolean isObserver;
}